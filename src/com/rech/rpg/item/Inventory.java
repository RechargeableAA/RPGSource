package com.rech.rpg.item;

import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

import com.rech.rpg.Main;
import com.rech.rpg.Menu;
import com.rech.rpg.entity.Player;

public class Inventory{
	//inventory stored as an array list which is just an array that can dynamically change its size, just a place holder for now
	private int coins;
	private Item[] inventory;
	private final static int inventorySize = 10;
	
	public Inventory() {
		inventory = new Item[inventorySize]; // inventory can store 10 items
	}
	
	public void pickup(Item item) {
		for(int i = 0; i < inventory.length; i++) {
			if(inventory[i] == null) {
				inventory[i] = item;
				return;
			}
		}
		System.out.println("Your inventory is full!"); //there needs to be an option to drop something to make room
	}
	
	
	public void sortInventory() {
		inventory = Arrays.stream(inventory).filter(Objects::nonNull).toArray(Item[]::new);
	}
	
	/**
	 * Inventory Menu
	 */
	public void showInventory(Player player, Scanner input) {
		Menu invMenu = new Menu("INVENTORY");
		
		while(true) {
			sortInventory();
			invMenu.clearPrompts();
			//print occupied inventory slots
			int occupiedSlots = 0;
			for(int inventorySlot = 0; inventorySlot < getSize(); inventorySlot++) {
				if(getSlot(inventorySlot) != null) {
					invMenu.addPrompt(""+inventorySlot, ""+ getSlot(inventorySlot).getName());
					System.out.println();
					occupiedSlots++;
				}
			}
			
			if (occupiedSlots > 0) {
			invMenu.setMenuInfo("Your "+player.getEquipped().getName()+" is equipped.\n" + 
								occupiedSlots+"/10 backpack slots are being used.\n" +
								"Coins: "+String.format("%,d", getCoins())+"gp\n");
			}else {
				invMenu.setMenuInfo("Your "+player.getEquipped().getName()+" is equipped.\nNone of your backpack slots are being used.\n" +
						"Coins: "+String.format("%,d", getCoins())+"gp\n");
			}
			invMenu.addPrompt("EQUIP");
			invMenu.addPrompt("DROP");	
			invMenu.addPrompt("BACK");	
			invMenu.display(false);
			
			
			String optionSelection = input.nextLine().toString();
			switch(optionSelection.toUpperCase()) {
			case "EQUIP":
				equipMenu(player, input);
				break;
			case "DROP":
				dropItemMenu(player, input);
				break;
			case "BACK":
				return;
			default:
				invMenu.message("You don't know  what "+optionSelection+" means.", input);
				break;
			}
		}
	}
	
	
	public void dropItemMenu(Player player, Scanner input) {
		Menu dropMenu = new Menu("DROP");
		
		while(true) {
			sortInventory();
			dropMenu.clearPrompts();
			
			//show inventory again
			dropMenu.setMenuInfo("Your " + player.getEquipped().getName() + " is equipped.");
			for(int inventorySlot = 0; inventorySlot < getSize(); inventorySlot++) {
				if(getSlot(inventorySlot) != null) {
					dropMenu.addPrompt(""+inventorySlot, ""+getSlot(inventorySlot).getName());
				}
			}
			
			if (this.isEmpty()) { // if this(inventory) is empty
				dropMenu.message("You have nothing in your backpack. [BACK]", input);
			}else {
				dropMenu.addPrompt("0-9", "Which slot do you want to drop?");
				dropMenu.addPrompt("BACK");
			}
			
			dropMenu.display(false);
			
			String optionSelection = input.nextLine(); 
			switch(optionSelection.toUpperCase()) {
			case "BACK":
				if (this.isEmpty()) { //the inventory menu will still be visible, so it acts as if the drop command never happened. so when you type back, it goes back to the menu before showing the backpack
					Main.mainMenu(input); 
				} 
				return;
			default:
					if(optionSelection.matches("[0-9]+")) { // check if string input is an integer
						int slot = Integer.parseInt(optionSelection);
							if(inventory[slot] != null) {
								dropMenu.clearPrompts();
								dropMenu.addPrompt("y/n", "Are you sure you want to drop " + getSlot(slot).getName() + "?");
								dropMenu.display(true);
								if(input.nextLine().equalsIgnoreCase("y")) {
									dropMenu.clearPrompts();
									dropMenu.message("You drop your " + getSlot(slot).getName() + ".", input);
									dropMenu.display(true);
									input.nextLine();
									drop(slot);
									
								}
							}else {
								dropMenu.message("There isn't an item in that slot.", input);
							}
					}else {
						dropMenu.message("You don't know what "+optionSelection+" means.", input);
					}
				break;
			}
		}
	}

	
	public void equipMenu(Player player, Scanner input){ //fromMain checks to see if you came from the backpack menu
		Menu equipMenu = new Menu("Equip");
		
		while(true) {
			//adding inventory to menu
			sortInventory();
			equipMenu.clearPrompts();
			for(int inventorySlot = 0; inventorySlot < getSize(); inventorySlot++) {
				if(getSlot(inventorySlot) != null) {
					equipMenu.addPrompt(""+inventorySlot,  getSlot(inventorySlot).getName());
				}
			}
			
			if (this.isEmpty()) { // if this(inventory) is empty
				equipMenu.message("You have nothing in your backpack. [BACK]", input);
			}else {
				equipMenu.addPrompt("0-9", "Which slot do you want to equip? [back]");
				equipMenu.addPrompt("back");
			}
			
			equipMenu.display(false);
			
			//error catching string to int
			String optionSelection = input.nextLine(); 
			switch(optionSelection.toUpperCase()) {
			case "BACK":
				if (this.isEmpty()) { //the inventory menu will still be visible, so it acts as if the drop command never happened. so when you type back, it goes back to the menu before showing the backpack
					Main.mainMenu(input); 
				} 
				return;
			default:
				if(optionSelection.matches("[0-9]+")) { // is optionSelection a number stored in a string?
					int slot = Integer.parseInt(optionSelection); // convert string of number into an integer
					if(getSlot(slot) != null) {
						if(getSlot(slot) instanceof Weapon) { // checks to see if item is a weapon
							equipMenu.message("You equip your " + getSlot(slot).getName() + ".", input);
							player.equip(slot);
						}else {
							equipMenu.message("You can't equip a " + player.getInventory().getSlot(slot).getName(), input);
						}
					}else {
						equipMenu.message("There's nothing in that inventory slot.", input);
					}
				}else {
					equipMenu.message("You don't know what "+optionSelection+" means.", input);
				}
				break;
			}
		}
	}

	public boolean isEmpty() {
		return Arrays.stream(inventory).filter(Objects::nonNull).toArray(Item[]::new).length == 0;
		//left side effectively uses java's Array util to read the array, filter out null values, convert back to an array, and get it's length
	}
	
	public Item getSlot(int i) {
		return inventory[i];
	}
	
	public int getSize() {
		return inventory.length;
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
