package com.rech.rpg;

import java.util.Scanner;

import com.rech.rpg.item.Weapon;

import java.util.Arrays;
import java.util.List;

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
*/

public class Main {
	//geography
	public static String[] worldMap = new String[]{"Graydrift", "Greenville", "Kilrock", "Bleakhost", "Duskwood", "Stormvalley", "Summerfelt", "Nevershore", "Southport", "Direwatch"};

	//magic
	public static String[] spellBooks = new String[] {"empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty",}; //10 slots. 0 is equipped.
	public static int[] mgcInventory = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
	public static String[] mgcInvElement = new String[] {"none", "none", "none", "none", "none", "none", "none", "none", "none", "none"}; //element aligned to each slot

	public static Player player;
	
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
		optionsMenu(input, true);
		//intro
		System.out.println("\n\nHello stranger...\nYou seem like you're not from around here.\nDo you have a name?\n");
		input.toString();

		player = new Player(input.next()); // creates new player with name input
		player.equip(Weapon.generateNewWeapon(2, 2));
		
		System.out.println("\n"+player.getName()+"? ... Can't say that's the name I would've given you... \nWell,  my name is Gavin. Welcome to "+player.getLocation()+".\n I'll let you rest in my home just down the way. It's not much, but I bet it'll work until you can sort yourself out.\n");
		player.teleport("Graydrift");
		
		while(!player.isDead())
		{
			input = new Scanner(System.in); // need to reset scanner, otherwise an 'ENTER' is passed to the menu
			mainMenu(input);
		}
		
