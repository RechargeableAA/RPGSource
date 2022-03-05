package com.rech.rpg.map.event;

import java.util.Scanner;

import com.rech.rpg.Menu;
import com.rech.rpg.Player;
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

	@Override
	public void runEvent(Scanner input, Player player) {
		Menu eventMenu = new Menu("EVENT");
		
		eventMenu.prompt.add(description);
		eventMenu.prompt.add("[RUN][DIE]");
		//What do you do? [attack] [flee] [luck]
		
		while(true) {
			eventMenu.display();
			
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
	
	
	public static final AmbushEvent HUMANAMBUSH1 = new AmbushEvent("Human Ambush", 
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
	
	public static final AmbushEvent ORCAMBUSH = new AmbushEvent("Human Ambush", 
			"You find a small opening in the mountain face just ahead. You decide to investigate.\nInside you find distiguished torches.\n'Odd.' you say to yourself aloud.\nSomething falls over just outside of sight, deeper in"
			+ " the cave.\nYou walk further into the dark cave...\n~woosh~ You duck as a weapon swings over your head.\n\n'Human! You don't belong here!'\nAn orc emerges from the depths of the cave darkness. He lifts his"
			+ "weapon again.", 
					"orc", 100);

	
}
