package com.rech.rpg.map.event;

import java.util.Scanner;

import com.rech.rpg.Menu;
import com.rech.rpg.entity.Player;

public class StatusEffectEvent extends Event{
	protected enum statusEffect{
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
		
		eventMenu.setMenuInfo(description);
		eventMenu.display(true);
		
		switch(effect) {
		case POISON:
			eventMenu.message("You are poisoned!", input);
			break;
		case DAMAGE:
			eventMenu.message("You take " + effectValue + " damage.", input);
			player.damage(effectValue);
			break;
		}
	}
}
