package com.rech.rpg.gamestate;

/**
 * This class provides a template for which all enter-able menus, game screen, and major game functions should implement from. It provides a skeleton that can be predictable and added upon
 */
public interface GameState {
    public void enter();

    /**
     * Perform gamestate logic
     * @return true to return to previous game state, false to continue gamestate
     */
    public void update();

}