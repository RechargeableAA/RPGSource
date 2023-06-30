package com.rech.rpg.entity.combat;

import java.util.Random;

import com.rech.rpg.Main;
import com.rech.rpg.Menu;
import com.rech.rpg.entity.Enemy;
import com.rech.rpg.entity.Entity;
import com.rech.rpg.gamestate.inventory.InventoryMainMenu;
import com.rech.rpg.item.Item;

public class Combat {
	
	public Combat() {	//public Combat( maybe require a stage or something when initializing combat, would allow for effects of the stage to influence combat like poison marsh )
	}
	
	/**
	 * Enter combat with player and an enemy
	 * @param enemy
	 * @return if the player won the fight
	 */
	public boolean enterCombat(Main RPGS, Enemy enemy) {
		boolean fighting = true;
		Menu cbMenu = new Menu("Combat");
		while(fighting) {
			cbMenu.clearAllMenu();
			cbMenu.setMenuInfo("Player Health: " + RPGS.getPlayer().getHealth() + " Enemy Health: " + enemy.getHealth());
			cbMenu.addPrompt("[A]ttack");
			cbMenu.addPrompt("[B]ackpack");
			cbMenu.addPrompt("[R]un");
			cbMenu.display();
			
			// while loop to ensure the player has chosen a valid option
			boolean playerTurn = true;
			while(playerTurn) {
				// Player's turn
				String optionSelection = RPGS.getInput().nextLine().toString();
				switch(optionSelection.toUpperCase()) {
				case "ATTACK":
				case "A":
					System.out.println("You swing your " + RPGS.getPlayer().getEquipped().getName() + " and deal " + attack(RPGS.getPlayer(), enemy));
					playerTurn = false;
					break;
				case "BACKPACK":
				case "INV":
				case "B":
					RPGS.enterGameState(new InventoryMainMenu());
					break;
				case "RUN":
				case "R":
					return false;
				default:
					cbMenu.alert("You don't know  what "+optionSelection+" means.", RPGS.getInput());
					break;
				}
			}
			
			//Enemy dies
			if(enemy.getHealth() <= 0) {
				cbMenu.alert("The " + enemy.getRace() +" falls to the ground with a loud thud.", RPGS.getInput());
				for(Item item : enemy.getInventory().getItems()) {
					if(item != null) {
						RPGS.getPlayer().getInventory().pickup(item);
						cbMenu.alert("You find a " + item.getName(), RPGS.getInput());
					}
				}
				cbMenu.alert("You gained " + enemy.getInventory().getCoins() + " coins.", RPGS.getInput());
				RPGS.getPlayer().getInventory().addCoins(enemy.getInventory().getCoins());
				fighting = false;
				return true;
			}
			
 			//Enemy's turn
			cbMenu.alert("The " + enemy.getRace() + " swings their " + enemy.getEquipped().getName() + " at you dealing " + attack(enemy, RPGS.getPlayer()) + ".", RPGS.getInput());
			
			//Player dies
			if(RPGS.getPlayer().getHealth() <= 0) {
				cbMenu.alert("Oh dear, you are dead.", RPGS.getInput());
				fighting = false;
				return false;
			}
		}
		return false;
	}
	
	/**
	 * Attack an entity with another entity
	 * @param attacker
	 * @param defender
	 * @return The damage dealt
	 */
	private int attack(Entity attacker, Entity defender) {
		Random rand = new Random();
		int damageDealt = Math.max(0, rand.nextInt(attacker.attackDamage())-rand.nextInt(defender.getDefense()));
		defender.damage(damageDealt);
		return damageDealt;
	}
	
	public void endCombat() {
		
	}
	
}
