package com.rech.rpg.map.location;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import com.rech.rpg.Main;
import com.rech.rpg.entity.Entity;
import com.rech.rpg.entity.Player;
import com.rech.rpg.gamestate.GameState;
import com.rech.rpg.gamestate.location.TownMenu;

/**
 * Locations are points on the map. Can be Towns, Random events, or Wild
 */
public abstract class Location{
	private String name;
	private String description;
	protected String surroundings;
	public static enum LocationType {TOWN, WILDERNESS};

	protected Location(String name, String description) {
		this.name = name;
		this.description = description;
		surroundings = "There's nothing around you.";
	}

	public abstract GameState getGameState(Player pl, Scanner inp);

	public static void travelToNewLocation(Player pl, Scanner inp) {
		Location locations[] = {Town.generate(), Wilderness.generate()};
		Random rand = new Random();

		Main.enterGameState(locations[rand.nextInt(locations.length)].getGameState(pl, inp));
	}


	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getSurroundings() {
		return surroundings;
	}
	
	/**
	 * Optional component for handling entities within a location (protected EntityComponent entComp; inside new location type to implement) 
	 */
	public class EntityComponent{
		private ArrayList<Entity> entities;
		
		public EntityComponent() {
			entities = new ArrayList<Entity>();
		}
		
		public void spawnEntity(Entity entity) {
			entities.add(entity);
		}
		
		public ArrayList<Entity> getEntities(){
			return entities;
		}
	}


}
