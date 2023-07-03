package com.rech.rpg.gamestate;

import com.rech.rpg.Main;
import com.rech.rpg.Menu;
import com.rech.rpg.entity.Player;
import com.rech.rpg.item.Weapon;
import com.rech.rpg.map.location.Town;

/**
 * First menu when game starts
 */
public class IntroMenu implements GameState {

    Menu introMenu = new Menu("RPG Source v"+Main.versionNumber);

    @Override
    public void enter(Main RPGS) {
        introMenu.clearScreen();

        introMenu.addPrompt("[NEW] Game");
        introMenu.addPrompt("[LOAD] Game");

        introMenu.display();
    }

    @Override
    public void update(Main RPGS) {
        String selection = RPGS.getInput().nextLine();

        if(selection.equalsIgnoreCase("new")) {
            newGame(RPGS);
        }else if(selection.equalsIgnoreCase("load")) {
            //FileManager.loadGame();
        }else {
            introMenu.display();
            introMenu.message("\nYou don't know what '"+selection+"' means.\n");
            return;
        }

       RPGS.enterGameState(new MainMenu());
    }

    /**
     * Creates a new game with user input
     */
    private void newGame(Main RPGS) {
        Menu nG = new Menu("New Game");

        nG.clearScreen();
        nG.display();
        nG.message("You awake in a strange land. You have no recollection of how you got here.");
        nG.message("You notice a man standing over you.");
        nG.message("");
        nG.message("Hello stranger...");
        nG.message("You seem like you're not from around here.");
        nG.message("Do you have a name?");

        RPGS.setPlayer(new Player(RPGS.getInput().nextLine())); // creates new player with name input
        RPGS.getPlayer().getInventory().pickup(Weapon.generateNewWeapon(2, 2));
        RPGS.getPlayer().equip(0); // give player random weapon
        RPGS.setCurrentLocation(Town.generate()); // start in a random town

        nG.clearScreen();
        nG.display();
        nG.message(RPGS.getPlayer().getName()+"? ... Can't say that's the name I would've given you... ");
        nG.message("Well, my name is Gavin. This is " + RPGS.getCurrentLocation().getName());
        nG.message("I'll let you rest in my home just down the way.");
        nG.message("It's not much, but I bet it'll work until you can sort yourself out.");
        nG.message("Here, you can have my old "+RPGS.getPlayer().getEquipped().getName());
        nG.message("You're gonna need it. Watch yourself out there.");


        RPGS.getInput().nextLine(); // wait for enter key
    }
}
