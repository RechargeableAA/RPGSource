package com.rech.rpg.gamestate;

import com.rech.rpg.Main;

import java.util.Scanner;

public class OptionsMenu implements GameState {
    Scanner inp;
    public OptionsMenu(Scanner input) {
        inp = input;
    }

    @Override
    public void enter() {
        System.out.println("\n\nOPTIONS\n");
        System.out.println("[SAVE] Game");
        System.out.println("[LOAD] Game");
        System.out.println("[BACK]");
    }

    @Override
    public void update() {
        String in = inp.nextLine().toString();

        if (in.equals("back")) {
            Main.returnToPrevState();
        }else if (in.equals("save")) {
            //FileManager.saveGame(player);
            Main.returnToPrevState();
        }else if (in.equals("load")) {
            //FileManager.loadGame();
            Main.returnToPrevState();
        }else {
            System.out.println("Choose an option listed above\n");
        }
    }
}
