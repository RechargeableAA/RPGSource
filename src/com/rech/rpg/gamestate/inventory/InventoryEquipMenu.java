package com.rech.rpg.gamestate.inventory;

import com.rech.rpg.Main;
import com.rech.rpg.Menu;
import com.rech.rpg.gamestate.MainMenu;
import com.rech.rpg.item.Inventory;
import com.rech.rpg.item.Weapon;

import java.util.Scanner;

public class InventoryEquipMenu extends InventoryMainMenu {
    Menu equipMenu = new Menu("Equip");

    public InventoryEquipMenu(Inventory inventory, Scanner input) {
        super(inventory, input);
    }

    @Override
    public void enter() {
        //adding inventory to menu
        inv.sortInventory();
        equipMenu.clearPrompts();
        for(int inventorySlot = 0; inventorySlot < inv.getSize(); inventorySlot++) {
            if(inv.getSlot(inventorySlot) != null) {
                equipMenu.addPrompt(""+inventorySlot,  inv.getSlot(inventorySlot).getName());
            }
        }

        if (inv.isEmpty()) { // if this(inventory) is empty
            equipMenu.alert("You have nothing in your backpack.", inp);
            Main.returnToPrevState();
            return;
        }else {
            equipMenu.addPrompt("0-9", "Which slot do you want to equip? [back]");
            equipMenu.addPrompt("back");
        }

        equipMenu.display(false);
    }

    @Override
    public void update() {
        //error catching string to int needs try and cathch
        String optionSelection = inp.nextLine();
        switch(optionSelection.toUpperCase()) {
            case "BACK":
                if (inv.isEmpty()) { //goes back to the menu before showing the backpack
                    Main.enterGameState(new MainMenu(inp));
                }
            default:
                if(optionSelection.matches("[0-9]+")) { // is optionSelection a number stored in a string?
                    int slot = Integer.parseInt(optionSelection); // convert string of number into an integer
                    if(inv.getSlot(slot) != null) {
                        if(inv.getSlot(slot) instanceof Weapon) { // checks to see if item is a weapon
                            equipMenu.alert("You equip your " + inv.getSlot(slot).getName() + ".", inp);
                            Main.getPlayer().equip(slot);
                            Main.returnToPrevState();
                        }else {
                            equipMenu.display(false);
                            equipMenu.alert("You can't equip a " + Main.getPlayer().getInventory().getSlot(slot).getName(), inp);
                        }
                    }else {
                        equipMenu.display(false);
                        equipMenu.alert("There's nothing in that inventory slot.", inp);
                    }
                }else {
                    equipMenu.display(false);
                    equipMenu.alert("You don't know what "+optionSelection+" means.", inp);
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
