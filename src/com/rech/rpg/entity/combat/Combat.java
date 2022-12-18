package com.rech.rpg.entity.combat;

import java.util.Random;
import java.util.Scanner;

import com.rech.rpg.Menu;
import com.rech.rpg.entity.Enemy;
import com.rech.rpg.entity.Entity;
import com.rech.rpg.entity.Player;

public class Combat {
	
	public Combat() {	//public Combat( maybe require a stage or something when initializing combat, would allow for effects of the stage to influence combat like poison marsh )
	}
	
	/**
	 * Enter combat with player and an enemy
	 * @param player
	 * @param enemy
	 * @param input
	 * @return if the player won the fight
	 */
	public boolean enterCombat(Player player, Enemy enemy, Scanner input) {
		boolean fighting = true;
		Menu cbMenu = new Menu("Combat");
		while(fighting) {
			cbMenu.clearAll();
			cbMenu.setMenuInfo("Player Health: " + player.getHealth() + " Enemy Health: " + enemy.getHealth());
			cbMenu.addPrompt("[A]ttack");
			cbMenu.addPrompt("[B]ackpack");
			cbMenu.addPrompt("[R]un");
			cbMenu.display(true);
			
			// while loop to ensure the player has chosen a valid option
			boolean playerTurn = true;
			while(playerTurn) {
				// Player's turn
				String optionSelection = input.nextLine().toString();
				switch(optionSelection.toUpperCase()) {
				case "ATTACK":
				case "A":
					cbMenu.message("You swing your " + player.getEquipped().getName() + " and deal " + attack(player, enemy), input);
					playerTurn = false;
					break;
				case "BACKPACK":
				case "INV":
				case "B":
					player.getInventory().showInventory(player, input);										//Maybe add pokemon item use mechanic, where if u use an item it uses your turn
					break;
				case "RUN":
				case "R":
					return false;
				default:
					cbMenu.message("You don't know  what "+optionSelection+" means.", input);
					break;
				}
				break;
			}
			
			if(enemy.getHealth() <= 0) {
				cbMenu.message("The " + enemy.getRace() +" falls to the ground with a loud thud.", input);
				fighting = false;
				return true;
			}
			
 			//Enemy's turn
			cbMenu.message("The " + enemy.getRace() + " swings their " + enemy.getEquipped().getName() + " at you dealing " + attack(enemy, player) + ".", input);
			
			if(player.getHealth() <= 0) {
				cbMenu.message("Oh dear, you are dead.", input);
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
