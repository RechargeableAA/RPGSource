package com.rech.rpg.map;

import java.util.Scanner;

import com.rech.rpg.Menu;
import com.rech.rpg.Player;

public class Map {

	private Location[] map;
	private static final int MAPSIZE = 50;
	/**
	 * Map contains an array of locations the player can move between.
	 */
	public Map() {
		map = new Location[MAPSIZE];
		//Setting town locations
		map[0] = Town.generateTown(); // default town
		map[1] = Town.generateTown(); // default town
		map[2] = Town.generateTown(); // default town
		
	}
	
	public Location getLocation(int locationIndex) {
		return map[locationIndex];
	}

	public void locationMenu(Player player, Scanner input) {
		Location currentLocation = getLocation(player.getSector());
		Menu locationMenu = new Menu(currentLocation.getName().toUpperCase());

		locationMenu.prompt.add(currentLocation.getDescription());
		locationMenu.prompt.add(currentLocation.getSurroundings());
		locationMenu.prompt.add("[TRAVEL] to another location"); // move forward in map array

		
		
		while(true) {
			locationMenu.display();
			
			switch(input.nextLine().toUpperCase()) {
			case "TRAVEL":
				travelMenu(player, input);
			break;
			default:
				currentLocation.interact(input, player);
				break;
			}
		}
		
	}
	
	private void travelMenu(Player player, Scanner input) {
		Menu travelMenu = new Menu("TRAVEL");
		travelMenu.prompt.add("Where do you want travel?");
		travelMenu.prompt.add("[EAST] to " + map[player.getSector()+1].name);
		travelMenu.prompt.add("[WEST] to " + map[player.getSector()-1].name);
		travelMenu.display();
	}
	
}
