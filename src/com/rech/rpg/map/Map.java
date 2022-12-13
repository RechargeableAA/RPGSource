package com.rech.rpg.map;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

import com.rech.rpg.entity.Entity;
import com.rech.rpg.entity.Player;
import com.rech.rpg.entity.menu.Menu;
import com.rech.rpg.map.event.Event;
import com.rech.rpg.map.location.Location;
import com.rech.rpg.map.location.Town;
import com.rech.rpg.map.location.Wilderness;

public class Map {
	private HashMap<Point, Location> locations;
	//since entities will be contained within locations, only the player needs to be tracked this way
	private Point playerLocation;
	
	public Map() {
		locations = new HashMap<Point, Location>();
		playerLocation = new Point(0,0);
		locations.put(playerLocation, Town.generateTown()); // start in town by default
		generateAroundPoint(playerLocation);
	}
	
	private Location generateLocation(Location.LocationType locationType) {
		switch(locationType) {
		case TOWN:
			Town newTown = Town.generateTown();
			return newTown;
		case WILDERNESS:
			Wilderness newWild = Wilderness.generateWilderness();
			return newWild;
		default:
			System.err.println("An incorrect Location Type was passed into generateLocation method");
			return null;
		}
	}
	
	/**
	 * Generates locations around a point; 3x3 square. Does NOT overwrite existing locations
	 * @param position
	 */
	private void generateAroundPoint(Point position) {
		for(int x = -1; x <= 1; x++) {
			for(int y = -1; y <= 1; y++) {
				if(!locations.containsKey(new Point(position.x+x, position.y+y))) { // check if location is already generated
					Random rand = new Random(); // pick a random location type
					locations.put(new Point(position.x+x, position.y+y), generateLocation(Location.LocationType.values()[rand.nextInt(Location.LocationType.values().length)]));
				}
			}
		}
	}
	
	public void movePlayer(Point position) {
		generateAroundPoint(position);
		playerLocation = position;
	}
	
	public Location getLocation(Point pos) {
		return locations.get(pos);
	}
	
	public Point getPlayerPosition() {
		return playerLocation;
	}
}
