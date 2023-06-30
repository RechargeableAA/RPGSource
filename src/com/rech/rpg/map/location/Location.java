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

	public abstract GameState getGameState();

	public static void travelToNewLocation(Main RPGS) {
		Location locations[] = {Town.generate(), Wilderness.generate(RPGS)};
		Random rand = new Random();

		Location newLocal = locations[rand.nextInt(locations.length)];

		RPGS.setCurrentLocation(newLocal);
		RPGS.enterGameState(newLocal.getGameState());
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
