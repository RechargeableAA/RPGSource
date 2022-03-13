package com.rech.rpg.entity;

import com.rech.rpg.item.Weapon;

public class Entity {
	protected int
	level,
	health,
	maxHealth,
	strength,
	defense,
	dodge,
	luck,
	magic,
	mana,
	maxMana,
	resistance;
	
	protected String name;
	
	//Equipped item
	protected Weapon equipped;
	
	public Weapon getEquipped() {
		return equipped;
	}
	
	public int getLevel() {
		return level;
	}
	
	public int getHealth() {
		return health;
	}
	
	public int getMaxHealth() {
		return maxHealth;
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
	
	public int getMaxMana() {
		return maxMana;
	}
	
	public void damage(int damage) {
		health -= damage;
	}
	
	public void heal(int heal) {
		health += heal;
	}
	
}
