package com.rech.rpg.map.event;

import java.util.Scanner;

import com.rech.rpg.entity.Player;
import com.rech.rpg.entity.menu.Menu;

public class LoseItemEvent extends Event{

	protected LoseItemEvent(String name, String description) {
		super(name, description);
	}

	@Override
	public void runEvent(Scanner input, Player player) {
		Menu eventMenu = new Menu("EVENT");
		
		eventMenu.setMenuInfo(description);
		eventMenu.display(true);	
		
		input.nextLine();		
	}

	
	
}
