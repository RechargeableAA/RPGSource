package com.rech.rpg.gamestate.location;

import com.rech.rpg.Menu;
import com.rech.rpg.entity.Enemy;
import com.rech.rpg.entity.Entity;
import com.rech.rpg.entity.Player;
import com.rech.rpg.entity.combat.Combat;
import com.rech.rpg.gamestate.GameState;
import com.rech.rpg.map.location.Wilderness;

import java.util.Scanner;

public class WildernessMenu implements GameState {
    private Wilderness wn;
    private Player pl;
    private Scanner inp;

    Menu lcMenu = new Menu(wn.getName());

    public WildernessMenu(Wilderness wilderness, Player player, Scanner input){
        wn = wilderness;
        pl = player;
        inp = input;
    }

    @Override
    public void enter() {
        lcMenu.clearPrompts();
        lcMenu.setMenuInfo(wn.getDescription() + ". " + wn.getSurroundings());

        // add enemy ooptions
        for(Entity ent : wn.getEntComp().getEntities()) {
            if(ent instanceof Enemy) {  // every enemy for loop
                lcMenu.addPrompt(""+wn.getEntComp().getEntities().indexOf(ent), ent.getName() + " Lvl. " +ent.getLevel());
            }
        }

        lcMenu.addPrompt("BACK");

        lcMenu.display();
        String optionSelection = inp.nextLine().toUpperCase();

        //0-9
        try {
            if(Integer.valueOf(optionSelection) < wn.getEntComp().getEntities().size()) { // this is gonna cause an error or let u fight normal npcs but imma just let it happen
                if(new Combat().enterCombat(pl, (Enemy) wn.getEntComp().getEntities().get(Integer.valueOf(optionSelection)), inp)) { // player wins combat
                    wn.getEntComp().getEntities().remove((int)(Integer.valueOf(optionSelection)));
                }else { // player runs or dies

                }
            }

            //Back or anything else
        }catch(NumberFormatException nfe) {
            if(optionSelection.toUpperCase().equals("BACK")) {
                return;
            }else {
                lcMenu.alert("You don't know what " + optionSelection + " means.", inp);
            }
        }
    }

    @Override
    public void update() {

    }
}
