package com.rech.rpg;

import java.util.Scanner;

import com.rech.rpg.item.Inventory;
import com.rech.rpg.item.Weapon;


/*
 * Player class to store player info
 */
public class Player {
	
	//stats
	private static int 
	level,
	exp, 
	points,
	coins,
	health,
	maxHealth,
	strength,
	defense,
	dodge,
	luck,
	magic,
	mana,
	maxMana,
	resistance;
	private static String name;
	
	private int sector = 0;
	private String location = Main.worldMap[sector];
	
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
		
		this.location = location;
		this.sector = sector;
		
		//initializing inventory
		inventory = new Inventory();
		equipped = null;
	}
	
	protected void godMode() {
		coins = 99999;
		inventory.pickup(Weapon.adminBlade);
		inventory.pickup(Weapon.bsGreatSword);
		health = 10000;
		maxHealth = 10000;
	}
	
	public Inventory getInventory() {
		return inventory;
	}
	
	public void equip(Weapon weapon) { // this should be re-written to only equip items from inventory
		inventory.pickup(equipped);
		equipped = weapon;
	}
	
	
	/**
	 * stats menu
	 */
	public void showStats() {
		System.out.println("\n\n\n     STATISTICS");
		if (points > 0) { System.out.println("You have "+points+" skill points to spend!  [LEVELUP] - to spend points.\n"); }else{ System.out.println("\n"); }
		//add the sword and shield bonuses in parentheses after those are worked in
		System.out.println("Name: "+name+"\nLevel "+level+"\n"+"EXP: "+exp+"/"+level+"\n[Health]: "+health+"/"+maxHealth+"\n[Mana]: "+mana+"/"+maxMana+"\n\n[Strength]: "+strength+"\n[Defense]: "+defense+"\n[Dodge]: "+dodge+"\n[Luck]: "+luck+"\n[Magic]: "+magic+"\n[Resistance]: "+resistance+"\n\n");
		System.out.println("[HELP] - show descriptions for each stat.\n[BACK] - go back to the previous prompt.\n");
		Scanner input = new Scanner(System.in);

		String in = input.next();
		if (in.equals("help")) {
			System.out.println("\nSTRENGTH = melee damage modifier\r\nDEFENSE = how much incoming damage is reduced\r\nDODGE = chance to negate damage all together\r\nLUCK = modifies how many coins and materials you can gain.\r\nMAGIC = how powerful spells will be, will use spell books that work like swords with elemental bonuses and healing\r\nRESISTANCE = like defense, but against magic/status effects");
			showStats();
		} else if (in.equals("levelup")) {
			if (points <= 0) {
				System.out.println("You don't have any points to spend on skills.");
				showStats();
			}else{
				Main.skillMenu();
			}
		} else if (in.equals("back")) {
			System.out.println("\n\n\n");
			return;
		}else { 
			System.out.println("\nYou don't know what '"+in+"' means.\n");
			showStats();
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
			showStats();
		}catch (Exception e) {
			if (in.equals("back")) {
				Main.skillMenu();
			}else{
				System.out.println("Enter a number.");
				addSkillPoint(stat);
			}
		}

	}
	
	/**
	 * Teleports player
	 * @param destination
	 */
	public void teleport(String destination) {
		setlocation(destination);
		System.out.println("\n\nYou arrive at "+getLocation()+". What would you like to do?");
	}

	//The reason its good to make all of these 'get' commands is for a couple reasons
	//Controlling how variables are modified gives variables meaning - ie location can only be something we know exists, rather than just a random string
	//There shouldn't be this many 'get's, because most of what modifies the player should be contained within the player class, rather than all around the code - decoupling
	
	public int getSector() {
		return sector;
	}
	
	public boolean isDead() {
		if(health <= 0) {
			return true;
		}else{
			return false;
		}
	}
	
	public String getLocation() {
		return location;
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


	public void setlocation(String destination) {
		location = destination;
	}

	public void damage(int damage) {
		health -= damage;
	}
	
	public void heal(int heal) {
		health+= heal;
	}
	
	public void grantXP(int xp) {
		exp += xp;
	}
	
	public void levelUp() {
		level++;
		exp = 0;
	}

	public void setSector(int i) {
		sector = i;
	}
}
