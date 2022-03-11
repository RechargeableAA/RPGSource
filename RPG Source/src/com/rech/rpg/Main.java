package com.rech.rpg;

import java.util.Scanner;

import com.rech.rpg.item.Weapon;
import com.rech.rpg.map.Map;

/*
TODO:
- Make a save file to return to previous position - DONE
- integrate inventory - DONE
- integrate shops - DONE
- integrate story
- make an method that makes random names for enemies
- make separate methods for different locations
- make loot tables with crafting materials
- side quests and main quest
- fast traveling
- make a max level cap for skills
- Turn menus into objects

- Shields
- Spells (attacks + enchantment)
*/

public class Main {

	public static Player player;
	
	public static Map map;
	
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
		introMenu(input);
		
		input = new Scanner(System.in); // need to reset scanner, otherwise an 'ENTER' is passed to the menu
		mainMenu(input);
		
		input.close();
	}

	/**
	 * Primary menu used to move through menus around the game
	 * @param input - Input scanner
	 */
	public static void mainMenu(Scanner input) {
		Menu mainMenu = new Menu("Main Menu");
		mainMenu.prompt.add("[stats] - check your statistics");
		mainMenu.prompt.add("[backpack] - look at, potions, coins, and equip weapons that you own");
		mainMenu.prompt.add("[spells] - look at and equip spellbooks owned");
		mainMenu.prompt.add("[look] - examine your surroundings");
		mainMenu.prompt.add("[travel] - move to another location");
		mainMenu.prompt.add("[OPTIONS] - load or save your game");

		while(true) {
			mainMenu.display();
			
			String optionSelection = input.nextLine().toString();

			//Logic for menu
			if(optionSelection.equalsIgnoreCase("stats")) { player.showStats(input); }
			else if (optionSelection.equalsIgnoreCase("backpack") || optionSelection.equalsIgnoreCase("inv")) { player.getInventory().showInventory(player, input); }
			else if (optionSelection.equalsIgnoreCase("spellbooks") || optionSelection.equalsIgnoreCase("spells")) { player.getInventory().showInventory(player, input); }
			else if (optionSelection.equalsIgnoreCase("look")) { map.locationMenu(player, input); }
			else if (optionSelection.equalsIgnoreCase("travel")) { map.locationMenu(player, input); }
			else if (optionSelection.equalsIgnoreCase("options")) { optionsMenu(input); }
			else {
				mainMenu.message("\nYou don't know what '"+optionSelection+"' means.\n");
			}
		}
	}

	/**
	 * Game options menu
	 * @param input - Scanner input
	 */
	public static void optionsMenu(Scanner input) {
		System.out.println("\n\nOPTIONS\n");
		System.out.println("[SAVE] Game");
		System.out.println("[LOAD] Game");
		System.out.println("[BACK]");

		while(true) {
			String in = input.nextLine().toString();

			if (in.equals("back")) {
				return;
			}else if (in.equals("save")) {
				FileManager.saveGame(player);
				return;
			}else if (in.equals("load")) {
				FileManager.loadGame();
				return;
			}else {
				System.out.println("Choose an option listed above\n");
			}
		}
	}
	
	/**
	 * First menu when game starts
	 * @param input - Input Scanner
	 * @return True if player selects new game, otherwise returns false
	 */
	private static boolean introMenu(Scanner input) {
		while(true) {
			System.out.println("[NEW] Game");
			System.out.println("[LOAD] Game");
			
			String selection = input.nextLine();
			
			clearScreen();
			
			if(selection.equalsIgnoreCase("new")) {
				newGame(input);
				return true;
			}else if(selection.equalsIgnoreCase("load")) {
				FileManager.loadGame();
				return false;
			}else {
				System.out.println(selection + " is not an option.");
			}
		}
	}
	
	/**
	 * Creates a new game with user input
	 * @param input - Input scanner
	 */
	private static void newGame(Scanner input) {
		//intro
		input.toString();

		System.out.println("\n\nYou awake in a strange land. You have no recollection of how you got here.\nYou notice a man standing over you.\n\nHello stranger...\nYou seem like you're not from around here.\nDo you have a name?\n");
		
		map = new Map();
		
		player = new Player(input.nextLine()); // creates new player with name input
		player.getInventory().pickup(Weapon.generateNewWeapon(2, 2));
		player.equip(0); // give player random weapon
		
		clearScreen();
		System.out.println("\n"+player.getName()+"? ... Can't say that's the name I would've given you... \nWell,  my name is Gavin. This is "+map.getLocation(player.getSector())+".\nI'll let you rest in my home just down the way.\nIt's not much, but I bet it'll work until you can sort yourself out.\n"
				+ "\nHere, you can have my old "+player.getEquipped().getName()+".\nYou're gonna need it. Watch yourself out there.");
	}
	
	/**
	 * Clear console with a repeated new line character. Print is completely instantly, rather then a for loop with .println
	 */
	public static final void clearScreen() {
		String newLine = "\n";
		System.out.println(newLine.repeat(30));
	}
}