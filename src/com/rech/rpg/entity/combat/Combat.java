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
	
	public void enterCombat(Player player, Enemy enemy, Scanner input) {
		boolean fighting = true;
		Menu cbMenu = new Menu("Combat");
		while(fighting) {
			cbMenu.clearAll();
			cbMenu.addPrompt("Attack");
			cbMenu.addPrompt("Backpack");
			cbMenu.addPrompt("Run");
			cbMenu.display(true);
			
			boolean playerTurn = true;
			while(playerTurn) {
				// Player's turn
				String optionSelection = input.nextLine().toString();
				switch(optionSelection.toUpperCase()) {
				case "ATTACK":
					cbMenu.message("You swing your " + player.getEquipped().getName() + " and deal " + attack(player, enemy));
					playerTurn = false;
					break;
				case "BACKPACK":
				case "INV":
					player.getInventory().showInventory(player, input);										//Maybe add pokemon item use mechanic, where if u use an item it uses your turn
					break;
				case "RUN":
					return;
				default:
					cbMenu.message("You don't know  what "+optionSelection+" means.");
					break;
				}
				break;
			}
			
			if(enemy.getHealth() <= 0) {
				cbMenu.message("The " + enemy.getRace() +" falls to the ground with a loud thud.");
				fighting = false;
				break;
			}
			
			input.nextLine();
			
			//Enemy's turn
			cbMenu.message("The " + enemy.getRace() + " swings their " + enemy.getEquipped().getName() + " at you dealing " + attack(enemy, player) + ".");
			
			if(player.getHealth() <= 0) {
				cbMenu.message("Oh dear, you are dead.");
				fighting = false;
				break;
			}
			
			input.nextLine();
		}
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
