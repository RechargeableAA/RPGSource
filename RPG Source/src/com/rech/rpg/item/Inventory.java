package com.rech.rpg.item;

import java.util.ArrayList;

import com.rech.rpg.Player;

public class Inventory{
	//inventory stored as an array list which is just an array that can dynamically change its size, just a place holder for now
	private Item[] inventory;

	
	public Inventory() {
		inventory = new Item[10]; // inventory can store 10 items
	}
	
	public void pickup(Item item) {
		for(int i = 0; i < inventory.length; i++) {
			if(inventory[i] == null) {
				inventory[i] = item;
				return;
			}
		}
		System.out.println("Your inventory is full!");
	}
	
	public void sortInventory() {
		for (int i = 1; i < 9; ++i) {
			for (int index = 1; index < 9; ++index) {
				if (inventory[index+1] != null && inventory[index] == null) {
					System.out.println("index: "+index+" current slot = "+inventory[index]+" next slot = "+inventory[index+1]);
					inventory[index] = inventory[index+1];
					inventory[index+1] = null;
				}
			}
		}
	}
	
	public Item getSlot(int i) {
		return inventory[i];
	}
	
	public int getSize() {
		return inventory.length;
	}

	public void setSlot(int slot, Item item) {
		inventory[slot] = item;
	}
	
	public void drop(int slot) {
		inventory[slot] = null;
	}
	 
}
