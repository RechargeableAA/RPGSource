package com.rech.rpg.gamestate.inventory;

import com.rech.rpg.Main;
import com.rech.rpg.Menu;
import com.rech.rpg.gamestate.GameState;

public class InventoryDropMenu implements GameState {
    Menu dropMenu = new Menu("DROP");

    @Override
    public void enter(Main RPGS) { // display menu
        RPGS.getPlayer().getInventory().sortInventory();
        dropMenu.clearPrompts();

        //show inventory again
        dropMenu.setMenuInfo("Your " + RPGS.getPlayer().getEquipped().getName() + " is equipped.");
        for (int inventorySlot = 0; inventorySlot < RPGS.getPlayer().getInventory().getSize(); inventorySlot++) {
            if (RPGS.getPlayer().getInventory().getSlot(inventorySlot) != null) {
                dropMenu.addPrompt("" + inventorySlot, "" + RPGS.getPlayer().getInventory().getSlot(inventorySlot).getName());
            }
        }

        if (RPGS.getPlayer().getInventory().isEmpty()) { // if this(inventory) is empty
            dropMenu.alert("You have nothing in your backpack.");
            RPGS.returnToPrevState();
            return;
        } else {
            dropMenu.addPrompt("0-9", "Which slot do you want to drop?");
            dropMenu.addPrompt("BACK");
        }

        dropMenu.display();
    }

    @Override
    public void update(Main RPGS) { // handle user input for menu
        String optionSelection = RPGS.getInput().nextLine();
        switch(optionSelection.toUpperCase()) {
            case "BACK":
                RPGS.returnToPrevState();
                break;
            default:
                if(optionSelection.matches("[0-9]+")) { // check if string input is an integer
                    int slot = Integer.parseInt(optionSelection);
                    if(RPGS.getPlayer().getInventory().getItems()[slot] != null) {
                        dropMenu.clearPrompts();
                        dropMenu.addPrompt("y/n", "Are you sure you want to drop " + RPGS.getPlayer().getInventory().getSlot(slot).getName() + "?");
                        dropMenu.display();
                        if(RPGS.getInput().nextLine().equalsIgnoreCase("y")) {
                            dropMenu.clearPrompts();
                            dropMenu.alert("You drop your " + RPGS.getPlayer().getInventory().getSlot(slot).getName() + ".");
                            dropMenu.display();
                            RPGS.getInput().nextLine();
                            RPGS.getPlayer().getInventory().drop(slot);
                            RPGS.returnToPrevState();
                        }
                    }else {
                        dropMenu.alert("There isn't an item in that slot.");
                    }
                }else {
                    dropMenu.display();
                    dropMenu.message("You don't know what "+optionSelection+" means.");
                }
        }
    }
}
