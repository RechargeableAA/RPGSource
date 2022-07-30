package com.rech.rpg.map;

import java.awt.Point;
import java.util.ArrayList;
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
	
	private static final int MAPSIZE = 50;
	private static final int eventProbability = 100; // 30 out of 100
	
	//ArrayList containing an arraylist of locations, top array is x contained arrays are y
	private HashMap<Point, Location> map;
	/**
	 * Map contains an array of locations the player can move between.
	 */
	public Map() {
		map = new HashMap<Point, Location>();
		//Setting town locations
		setLocation(new Point(0,0), Town.generateTown()); // default town
		setLocation(new Point(1,0), Town.generateTown()); // default town
		setLocation(new Point(2,0), Town.generateTown()); // default town
		setLocation(new Point(3,0), Town.generateTown()); // default town
		
	}
	
	/**
	 * Method for modifying map locations
	 * @param position - position of location on map
	 * @param location - the new location
	 */
	private void setLocation(Point position, Location location) {
		map.put(position, location);
	}
	
	/**
	 * get location object of a position on map
	 * @param position - requested position
	 * @return location on the map at requested position
	 */
	public Location getLocation(Point position) {
		if(map.get(position) == null) {
			return new Wilderness("Location doesn't exist. This is an error.", "Location doesn't exist. This is an error.");
		}
		return map.get(position);
	}
	/**
	 * Get location object of a x and y position on the map
	 * @param x
	 * @param y
	 * @return location on the map at requested x and y
	 */
	public Location getLocation(int x, int y) {
		if(map.get(new Point(x, y)) == null) {
			return new Wilderness("Location doesn't exist. This is an error.", "Location doesn't exist. This is an error.");
		}
		return map.get(new Point(x, y));
	}

	public void locationMenu(Player player, Scanner input) {
		Location currentLocation = getLocation(player.getPosition());
		Menu locationMenu = new Menu(currentLocation.getName().toUpperCase());
		
		while(true) {
			locationMenu.clearPrompts();
			locationMenu.setMenuInfo(currentLocation.getDescription() + " " + currentLocation.getSurroundings());;
			locationMenu.addPrompt("TRAVEL", "to another location"); // move forward in map array
			locationMenu.addPrompt("BACK");
			
			locationMenu.display();
			String optionSelection = input.nextLine().toUpperCase();
			
			switch(optionSelection) {
			
				case "BACK":
					return;
				case "TRAVEL":
					travelMenu(player, input);
					//update menu
					currentLocation = getLocation(player.getPosition());
					locationMenu = new Menu(currentLocation.getName().toUpperCase());
					break;
				default: 
					if(Location.directionEnumContains(optionSelection)) {
						Direction direction = Direction.valueOf(optionSelection);
						currentLocation.interact(input, direction, player); // I dont like having to pass the direction to the next menu, but thats the only solution i have atm
					}else {
						locationMenu.message("You don't know what " + optionSelection + " means.");
					}
					break;
			}
		}
	}
	
	
	
	private void travelMenu(Player player, Scanner input) {
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

	private void procEvent(Scanner input, Player player) {
		Random random = new Random();
		if(random.nextInt(100) <= eventProbability) {
			Event.getEvents().get(random.nextInt(Event.getEvents().size())).runEvent(input, player);
		}
	}
}
