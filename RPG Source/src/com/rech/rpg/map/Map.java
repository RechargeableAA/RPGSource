package com.rech.rpg.map;

import java.util.Scanner;

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
		map[0] = TownGenerator.generateTown(); // default town
		
	}
	
	public Location getLocation(int locationIndex) {
		return map[locationIndex];
	}

	public void showTravelMenu(Player player, Scanner input) {
		Location currentLocation = getLocation(player.getSector());
		
		System.out.println(currentLocation.getDescription());
		if(currentLocation instanceof Town) {
			System.out.println(Town.class.cast(currentLocation).getSurroundings());
			Town.class.cast(currentLocation).interact(input, player);
		}
	}
	
}
