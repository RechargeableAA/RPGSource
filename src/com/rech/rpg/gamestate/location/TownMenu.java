package com.rech.rpg.gamestate.location;

import com.rech.rpg.Main;
import com.rech.rpg.Menu;
import com.rech.rpg.entity.Player;
import com.rech.rpg.gamestate.GameState;
import com.rech.rpg.map.location.Location;
import com.rech.rpg.map.location.Town;
import com.rech.rpg.map.location.shop.Shop;

import java.util.Scanner;

public class TownMenu implements GameState{

    private Town tn;
    private Player pl;
    private Scanner inp;

    Menu locationMenu;

    public TownMenu(Town town, Player player, Scanner input){
        tn = town;
        pl = player;
        inp = input;
    }
    @Override
    public void enter() {
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
    public void update() {
        String optionSelection = inp.nextLine().toUpperCase();

        //0-9
        try {
            if(Integer.valueOf(optionSelection) < tn.getShops().size()) {
                tn.getShops().get(Integer.valueOf(optionSelection)).interact(inp, pl);
            }

            //Back or anything else
        }catch(NumberFormatException nfe) {
            if(optionSelection.equalsIgnoreCase("T")){
                Location.travelToNewLocation(pl, inp);
            }else if(optionSelection.equalsIgnoreCase("BACK")) {
                Main.returnToPrevState();
            }else {
                locationMenu.alert("You don't know what " + optionSelection + " means.", inp);
            }
        }
    }
}
