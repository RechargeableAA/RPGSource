package com.rech.rpg.gamestate;

import com.rech.rpg.Main;

import java.util.Scanner;

public class OptionsMenu implements GameState {
    @Override
    public void enter(Main RPGS) {
        System.out.println("\n\nOPTIONS\n");
        System.out.println("[SAVE] Game");
        System.out.println("[LOAD] Game");
        System.out.println("[BACK]");
    }

    @Override
    public void update(Main RPGS) {
        String in = RPGS.getInput().nextLine();

        if (in.equals("back")) {
            RPGS.returnToPrevState();
        }else if (in.equals("save")) {
            //FileManager.saveGame(player);
            RPGS.returnToPrevState();
        }else if (in.equals("load")) {
            //FileManager.loadGame();
            RPGS.returnToPrevState();
        }else {
            System.out.println("Choose an option listed above\n");
        }
    }
}
