package com.rech.rpg.map;

/**
 * Locations are points on the map. Can be Towns, Random events, or Wild
 */
public abstract class Location {
	String locationName;
	
	public Location(String name) {
		locationName = name;
	}
	
	public String getName() {
		return locationName;
	}
	
	public abstract String getSurroundings();
}