		input.close();
	}

	public static void mainMenu(Scanner input) {
		System.out.println(
				  "[stats] - check your statistics\n[backpack] - look at, potions, coins, and equip weapons that you own\n"
				+ "[spells] - look at and equip spellbooks owned\n[look] - examine your surroundings\n"
				+ "[travel] - move to another location\n"
				+ "[OPTIONS] - load or save your game\n"
				);

		
		String optionSelection = input.nextLine().toString();

		//Logic for menu
		if(optionSelection.equalsIgnoreCase("stats")) { player.showStats(); }
		else if (optionSelection.equalsIgnoreCase("backpack") || optionSelection.equalsIgnoreCase("inv")) { showInventory(input); }
		else if (optionSelection.equalsIgnoreCase("spellbooks") || optionSelection.equalsIgnoreCase("spells")) { showInventory(input); }
		else if (optionSelection.equalsIgnoreCase("look")) { showSurroundings(player.getLocation()); }
		else if (optionSelection.equalsIgnoreCase("travel")) { showTravelMenu(player.getLocation()); }
		else if (optionSelection.equalsIgnoreCase("options")) { optionsMenu(input, false); }
		else {
			System.out.println("\nYou don't know what '"+optionSelection+"' means.\n");
		}
	}

	public static void skillMenu() {
		System.out.println("\n\nYou have "+player.getPoints()+" skill point(s) to spend. Enter the name of the skill you want to add points to.\n\rOnce added, they cannot be reset, without a fee.\n\r[STR][DEF][DGE][LCK][MGC][RST]\n\r[HELP] - show descriptions for each stat.\n[BACK] - go back to the previous prompt.\n");
		//making an array and converting it into a list, so that it can check the list against the input and see if it is a valid stat to modify
		final String validate[] = new String[] {"str", "Strength", "def", "Defense", "lck", "luck", "dge", "dodge", "mgc", "magic", "rst", "resistance"};
		List<String> validStat = Arrays.asList(validate); 
		Scanner input = new Scanner(System.in);		
		String in = input.next().toString();;

		if (in.equals("help")) {//checking input for menu related navigation
			System.out.println("\nSTRENGTH = melee damage modifier\r\nDEFENSE = how much incoming damage is reduced\r\nDODGE = chance to negate damage all together\r\nLUCK = modifies how many coins and materials you can gain.\r\nMAGIC = how powerful spells will be, will use spell books that work like swords with elemental bonuses and healing\r\nRESISTANCE = like defense, but against magic/status effects");
			skillMenu();
		}else if (in.equals("back")) {
			mainMenu(input);
		}else if (validStat.contains(in) == true) { //seeing which stat to modify
			player.addSkillPoint(in);
		}else {
			System.out.println("\nYou don't know what '"+in+"' means.\n");
			skillMenu();
		}
	}

	
	public static void showInventory(Scanner input) {
		System.out.println("\n\n      INVENTORY\n");
		player.getInventory().sortInventory();
		
		//print occupied inventory slots
		int occupiedSlots = 0;
		for(int inventorySlot = 0; inventorySlot < player.getInventory().getSize(); inventorySlot++) {
			if(player.getInventory().getSlot(inventorySlot) != null) {
				System.out.println("["+inventorySlot+"] " + player.getInventory().getSlot(inventorySlot).getName());
				occupiedSlots++;
			}
		}
		
		System.out.println("Your "+player.getEquipped().getName()+" is equipped.");
		System.out.println(occupiedSlots+"/10 backpack slots used.\n");
		System.out.println("Coins: "+String.format("%,d",player.getCoins())+"gp\n"); // formats coins with a comma
		System.out.println("[EQUIP] [DROP] [BACK]");
		
		
		String optionSelection = input.nextLine().toString();
		if (optionSelection.equals("back")) {
			System.out.println("\n\n\n");
			mainMenu(input);
		}else if (optionSelection.equals("equip")) {
			equipMenu(input);
		}else if (optionSelection.equals("drop")) {
			dropItemMenu(input);
		}else {
			System.out.println("You don't know  what "+optionSelection+" means.");
			showInventory(input);
		}
		
	}
	
	private static void dropItemMenu(Scanner input) {
		//show inventory again
		for(int inventorySlot = 0; inventorySlot < player.getInventory().getSize(); inventorySlot++) {
			if(player.getInventory().getSlot(inventorySlot) != null) {
				System.out.println("["+inventorySlot+"] " + player.getInventory().getSlot(inventorySlot).getName());
			}
		}
		System.out.println("Which slot do you want to drop? [0-9] [back]");
		
		String optionSelection = input.nextLine(); 
		
		//catching string to int error, also handles option for back. probably not the best way to do this
		int intSelection = 0;
		try {
			intSelection = Integer.valueOf(optionSelection);
		}catch(NumberFormatException e) {
			if(optionSelection.equalsIgnoreCase("back")) {
				return;
			}else {
				System.out.println("You don't know  what "+optionSelection+" means.");
				equipMenu(input);
			}
		}
		
		if(intSelection >= 0 && intSelection <= 9) {
			System.out.println("Are you sure you want to drop " + player.getInventory().getSlot(intSelection).getName() + "? [y/n]");
			if(input.next().equalsIgnoreCase("y")) {
				System.out.println("You drop your " + player.getInventory().getSlot(intSelection).getName() + ".");
				player.getInventory().drop(intSelection);
			}
		}
		
		dropItemMenu(input);
		
	}

	public static void equipMenu(Scanner input){ //fromMain checks to see if you came from the backpack menu
		//show inventory again
		for(int inventorySlot = 0; inventorySlot < player.getInventory().getSize(); inventorySlot++) {
			if(player.getInventory().getSlot(inventorySlot) != null) {
				System.out.println("["+inventorySlot+"] " + player.getInventory().getSlot(inventorySlot).getName());
			}
		}
		
		System.out.println("Which slot do you want to equip? [0-9] [back]");
		
		//error catching string to int
		String optionSelection = input.nextLine(); 
		int intSelection = 0;
		try {
			intSelection = Integer.valueOf(optionSelection);
		}catch(NumberFormatException e) {
			if(optionSelection.equalsIgnoreCase("back")) {
				showInventory(input);
			}else {
				System.out.println("You don't know  what "+optionSelection+" means.");
				equipMenu(input);
			}
		}
		if(intSelection >= 0 && intSelection <= 9) {
			if(player.getInventory().getSlot(intSelection) != null) {
				if(player.getInventory().getSlot(intSelection) instanceof Weapon) { // checks to see if item is a weapon
					System.out.println("You equip your " + player.getInventory().getSlot(intSelection).getName() + ".");
					player.equip((Weapon) player.getInventory().getSlot(intSelection));
					player.getInventory().drop(intSelection);
				}else {
					System.out.println("You can't equip a " + player.getInventory().getSlot(intSelection).getName());
				}
			}else {
				System.out.println("There's nothing in that inventory slot.");
			}
		}
		equipMenu(input);
	}

	public static void showSurroundings(String where) {
		System.out.println("\n\nYou take a look at your surroundings.");
		Surroundings.setLocation(player);
		player.teleport(player.getLocation());
	}

	public static void optionsMenu(Scanner input, boolean fromStart) {
		System.out.println("\n\nOPTIONS\n");
		if (fromStart == true) {
			System.out.println("[NEW] Game");
		}else {
			System.out.println("[SAVE] Game");
		}
		System.out.println("[LOAD] Game");
		if (fromStart == false) {
			System.out.println("[BACK]");
		}
		String in = input.nextLine().toString();

		if (in.equals("new") && fromStart == true) {
			return;
		}else if (in.equals("back") && fromStart == false) {
			mainMenu(input);
		}else if (in.equals("save")) {
			FileManager.saveGame(player);
		}else if (in.equals("load")) {
			FileManager.loadGame();
		}else {
			System.out.println("Choose an option listed above\n");
			optionsMenu(input, fromStart);
		}
	}

	public static void showTravelMenu(String where) {
		//TODO
		System.out.println("WIP");
		player.teleport(player.getLocation());
	}

}