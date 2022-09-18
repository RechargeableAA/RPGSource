package com.rech.rpg.map;

import java.awt.Point;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

import com.rech.rpg.Menu;
import com.rech.rpg.entity.Player;
import com.rech.rpg.map.Map.Direction;
import com.rech.rpg.map.event.Event;
import com.rech.rpg.map.location.Location;
import com.rech.rpg.map.location.Town;
import com.rech.rpg.map.location.Wilderness;

public class Map {
	public static enum Direction{
		NORTH,
		SOUTH,
		EAST,
		WEST
	}
	
	//IDK if its really necessary to list the types here, but imma do it anyways
	private static enum locationType {
			TOWN,
			WILDERNESS
	};
	
	//ArrayList containing an arraylist of locations, top array is x contained arrays are y
	private HashMap<Point, Location> hashMap;
	
	/**
	 * Map contains an array of locations the player can move between.
	 */
	public Map() {
		hashMap = new HashMap<Point, Location>();
		//default location is a town
		hashMap.put(new Point(Short.MAX_VALUE/2, Short.MAX_VALUE/2), Town.generateTown());
	}
	
	/**
	 * Tests for uninitialized locations around the player, then generates them. Does not overwrite already generated locations
	 * @param player
	 */
	public void generateEmptySurroundingLocations(Player player) {
		for(int x = player.getPosition().x-1; x <= player.getPosition().x+1; x++) {
			for(int y = player.getPosition().y-1; y <= player.getPosition().y+1; y++) {
				if(!hashMap.containsKey(new Point(x,y))) {
					Random rand = new Random();
					locationType randomLocationType = locationType.values()[rand.nextInt(locationType.values().length)];
					
					//bad way to determine what location type to use imo
					switch(randomLocationType) {
					case TOWN:
						setLocation(new Point(x,y), Town.generateTown()); // default town
						break;
					case WILDERNESS:
						setLocation(new Point(x,y), Wilderness.generateWilderness()); // default town
						break;
					}
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
			return Wilderness.generateWilderness();
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
			return Wilderness.generateWilderness();
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
		travelMenu.addPrompt("NORTH", "to " + getLocation(player.getPosition().x, player.getPosition().y+1).getName());
		travelMenu.addPrompt("EAST", "to " + getLocation(player.getPosition().x+1, player.getPosition().y).getName());
		travelMenu.addPrompt("SOUTH", "to " + getLocation(player.getPosition().x, player.getPosition().y-1).getName());
		travelMenu.addPrompt("WEST", "to " + getLocation(player.getPosition().x-1, player.getPosition().y).getName());
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
					travel(player, direction);
					generateEmptySurroundingLocations(player);
					travelMenu.message("You begin to travel " + direction.name() + " towards " + getLocation(player.getPosition()).getName(), input);
					Event.procEvent(input, player);
					return;
				}else {
					travelMenu.message("You don't know what " + optionSelection + " means.", input);
				}
				break;
			}
		}
	}
	
	public void travel(Player player, Direction direction) {
		switch(direction) {
		case EAST:
			player.setMapPosition(new Point(player.getMapPosition().x+1, player.getMapPosition().y));;
			break;
		case WEST:
			player.setMapPosition(new Point(player.getMapPosition().x-1, player.getMapPosition().y));;
			break;
		case NORTH:
			player.setMapPosition(new Point(player.getMapPosition().x, player.getMapPosition().y+1));;
			break;
		case SOUTH:
			player.setMapPosition(new Point(player.getMapPosition().x, player.getMapPosition().y-1));;
			break;
		}
	}
}
