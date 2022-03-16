package com.rech.rpg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.rech.rpg.entity.Entity;
import com.rech.rpg.item.Inventory;
import com.rech.rpg.item.Spellbook;
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
	private int 
	exp, 
	points,
	coins;

	private int sector = 1;
	//private String location = Main.worldMap[sector];
	
	// Inventory
	private Inventory inventory;
		
	public Player(String name) {
		//player defaults
		super(
				name,
				50,  //maxHealth/health
				1,	//strength
				1,	//defense
				1,	//dodge
				1,	//luck
				1,	//magic
				50,	//maxMana
				1	//resistance
			);
		//setting player stat defaults
		exp = 0;
		points = 0;
		coins = 0;
		//initializing inventory
		inventory = new Inventory();
		equipped = null;
		
		//Certain names give certain attributes ****for testing
		testerNames(name);
		
		this.name = name;
	}

	
	private void testerNames(String name) {
		if (name.equals(null)) {
			this.name = "no name";
		}else if (name.equals("admin")) {
			coins = 999999;
			inventory.pickup(Weapon.adminBlade);
			levelUpStat(Stats.MAXHEALTH, 100);
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
			levelUpStat(Stats.MAXHEALTH, 100);

			
		}else if (name.equals("test")) {
			Enemy.beggar.sayTest();
			Enemy.bear.sayTest();
			new Enemy("cat", 10, 1, Enemy.Race.HUMAN).sayTest();
			new Enemy("big cat", 10, 2, Enemy.Race.HUMAN).sayTest();
			Enemy.serpent.sayTest();
			Enemy.serpent.sayTest();
			Spellbook.fireBallSB.sayTest();
		
		}
	}


	//used during loading, this is an alternate constructor for created a player object
	public Player(String name, int level, int exp, int points, String location, int sector, int health, int maxHealth, int mana, int maxMana, int strength, int defense, int dodge, int luck, int magic, int resistance, int coins) {
		super(
				name,
				maxHealth,
				strength,
				defense,
				dodge,
				luck,
				magic,
				maxMana,
				resistance	
			);
		//setting player stat defaults
		this.exp = exp;
		this.points = points;
		this.coins = coins;
		
		//this.location = location;
		this.sector = sector;
		
		//initializing inventory
		inventory = new Inventory();
		this.equipped = null;
	}
	
	public void showStats(Scanner input) {
		Menu statsMenu = new Menu("STATISTICS");
		statsMenu.prompt.add("Name: \t\t"+name);
		statsMenu.prompt.add("Level: \t\t"+getLevel());
		statsMenu.prompt.add("EXP: \t\t"+exp+"/"+ getLevelUpXP());
		statsMenu.prompt.add("\n[Health]: \t"+getHealth()+"/"+getStat(Stats.MAXHEALTH));
		statsMenu.prompt.add("[Mana]: \t"+getMana()+"/"+getStat(Stats.MAXMANA));
		statsMenu.prompt.add("\n[Strength]: \t"+getStat(Stats.STRENGTH));
		statsMenu.prompt.add("[Defense]: \t"+getStat(Stats.DEFENSE));
		statsMenu.prompt.add("[Dodge]: \t"+getStat(Stats.DODGE));
		statsMenu.prompt.add("[Luck]: \t"+getStat(Stats.LUCK));
		statsMenu.prompt.add("[Magic]: \t"+getStat(Stats.MAGIC));
		statsMenu.prompt.add("[Resistance]: \t"+getStat(Stats.RESISTANCE));
		statsMenu.prompt.add("\n[HELP] - show descriptions for each stat.");
		statsMenu.prompt.add("[BACK] - go back to the previous prompt.\n");

		//level up info
		if (points < 0) { 
			statsMenu.message("You have \"+points+\" skill points to spend!  [LEVELUP] - to spend points.");
		}else {
			skillMenu(input);
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
	public void addSkillPoint(Stats stat) { //stat has been validated by the "validStat.contains" condition
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
			levelUpStat(stat, amount);
			points -= amount;
			System.out.println("You've advanced your "+stat.toString()+ " by " +amount+".\nYour "+stat+" is now level "+getStat(stat)+".\nYou have "+points+" left.");
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
		
		while(true) {
			String selection = input.next().toString();

			if (selection.equals("back")) {
				Main.mainMenu(input);
			}else if (isStat(selection)) { //verifies if input was a stat
				addSkillPoint(Stats.valueOf(selection));
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
	protected void godMode() {
		coins = 99999;
		inventory.pickup(Weapon.adminBlade);
		inventory.pickup(Weapon.testWeapon);
		levelUpStat(Stats.MAXHEALTH, 1000);
	}
	
	public void equip(int slot) { // this should be re-written to only equip items from inventory
		inventory.pickup(this.equipped);
		this.equipped = (Weapon) inventory.getSlot(slot);
		inventory.drop(slot);
	}
	
	public Inventory getInventory() {
		return inventory;
	}
	
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
		if(getHealth() <= 0) {
			return true;
		}else{
			return false;
		}
	}
	
	public int getExp() {
		return exp;
	}
	
	public int getLevelUpXP() {
		return getLevel()*81;
	}
	
	public int getPoints() {
		return points;
	}	

	public String getName() {
		return name;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	
	public void grantXP(int xp) {
		exp += xp;
	}
}
