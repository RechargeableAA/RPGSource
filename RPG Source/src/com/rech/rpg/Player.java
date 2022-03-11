package com.rech.rpg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.rech.rpg.entity.Entity;
import com.rech.rpg.item.Inventory;
import com.rech.rpg.item.Weapon;
import com.rech.rpg.map.Map.Direction;
import com.rech.rpg.map.Town; //temporary!!! - for debugging purposes
import com.rech.rpg.map.event.AmbushEvent;
import com.rech.rpg.entity.Enemy;


/*
 * Player class to store player info
 */
public class Player extends Entity{
	
	//stats
	private static int 
	exp, 
	points,
	coins;

	private int sector = 1;
	//private String location = Main.worldMap[sector];
	
	// Inventory
	private Inventory inventory;
	
	//Equipped item
	private Weapon equipped;

	
	public Player(String name) {
		//setting stat defaults
		level = 1;
		exp = 0;
		points = 0;
		coins = 0;
		health = 50;
		maxHealth = 50;
		strength = 1;
		defense = 1;
		dodge = 1;
		luck = 1;
		magic = 1; 
		mana = 50; 
		maxMana = 50; 
		resistance = 1;
		
		//initializing inventory
		inventory = new Inventory();
		equipped = null;
		
		// God mode for name "admin" and "no name" for null name input
		this.name = name;
		if (name.equals(null)) {
			this.name = "no name";
		}else if (name.equals("admin")) {
			coins = 999999;
			inventory.pickup(Weapon.adminBlade);
			health = 10000;
			maxHealth = 10000;
		/*}else if (name.equals("town")) {
			String out;
			for (int i = 0; i < 10; ++i) {
				out = Town.generateTown(0,0);
				System.out.println(out);
			}
			System.out.println("\n!!NOTICE!!\nGame will not procede properly.");
			name = "ERROR";
			*/
		}else if (name.equals("invtest")) {
			coins = 99100;
			inventory.pickup(Weapon.generateChanceWeapon(100, 30));
			inventory.pickup(Weapon.generateChanceWeapon(100, 40));
			inventory.pickup(Weapon.generateChanceWeapon(100, 50));
			inventory.pickup(Weapon.generateChanceWeapon(100, 60));
			inventory.pickup(Weapon.generateChanceWeapon(100, 70));
			inventory.pickup(Weapon.generateChanceWeapon(100, 80));
			inventory.pickup(Weapon.generateChanceWeapon(100, 90));
			inventory.pickup(Weapon.generateChanceWeapon(100, 100));
			health = 10;
			maxHealth = 10;
			
		}else if (name.equals("test")) {

			Enemy.sayTest(Enemy.thief);
			Enemy.sayTest(Enemy.orcBandit);
			Enemy.sayTest(Enemy.guard);
			Enemy.sayTest(Enemy.boar);
		
		}
		
		

	}
	
	
	//used during loading, this is an alternate constructor for created a player object
	public Player(String name, int level, int exp, int points, String location, int sector, int health, int maxHealth, int mana, int maxMana, int strength, int defense, int dodge, int luck, int magic, int resistance, int coins) {
		//setting stat defaults
		level = 1;
		exp = 0;
		points = 0;
		coins = 0;
		health = 50;
		maxHealth = 50;
		strength = 1;
		defense = 1;
		dodge = 1;
		luck = 1;
		magic = 1; 
		mana = 50; 
		maxMana = 50; 
		resistance = 1;
		
		//this.location = location;
		this.sector = sector;
		
		//initializing inventory
		inventory = new Inventory();
		equipped = null;
	}
	
	protected void godMode() {
		coins = 99999;
		inventory.pickup(Weapon.adminBlade);
		inventory.pickup(Weapon.testWeapon);
		health = 10000;
		maxHealth = 10000;
	}
	
	public Inventory getInventory() {
		return inventory;
	}
	
