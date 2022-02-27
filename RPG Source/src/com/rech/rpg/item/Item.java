package com.rech.rpg.item;
/*
 * This class defines things that can be stored in inventory, picked up, dropped, equipped
 * 
 */
public abstract class Item{
	private String itemName;
	private int cost;
	private boolean stackable;

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
