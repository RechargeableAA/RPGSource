package com.rech.rpg.map;

import java.util.ArrayList;
import java.util.Scanner;

import com.rech.rpg.Player;

/**
 * Events occur inbetween locations
 * @author Nolan DeMatteis
 *
 */

public abstract class Event{
	
	private String 
	name,
	description;
	private ArrayList<Event> eventList = new ArrayList<Event>();
	
	
	private Event(String name, String description) {
		this.name = name;
		this.description = description;
		eventList.add(this);
	}

	public abstract void runEvent(Scanner input, Player player);
	
	
	public static class Ambush extends Event{

		public Ambush(String name, String description) {
			super(name, description);
		}

		@Override
		public void runEvent(Scanner input, Player player) {
			
		}
	}
}
