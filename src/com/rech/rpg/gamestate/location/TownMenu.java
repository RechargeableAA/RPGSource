package com.rech.rpg.gamestate.location;

import com.rech.rpg.Main;
import com.rech.rpg.Menu;
import com.rech.rpg.entity.Player;
import com.rech.rpg.gamestate.GameState;
import com.rech.rpg.gamestate.MainMenu;
import com.rech.rpg.map.location.Location;
import com.rech.rpg.map.location.Town;
import com.rech.rpg.map.location.shop.Shop;

import java.util.Scanner;

public class TownMenu implements GameState{

    private Menu locationMenu;
    private  Town tn;
    @Override
    public void enter(Main RPGS) {
        // load current location and cast it to a town
        try{
            tn = (Town)RPGS.getCurrentLocation();
        }catch (Exception e){   //This gamestate was entered when the current game location was not a town. This shouldn't happen if the gamestate is entered by using each location's getGameState method
            e.printStackTrace();
            System.err.println("Town menu was entered while current location is a " + RPGS.getCurrentLocation().getClass());
            RPGS.enterGameState(new MainMenu());
            return;
        }

        locationMenu = new Menu(tn.getName().toUpperCase());

        locationMenu.clearPrompts();
        locationMenu.setMenuInfo(tn.getDescription() + ". " + tn.getSurroundings());
        for(Shop shop : tn.getShops()) {
            locationMenu.addPrompt(tn.getShops().indexOf(shop)+"", shop.getName());
        }
        locationMenu.addPrompt("[T]RAVEL");
        locationMenu.addPrompt("BACK");

        locationMenu.display();
    }

    @Override
    public void update(Main RPGS) {
        String optionSelection = RPGS.getInput().nextLine().toUpperCase();

        //0-9
        try {
            if(Integer.valueOf(optionSelection) < tn.getShops().size()) {
                tn.getShops().get(Integer.valueOf(optionSelection)).interact(RPGS);
            }

            //Back or anything else
        }catch(NumberFormatException nfe) {
            if(optionSelection.equalsIgnoreCase("T")){
                Location.travelToNewLocation(RPGS);
            }else if(optionSelection.equalsIgnoreCase("BACK")) {
                RPGS.returnToPrevState();
            }else {
                locationMenu.display();
                locationMenu.message("You don't know what " + optionSelection + " means.");
            }
        }
    }
}
