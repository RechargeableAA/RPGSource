package com.rech.rpg.gamestate.inventory;

import com.rech.rpg.Main;
import com.rech.rpg.Menu;
import com.rech.rpg.gamestate.MainMenu;
import com.rech.rpg.item.Inventory;
import com.rech.rpg.item.Weapon;

import java.util.Scanner;

public class InventoryEquip extends InventoryMain {
    Menu equipMenu = new Menu("Equip");

    public InventoryEquip(Inventory inventory, Scanner input) {
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

        equipMenu.display(false);
    }

    @Override
    public void update() {
        if (inv.isEmpty()) { // if this(inventory) is empty
            equipMenu.message("You have nothing in your backpack.", inp);
            Main.enterGameState(new MainMenu(inp));
        }else {
            equipMenu.addPrompt("0-9", "Which slot do you want to equip? [back]");
            equipMenu.addPrompt("back");
        }

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
                            equipMenu.message("You equip your " + inv.getSlot(slot).getName() + ".", inp);
                            Main.getPlayer().equip(slot);
                            Main.returnToPrevState();
                        }else {
                            equipMenu.display(false);
                            equipMenu.message("You can't equip a " + Main.getPlayer().getInventory().getSlot(slot).getName(), inp);
                        }
                    }else {
                        equipMenu.display(false);
                        equipMenu.message("There's nothing in that inventory slot.", inp);
                    }
                }else {
                    equipMenu.display(false);
                    equipMenu.message("You don't know what "+optionSelection+" means.", inp);
                }
                break;
        }
    }
}
