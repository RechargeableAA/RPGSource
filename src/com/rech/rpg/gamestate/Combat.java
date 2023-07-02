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

	private Enemy enemy;
	Menu cbMenu = new Menu("Combat");

	public Combat(Enemy enemy){
		this.enemy = enemy;
	}


	@Override
	public void enter(Main RPGS) {
		cbMenu.clearScreen();
		cbMenu.clearAllMenu();
		cbMenu.setMenuInfo("Player Health: " + RPGS.getPlayer().getHealth() + " Enemy Health: " + enemy.getHealth());
		cbMenu.addPrompt("[A]ttack");
		cbMenu.addPrompt("[B]ackpack");
		cbMenu.addPrompt("[R]un");
		cbMenu.display();

	}

	@Override
	public void update(Main RPGS) {

		// while loop to ensure the player has chosen a valid option
		boolean playerTurn = true;
		while(playerTurn) {
			// Player's turn
			String optionSelection = RPGS.getInput().nextLine().toString();
			switch(optionSelection.toUpperCase()) {
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
					cbMenu.alert("You don't know  what "+optionSelection+" means.");
					break;
			}
		}

		//Enemy dies
		if(enemy.getHealth() <= 0) {
			cbMenu.message("The " + enemy.getRace() +" falls to the ground with a loud thud.");
			for(Item item : enemy.getInventory().getItems()) {
				if(item != null) {
					RPGS.getPlayer().getInventory().pickup(item);
					cbMenu.message("You find a " + item.getName());
				}
			}
			cbMenu.message("You gained " + enemy.getInventory().getCoins() + " coins.");
			RPGS.getPlayer().getInventory().addCoins(enemy.getInventory().getCoins());
			RPGS.enterGameState(RPGS.getCurrentLocation().getGameState());
		}

		//Enemy's turn
		cbMenu.message("The " + enemy.getRace() + " swings their " + enemy.getEquipped().getName() + " at you dealing " + attack(enemy, RPGS.getPlayer()) + ".");

		//Player dies
		if(RPGS.getPlayer().getHealth() <= 0) {
			cbMenu.message("Oh dear, you are dead. dummy");
			RPGS.setPlayer(new Player(RPGS.getPlayer().getName())); // reset ur player i guess
			RPGS.enterGameState(new MainMenu());
		}

		cbMenu.alert("");
		enter(RPGS);
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
}
