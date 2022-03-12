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
	
	public Enemy (String name, String race){ //this enemy has no resistances
		this.name = name;
		this.race = race; 
		level = getRandLevel(race);
		//stat initialization
		int[] stats = getRandStats();
		health = super.maxHealth = stats[0];
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
	
	public void setRandLevel(Enemy enemy, int minLevel, int maxLevel) { //can be for bosses or specific encounters
		Random r = new Random();
		enemy.level = r.nextInt(maxLevel)+minLevel; 
		enemy.setRandStats(level);
	}
	
	public void setLevel(Enemy enemy, int level) {
		super.level = level;
		enemy.setRandStats(level);
	}
	
	public static void sayTest(Enemy enemy) {
		System.out.println("Hi, I'm a " + enemy.name + ", and I'm level " + enemy.level + "!\nI have "+ enemy.health + " HP, my STR is " + enemy.strength + ", my DEF is " + enemy.defense + ", my DGE is " + enemy.dodge + ", and my MGC is " + enemy.magic + "\n\n");
	}
	
	/**
	 * Distributes points randomly between stats. This simulates the enemy leveling and spending 5 points per level.
	 * ***Returns a 5-long array***!
	 */
	private int[] getRandStats() {
		Random rand = new Random();
		int[] report = new int[5];
		
		int distPoints = this.level*5; //5 points earned per level.
		report[0] = (Enemy.this.level*5)+10; //maxHealth
		report[1] = 0; report[2] =0; report[3] = 0; report[4] = 0; //preinitialize
		int pick;
		
		while (distPoints > 0) { 
			pick = rand.nextInt(4)+1;
			switch (pick) {
			case 1:
				++report[1];
				break;
			case 2:
				++report[2];
				break;
			case 3:
				++report[3];
				break;
			case 4:
				++report[4];
				break;
			}
			--distPoints;
		}
		return report;
	}
	
	/**
	 * pass through a preset level and roll the enemy's stats
	 * @param level
	 */
	private void setRandStats(int level) { 
		int[] stats = getRandStats();
		Enemy.this.health = super.maxHealth = stats[0];
		Enemy.this.strength = stats[1];
		Enemy.this.defense = stats[2];
		Enemy.this.dodge = stats[3];
		Enemy.this.magic = stats[4];
	}
	
	private void setFixedStats(int lvl, int maxHP, int str, int def, int dge, int mgc) { 
		Enemy.this.level = lvl;
		Enemy.this.health = super.maxHealth = maxHP;
		Enemy.this.strength = str;
		Enemy.this.defense = def;
		Enemy.this.dodge = dge;
		Enemy.this.magic = mgc;
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
