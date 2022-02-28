package com.rech.rpg.map;

import java.util.ArrayList;

public class Map {

	private Location[] map;
	private static final int MAPSIZE = 50;
	/**
	 * Map contains an array of locations the player can move between.
	 */
	public Map() {
		map = new Location[MAPSIZE];
		//Setting town locations
		map[0] = Town.GRAYDRIFT; // default town
		
	}
	
	public Location getLocation(int locationIndex) {
		return map[locationIndex];
	}

}
