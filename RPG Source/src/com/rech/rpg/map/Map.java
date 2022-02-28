package com.rech.rpg.map;

import java.util.ArrayList;

public class Map {

	private Location[] map;
	private static final int MAPSIZE = 50;
	/**
	 * Map contains an array of locations the player can move between.
	 */
	public Map() {

		
		
		//Setting town locations
		map[0] = Town.GRAYDRIFT;
		
	}

}
