package com.rech.rpg.map;

import java.util.Random;
import java.util.Scanner;

import com.rech.rpg.Menu;
import com.rech.rpg.Player;

public class Map {
	public static enum Direction{
		NORTH,
		SOUTH,
		EAST,
		WEST
	}
	
	private Location[] map;
	private static final int MAPSIZE = 50;
	private static final int eventProbability = 30; // 30 out of 100
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
		
		while(true) {
			locationMenu.prompt.clear();
			locationMenu.prompt.add(currentLocation.getDescription());
			locationMenu.prompt.add(currentLocation.getSurroundings());
			locationMenu.prompt.add("[TRAVEL] to another location"); // move forward in map array
			
			locationMenu.display();
			String optionSelection = input.nextLine().toUpperCase();
			
			switch(optionSelection) {
				case "TRAVEL":
					travelMenu(player, input);
					//update menu
					currentLocation = getLocation(player.getSector());
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
		travelMenu.prompt.add("Where do you want travel?");
		travelMenu.prompt.add("[EAST] to " + map[player.getSector()+1].name);
		travelMenu.prompt.add("[WEST] to " + map[player.getSector()-1].name);
		travelMenu.prompt.add("[BACK]");
		travelMenu.display();
		
		while(true) {
			String optionSelection = input.nextLine().toUpperCase();

			switch(optionSelection) {
			case "BACK":
				return;
			default:
				if(Location.directionEnumContains(optionSelection)) {
					Direction direction = Direction.valueOf(optionSelection);
					travelMenu.prompt.clear();
					player.travel(direction);
					travelMenu.message("You begin to travel " + direction.name() + " towards " + map[player.getSector()].name);
					procEvent();
					input.nextLine(); // pause
					return;
				}else {
					travelMenu.message("You don't know what " + optionSelection + " means.");
				}
				break;
			}
		}
	}

	private void procEvent() {
		Random random = new Random();
		if(random.nextInt(100) <= eventProbability) {
			
		}
	}
}
