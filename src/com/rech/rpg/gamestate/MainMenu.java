package com.rech.rpg.gamestate;

import com.rech.rpg.Main;
import com.rech.rpg.Menu;
import com.rech.rpg.gamestate.inventory.InventoryMainMenu;
import com.rech.rpg.gamestate.stats.StatsMenu;

import java.util.Scanner;

public class MainMenu implements GameState {
    Scanner inp;
    private static Menu mainMenu  = new Menu("Main Menu");
    public MainMenu(Scanner input){
        inp = input;
    }
    @Override
    public void enter() {
        mainMenu.clearAll();
        mainMenu.addPrompt("STATS", "check your statistics");
        mainMenu.addPrompt("BACKPACK", "look at, potions, coins, and equip weapons that you own");
        mainMenu.addPrompt("SPELLS", "look at and equip spellbooks owned");
        mainMenu.addPrompt("LOOK", "examine your surroundings");
        mainMenu.addPrompt("OPTIONS", "load or save your game");

        mainMenu.display();

        Main.saveState(this); // save this state so that other menus that simply return to the last saved menu, return here.
    }

    @Override
    public void update() {
        String optionSelection = inp.nextLine();

        //Logic for menu
        if(optionSelection.equalsIgnoreCase("stats")) {Main.enterGameState(new StatsMenu(Main.getPlayer(), inp)); }
        else if (optionSelection.equalsIgnoreCase("backpack") || optionSelection.equalsIgnoreCase("inv")) {Main.enterGameState(new InventoryMainMenu(Main.getPlayer().getInventory(), inp));}
        else if (optionSelection.equalsIgnoreCase("spellbooks") || optionSelection.equalsIgnoreCase("spells")) { Main.enterGameState(new InventoryMainMenu(Main.getPlayer().getInventory(), inp));}
        else if (optionSelection.equalsIgnoreCase("look")) { Main.enterGameState(Main.getCurrentLocation().getGameState(Main.getPlayer(), inp));  }
        else if (optionSelection.equalsIgnoreCase("options")) { Main.enterGameState(new OptionsMenu(inp)); }
        else if (optionSelection.equalsIgnoreCase("")) { mainMenu.display(); }
        else {
            mainMenu.alert("\nYou don't know what '"+optionSelection+"' means.\n", inp);
        }
    }
}
