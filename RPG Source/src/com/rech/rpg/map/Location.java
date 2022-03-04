package com.rech.rpg.map;

import java.util.Scanner;

import com.rech.rpg.Player;

/**
 * Locations are points on the map. Can be Towns, Random events, or Wild
 */
public abstract class Location {
	String name;
	String description;
	public static enum Direction{
		NORTH,
		SOUTH,
		EAST,
		WEST
	}
	
	public Location(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	/**
	 * Converts user input into a direction to be used by the interact method
	 * @param input - Input scanner
	 * @return the input of the user converted into NORTH, SOUTH, EAST, WEST
	 */
	public static Direction userInputToDirection(Scanner input) {
		while(true) {
			String rawInput = input.nextLine();

			
			if(rawInput.equalsIgnoreCase(Direction.NORTH.toString())) {
				return Direction.NORTH;
			}else if(rawInput.equalsIgnoreCase(Direction.SOUTH.toString())) {
				return Direction.SOUTH;
			}else if(rawInput.equalsIgnoreCase(Direction.EAST.toString())) {
				return Direction.EAST;
			}else if(rawInput.equalsIgnoreCase(Direction.WEST.toString())) {
				return Direction.WEST;
			}
			
			System.out.println(rawInput+ " is not a direction.");
		}
	}
	
	public abstract void interact(Scanner input, Player player);
	
	public abstract String getSurroundings();

}
