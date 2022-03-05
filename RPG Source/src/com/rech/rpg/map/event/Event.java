package com.rech.rpg.map.event;

import java.util.ArrayList;
import java.util.Scanner;

import com.rech.rpg.Menu;
import com.rech.rpg.Player;
import com.rech.rpg.item.Item;

/**
 * Events occur inbetween locations
 * @author Nolan DeMatteis
 *
 */

public abstract class Event{
	
	protected String 
	name,
	description;
	private static ArrayList<Event> eventList = new ArrayList<Event>();
	
	
	protected Event(String name, String description) {
		this.name = name;
		this.description = description;
		eventList.add(this);
	}

	public abstract void runEvent(Scanner input, Player player);
	
	public static ArrayList<Event> getEvents() {
		return eventList;
	}
	
}
