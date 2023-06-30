package com.rech.rpg.gamestate;

import com.rech.rpg.Main;
import com.rech.rpg.entity.Player;
import com.rech.rpg.item.Weapon;
import com.rech.rpg.map.location.Town;

import java.util.Scanner;

/**
 * First menu when game starts
 */
public class IntroMenu implements GameState {

    @Override
    public void enter(Main RPGS) {
        System.out.println("[NEW] Game");
        System.out.println("[LOAD] Game");
    }

    @Override
    public void update(Main RPGS) {
        String selection = RPGS.getInput().nextLine();

        if(selection.equalsIgnoreCase("new")) {
            newGame(RPGS);
        }else if(selection.equalsIgnoreCase("load")) {
            //FileManager.loadGame();
        }else {
            System.out.println(selection + " is not an option.");
        }

        RPGS.enterGameState(new MainMenu());
    }

    /**
     * Creates a new game with user input
     */
    private void newGame(Main RPGS) {
        System.out.println("\n\nYou awake in a strange land. You have no recollection of how you got here.\nYou notice a man standing over you.\n\nHello stranger...\nYou seem like you're not from around here.\nDo you have a name?\n");

        RPGS.setPlayer(new Player(RPGS.getInput().nextLine())); // creates new player with name input
        RPGS.getPlayer().getInventory().pickup(Weapon.generateNewWeapon(2, 2));
        RPGS.getPlayer().equip(0); // give player random weapon


        RPGS.setCurrentLocation(Town.generate()); // start in a random town
        System.out.println("\n"+RPGS.getPlayer().getName()+"? ... Can't say that's the name I would've given you... \nWell,  my name is Gavin. This is " + RPGS.getCurrentLocation().getName()
                + ".\nI'll let you rest in my home just down the way.\nIt's not much, but I bet it'll work until you can sort yourself out.\n"
                + "\nHere, you can have my old "+RPGS.getPlayer().getEquipped().getName()+".\nYou're gonna need it. Watch yourself out there.");

        RPGS.getInput().nextLine(); // wait for enter key
    }
}
