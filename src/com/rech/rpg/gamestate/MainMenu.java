package com.rech.rpg.gamestate;

import com.rech.rpg.Main;
import com.rech.rpg.Menu;
import com.rech.rpg.gamestate.inventory.InventoryMainMenu;
import com.rech.rpg.gamestate.stats.SkillsMenu;

import java.util.Scanner;

public class MainMenu implements GameState {
    private static Menu mainMenu  = new Menu("Main Menu");
    @Override
    public void enter(Main RPGS) {
        mainMenu.clearAllMenu();
        mainMenu.addPrompt("STATS", "check your statistics");
        mainMenu.addPrompt("BACKPACK", "look at, potions, coins, and equip weapons that you own");
        mainMenu.addPrompt("SPELLS", "look at and equip spellbooks owned");
        mainMenu.addPrompt("LOOK", "examine your surroundings");
        mainMenu.addPrompt("OPTIONS", "load or save your game");

        mainMenu.display();

        RPGS.saveState(this); // save this state so that other menus that simply return to the last saved menu, return here.
    }

    @Override
    public void update(Main RPGS) {
        String optionSelection = RPGS.getInput().nextLine();

        //Logic for menu
        if(optionSelection.equalsIgnoreCase("stats")) {RPGS.enterGameState(new SkillsMenu()); }
        else if (optionSelection.equalsIgnoreCase("backpack") || optionSelection.equalsIgnoreCase("inv")) {RPGS.enterGameState(new InventoryMainMenu());}
        else if (optionSelection.equalsIgnoreCase("spellbooks") || optionSelection.equalsIgnoreCase("spells")) { RPGS.enterGameState(new InventoryMainMenu());}
        else if (optionSelection.equalsIgnoreCase("look")) { RPGS.enterGameState(RPGS.getCurrentLocation().getGameState());  }
        else if (optionSelection.equalsIgnoreCase("")) { mainMenu.display(); }
        else {
            mainMenu.display();
            mainMenu.message("\nYou don't know what '"+optionSelection+"' means.\n");
        }
    }
}
