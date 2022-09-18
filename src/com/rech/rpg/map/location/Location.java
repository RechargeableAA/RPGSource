package com.rech.rpg.map.location;

import java.util.Scanner;

import com.rech.rpg.Menu;
import com.rech.rpg.entity.Player;
import com.rech.rpg.map.Map.Direction;

/**
 * Locations are points on the map. Can be Towns, Random events, or Wild
 */
public abstract class Location {
	private String name;
	private String description;
	
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
	
	public void locationMenu(Player player, Scanner input) {
		Menu locationMenu = new Menu(getName().toUpperCase());
		
		while(true) {
			locationMenu.clearPrompts();
			locationMenu.setMenuInfo("There is nothing to do here");
			locationMenu.addPrompt("BACK");
			
			locationMenu.display(true);
			String optionSelection = input.nextLine().toUpperCase();
			
			switch(optionSelection) {
				case "BACK":
					return;
			}
		}
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
	
	
	/**
	 * Test if Directions contain a string
	 * @param rawDirection - string to test
	 * @return True - Direction contains string; False - Direction does not contain String
	 */
	public static boolean directionEnumContains(String rawDirection) {
	    for (Direction d : Direction.values()) {
	        if (d.name().equals(rawDirection)) {
	            return true;
	        }
	    }

	    return false;
	}
	
	public abstract void interact(Scanner input, Direction directionSelection, Player player);
	
	public abstract String getSurroundings();


}
