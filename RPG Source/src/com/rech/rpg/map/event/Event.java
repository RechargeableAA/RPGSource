package com.rech.rpg.map.event;

import java.util.ArrayList;
import java.util.Scanner;

import com.rech.rpg.Player;
import com.rech.rpg.item.Item;
import com.rech.rpg.map.event.StatusEffectEvent.statusEffect;

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
	
	
	public Event(String name, String description) {
		this.name = name;
		this.description = description;
		eventList.add(this);
	}

	public abstract void runEvent(Scanner input, Player player);
	
	public static ArrayList<Event> getEvents() {
		return eventList;
	}
	
	/**
	 * Events - must be within event class to initialize
	 */
	
	/**
	 * AMBUSH
	 */
	public static AmbushEvent HUMANAMBUSH1 = new AmbushEvent("Human Ambush", 
			"\"You walk outside of the gates of \"+player.getLocation()+\", when you hear a desparate cry for help.\\n\"\r\n"
			+ "You dash over to see who is in trouble.\\nYou see a \"+enemy+\" slouched against the wall of a building.\\nAs you reach out to see if he has a pulse, \"\r\n"
			+ "he punches you in the face (-1 HP)!\\nYou Stagger back as the \"+enemy+\" stands up revealing a \"+enemyWeapon.getName()+\". \\n\\n'Ha ha ha, the look on your face,'\\nhe laughs,\\n\\n\"\r\n"
			+ "'Why don't you hand over the gold in that backpack of yours?'\\n\\n", 
			"human", 100);
	
	public static final AmbushEvent HUMANAMBUSH2 = new AmbushEvent("Human Ambush", 
			"As you walk across the bridge just outside of town, you come across a small building made of brick.\nThere aren't many people living just outside of town, so you decide to investigate.\n"
		  + "Just before you get to the entrance you hear a loud scraping noise. You look over to see a human, grinding his weapon against the brick as he approaches.\n\n'Not from around here are ya?'\nHe says, grinning.\n\n"
		  + "He's getting closer",
			"human", 100);
	
	public static final AmbushEvent ORCAMBUSH = new AmbushEvent("Orc Ambush", 
			"You find a small opening in the mountain face just ahead. You decide to investigate.\nInside you find distiguished torches.\n'Odd.' you say to yourself aloud.\nSomething falls over just outside of sight, deeper in"
			+ " the cave.\nYou walk further into the dark cave...\n~woosh~ You duck as a weapon swings over your head.\n\n'Human! You don't belong here!'\nAn orc emerges from the depths of the cave darkness. He lifts his"
			+ "weapon again.", 
					"orc", 100);
	
	/**
	 * STATUS EFFECT
	 */
	public static final StatusEffectEvent ACIDLAKE = new StatusEffectEvent("Green Lake", 
			"You find a shortcut down a valley, this'll definitely cut the distance alot shorter.\\nHalfway through the valley, there is a pond of bright green water in the way. You've gotten this far, so you decide to walk through it.\"\r\n"
		  + "\nWhen you finally get across the pond, your legs begin to burn. You're not sure that was water anymore...", 
		  StatusEffectEvent.statusEffect.DAMAGE, 6);
	
	public static final StatusEffectEvent CLIFF = new StatusEffectEvent("Cliff", 
			"You come across a small steep cliff where the path connects down alot closer.\\nYou attempt to climb down the cliff, but lose your footing and land on your back. Ouch...", 
		  StatusEffectEvent.statusEffect.DAMAGE, 6);
	
	public static final StatusEffectEvent POISONBERRIES = new StatusEffectEvent("Berries", 
			"You find a berry bush that you haven't noticed before. You seem to recognise the berries.\nYou decide to eat a few to recover some of the energy that you've lost traveling.\"\r\n"
		  + "\nYou fall to your knees and vomit onto the ground... Tasted good going down, not the other way though...", 
		  StatusEffectEvent.statusEffect.POISON, 8);
}
