package com.rech.rpg.gamestate;

/**
 * This class provides a template for which all enterable menus, game screen, games functions should implement from. It provides a skeleton that can be predictable and added upon
 */
public interface GameState {
    public void enter();

    /**
     * Perform gamestate logic
     * @return true to return to previous game state, false to continue gamestate
     */
    public void update();
}