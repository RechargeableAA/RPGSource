package com.rech.rpg.entity;

import java.util.Random;
import com.rech.rpg.item.Weapon;
import com.rech.rpg.item.Inventory;
import com.rech.rpg.item.Item;
import com.rech.rpg.Player;

public class Enemy extends Entity{ 
	
	private String race; 
	private String magicResist;
	private String metalResist;
	
	//private String[] humanList = new String[]{"Beggar", "Thief", "Bandit", "Raider", "Marauder"};
	//private String[] knightList = new String[]{"Guard", "Bronze Knight", "Wrought Knight", "Royal Knight", "Dark Knight", "Juggernaught Knight"};
	//private String[] orcList = new String[]{"Orc Bandit", "Orc Warrior", "Orc Bezerker", "Orc Juggernaught", "Orc Warlord"};
	//private String[] beastList = new String[]{"Boar", "Wolf", "Alpha Wolf", "Bobcat", "Bear", "Sepeant", "Prowler"}; 
	
	public Enemy (String name, String race, String metalResist, String magicResist){ //contructor enemy with both resistances
		super.name = name;
		this.race = race; 
		this.metalResist = metalResist; //these will need to be moved to methods, like how getRandLevel works.
		this.magicResist = magicResist;
		super.level = getRandLevel(race);
	}
	
	public Enemy (String name, String race, String metalResist){ //contructor enemy with only metal resistances
		super.name = name;
		this.race = race; 
		this.metalResist = metalResist;
		super.level = getRandLevel(race);
	}
	
	
	public Enemy (String name, String race){ //this enemy has no resistances
		super.name = name;
		this.race = race; 
		super.level = getRandLevel(race);
		
	}
	
	// ***   ENEMYS   ***
	//Humans
	public static final Enemy beggar = new Enemy("Beggar", "human");
	public static final Enemy thief = new Enemy("Thief", "human");
	public static final Enemy bandit = new Enemy("Bandit", "human");
	public static final Enemy raider = new Enemy("Raider", "human");
	public static final Enemy marauder = new Enemy("Marauder", "human");
	
	//Orcs
	public static final Enemy orcBandit = new Enemy("Orc Bandit", "orc", "steel", "wind"); //temp syntax until methods are introduced.
	
	//knights
	public static final Enemy guard = new Enemy("Guard", "knight", "bronze");
	
	//beasts
	public static final Enemy boar = new Enemy("Boar", "beast");
	
	
	private int getRandLevel(String race) { //presets (temporary values)
		Random r = new Random();
		if (race.equals("human")) {
			return r.nextInt(5)+1; //1 - 5
		}else if (race.equals("orc")) {
			return r.nextInt(5)+5; //5 - 10
		}else if (race.equals("knight")) {
			return r.nextInt(10)+10; //10 - 20 
		}else if (race.equals("beast")) {
			return r.nextInt(15)+1; //1 - 15
		}else {
			return 1;
		}
	}
	
	public void setRandLevel(int minLevel, int maxLevel) { //can be for bosses or specific encounters
		Random r = new Random();
		super.level = r.nextInt(maxLevel)+minLevel; 
	}
	
	public void setLevel(int level) {
		super.level = level;
	}
	
	public static void sayTest(Enemy enemy) {
		System.out.println("Hi, I'm a " + enemy.name + ", and I'm level " + enemy.level + "!");
	}
	
	/*private void findMetalResist(Player player) {
		String equippedMaterial = Weapon.getEquippedMaterial(player);
		if (race == "human") {
			
		}
	} */
	
	//Getter and Setters

	public String getRace() {
		return race;
	}
	
	public void setRace(String race) {
		this.race = race;
	}
	
	public String getMagicResist() {
		return magicResist;
	}
	
	public void setMagicResist(String magicType) {
		magicResist = magicType;
	}
	
	public String getMetalResist() {
		return metalResist;
	}
	
	public void setMetalResist(String metal) {
		metalResist = metal;
	}
	
}
