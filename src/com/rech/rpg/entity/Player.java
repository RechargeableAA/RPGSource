package com.rech.rpg.entity;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.rech.rpg.Menu;
import com.rech.rpg.item.Inventory;
import com.rech.rpg.item.Spellbook;
import com.rech.rpg.item.Weapon;
import com.rech.rpg.map.Map.Direction;
import com.rech.rpg.map.event.AmbushEvent;
import com.rech.rpg.map.location.Town;


/*
 * Player class to store player info
 */
public class Player extends Entity{
	
	//stats
	private int 
	exp, 
	points,
	coins;

	private Point mapPosition;
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
		//Default map position, short's max value 32,767 so we just jump to the middle of that, so we can move left or right without entering negatives
		mapPosition = new Point(Short.MAX_VALUE/2, Short.MAX_VALUE/2);
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
			new Enemy("cat", 10, 1, Enemy.Race.BEAST).sayTest();
			new Enemy("big cat", 10, 2, Enemy.Race.BEAST).sayTest();
			Enemy.serpent.sayTest();
			Enemy.serpent.sayTest();
			Spellbook.fireBallSB.sayTest();
		
		}
	}


	//used during loading, this is an alternate constructor for created a player object
	public Player(String name, int level, int exp, int points, String location, Point mapPosition, int health, int maxHealth, int mana, int maxMana, int strength, int defense, int dodge, int luck, int magic, int resistance, int coins) {
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
		this.mapPosition = mapPosition;
		
		//initializing inventory
		inventory = new Inventory();
		this.equipped = null;
	}
	
	public void showStats(Scanner input) {
		Menu statsMenu = new Menu("STATISTICS");
		statsMenu.setMenuInfo("Name: \t\t"+name + 
							"\nLevel: \t\t"+getLevel() + 
							"\nEXP: \t\t"+exp+"/"+ getLevelUpXP() + "\n" +
							"Health | Mana | Strength | Defense | Dodge | Luck | Magic | Resistance \n"
						  + getHealth()+"/"+getStat(Stats.MAXHEALTH) + "   " //the spacing here will be fucked up as soon as a number goes to 10s, need to make a feature in menu class to handle this
						  + getMana()+"/"+getStat(Stats.MAXMANA) + "      "
						  + getStat(Stats.STRENGTH) + "          "
						  + getStat(Stats.DEFENSE) + "        "
						  + getStat(Stats.DODGE) + "      "
						  + getStat(Stats.LUCK) + "       "
						  + getStat(Stats.MAGIC) + "         "
						  + getStat(Stats.RESISTANCE)); // this menu feature needs to be explicitly created in the menu class, since numbers will move the entire line when going from 0-10 10-100 etc
							
		
		statsMenu.addPrompt("HELP", "show descriptions for each stat.");
		statsMenu.addPrompt("BACK", "go back to the previous prompt.");
		//level up info
		if (points > 0) { 
			statsMenu.message("You have \"+points+\" skill points to spend!  [LEVELUP] - to spend points.");
		}else {
		//	skillMenu(input);
		}
		
		while(true) {
			statsMenu.display();
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
		skillsMenu.setMenuInfo("You have \"+getPoints()+\" skill point(s) to spend. Enter the name of the skill you want to add points to. \n"
							 + "Once added, they cannot be reset, without a fee. \n"
							 + "[STR][DEF][DGE][LCK][MGC][RST]");
		skillsMenu.addPrompt("HELP", "show descriptions for each stat.");
		skillsMenu.addPrompt("BACK", "go back to the previous prompt.");
		
		while(true) {
			String selection = input.next().toString();

			if (selection.equals("back")) {
				return;
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
	
	public Point getPosition() {
		return mapPosition;
	}
	
	public void setPosition(int sector) {
		this.mapPosition = mapPosition;
	}
	
	public void travel(Direction direction) {
		switch(direction) {
		case EAST:
			mapPosition = new Point(mapPosition.x+1, mapPosition.y);
			break;
		case WEST:
			mapPosition = new Point(mapPosition.x-1, mapPosition.y);
			break;
		case NORTH:
			mapPosition = new Point(mapPosition.x, mapPosition.y+1);
			break;
		case SOUTH:
			mapPosition = new Point(mapPosition.x, mapPosition.y-1);
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
