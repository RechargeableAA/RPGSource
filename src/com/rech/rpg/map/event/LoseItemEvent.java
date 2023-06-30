package com.rech.rpg.map.event;

import com.rech.rpg.Main;
import com.rech.rpg.Menu;

public class LoseItemEvent extends Event{

	protected LoseItemEvent(String name, String description) {
		super(name, description);
	}

	@Override
	public void runEvent(Main RPGS) {
		Menu eventMenu = new Menu("EVENT");
		
		eventMenu.setMenuInfo(description);
		eventMenu.display();

		RPGS.getInput().nextLine();
	}

	
	
}
