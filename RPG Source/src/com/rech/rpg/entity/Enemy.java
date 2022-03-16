package com.rech.rpg.entity;

import java.util.ArrayList;
import java.util.Random;

import com.rech.rpg.entity.Entity.Stats;


public class Enemy extends Entity{ 
	
	//private String race; //TODO I want to turn this into ENUM!
	//private String magicResist;
		
	public enum Race{
		HUMAN, ORC, KNIGHT, BEAST; 
	}
	private static final int DEFAULTENEMYHEALTH = 10;
	
	private static ArrayList<Enemy> enemyList = new ArrayList<Enemy>(); 
	
	private Race race;
	
	/**
	 * Create a new enemy; Determine this enemy's level based off of its race; It's levels are randomly allocated
	 * @param name
	 * @param race
	 */
	public Enemy (String name, Race race){ 
		//enemy defaults
		super(name, DEFAULTENEMYHEALTH);
		this.race = race; 
		//stat initialization
		allocateRandomLevels(getRandLevel(race));
		
		enemyList.add(this);
	}
	
	/**
	 * Create a new enemy with a specific race and level; It's levels are randomly allocated
	 * @param name
	 * @param level
	 * @param race
	 */
	public Enemy (String name, int maxHealth, int level, Race race){ 
		//enemy defaults
		super(name, maxHealth);
		this.race = race; 
		//stat initialization
		allocateRandomLevels(level);
		
		enemyList.add(this);
	}
	
	/**
	 * Create a specific enemy
	 * @param name
	 * @param level
	 * @param health
	 * @param maxHealth
	 * @param mana
	 * @param maxMana
	 * @param strength
	 * @param defense
	 * @param dodge
	 * @param luck
	 * @param magic
	 * @param resistance
	 * @param race
	 */
	public Enemy (String name, int level, int health, int maxHealth, int mana, int maxMana, int strength, int defense, int dodge, int luck, int magic, int resistance, Race race){ 
		//enemy defaults
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
		this.race = race; 
		enemyList.add(this);
	}
	
	public void sayTest() {
		System.out.println("Hi, I'm a " + name + ". I'm a " + getRace().toString() + ", and I'm level " + getLevel() + "!\nI have "+ getHealth() + " HP, my STR is " + getStat(Stats.STRENGTH) + ", my DEF is " + getStat(Stats.DEFENSE) + ", my DGE is " + getStat(Stats.DODGE) + ", and my MGC is " + getStat(Stats.MAGIC) + ".\n\n");
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
	 * Distributes points randomly between stats. This simulates the enemy leveling and spending 5 points per level.
	 * ***Returns a 5-long array***!
	 */
	private void allocateRandomLevels(int levels) { //must remain private
		Random rand = new Random();
		int distPoints = (levels-1)*POINTSPERLEVEL; // -1 because level 1 shouldn't count
		
		while(distPoints > 0) {
			int pick = rand.nextInt(5)+1;
			switch(pick){
			case 1:
				this.levelUpStat(Stats.STRENGTH);
				break;
			case 2:
				this.levelUpStat(Stats.DEFENSE);
				break;
			case 3:
				this.levelUpStat(Stats.DODGE);
				break;
			case 4:
				if (this.getRace().equals(Race.BEAST)) { //no magic beasts (yet) // Beasts are gonna be pretty op if we add a sp for every case 4
					++distPoints;
				}else {
					this.levelUpStat(Stats.MAGIC); //mgc
				}
				break;
			case 5:
				this.levelUpStat(Stats.MAXHEALTH);
				break;
			}
			distPoints--;
		}
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
