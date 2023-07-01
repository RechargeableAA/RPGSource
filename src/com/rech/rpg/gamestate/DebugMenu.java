package com.rech.rpg.gamestate;

import com.rech.rpg.Main;
import com.rech.rpg.Menu;
import com.rech.rpg.entity.Enemy;
import com.rech.rpg.gamestate.inventory.InventoryMainMenu;
import com.rech.rpg.item.Weapon;
import com.rech.rpg.map.location.Wilderness;

import java.util.Scanner;

/**
 * Obligatory debug menu :). Enter by typing "true" into any input
 */
public class DebugMenu{

    Menu debug = new Menu("DEBUG");

    public void enter(Main RPGS) {
        debug.clearAllMenu();

        debug.addPrompt("0", "add dark knight to current location - must be wilderness or ull crash");
        debug.addPrompt("1", "pickup admin blade");

        debug.display();

        Scanner input = new Scanner(System.in);

        String optionSelection = input.nextLine();

        //Logic for menu
        if(optionSelection.equalsIgnoreCase("0")) {Wilderness.class.cast(RPGS.getCurrentLocation()).getEntComp().spawnEntity(Enemy.darkKnight); }
        else if (optionSelection.equalsIgnoreCase("1")){RPGS.getPlayer().getInventory().pickup(Weapon.adminBlade);}
        else {
            debug.display();
            debug.message("\nYou don't know what the fuck '"+optionSelection+"' means.\n");
        }

        debug.message("doing option " + optionSelection);
    }
}
