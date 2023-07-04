package com.rech.rpg.gamestate;

import java.util.Random;

import com.rech.rpg.Main;
import com.rech.rpg.Menu;
import com.rech.rpg.entity.Enemy;
import com.rech.rpg.entity.Entity;
import com.rech.rpg.entity.Player;
import com.rech.rpg.gamestate.inventory.InventoryMainMenu;
import com.rech.rpg.item.Item;
import com.rech.rpg.map.location.Location;

public class Combat implements GameState {

    private final Enemy enemy;
    Menu cbMenu = new Menu("Combat");

    public Combat(Enemy enemy) {
        this.enemy = enemy;
    }


    @Override
    public void enter(Main RPGS) {
        String playerHealthBar = getHorizStatBar(RPGS.getPlayer().getStat(Entity.Stats.HEALTH).value, RPGS.getPlayer().getStat(Entity.Stats.HEALTH).getCurrentMaximum(), 5);
        String enemyHealthBar = getHorizStatBar(enemy.getStat(Entity.Stats.HEALTH).value, enemy.getStat(Entity.Stats.HEALTH).getCurrentMaximum(), 5);

        String menuInfo = "";
        menuInfo += "Player \t\t\t\t\t " + enemy.getName();
        menuInfo += "\nH: " + playerHealthBar + "\t\t\t\t H:" + enemyHealthBar;

        cbMenu.clearScreen();
        cbMenu.clearAllMenu();
        cbMenu.setMenuInfo(menuInfo);
        cbMenu.addPrompt("[A]ttack");
        cbMenu.addPrompt("[B]ackpack");
        cbMenu.addPrompt("[R]un");
        cbMenu.display();

    }

    @Override
    public void update(Main RPGS) {

        playerTurn(RPGS);

        //Enemy dies
        if (enemy.getStat(Entity.Stats.HEALTH).value <= 0) {
            enemyDeath(RPGS);
        } else {
            //Enemy's turn
            cbMenu.message("The " + enemy.getRace() + " swings their " + enemy.getEquipped().getName() + " at you dealing " + attack(enemy, RPGS.getPlayer()) + ".");
        }

        //Player dies
        if (RPGS.getPlayer().getStat(Entity.Stats.HEALTH).value <= 0) {
            playerDeath(RPGS);
        }

        cbMenu.alert("");
        enter(RPGS);
    }


    private void playerTurn(Main RPGS) {
        boolean playerTurn = true;
        while (playerTurn) {
            // Player's turn
            String optionSelection = RPGS.getInput().nextLine();
            switch (optionSelection.toUpperCase()) {
                case "ATTACK":
                case "A":
                    cbMenu.message("You swing your " + RPGS.getPlayer().getEquipped().getName() + " and deal " + attack(RPGS.getPlayer(), enemy));
                    playerTurn = false;
                    break;
                case "BACKPACK":
                case "INV":
                case "B":
                    RPGS.enterGameState(new InventoryMainMenu());
                    break;
                case "RUN":
                case "R":
                    Location.travelToNewLocation(RPGS); // this should probably let you travel to a new location but have some sort of punishment
                    break;
                default:
                    cbMenu.alert("You don't know  what " + optionSelection + " means.");
                    break;
            }
        }
    }

    private void playerDeath(Main RPGS) {
        cbMenu.message("Oh dear, you are dead. dummy");
        RPGS.setPlayer(new Player(RPGS.getPlayer().getName())); // reset ur player i guess
        RPGS.enterGameState(new MainMenu());
    }

    private void enemyDeath(Main RPGS) {
        cbMenu.clearScreen();
        cbMenu.message("The " + enemy.getRace() + " falls to the ground with a loud thud.");
        for (Item item : enemy.getInventory().getItems()) {
            if (item != null) {
                RPGS.getPlayer().getInventory().pickup(item);
                cbMenu.message("You find a " + item.getName());
            }
        }
        cbMenu.message("You gained " + enemy.getInventory().getCoins() + " coins.");
        cbMenu.alert("");
        RPGS.getPlayer().getInventory().addCoins(enemy.getInventory().getCoins());
        RPGS.enterGameState(RPGS.getCurrentLocation().getGameState());
    }

    /**
     * Attack an entity with another entity
     *
     * @param attacker
     * @param defender
     * @return The damage dealt
     */
    private int attack(Entity attacker, Entity defender) {
        Random rand = new Random();
        int damageDealt = Math.max(0, rand.nextInt(attacker.attackDamage()) - rand.nextInt(defender.getStat(Entity.Stats.DEFENSE).value));
        defender.getStat(Entity.Stats.HEALTH).value -= damageDealt;
        return damageDealt;
    }


    /**
     * Creates a bar out of unicode characters
     *
     * @param stat
     * @param statMax
     * @param segments
     * @return
     */
    private String getHorizStatBar(int stat, int statMax, int segments) {
        char[] barSegments = new char[segments];
        double singleSegmentMax = (double) statMax / (double) segments; // what amount would fill an entire segment. IE 100max/3segments = 33, so 33 is one full segment
        double segPercent = (100.0 / 8.0) / 100.0; //there are only 8 unicode characters for this bar, so 8 is 12.5% of 100

        for (int segment = segments; segment >= 1; segment--) {
            double segmentVal = (stat - singleSegmentMax * (segments - segment)) / singleSegmentMax;

            if (segmentVal <= 0.0 * segPercent) {
                barSegments[segments - segment] = ' ';
            } else if (segmentVal < segPercent) {
                barSegments[segments - segment] = '▏';
            } else if (segmentVal < 2.0 * segPercent) {
                barSegments[segments - segment] = '▎';
            } else if (segmentVal < 3.0 * segPercent) {
                barSegments[segments - segment] = '▍';
            } else if (segmentVal < 4.0 * segPercent) {
                barSegments[segments - segment] = '▌';
            } else if (segmentVal < 5.0 * segPercent) {
                barSegments[segments - segment] = '▋';
            } else if (segmentVal < 6.0 * segPercent) {
                barSegments[segments - segment] = '▊';
            } else if (segmentVal < 7.0 * segPercent) {
                barSegments[segments - segment] = '▉';
            } else {
                barSegments[segments - segment] = '█';
            }
        }

        String finalBar = "";
        for (char seg : barSegments) {
            finalBar += seg;
        }

        return finalBar;
    }
}
