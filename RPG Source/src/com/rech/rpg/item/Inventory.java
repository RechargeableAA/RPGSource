package com.rech.rpg.item;

import java.util.Scanner;

import com.rech.rpg.Main;
import com.rech.rpg.Menu;
import com.rech.rpg.Player;

public class Inventory{
	//inventory stored as an array list which is just an array that can dynamically change its size, just a place holder for now
	private static Item[] inventory;
	private final static int maxInventorySize = 10;
	
	public Inventory() {
		inventory = new Item[maxInventorySize]; // inventory can store 10 items
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
		for (int item = 0; item < inventory.length; item++) {
			for (int index = 0; index < inventory.length; ++index) {
				if(inventory[index] == null) {
					inventory[index] = inventory[item];
					inventory[item] = null;
				}
			}
		}
	}
	
	/**
	 * Inventory Menu
	 */
	public void showInventory(Player player, Scanner input) {
		Menu invMenu = new Menu("INVENTORY");
		
		while(true) {
			sortInventory();
			invMenu.prompt.clear();
			//print occupied inventory slots
			int occupiedSlots = 0;
			for(int inventorySlot = 0; inventorySlot < getSize(); inventorySlot++) {
				if(getSlot(inventorySlot) != null) {
					invMenu.prompt.add("["+inventorySlot+"] " + getSlot(inventorySlot).getName());
					System.out.println();
					occupiedSlots++;
				}
			}
			invMenu.prompt.add("Your "+player.getEquipped().getName()+" is equipped.");
			invMenu.prompt.add(occupiedSlots+"/10 backpack slots used.\n");
			invMenu.prompt.add("Coins: "+String.format("%,d",player.getCoins())+"gp\n");
			invMenu.prompt.add("[EQUIP] [DROP] [BACK]");
			
			invMenu.display();
			
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
				invMenu.setMessage("You don't know  what "+optionSelection+" means.");
				break;
			}
		}
	}
	
	
	
	
	
	public void dropItemMenu(Player player, Scanner input) {
		Menu dropMenu = new Menu("DROP");
		
		int emptySlotCount = 0;
		while(true) {
			sortInventory();
			dropMenu.prompt.clear();
			//show inventory again
			dropMenu.prompt.add("Your " + player.getEquipped().getName() + " is equipped.");
			for(int inventorySlot = 0; inventorySlot < getSize(); inventorySlot++) {
				if(getSlot(inventorySlot) != null) {
					dropMenu.prompt.add("["+inventorySlot+"] " + getSlot(inventorySlot).getName());
				}else{
					++emptySlotCount;
				}
			}
			if (emptySlotCount == 10) {
				dropMenu.setMessage("You have nothing in your backpack.");
			}else {
				dropMenu.prompt.add("Which slot do you want to drop? [0-9] [back]");
			}
			dropMenu.display();
			
			String optionSelection = input.nextLine(); 
			switch(optionSelection.toUpperCase()) {
			case "BACK":
				if (emptySlotCount == 10) {
					Main.mainMenu(input); //the inventory menu will still be visible, so it acts as if the drop command never happened. so when you type back, it goes back to the menu before showing the backpack
				}else {
					return;
				}
			default:
				try {
					if(optionSelection.matches("[0-9]+")) { // check if string input is an integer
						int slot = Integer.parseInt(optionSelection);
						dropMenu.setMessage("Are you sure you want to drop " + getSlot(slot).getName() + "? [y/n]");
						dropMenu.display();
						if(input.nextLine().equalsIgnoreCase("y")) {
							System.out.println("You drop your " + getSlot(slot).getName() + ".");
							drop(slot);
						}
					}else {
						dropMenu.setMessage("You don't know what "+optionSelection+" means.");
					}
				}catch (Exception NullPointerException) {
					System.out.println("You cannot drop what you do not have.");
				}
				break;
			}
		}
	}

	
	
	
	
	public void equipMenu(Player player, Scanner input){ //fromMain checks to see if you came from the backpack menu
		Menu equipMenu = new Menu("Equip");
		while(true) {
			sortInventory();
			equipMenu.prompt.clear();
			for(int inventorySlot = 0; inventorySlot < getSize(); inventorySlot++) {
				if(getSlot(inventorySlot) != null) {
					equipMenu.prompt.add("["+inventorySlot+"] " + getSlot(inventorySlot).getName());
				}
			}
			equipMenu.prompt.add("Which slot do you want to equip? [0-9] [back]");
			
			equipMenu.display();
			
			//error catching string to int
			String optionSelection = input.nextLine(); 
			switch(optionSelection.toUpperCase()) {
			case "BACK":
				return;
			default:
				if(optionSelection.matches("[0-9]+")) {
					int slot = Integer.parseInt(optionSelection);
					if(getSlot(slot) != null) {
						if(getSlot(slot) instanceof Weapon) { // checks to see if item is a weapon
							equipMenu.setMessage("You equip your " + getSlot(slot).getName() + ".");
							player.equip(slot);
						}else {
							equipMenu.setMessage("You can't equip a " + player.getInventory().getSlot(slot).getName());
						}
					}else {
						equipMenu.setMessage("There's nothing in that inventory slot.");
					}
				}else {
					equipMenu.setMessage("You don't know what "+optionSelection+" means.");
				}
				break;
			}
		}
	}

	
	public Item getSlot(int i) {
		return inventory[i];
	}
	
	public int getSize() {
		return inventory.length;
	}
	
	public static boolean isFull() { //purely for checking if inv is full
		if (inventory[maxInventorySize] != null) {
			return true;
		}else {
			return false;
		}
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
