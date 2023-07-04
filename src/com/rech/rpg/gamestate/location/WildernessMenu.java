package com.rech.rpg.gamestate.location;

import com.rech.rpg.Main;
import com.rech.rpg.Menu;
import com.rech.rpg.entity.Enemy;
import com.rech.rpg.entity.Entity;
import com.rech.rpg.gamestate.Combat;
import com.rech.rpg.gamestate.GameState;
import com.rech.rpg.gamestate.MainMenu;
import com.rech.rpg.map.location.Location;
import com.rech.rpg.map.location.Wilderness;

public class WildernessMenu implements GameState {
    private Wilderness wn;

    Menu lcMenu;

    @Override
    public void enter(Main RPGS) {
        // load current location and cast it to a wild
        try {
            wn = (Wilderness) RPGS.getCurrentLocation();
        } catch (Exception e) {   //This gamestate was entered when the current game location was not a town. This shouldn't happen if the gamestate is entered by using each location's getGameState method
            e.printStackTrace();
            System.err.println("Wilderness menu was entered while current location is a " + RPGS.getCurrentLocation().getClass());
            RPGS.enterGameState(new MainMenu());
            return;
        }

        lcMenu = new Menu(wn.getName());
        lcMenu.clearPrompts();
        lcMenu.setMenuInfo(wn.getDescription() + ". " + wn.getSurroundings());

        // add enemy options
        int enemyCounter = 0;
        for (Entity ent : wn.getEntComp().getEntities()) {
            if (ent instanceof Enemy) {  // every enemy for loop
                lcMenu.addPrompt("" + enemyCounter, ent.getName() + " Lvl. " + ent.getLevel());
                enemyCounter++;
            }
        }

        //Prevent travel if enemies are present
        if (wn.getEntComp().getEntities().size() == 0) {
            lcMenu.addPrompt("[T]RAVEL");
        }

        lcMenu.addPrompt("BACK");

        lcMenu.display();
    }

    @Override
    public void update(Main RPGS) {
        String optionSelection = RPGS.getInput().nextLine().toUpperCase();

        //0-9
        try {
            int numSel = Integer.valueOf(optionSelection);

            if (numSel < wn.getEntComp().getEntities().size()) { // this is gonna cause an error or let u fight normal npcs but imma just let it happen
                RPGS.enterGameState(new Combat((Enemy) wn.getEntComp().getEntities().get(numSel))); // enter combat
                if (wn.getEntComp().getEntities().get(numSel).getStat(Entity.Stats.HEALTH).value <= 0) { // player wins combat
                    System.out.println("ASSSSSSSSSSSSSSSSSSSSSS");
                    wn.getEntComp().getEntities().remove((int) (Integer.valueOf(optionSelection)));
                    enter(RPGS); //update Menu info
                } else { // player runs or dies

                }
            }

            //Back or anything else
        } catch (NumberFormatException nfe) {
            if (optionSelection.equalsIgnoreCase("T")) {
                if (wn.getEntComp().getEntities().size() == 0) {
                    Location.travelToNewLocation(RPGS);
                }
            } else if (optionSelection.equalsIgnoreCase("BACK")) {
                RPGS.returnToPrevState();
            } else {
                lcMenu.display();
                lcMenu.message("You don't know what " + optionSelection + " means.");
            }
        }
    }
}
