package com.rech.rpg.map.event;

import java.util.Scanner;

import com.rech.rpg.Menu;
import com.rech.rpg.entity.Player;

public class StatusEffectEvent extends Event{
	protected enum statusEffect{
		DAMAGE,
		POISON,
		DECAY,
		BINDING,
		CURSE
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
		
		eventMenu.setMenuInfo(description);
<<<<<<< Updated upstream
		switch(effect) {
		case POISON:
			eventMenu.message("You are poisoned!");
			break;
=======
		eventMenu.display(true);
		
		switch(effect) {
>>>>>>> Stashed changes
		case DAMAGE:
			eventMenu.message("You take " + effectValue + " damage.");
			player.damage(effectValue);
			break;
		case POISON:
			eventMenu.message("You are poisoned!", input); //will damage over time
			break;
		case DECAY:
			eventMenu.message("You are weakened by decay!", input); //will reduce STR and DEF attributes
			break;
		case BINDING:
			eventMenu.message("You are affected by binding!", input); //will reduce MGC and RES attributes
			break;
		case CURSE:
			eventMenu.message("You are cursed!", input); //reduces LUK attribute
			break;
		}
		eventMenu.display();	
		
		input.nextLine();
	}
}
