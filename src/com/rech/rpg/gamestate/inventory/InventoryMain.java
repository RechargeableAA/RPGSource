package com.rech.rpg.gamestate.inventory;

import com.rech.rpg.gamestate.GameState;
import com.rech.rpg.Main;
import com.rech.rpg.Menu;
import com.rech.rpg.gamestate.MainMenu;
import com.rech.rpg.item.Inventory;

import java.util.Scanner;

public class InventoryMain implements GameState {

    //The temporary inventory that's used to store the inventory of the player/entity while its being modified in this instance
    protected Inventory inv;
    protected Scanner inp;
    Menu invMenu = new Menu("INVENTORY");

    public InventoryMain(Inventory inventory, Scanner input){
        inv = new Inventory();
        inv = inventory;
        inp = input;
    }

    @Override
    public void enter() {
        inv.sortInventory();
        invMenu.clearPrompts();
        //print occupied inventory slots
        int occupiedSlots = 0;
        for (int inventorySlot = 0; inventorySlot < inv.getSize(); inventorySlot++) {
            if (inv.getSlot(inventorySlot) != null) {
                invMenu.addPrompt("" + inventorySlot, "" + inv.getSlot(inventorySlot).getName());
                System.out.println();
                occupiedSlots++;
            }
        }

        if (occupiedSlots > 0) {
            invMenu.setMenuInfo("Your " + Main.getPlayer().getEquipped().getName() + " is equipped.\n" +
                    occupiedSlots + "/10 backpack slots are being used.\n" +
                    "Coins: " + String.format("%,d", inv.getCoins()) + "gp\n");
        } else {
            invMenu.setMenuInfo("Your " + Main.getPlayer().getEquipped().getName() + " is equipped.\nNone of your backpack slots are being used.\n" +
                    "Coins: " + String.format("%,d", inv.getCoins()) + "gp\n");
        }
        invMenu.addPrompt("EQUIP");
        invMenu.addPrompt("DROP");
        invMenu.addPrompt("BACK");
        invMenu.display(false);
    }

    @Override
    public void update() {
        String optionSelection = inp.nextLine().toString();
        switch(optionSelection.toUpperCase()) {
            case "EQUIP":
                Main.saveState(this);
                Main.enterGameState(new InventoryEquip(inv, inp));
                break;
            case "DROP":
                Main.saveState(this);
                Main.enterGameState(new InventoryDrop(inv, inp));
                break;
            case "BACK":
                Main.returnToPrevState();
            default:
                invMenu.message("You don't know  what "+optionSelection+" means.", inp);
                invMenu.display(false);
        }
    }
}