	public void equip(int slot) { // this should be re-written to only equip items from inventory
		inventory.pickup(equipped);
		equipped = (Weapon) inventory.getSlot(slot);
		inventory.drop(slot);
	}
	
	
	public void showStats(Scanner input) {
		Menu statsMenu = new Menu("STATISTICS");
		statsMenu.prompt.add("Name: \t\t"+name);
		statsMenu.prompt.add("Level: \t\t"+level);
		statsMenu.prompt.add("EXP: \t\t"+exp+"/"+ getLevelUpXP());
		statsMenu.prompt.add("\n[Health]: \t"+health+"/"+maxHealth);
		statsMenu.prompt.add("[Mana]: \t"+mana+"/"+maxMana);
		statsMenu.prompt.add("\n[Strength]: \t"+strength);
		statsMenu.prompt.add("[Defense]: \t"+defense);
		statsMenu.prompt.add("[Dodge]: \t"+dodge);
		statsMenu.prompt.add("[Luck]: \t"+luck);
		statsMenu.prompt.add("[Magic]: \t"+magic);
		statsMenu.prompt.add("[Resistance]: \t"+resistance);
		statsMenu.prompt.add("\n[HELP] - show descriptions for each stat.");
		statsMenu.prompt.add("[BACK] - go back to the previous prompt.\n");

		//level up info
		if (points < 0) { 
			statsMenu.message("You have \"+points+\" skill points to spend!  [LEVELUP] - to spend points.");
		}else {
			levelUp();
		}
		
		statsMenu.display();
		
		while(true) {
			String selection = input.nextLine();
			
			switch(selection.toUpperCase()){
			case "LEVELUP":
				if (points <= 0) {
					statsMenu.message("You don't have any points to spend on skills.");
				}else{
					skillMenu(input);
				}
				break;
			case "BACK":
				return;
			case "HELP":
				statsMenu.message(
						"STRENGTH = melee damage modifier\r\n"
					  + "DEFENSE = how much incoming damage is reduced\r\n"
					  + "DODGE = chance to negate damage all together\r\n"
					  + "LUCK = modifies how many coins and materials you can gain.\r\n"
					  + "MAGIC = how powerful spells will be, will use spell books that work like swords with elemental bonuses and healing\r\n"
					  + "RESISTANCE = like defense, but against magic/status effects"
				);
			break;
			default:
				statsMenu.message("\nYou don't know what '"+selection+"' means.\n");
				break;
			}
		}
		
	}
	
	
	/**
	 * skill point menu
	 * @param stat
	 */
	public void addSkillPoint(String stat) { //stat has been validated by the "validStat.contains" condition
		Scanner input = new Scanner(System.in);
		String in = input.next();
		input.close();
		try {
			int amount = Integer.parseInt(in); //converts string to int
			if (amount > points) {
				System.out.println("You don't have enough points for that!");
				addSkillPoint(stat);
				return;
			}
			int total;
			switch (stat) {
			case "str":
			case "strength":
				stat = "Strength"; //converts to proper full word
				strength = strength+amount;
				total = strength;
				break;
			case "def":
			case "defense":
				stat = "Defense";
				defense = defense+amount;
				total = defense;
				break;
			case "lck":
			case "luck":
				stat = "Luck";
				defense = luck+amount;
				total = luck;
				break;
			case "dge":
			case "dodge":
				stat = "Dodge";
				dodge = dodge+amount;
				total = dodge;
				break;
			case "mgc":
			case "magic":
				stat = "Magic";
				magic = magic+amount;
				total = magic;
				break;
			case "rst":
			case "resistance":
				stat = "Resistance";
				resistance = resistance+amount;
				total = resistance;
				break;
			default:
				total = 0;
				System.err.println("ERROR: line 220 in main"); // this has to be the worst error message lol!
			}
			points -= amount;
			System.out.println("You've advanced your "+stat+ " by " +amount+".\nYour "+stat+" is now level "+total+".\nYou have "+points+" left.");
			showStats(input);
		}catch (Exception e) {
			if (in.equals("back")) {
				skillMenu(input);
			}else{
				System.out.println("Enter a number.");
				addSkillPoint(stat);
			}
		}

	}
	
