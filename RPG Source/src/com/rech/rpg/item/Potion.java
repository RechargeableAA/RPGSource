package com.rech.rpg.item;

public class Potion extends Item{

	//what the potion does to player stats
	private int healthAddition;
	private int manaAddition;

	
	protected Potion(String itemName, int cost, int healthAdditon, int manaAddition) {
		super(itemName, cost, true);
		
		this.healthAddition = healthAdditon;
		this.manaAddition = manaAddition;
	}
	
	//Health pots
	public static final Potion minorHealth = new Potion("Minor Health Potion", 10, 10, 0);
	public static final Potion standardHealth = new Potion("Standard Health Potion", 10, 20, 0);
	public static final Potion majorHealth = new Potion("Major Health Potion", 10, 30, 0);
	
	//Mana pots
	public static final Potion minorMana = new Potion("Minor Mana Potion", 10, 0, 10);
	public static final Potion standardMana = new Potion("Standard Mana Potion", 10, 0, 20);
	public static final Potion majorMana = new Potion("Major Mana Potion", 10, 0, 30);
	
	public int getHealAmount() {
		return healthAddition;
	}
	
	public int getManaAmount() {
		return manaAddition;
	}

}
