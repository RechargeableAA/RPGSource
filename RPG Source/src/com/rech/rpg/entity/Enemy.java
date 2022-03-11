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
		int[] stats = getStats();
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
	
	public void setRandLevel(int minLevel, int maxLevel) { //can be for bosses or specific encounters
		Random r = new Random();
		level = r.nextInt(maxLevel)+minLevel; 
		setStats(level);
	}
	
	public void setLevel(int level) {
		super.level = level;
		setStats(level);
	}
	
	public static void sayTest(Enemy enemy) {
		System.out.println("Hi, I'm a " + enemy.name + ", and I'm level " + enemy.level + "!");
	}
	
	private int[] getStats() {
		Random rand = new Random();
		int[] report = new int[5];
		report[0] = rand.nextInt(Enemy.this.level*5)+Enemy.this.level*5; //maxHealth
		report[1] = rand.nextInt(Enemy.this.level)+Enemy.this.level; //strength
		report[2] = rand.nextInt(Enemy.this.level)+Enemy.this.level; //defense
		report[3] = rand.nextInt(Enemy.this.level)+Enemy.this.level; //dodge
		report[4] = rand.nextInt(Enemy.this.level*3)+Enemy.this.level; //magic
		return report;
	}
	
	private void setStats(int level) {
		int[] stats = getStats();
		Enemy.this.health = super.maxHealth = stats[0];
		Enemy.this.strength = stats[1];
		Enemy.this.defense = stats[2];
		Enemy.this.dodge = stats[3];
		Enemy.this.magic = stats[4];
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