	/**
	 * Skills Menu
	 */
	public void skillMenu(Scanner input) {
		Menu skillsMenu = new Menu("SKILLS MENU");
		skillsMenu.prompt.add("You have \"+getPoints()+\" skill point(s) to spend. Enter the name of the skill you want to add points to.");
		skillsMenu.prompt.add("Once added, they cannot be reset, without a fee.");
		skillsMenu.prompt.add("[STR][DEF][DGE][LCK][MGC][RST]");
		skillsMenu.prompt.add("[HELP] - show descriptions for each stat.");
		skillsMenu.prompt.add("[BACK] - go back to the previous prompt.");
		
		//making an array and converting it into a list, so that it can check the list against the input and see if it is a valid stat to modify
		final String validate[] = new String[] {"str", "Strength", "def", "Defense", "lck", "luck", "dge", "dodge", "mgc", "magic", "rst", "resistance"};
		List<String> validStat = Arrays.asList(validate); 
		
		while(true) {
			String selection = input.next().toString();

			if (selection.equals("back")) {
				Main.mainMenu(input);
			}else if (validStat.contains(selection)) { //seeing which stat to modify
				addSkillPoint(selection);
			}else if (selection.equals("help")) {//checking input for menu related navigation
				skillsMenu.message(
							 "STRENGTH = melee damage modifier\r\n"
						   + "DEFENSE = how much incoming damage is reduced\r\n"
						   + "DODGE = chance to negate damage all together\r\n"
						   + "LUCK = modifies how many coins and materials you can gain.\r\n"
						   + "MAGIC = how powerful spells will be, will use spell books that work like swords with elemental bonuses and healing\r\n"
						   + "RESISTANCE = like defense, but against magic/status effects"
						   );
			}else {
				skillsMenu.message("You don't know what '\"+selection+\"' means.");
			}
		}
	}
	
	
	/**
	 * Teleports player
	 * @param destination
	 */
	//public void teleport(String destination) {
	//	setLocation(destination);
	//	System.out.println("\n\nYou arrive at "+getLocation()+". What would you like to do?");
	//}

	//The reason its good to make all of these 'get' commands is for a couple reasons
	//Controlling how variables are modified gives variables meaning - ie location can only be something we know exists, rather than just a random string
	//There shouldn't be this many 'get's, because most of what modifies the player should be contained within the player class, rather than all around the code - decoupling
	
	public int getSector() {
		return sector;
	}
	
	public void setSector(int sector) {
		this.sector = sector;
	}
	
	public void travel(Direction direction) {
		switch(direction) {
		case EAST:
			sector += 1;
			break;
		case WEST:
			sector -= 1;
			break;
		}
	}
	
	
	public boolean isDead() {
		if(health <= 0) {
			return true;
		}else{
			return false;
		}
	}
	
	public Weapon getEquipped() {
		return equipped;
	}
	
	public int getLevel() {
		return level;
	}
	
	public int getExp() {
		return exp;
	}
	
	public int getLevelUpXP() {
		return level*81;
	}
	
	public int getPoints() {
		return points;
	}
	
	public int getCoins() {
		return coins;
	}
	
	public void setCoins(int amount) {
		coins = amount;
	}
	
	public void loseCoins(int amount) {
		coins = coins-amount;
	}
	
	public int getHealth() {
		return health;
	}
	
	public int getMaxHealth() {
		return maxHealth;
	}
	
	public int getStrength() {
		return strength;
	}
	
	public int getDefense() {
		return defense;
	}
	
	public int getDodge() {
		return dodge;
	}
	
	public int getLuck() {
		return luck;
	}
	
	public int getMagic() {
		return magic;
	}
	
	public int getMana() {
		return mana;
	}
	
	public int getMaxMana() {
		return maxMana;
	}
	
	public int getResistance() {
		return resistance;
	}

	public String getName() {
		return name;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public void damage(int damage) {
		health -= damage;
	}
	
	public void heal(int heal) {
		health += heal;
	}
	
	public void grantXP(int xp) {
		exp += xp;
	}
	
	public void levelUp() {
		level++;
		exp = 0;
		maxHealth = getMaxHealth() + getLevel();
		maxMana = getMaxMana() + getLevel();
	}
}
