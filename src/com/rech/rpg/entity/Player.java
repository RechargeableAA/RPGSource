package com.rech.rpg.entity;

import java.awt.Point;
import java.util.Scanner;

import com.rech.rpg.Main;
import com.rech.rpg.Menu;
import com.rech.rpg.item.Inventory;
import com.rech.rpg.item.Spellbook;
import com.rech.rpg.item.Weapon;


/*
 * Player class to store player info
 */
public class Player extends Entity{
	
	//stats
	private int 
	exp, 
	points,
	coins;

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
		equipped = null;
		
		//Certain names give certain attributes ****for testing
		testerNames(name);
		
		this.name = name;
	}

	
	private void testerNames(String name) {
		if (name.equals("")) {
			this.name = "no name";
			this.setPoints(100);
		}else if (name.equals("admin")) {
			coins = 999999;
			inventory.pickup(Weapon.adminBlade);
			levelUpStat(Stats.MAXHEALTH, 100);
		}else if (name.equals("invtest")) {
			coins = 99100;
			inventory.pickup(Weapon.generateChanceWeapon(100, 40));
			inventory.pickup(Weapon.generateChanceWeapon(100, 50));
			inventory.pickup(Weapon.generateChanceWeapon(100, 60));
			inventory.pickup(Weapon.generateChanceWeapon(100, 70));
			inventory.pickup(Weapon.generateChanceWeapon(100, 80));
			inventory.pickup(Weapon.generateChanceWeapon(100, 90));
			inventory.pickup(Weapon.generateChanceWeapon(100, 100));
			//levelUpStat(Stats.MAXHEALTH, 100);

			
		}
	}

	protected void godMode() {
		coins = 99999;
		inventory.pickup(Weapon.adminBlade);
		inventory.pickup(Weapon.testWeapon);
		levelUpStat(Stats.MAXHEALTH, 1000);
	}


	//used during loading, this is an alternate constructor for creating a player object
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
		
		//initializing inventory
		inventory = new Inventory();
		this.equipped = null;
	}
	
	/**
	 * Add an amount of skillpoints to a skill. removes used skillpoints
	 * @param stat
	 */
	public void addSkillPoint(Stats stat, int amount) { //stat has been validated by the "validStat.contains" condition
		levelUpStat(stat, amount);
		points -= amount;
	}

	public void equip(int slot) { // this should be re-written to only equip items from inventory
		inventory.pickup(this.equipped);
		this.equipped = (Weapon) inventory.getSlot(slot);
		inventory.drop(slot);
	}
	
	public Inventory getInventory() {
		return inventory;
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

	public void setPoints(int points) {
		this.points = points;
	}
	
	public void grantXP(int xp) {
		exp += xp;
	}
}
