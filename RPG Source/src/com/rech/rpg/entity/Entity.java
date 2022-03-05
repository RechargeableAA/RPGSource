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
}
