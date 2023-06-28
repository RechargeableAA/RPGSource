package com.rech.rpg.gamestate.inventory;

import com.rech.rpg.Main;
import com.rech.rpg.Menu;
import com.rech.rpg.item.Inventory;

import java.util.Scanner;

public class InventoryDropMenu extends InventoryMainMenu {
    Menu dropMenu = new Menu("DROP");

    public InventoryDropMenu(Inventory inventory, Scanner input){
        super(inventory, input);
    }
    @Override
    public void enter() { // display menu
        inv.sortInventory();
        dropMenu.clearPrompts();

        //show inventory again
        dropMenu.setMenuInfo("Your " + Main.getPlayer().getEquipped().getName() + " is equipped.");
        for (int inventorySlot = 0; inventorySlot < inv.getSize(); inventorySlot++) {
            if (inv.getSlot(inventorySlot) != null) {
                dropMenu.addPrompt("" + inventorySlot, "" + inv.getSlot(inventorySlot).getName());
            }
        }

        if (inv.isEmpty()) { // if this(inventory) is empty
            dropMenu.alert("You have nothing in your backpack.", inp);
            Main.returnToPrevState();
            return;
        } else {
            dropMenu.addPrompt("0-9", "Which slot do you want to drop?");
            dropMenu.addPrompt("BACK");
        }

        dropMenu.display();
    }

    @Override
    public void update() { // handle user input for menu
        String optionSelection = inp.nextLine();
        switch(optionSelection.toUpperCase()) {
            case "BACK":
                Main.returnToPrevState();
                break;
            default:
                if(optionSelection.matches("[0-9]+")) { // check if string input is an integer
                    int slot = Integer.parseInt(optionSelection);
                    if(inv.getItems()[slot] != null) {
                        dropMenu.clearPrompts();
                        dropMenu.addPrompt("y/n", "Are you sure you want to drop " + inv.getSlot(slot).getName() + "?");
                        dropMenu.display();
                        if(inp.nextLine().equalsIgnoreCase("y")) {
                            dropMenu.clearPrompts();
                            dropMenu.alert("You drop your " + inv.getSlot(slot).getName() + ".", inp);
                            dropMenu.display();
                            inp.nextLine();
                            inv.drop(slot);
                            Main.returnToPrevState();
                        }
                    }else {
                        dropMenu.alert("There isn't an item in that slot.", inp);
                    }
                }else {
                    dropMenu.alert("You don't know what "+optionSelection+" means.", inp);
                }
        }
    }
}
