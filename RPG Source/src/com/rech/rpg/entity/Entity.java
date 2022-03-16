package com.rech.rpg.entity;

import com.rech.rpg.item.Weapon;
import com.rech.rpg.map.Map.Direction;

public class Entity {
	protected String name;
	
	//5 points earned per level.
	public static final int POINTSPERLEVEL = 5;
	
	private static final int //default stats constants, used to determine an entity's level, also determines the stat's increment when leveling, besides MAXHEALTH and MAXMANA
	STRENGTH = 1,
	MAXHEALTH = 50,
	HEALLEVELINC = 10, //The amount health increments by level
	DEFENSE = 1,
	DODGE = 1,
	LUCK = 1,
	MAGIC = 1,
	MAXMANA = 10,
	MANALEVELINC = 10, //The amount mana is incremented by level
	RESISTANCE = 1;
	
	public enum Stats{
		MAXHEALTH,
		STRENGTH,
		DEFENSE,
		DODGE,
		LUCK,
		MAGIC,
		MAXMANA,
		RESISTANCE
	}
	
	// Managed by entity, Level determines max health, and by affiliation its health
	private int
	health = MAXHEALTH,
	maxHealth = MAXHEALTH,
	strength = STRENGTH,
	defense = DEFENSE,
	dodge = DODGE,
	luck = LUCK,
	magic = MAGIC,
	mana = MAXMANA,
	maxMana = MAXMANA,
	resistance = RESISTANCE;
	
	/**
	 * Use entity defaults
	 * @param name
	 */
	protected Entity(String name, int maxHealth) {
		this.name = name;
		this.maxHealth = maxHealth;
		this.health = maxHealth;
	}
	
	protected Entity(String name, int maxHealth, int strength, int defense, int dodge, int luck, int magic, int maxMana, int resistance) {
		this.name = name;
		this.maxHealth = maxHealth;
		this.health = maxHealth;
		this.strength = strength;
		this.defense = defense;
		this.dodge = dodge;
		this.luck = luck;
		this.magic = magic;
		this.maxMana = maxMana;
		this.mana = maxMana;
		this.resistance = resistance;
	}
	
	/**
	 * Calculate entity level based off stats
	 * @return entity's calculate level
	 */
	public int getLevel() {
		int skillPointsSpent = 1;
		for(Stats stat : Stats.values()) {
			if(!stat.equals(MAXHEALTH) || !stat.equals(MAXMANA)) { // max health and max mana are calculated differently
				skillPointsSpent += (getStat(stat)/getStatDefault(stat));
			}else if(stat.equals(MAXHEALTH)){ 
				skillPointsSpent += (getStat(stat)/HEALLEVELINC);
			}else if(stat.equals(MAXMANA)) {
				skillPointsSpent += (getStat(stat)/MANALEVELINC);
			}
		}
		return skillPointsSpent/POINTSPERLEVEL; // rounds down
	}
	
	public void levelUpStat(Stats stat) {
		switch(stat) {
		case MAXHEALTH:
			maxHealth += HEALLEVELINC;
			health = maxHealth; // restore health
			return;
		case DEFENSE:
			defense += DEFENSE;
			return;
		case DODGE:
			dodge += DODGE;
			return;
		case LUCK:
			luck += LUCK;
			return;
		case MAGIC:
			magic += MAGIC;
			return;
		case MAXMANA:
			maxMana += MANALEVELINC;
			return;
		case RESISTANCE:
			resistance += RESISTANCE;
			return;
		case STRENGTH:
			strength += STRENGTH;
			return;
		default:
			return;
		}
	}
	
	public void levelUpStat(Stats stat, int levels) {
		switch(stat) {
		case MAXHEALTH:
			maxHealth += HEALLEVELINC*levels;
			health = maxHealth; // restore health
			return;
		case DEFENSE:
			defense += DEFENSE*levels;
			return;
		case DODGE:
			dodge += DODGE*levels;
			return;
		case LUCK:
			luck += LUCK*levels;
			return;
		case MAGIC:
			magic += MAGIC*levels;
			return;
		case MAXMANA:
			maxMana += MANALEVELINC*levels;
			return;
		case RESISTANCE:
			resistance += RESISTANCE*levels;
			return;
		case STRENGTH:
			strength += STRENGTH*levels;
			return;
		default:
			return;
		}
	}
	
	public int getStat(Stats stat) {
		switch(stat) {
		case MAXHEALTH:
			return maxHealth;
		case DEFENSE:
			return defense;
		case DODGE:
			return dodge;
		case LUCK:
			return luck;
		case MAGIC:
			return magic;
		case MAXMANA:
			return maxMana;
		case RESISTANCE:
			return resistance;
		case STRENGTH:
			return strength;
		default:
			return -1;
		}
	}
	
	private int getStatDefault(Stats stat) {
		switch(stat) {
		case MAXHEALTH:
			return MAXHEALTH;
		case DEFENSE:
			return DEFENSE;
		case DODGE:
			return DODGE;
		case LUCK:
			return LUCK;
		case MAGIC:
			return MAGIC;
		case MAXMANA:
			return MAXMANA;
		case RESISTANCE:
			return RESISTANCE;
		case STRENGTH:
			return STRENGTH;
		default:
			return -1;
		}
	}

	
	//Equipped item
	protected Weapon equipped;
	
	public Weapon getEquipped() {
		return equipped;
	}
	
	public int getHealth() {
		return health;
	}
	
	public int getStrength() {
		return strength;
	}
	
	public int getDefense() {
		return defense;
	}
	
	public int getDodge() {
		return dodge;
	}
	
	public int getLuck() {
		return luck;
	}
	
	public int getMagic() {
		return magic;
	}
	
	public int getMana() {
		return mana;
	}
	
	public int getResistance() {
		return resistance;
	}
	
	public void damage(int damage) {
		health -= damage;
	}
	
	public void heal(int heal) {
		health += heal;
	}
	
	public static boolean isStat(String rawStatString) {
	    for (Stats s : Stats.values()) {
	        if (s.name().equals(rawStatString)) {
	            return true;
	        }
	    }
	    return false;
	}
	
}
