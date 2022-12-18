package com.rech.rpg.map.event;

import java.util.Scanner;

import com.rech.rpg.Main;
import com.rech.rpg.Menu;
import com.rech.rpg.entity.Player;
import com.rech.rpg.item.Item;
import com.rech.rpg.item.Weapon;

public class AmbushEvent extends Event{
	String enemy; // this should be Enemy enemy later
	Item reward;
	int coinReward;

	public AmbushEvent(String name, String description, String enemy, Item reward) {
		super(name, description);
		this.enemy = enemy;
		this.reward = reward;
	}
	
	public AmbushEvent(String name, String description, String enemy, int coinReward) {
		super(name, description);
		this.enemy = enemy;
		this.coinReward = coinReward;
	}
	
	public String getEnemy() {
		return enemy;
	}

	@Override
	public void runEvent(Scanner input, Player player) {
		Menu eventMenu = new Menu("EVENT");
		
		eventMenu.setMenuInfo(description);
		eventMenu.addPrompt("[RUN][DIE]");
		//What do you do? [attack] [flee] [luck]
		
		while(true) {
			eventMenu.display(true);
			
			String optionSelection = input.nextLine();
			switch(optionSelection.toUpperCase()) {
			case "RUN":
				return;
			case "DIE":
				player.heal(-player.getHealth());
				return;
				
			}
		}
	}
}
