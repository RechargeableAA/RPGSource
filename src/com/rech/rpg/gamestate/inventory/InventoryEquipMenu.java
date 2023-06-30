package com.rech.rpg.gamestate.inventory;

import com.rech.rpg.Main;
import com.rech.rpg.Menu;
import com.rech.rpg.gamestate.GameState;
import com.rech.rpg.gamestate.MainMenu;
import com.rech.rpg.item.Inventory;
import com.rech.rpg.item.Weapon;

import java.util.Scanner;

public class InventoryEquipMenu implements GameState {
    Menu equipMenu = new Menu("Equip");

    @Override
    public void enter(Main RPGS) {
        //adding inventory to menu
        RPGS.getPlayer().getInventory().sortInventory();
        equipMenu.clearPrompts();
        for(int inventorySlot = 0; inventorySlot < RPGS.getPlayer().getInventory().getSize(); inventorySlot++) {
            if(RPGS.getPlayer().getInventory().getSlot(inventorySlot) != null) {
                equipMenu.addPrompt(""+inventorySlot,  RPGS.getPlayer().getInventory().getSlot(inventorySlot).getName());
            }
        }

        if (RPGS.getPlayer().getInventory().isEmpty()) { // if this(inventory) is empty
            RPGS.returnToPrevState();
            return;
        }else {
            equipMenu.addPrompt("0-9", "Which slot do you want to equip? [back]");
            equipMenu.addPrompt("back");
        }

        equipMenu.display();
    }

    @Override
    public void update(Main RPGS) {
        //error catching string to int needs try and cathch
        String optionSelection = RPGS.getInput().nextLine();
        switch(optionSelection.toUpperCase()) {
            case "BACK":
                if (RPGS.getPlayer().getInventory().isEmpty()) { //goes back to the menu before showing the backpack
                    RPGS.enterGameState(new MainMenu());
                }
            default:
                if(optionSelection.matches("[0-9]+")) { // is optionSelection a number stored in a string?
                    int slot = Integer.parseInt(optionSelection); // convert string of number into an integer
                    if(RPGS.getPlayer().getInventory().getSlot(slot) != null) {
                        if(RPGS.getPlayer().getInventory().getSlot(slot) instanceof Weapon) { // checks to see if item is a weapon
                            equipMenu.alert("You equip your " + RPGS.getPlayer().getInventory().getSlot(slot).getName() + ".", RPGS.getInput());
                            RPGS.getPlayer().equip(slot);
                            RPGS.returnToPrevState();
                        }else {
                            equipMenu.display();
                            equipMenu.alert("You can't equip a " + RPGS.getPlayer().getInventory().getSlot(slot).getName(), RPGS.getInput());
                        }
                    }else {
                        equipMenu.display();
                        equipMenu.alert("There's nothing in that inventory slot.", RPGS.getInput());
                    }
                }else {
                    equipMenu.display();
                    equipMenu.message("You don't know what "+optionSelection+" means.");
                }
                break;
        }
    }

    /**
     * executes actions based off of string input. I seperated this method so it can be ran again easily by the menu's function. Allowing the user to enter an option after a message rather than having to press enter, then entering the option.
     * @param input
     */
    private void handleOptions(String input){

    }
}
