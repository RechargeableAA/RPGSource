package com.rech.rpg.map.event;

import java.util.Scanner;

import com.rech.rpg.Menu;
import com.rech.rpg.Player;

public class StatusEffectEvent extends Event{
	private enum statusEffect{
		DAMAGE,
		POISON
	};

	statusEffect effect;
	int effectValue;
	
	protected StatusEffectEvent(String name, String description, statusEffect effect, int effectValue) {
		super(name, description);
		this.effect = effect;
		this.effectValue = effectValue;
	}

	@Override
	public void runEvent(Scanner input, Player player) {
		Menu eventMenu = new Menu("EVENT");
		
		eventMenu.prompt.add(description);
		switch(effect) {
		case POISON:
			eventMenu.prompt.add("You are poisoned!");
			break;
		case DAMAGE:
			eventMenu.prompt.add("You take " + effectValue + " damage.");
			break;
		}
		eventMenu.display();	
		
		input.nextLine();
	}
	
	public static final StatusEffectEvent ACIDLAKE = new StatusEffectEvent("Green Lake", 
			"You find a shortcut down a valley, this'll definitely cut the distance alot shorter.\\nHalfway through the valley, there is a pond of bright green water in the way. You've gotten this far, so you decide to walk through it.\"\r\n"
		  + "\nWhen you finally get across the pond, your legs begin to burn. You're not sure that was water anymore...", 
		  statusEffect.DAMAGE, 6);
	
	public static final StatusEffectEvent CLIFF = new StatusEffectEvent("Cliff", 
			"You come across a small steep cliff where the path connects down alot closer.\\nYou attempt to climb down the cliff, but lose your footing and land on your back. Ouch...", 
		  statusEffect.DAMAGE, 6);
	
	public static final StatusEffectEvent POISONBERRIES = new StatusEffectEvent("Berries", 
			"You find a berry bush that you haven't noticed before. You seem to recognise the berries.\nYou decide to eat a few to recover some of the energy that you've lost traveling.\"\r\n"
		  + "\nYou fall to your knees and vomit onto the ground... Tasted good going down, not the other way though...", 
		  statusEffect.POISON, 8);

}
