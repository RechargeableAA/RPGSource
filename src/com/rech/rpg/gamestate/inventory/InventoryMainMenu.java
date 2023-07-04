package com.rech.rpg.gamestate.inventory;

import com.rech.rpg.Main;
import com.rech.rpg.Menu;
import com.rech.rpg.gamestate.GameState;

public class InventoryMainMenu implements GameState {

    Menu invMenu = new Menu("INVENTORY");

    @Override
    public void enter(Main RPGS) {
        RPGS.getPlayer().getInventory().sortInventory();
        invMenu.clearPrompts();
        //print occupied inventory slots
        int occupiedSlots = 0;
        for (int inventorySlot = 0; inventorySlot < RPGS.getPlayer().getInventory().getSize(); inventorySlot++) {
            if (RPGS.getPlayer().getInventory().getSlot(inventorySlot) != null) {
                invMenu.addPrompt("" + inventorySlot, "" + RPGS.getPlayer().getInventory().getSlot(inventorySlot).getName());
                System.out.println();
                occupiedSlots++;
            }
        }

        if (occupiedSlots > 0) {
            invMenu.setMenuInfo("Your " + RPGS.getPlayer().getEquipped().getName() + " is equipped.\n" + occupiedSlots + "/10 backpack slots are being used.\n" + "Coins: " + String.format("%,d", RPGS.getPlayer().getInventory().getCoins()) + "gp\n");
        } else {
            invMenu.setMenuInfo("Your " + RPGS.getPlayer().getEquipped().getName() + " is equipped.\nNone of your backpack slots are being used.\n" + "Coins: " + String.format("%,d", RPGS.getPlayer().getInventory().getCoins()) + "gp\n");
        }
        invMenu.addPrompt("EQUIP");
        invMenu.addPrompt("DROP");
        invMenu.addPrompt("BACK");
        invMenu.display();
    }

    @Override
    public void update(Main RPGS) {
        String optionSelection = RPGS.getInput().nextLine();
        switch (optionSelection.toUpperCase()) {
            case "EQUIP":
                RPGS.saveState(this);
                RPGS.enterGameState(new InventoryEquipMenu());
                break;
            case "DROP":
                RPGS.saveState(this);
                RPGS.enterGameState(new InventoryDropMenu());
                break;
            case "BACK":
                RPGS.returnToPrevState();
                break;
            default:
                invMenu.display();
                invMenu.message("You don't know  what " + optionSelection + " means.");
        }
    }
}
