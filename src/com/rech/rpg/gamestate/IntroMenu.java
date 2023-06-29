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
    Scanner inp;
    public IntroMenu(Scanner input){
        inp = input;
    }

    @Override
    public void enter() {
        Main.clearScreen();
        System.out.println("[NEW] Game");
        System.out.println("[LOAD] Game");
    }

    @Override
    public void update() {
        String selection = inp.nextLine();

        if(selection.equalsIgnoreCase("new")) {
            newGame();
        }else if(selection.equalsIgnoreCase("load")) {
            //FileManager.loadGame();
        }else {
            System.out.println(selection + " is not an option.");
        }

        Main.enterGameState(new MainMenu(inp));
    }

    /**
     * Creates a new game with user input
     */
    private void newGame() {
        System.out.println("\n\nYou awake in a strange land. You have no recollection of how you got here.\nYou notice a man standing over you.\n\nHello stranger...\nYou seem like you're not from around here.\nDo you have a name?\n");

        Main.setPlayer(new Player(inp.nextLine())); // creates new player with name input
        Main.getPlayer().getInventory().pickup(Weapon.generateNewWeapon(2, 2));
        Main.getPlayer().equip(0); // give player random weapon


        Main.setCurrentLocation(Town.generate()); // start in a random town
        Main.clearScreen();
        System.out.println("\n"+Main.getPlayer().getName()+"? ... Can't say that's the name I would've given you... \nWell,  my name is Gavin. This is " + Main.getCurrentLocation().getName()
                + ".\nI'll let you rest in my home just down the way.\nIt's not much, but I bet it'll work until you can sort yourself out.\n"
                + "\nHere, you can have my old "+Main.getPlayer().getEquipped().getName()+".\nYou're gonna need it. Watch yourself out there.");

        inp.nextLine(); // wait for enter key
    }
}
