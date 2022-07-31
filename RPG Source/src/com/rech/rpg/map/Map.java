package com.rech.rpg.map;

import java.awt.Point;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

import com.rech.rpg.Menu;
import com.rech.rpg.Player;
import com.rech.rpg.map.event.Event;

public class Map {
	public static enum Direction{
		NORTH,
		SOUTH,
		EAST,
		WEST
	}
	
	private static final int eventProbability = 100; // 30 out of 100
	
	//ArrayList containing an arraylist of locations, top array is x contained arrays are y
	private HashMap<Point, Location> hashMap;
	/**
	 * Map contains an array of locations the player can move between.
	 */
	public Map() {
		hashMap = new HashMap<Point, Location>();
	}
	
	/**
	 * Tests for uninitialized locations around the player, then generates them. Does not overwrite already generated locations
	 * @param player
	 */
	public void generateEmptySurroundingLocations(Player player) {
		for(int x = player.getPosition().x-1; x <= player.getPosition().x+1; x++) {
			for(int y = player.getPosition().y-1; y <= player.getPosition().y+1; y++) {
				if(!hashMap.containsKey(new Point(x,y))) {
					setLocation(new Point(x,y), Town.generateTown()); // default town
				}
			}	
		}
	}
	
	/**
	 * Method for modifying map locations
	 * @param position - position of location on map
	 * @param location - the new location
	 */
	private void setLocation(Point position, Location location) {
		hashMap.put(position, location);
	}
	
	/**
	 * get location object of a position on map
	 * @param position - requested position
	 * @return location on the map at requested position
	 */
	public Location getLocation(Point position) {
		if(hashMap.get(position) == null) {
			return new Wilderness("Location doesn't exist. This is an error.", "Location doesn't exist. This is an error.");
		}
		return hashMap.get(position);
	}
	/**
	 * Get location object of a x and y position on the map
	 * @param x
	 * @param y
	 * @return location on the map at requested x and y
	 */
	public Location getLocation(int x, int y) {
		if(hashMap.get(new Point(x, y)) == null) {
			return new Wilderness("Location doesn't exist. This is an error.", "Location doesn't exist. This is an error.");
		}
		return hashMap.get(new Point(x, y));
	}

	/**
	 * Menu used to travel to locations on the map
	 * @param player
	 * @param input
	 */
	public void travelMenu(Player player, Scanner input) {
		Menu travelMenu = new Menu("TRAVEL");
		travelMenu.setMenuInfo("Where do you want travel?");
		travelMenu.addPrompt("NORTH", "to " + getLocation(player.getPosition().x, player.getPosition().y+1).name);
		travelMenu.addPrompt("EAST", "to " + getLocation(player.getPosition().x+1, player.getPosition().y).name);
		travelMenu.addPrompt("SOUTH", "to " + getLocation(player.getPosition().x, player.getPosition().y-1).name);
		travelMenu.addPrompt("WEST", "to " + getLocation(player.getPosition().x-1, player.getPosition().y).name);
		travelMenu.addPrompt("BACK");
		travelMenu.display();
		
		while(true) {
			String optionSelection = input.nextLine().toUpperCase();

			switch(optionSelection) {
			case "BACK":
				return;
			default:
				if(Location.directionEnumContains(optionSelection)) {
					Direction direction = Direction.valueOf(optionSelection);
					travelMenu.clearPrompts();
					travelMenu.clearMenuInfo();
					player.travel(direction);
					generateEmptySurroundingLocations(player);
					travelMenu.message("You begin to travel " + direction.name() + " towards " + getLocation(player.getPosition()).name);
					input.nextLine();
					procEvent(input, player);
					return;
				}else {
					travelMenu.message("You don't know what " + optionSelection + " means.");
				}
				break;
			}
		}
	}
	
	/**
	 * MODIFY LATER SHOULD BE MOVED SOMEWHERE ELSE. Randomly tests for an event, rarity is eventprobabilty/100
	 * @param input
	 * @param player
	 */
	private void procEvent(Scanner input, Player player) {
		Random random = new Random();
		if(random.nextInt(100) <= eventProbability) {
			Event.getEvents().get(random.nextInt(Event.getEvents().size())).runEvent(input, player);
		}
	}
}
