package com.rech.rpg.item;

import com.rech.rpg.entity.Entity;

/*
 * This class defines things that can be stored in inventory, picked up, dropped, equipped
 * 
 */
public abstract class Item{
	private final String itemName;
	private final int cost;
	private final boolean stackable;

	protected Item(String itemName, int cost, boolean stackable) {
		this.itemName = itemName;
		this.cost = cost;
		this.stackable = stackable;
	}
	
	public String getName() {
		return itemName;
	}
	
	public int getCost() {
		return cost;
	}
	
}
