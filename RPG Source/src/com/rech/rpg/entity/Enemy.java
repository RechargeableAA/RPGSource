package com.rech.rpg.entity;

import java.util.ArrayList;
import java.util.Random;


public class Enemy extends Entity{ 
	
	private String race; 
	//private String magicResist;
		
	//private String[] humanTier = new String[]{"Beggar", "Thief", "Bandit", "Raider", "Marauder"};
	//private String[] knightTier = new String[]{"Guard", "Bronze Knight", "Wrought Knight", "Royal Knight", "Dark Knight"};
	//private String[] orcTier = new String[]{"Orc Bandit", "Orc Warrior", "Orc Bezerker", "Orc Juggernaught", "Orc Warlord"};
	//private String[] beast = new String[]{"Boar", "Wolf", "Alpha Wolf", "Bobcat", "Bear", "Serpent", "Prowler"}; 
	
	private static ArrayList<Enemy> enemyList = new ArrayList<Enemy>(); 
	
	public Enemy (String name, String race){ 
		this.name = name;
		this.race = race; 
		level = getRandLevel(race);
		//stat initialization
		int[] stats = getRandStats();
		health = maxHealth = stats[0];
		strength = stats[1];
		defense = stats[2];
		dodge = stats[3];
		magic = stats[4];
		
		enemyList.add(this);
	}	
	
	private int getRandLevel(String race) { //presets (temporary values), the commented arrays are in the order of difficulty
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
	/**
	 * Make an enemy with a level range, and spends stat points randomly.
	 * 
	 * @param minLevel - Lowest possible level
	 * @param maxLevel - Highest possible level
	 */
	public Enemy setRandLevel(int minLevel, int maxLevel) { //can be for bosses or specific encounters
		maxLevel -= minLevel;
		Random r = new Random();
		level = r.nextInt(maxLevel)+minLevel; 
		setRandStats(level);
		return this;
	}
	
	/*public static void sayTest(Enemy enemy) {
		System.out.println("Hi, I'm a " + enemy.name + ", and I'm level " + enemy.level + "!\nI have "+ enemy.health + " HP, my STR is " + enemy.strength + ", my DEF is " + enemy.defense + ", my DGE is " + enemy.dodge + ", and my MGC is " + enemy.magic + ".\n\n");
	}*/
	
	/**
	 * Distributes points randomly between stats. This simulates the enemy leveling and spending 5 points per level.
	 * ***Returns a 5-long array***!
	 */
	private int[] getRandStats() { //must remain private
		Random rand = new Random();
		int[] report = new int[] {(Enemy.this.level*5)+10, 1, 1, 1, 1}; //preinitialize
		int distPoints = this.level*5; //5 points earned per level.
		int pick;
		
		while (distPoints > 0) { 
			pick = rand.nextInt(4)+1;
			switch (pick) {
			case 1:
				++report[1]; //str
				break;
			case 2:
				++report[2]; //def
				break;
			case 3:
				++report[3]; //dge
				break;
			case 4:
				++report[4]; //mgc
				break;
			}
			--distPoints;
		}
		return report;
	}
	
	/**
	 * pass through a preset level and spend the enemy's statpoints randomly.
	 * @param level
	 */
	public Enemy setRandStats(int level) { 
		this.level = level;
		int[] stats = getRandStats();
		this.health = super.maxHealth = stats[0];
		this.strength = stats[1];
		this.defense = stats[2];
		this.dodge = stats[3];
		this.magic = stats[4];
		return this;
	}
	
	/**
	 * Sets EXACT stats.
	 */
	public Enemy setFixedStats(int lvl, int maxHP, int str, int def, int dge, int mgc) { 
		this.level = lvl;
		this.health = this.maxHealth = maxHP;
		this.strength = str;
		this.defense = def;
		this.dodge = dge;
		this.magic = mgc;
		return this;
	}
	
	//Getter and Setters

	public String getRace() {
		return race;
	}
	
	public void setRace(String race) {
		this.race = race;
	}
	
	// ***   ENEMYS   ***
	//Humans
	public static final Enemy beggar = new Enemy("Beggar", "human");
	public static final Enemy thief = new Enemy("Thief", "human");
	public static final Enemy bandit = new Enemy("Bandit", "human");
	public static final Enemy raider = new Enemy("Raider", "human");
	public static final Enemy marauder = new Enemy("Marauder", "human");
	
	//Orcs
	public static final Enemy orcBandit = new Enemy("Orc Bandit", "orc"); 
	public static final Enemy orcWarrior = new Enemy("Orc Warrior", "orc");
	public static final Enemy orcBezerker = new Enemy("Orc Bezerker", "orc");
	public static final Enemy orcJuggernaught = new Enemy("Orc Juggernaught", "orc"); 
	public static final Enemy orcWarlord = new Enemy("Orc Warlord", "orc"); 
	
	//knights
	public static final Enemy guard = new Enemy("Guard", "knight");
	public static final Enemy bronzeKnight = new Enemy("Bronze Knight", "knight");
	public static final Enemy wroughtKnight = new Enemy("Wrought Knight", "knight");
	public static final Enemy royalKnight = new Enemy("Royal Knight", "knight");
	public static final Enemy darkKnight = new Enemy("Dark Knight", "knight");
	
	//beasts
	public static final Enemy boar = new Enemy("Boar", "beast");
	public static final Enemy wolf = new Enemy("Wolf", "beast");
	public static final Enemy bobcat = new Enemy("Bobcat", "beast");
	public static final Enemy bear = new Enemy("Bear", "beast");
	public static final Enemy serpent = new Enemy("Serpent", "beast");
	
}
