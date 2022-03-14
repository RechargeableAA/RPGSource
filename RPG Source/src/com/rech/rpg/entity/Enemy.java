package com.rech.rpg.entity;

import java.util.ArrayList;
import java.util.Random;


public class Enemy extends Entity{ 
	
	//private String race; //TODO I want to turn this into ENUM!
	//private String magicResist;
		
	private enum Race{
		HUMAN, ORC, KNIGHT, BEAST; 
	}
	
	private Race race;
		
	private static ArrayList<Enemy> enemyList = new ArrayList<Enemy>(); 
	
	public Enemy (String name, Race race){ 
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
	
	private int getRandLevel(Race race) { //presets (temporary values),
		Random r = new Random();
		switch (race) {
		case HUMAN:
			return r.nextInt(5)+1; //1 - 5
		case ORC:
			return r.nextInt(5)+5; //5 - 10
		case KNIGHT:
			return r.nextInt(10)+10; //10 - 20
		case BEAST:
			return r.nextInt(15)+1; //1 - 15
		default:
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
	
	public static void sayTest(Enemy enemy) {
		System.out.println("Hi, I'm a " + enemy.name + ". I'm a " + enemy.getRace().toString() + ", and I'm level " + enemy.level + "!\nI have "+ enemy.health + " HP, my STR is " + enemy.strength + ", my DEF is " + enemy.defense + ", my DGE is " + enemy.dodge + ", and my MGC is " + enemy.magic + ".\n\n");
	}
	
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
				if (this.getRace().equals(Race.BEAST)) { //no magic beasts (yet)
					++distPoints;
				}else {
					++report[4]; //mgc
				}
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

	public Race getRace() {
		return race;
	}
	
	public void setRace(Race race) {
		this.race = race;
	}
	
	// ***   ENEMYS   ***
	//Humans
	public static final Enemy beggar = new Enemy("Beggar", Race.HUMAN);
	public static final Enemy thief = new Enemy("Thief", Race.HUMAN);
	public static final Enemy bandit = new Enemy("Bandit", Race.HUMAN);
	public static final Enemy raider = new Enemy("Raider", Race.HUMAN);
	public static final Enemy marauder = new Enemy("Marauder", Race.HUMAN);
	
	//Orcs
	public static final Enemy orcBandit = new Enemy("Orc Bandit", Race.ORC); 
	public static final Enemy orcWarrior = new Enemy("Orc Warrior", Race.ORC);
	public static final Enemy orcBezerker = new Enemy("Orc Bezerker", Race.ORC);
	public static final Enemy orcJuggernaught = new Enemy("Orc Juggernaught", Race.ORC); 
	public static final Enemy orcWarlord = new Enemy("Orc Warlord", Race.ORC); 
	
	//knights
	public static final Enemy guard = new Enemy("Guard", Race.KNIGHT);
	public static final Enemy bronzeKnight = new Enemy("Bronze Knight", Race.KNIGHT);
	public static final Enemy wroughtKnight = new Enemy("Wrought Knight", Race.KNIGHT);
	public static final Enemy royalKnight = new Enemy("Royal Knight", Race.KNIGHT);
	public static final Enemy darkKnight = new Enemy("Dark Knight", Race.KNIGHT);
	
	//beasts
	public static final Enemy boar = new Enemy("Boar", Race.BEAST);
	public static final Enemy wolf = new Enemy("Wolf", Race.BEAST);
	public static final Enemy bobcat = new Enemy("Bobcat", Race.BEAST);
	public static final Enemy bear = new Enemy("Bear", Race.BEAST);
	public static final Enemy serpent = new Enemy("Serpent", Race.BEAST);
	
}
