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
	
	private String[] humanList = new String[]{"Beggar", "Thief", "Bandit", "Raider", "Marauder"};
	private String[] knightList = new String[]{"Guard", "Bronze Knight", "Wrought Knight", "Royal Knight", "Dark Knight", "Juggernaught Knight"};
	private String[] orcList = new String[]{"Orc Bandit", "Orc Warrior", "Orc Bezerker", "Orc Juggernaught", "Orc Warlord"};
	private String[] beastList = new String[]{"Boar", "Wolf", "Alpha Wolf", "Bobcat", "Bear", "Sepeant", "Prowler"}; 
	
	public Enemy (String name, String race, String metalResist, String magicResist){ //contructor enemy with resistances
		super.name = name;
		this.race = race; 
		this.metalResist = metalResist;
		this.magicResist = magicResist;
	}
	
	
	public Enemy (String name, String race){ //this enemy has no resistances
		super.name = name;
		this.race = race; 
	}
	
	public static Enemy beggar = new Enemy("Beggar", "human");
	
	
	private void getRandLevel(String race) { //presets (temporary values)
		if (race.equals("human")) {
			Random r = new Random();
			super.level = r.nextInt(5)+1; 
		}else if (race.equals("orc")) {
			Random r = new Random();
			super.level = r.nextInt(10)+5; 
		}else if (race.equals("knight")) {
			Random r = new Random();
			super.level = r.nextInt(20)+10; 
		}else if (race.equals("beast")) {
			Random r = new Random();
			super.level = r.nextInt(15)+1; 
		}
	}
	
	public void setRandLevel(int minLevel, int maxLevel) { //can be for bosses or specific encounters
		Random r = new Random();
		super.level = r.nextInt(maxLevel)+minLevel; 
	}
	
	public void setLevel(int level) {
		super.level = level;
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
