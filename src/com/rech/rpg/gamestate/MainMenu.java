package com.rech.rpg.gamestate;

import com.rech.rpg.Main;
import com.rech.rpg.Menu;
import com.rech.rpg.gamestate.inventory.InventoryMain;

import java.util.Scanner;

public class MainMenu implements GameState {
    Scanner inp;
    private static Menu mainMenu  = new Menu("Main Menu");
    public MainMenu(Scanner input){
        inp = input;
    }
    @Override
    public void enter() {
        Main.saveState(this);

        mainMenu.clearAll();
        mainMenu.addPrompt("TRAVEL", "move to another location");
        mainMenu.addPrompt("STATS", "check your statistics");
        mainMenu.addPrompt("BACKPACK", "look at, potions, coins, and equip weapons that you own");
        mainMenu.addPrompt("SPELLS", "look at and equip spellbooks owned");
        mainMenu.addPrompt("LOOK", "examine your surroundings");
        mainMenu.addPrompt("OPTIONS", "load or save your game");

        mainMenu.display(false);
    }

    @Override
    public void update() {
        String optionSelection = inp.nextLine().toString();

        //Logic for menu
        if(optionSelection.equalsIgnoreCase("stats")) {Main.getPlayer().showStats(inp); }
        else if (optionSelection.equalsIgnoreCase("backpack") || optionSelection.equalsIgnoreCase("inv")) {Main.enterGameState(new InventoryMain(Main.getPlayer().getInventory(), inp));}
        else if (optionSelection.equalsIgnoreCase("spellbooks") || optionSelection.equalsIgnoreCase("spells")) { Main.enterGameState(new InventoryMain(Main.getPlayer().getInventory(), inp));}
        else if (optionSelection.equalsIgnoreCase("look")) { Main.getMap().getLocation(Main.getMap().getPlayerPosition()).locationMenu(Main.getPlayer(), inp); }
        else if (optionSelection.equalsIgnoreCase("travel")) { Main.getMap().mapMenu(inp); }
        else if (optionSelection.equalsIgnoreCase("options")) { Main.enterGameState(new OptionsMenu(inp)); }
        else {
            mainMenu.message("\nYou don't know what '"+optionSelection+"' means.\n", inp);
        }
    }
}
