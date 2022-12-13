package com.rech.rpg.map.location;

import java.util.ArrayList;
import java.util.Scanner;

import com.rech.rpg.entity.Entity;
import com.rech.rpg.entity.Player;
import com.rech.rpg.entity.menu.Menu;

/**
 * Locations are points on the map. Can be Towns, Random events, or Wild
 */
public abstract class Location {
	private String name;
	private String description;
	protected String surroundings;
	public static enum LocationType {TOWN, WILDERNESS};
	
	public Location(String name, String description) {
		this.name = name;
		this.description = description;
		surroundings = "There's nothing around you.";
	}
	
	/**
	 * The menu for when the player uses the look option in the mainmenu; allows interaction with location
	 * @param player
	 * @param input
	 */
	public abstract void locationMenu(Player player, Scanner input);
	
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
	 * @author Nolan
	 *
	 */
	protected class EntityComponent{
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
