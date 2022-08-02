package com.rech.rpg.map.location;

import java.util.Scanner;

import com.rech.rpg.Menu;
import com.rech.rpg.Player;
import com.rech.rpg.entity.Enemy;
import com.rech.rpg.map.Map.Direction;


/**
 * Wilderness locations are areas usually containing enemies/animals/people/items/puzzles/etc
 * @author Nolan DeMatteis
 *
 */
public class Wilderness extends Location{
	private Enemy[] enemies;

	private Wilderness(String name, String description, Enemy[] enemies) {
		super(name, description);
		this.enemies = enemies;
	}

	@Override
	public void interact(Scanner input, Direction directionSelection, Player player) {
		
	}

	@Override
	public String getSurroundings() {
		String surroundings = "There's ";
		for(int enCount = 0; enCount < enemies.length; enCount++) {
			if(enemies.length == 1) {
				surroundings += "an enemy " + enemies[enCount].getRace() + ".";
			}else if(enCount+1 == enemies.length) {
				surroundings += "and an enemy " + enemies[enCount].getRace() + ".";
			}else {
				surroundings += "an enemy " + enemies[enCount].getRace() + ", ";
			}
		}
		return surroundings;
	}
	
	@Override
	public void locationMenu(Player player, Scanner input) {
		Menu locationMenu = new Menu(getName().toUpperCase());
		
		while(true) {
			locationMenu.clearPrompts();
			locationMenu.setMenuInfo(getDescription() + " " + getSurroundings());
			
			for(int enCount = 0; enCount < enemies.length; enCount++) {
				locationMenu.addPrompt(enCount+"", enemies[enCount].getRace().name());
			}
			
			locationMenu.addPrompt("BACK");
			
			locationMenu.display();
			String optionSelection = input.nextLine().toUpperCase();
			
			switch(optionSelection) {
			
				case "BACK":
					return;
				default: 
					if(Location.directionEnumContains(optionSelection)) {
						Direction direction = Direction.valueOf(optionSelection);
						interact(input, direction, player); // I dont like having to pass the direction to the next menu, but thats the only solution i have atm
					}else {
						locationMenu.message("You don't know what " + optionSelection + " means.");
					}
					break;
			}
		}
	}
	
	public static Wilderness generateWilderness() {
		Wilderness wildy = new Wilderness(
				"Wilderness",
				"You're surrounded by trees.",
				new Enemy[]
						{Enemy.bandit}
				);
		return wildy;
	}

}
