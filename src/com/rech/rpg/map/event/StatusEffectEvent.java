package com.rech.rpg.map.event;

import com.rech.rpg.Main;
import com.rech.rpg.Menu;

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
	public void runEvent(Main RPGS) {
		Menu eventMenu = new Menu("EVENT");
		
		eventMenu.setMenuInfo(description);
		eventMenu.display();
		
		switch(effect) {
		case POISON:
			eventMenu.alert("You are poisoned!", RPGS.getInput());
			break;
		case DAMAGE:
			eventMenu.alert("You take " + effectValue + " damage.", RPGS.getInput());
			RPGS.getPlayer().damage(effectValue);
			break;
		}
	}
}
