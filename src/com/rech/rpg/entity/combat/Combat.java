package com.rech.rpg.entity.combat;

import java.util.Random;
import java.util.Scanner;

import com.rech.rpg.Main;
import com.rech.rpg.Menu;
import com.rech.rpg.entity.Enemy;
import com.rech.rpg.entity.Entity;
import com.rech.rpg.entity.Player;
import com.rech.rpg.gamestate.inventory.InventoryMainMenu;
import com.rech.rpg.item.Item;

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
			cbMenu.display();
			
			// while loop to ensure the player has chosen a valid option
			boolean playerTurn = true;
			while(playerTurn) {
				// Player's turn
				String optionSelection = input.nextLine().toString();
				switch(optionSelection.toUpperCase()) {
				case "ATTACK":
				case "A":
					System.out.println("You swing your " + player.getEquipped().getName() + " and deal " + attack(player, enemy));
					playerTurn = false;
					break;
				case "BACKPACK":
				case "INV":
				case "B":
					Main.enterGameState(new InventoryMainMenu(player.getInventory(), input));
					break;
				case "RUN":
				case "R":
					return false;
				default:
					cbMenu.alert("You don't know  what "+optionSelection+" means.", input);
					break;
				}
			}
			
			//Enemy dies
			if(enemy.getHealth() <= 0) {
				cbMenu.alert("The " + enemy.getRace() +" falls to the ground with a loud thud.", input);
				for(Item item : enemy.getInventory().getItems()) {
					if(item != null) {
						player.getInventory().pickup(item);
						cbMenu.alert("You find a " + item.getName(), input);
					}
				}
				cbMenu.alert("You gained " + enemy.getInventory().getCoins() + " coins.", input);
				player.getInventory().addCoins(enemy.getInventory().getCoins());
				fighting = false;
				return true;
			}
			
 			//Enemy's turn
			cbMenu.alert("The " + enemy.getRace() + " swings their " + enemy.getEquipped().getName() + " at you dealing " + attack(enemy, player) + ".", input);
			
			//Player dies
			if(player.getHealth() <= 0) {
				cbMenu.alert("Oh dear, you are dead.", input);
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
