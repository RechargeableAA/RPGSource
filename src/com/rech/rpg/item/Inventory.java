package com.rech.rpg.item;

import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

import com.rech.rpg.Main;
import com.rech.rpg.Menu;
import com.rech.rpg.entity.Player;
import com.rech.rpg.gamestate.MainMenu;

public class Inventory{
	//inventory stored as an array list which is just an array that can dynamically change its size, just a place holder for now
	private int coins;
	private Item[] inventory;
	private final static int inventorySize = 10;
	
	public Inventory() {
		inventory = new Item[inventorySize]; // inventory can store 10 items
	}
	
	public void pickup(Item item) {
		for(int i = 0; i < getSize(); i++) {
			System.out.println(getSlot(i));
			if(getSlot(i) == null) {
				inventory[i] = item;
				return;
			}
		}
		System.out.println("Your inventory is full!"); //there needs to be an option to drop something to make room
	}
	
	
	public void sortInventory() {
		Item[] tempArray = Arrays.stream(inventory).filter(Objects::nonNull).toArray(Item[]::new);
		inventory = new Item[inventorySize];
        System.arraycopy(tempArray, 0, inventory, 0, tempArray.length);
	}
	
	public boolean isEmpty() {
		return Arrays.stream(inventory).filter(Objects::nonNull).toArray(Item[]::new).length == 0;
		//left side effectively uses java's Array util to read the array, filter out null values, convert back to an array, and get it's length
	}
	
	public Item getSlot(int i) {
		try{
			return inventory[i];
		}catch (IndexOutOfBoundsException iob){
			return null;
		}
	}
	
	public int getSize() {
		return inventorySize;
	}
	
	public int getCoins() {
		return coins;
	}
	
	public Item[] getItems() {
		return inventory;
	}
	
	public void setCoins(int amount) {
		coins = amount;
	}
	
	public void addCoins(int amount) {
		coins += amount;
	}
	
	public void loseCoins(int amount) {
		coins = coins-amount;
	}
	
	
	/**
	 * Determine if inventory is full
	 * @return true - if full; false - contains empty slots
	 */
	public boolean isFull() { // Arrays cannot reference a null index
		return Arrays.stream(inventory).filter(Objects::nonNull).toArray(Item[]::new).length == inventorySize;
		//left side effectively uses java's Array util to read the array, filter out null values, convert back to an array, and get it's length
	}

	public void setSlot(int slot, Item item) {
		inventory[slot] = item;
	}
	
	public void drop(int slot) {
		inventory[slot] = null;
	}
	
	public void dropPrompt() {
		//yes or no? may need to modify the drop functionality so that it doesnt take you back to the equip menu
	}

}
