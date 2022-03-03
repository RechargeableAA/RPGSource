package com.rech.rpg.item;

import java.util.Scanner;

import com.rech.rpg.Main;
import com.rech.rpg.Menu;
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
	
<<<<<<< Updated upstream
=======
	
>>>>>>> Stashed changes
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
			invMenu.options.clear();
			//print occupied inventory slots
			int occupiedSlots = 0;
			for(int inventorySlot = 0; inventorySlot < getSize(); inventorySlot++) {
				if(getSlot(inventorySlot) != null) {
					invMenu.options.add("["+inventorySlot+"] " + getSlot(inventorySlot).getName());
					System.out.println();
					occupiedSlots++;
				}
			}
			invMenu.options.add("Your "+player.getEquipped().getName()+" is equipped.");
			invMenu.options.add(occupiedSlots+"/10 backpack slots used.\n");
			invMenu.options.add("Coins: "+String.format("%,d",player.getCoins())+"gp\n");
			invMenu.options.add("[EQUIP] [DROP] [BACK]");
			
			invMenu.display();
			
			
			String optionSelection = input.nextLine().toString();
			switch(optionSelection.toUpperCase()) {
			case "EQUIP":
				equipMenu(player, input);
				break;
			case "DROP":
				dropItemMenu(input);
				break;
			case "BACK":
				return;
			default:
				invMenu.message("You don't know  what "+optionSelection+" means.");
				break;
			}
		}
	}
	
	
<<<<<<< Updated upstream
	public void dropItemMenu(Scanner input) {
=======
	public void dropItemMenu(Player player, Scanner input) {
>>>>>>> Stashed changes
		Menu dropMenu = new Menu("DROP");
		
		while(true) {
			sortInventory();
<<<<<<< Updated upstream
			dropMenu.options.clear();
=======
			dropMenu.prompt.clear();
			
>>>>>>> Stashed changes
			//show inventory again
			for(int inventorySlot = 0; inventorySlot < getSize(); inventorySlot++) {
				if(getSlot(inventorySlot) != null) {
<<<<<<< Updated upstream
					dropMenu.options.add("["+inventorySlot+"] " + getSlot(inventorySlot).getName());
				}
			}
			dropMenu.options.add("Which slot do you want to drop? [0-9] [back]");
=======
					dropMenu.prompt.add("["+inventorySlot+"] " + getSlot(inventorySlot).getName());
				}
			}
			
			if (this.isEmpty()) { // if this(inventory) is empty
				dropMenu.message("You have nothing in your backpack. [BACK]");
			}else {
				dropMenu.prompt.add("Which slot do you want to drop? [0-9] [BACK]");
			}
>>>>>>> Stashed changes
			
			dropMenu.display();
			
			String optionSelection = input.nextLine(); 
			switch(optionSelection.toUpperCase()) {
			case "BACK":
<<<<<<< Updated upstream
				return;
			default:
				if(optionSelection.matches("[0-9]+")) { // check if string input is an integer
					int slot = Integer.parseInt(optionSelection);
					if(getSlot(slot) != null) {
						dropMenu.setMessage("Are you sure you want to drop " + getSlot(slot).getName() + "? [y/n]");
						if(input.nextLine().equalsIgnoreCase("y")) {
							System.out.println("You drop your " + getSlot(slot).getName() + ".");
							drop(slot);
						}
=======
				if (this.isEmpty()) { //the inventory menu will still be visible, so it acts as if the drop command never happened. so when you type back, it goes back to the menu before showing the backpack
					Main.mainMenu(input); 
				} 
				return;
			default:
				try {
					if(optionSelection.matches("[0-9]+")) { // check if string input is an integer
						int slot = Integer.parseInt(optionSelection);
							if(inventory[slot] != null) {
								dropMenu.prompt.clear();
								dropMenu.clearMessage();
								dropMenu.prompt.add("Are you sure you want to drop " + getSlot(slot).getName() + "? [y/n]");
								dropMenu.display();
								if(input.nextLine().equalsIgnoreCase("y")) {
									dropMenu.prompt.clear();
									dropMenu.prompt.add("You drop your " + getSlot(slot).getName() + ".");
									dropMenu.display();
									input.nextLine();
									drop(slot);
									
								}
							}else {
								dropMenu.message("There isn't an item in that slot.");
							}
					}else {
						dropMenu.message("You don't know what "+optionSelection+" means.");
>>>>>>> Stashed changes
					}
				}else {
					dropMenu.setMessage("You don't know what "+optionSelection+" means.");
				}
				break;
			}
		}
	}

<<<<<<< Updated upstream
=======
	
>>>>>>> Stashed changes
	public void equipMenu(Player player, Scanner input){ //fromMain checks to see if you came from the backpack menu
		Menu equipMenu = new Menu("Equip");
		
		while(true) {
			//adding inventory to menu
			sortInventory();
			equipMenu.options.clear();
			for(int inventorySlot = 0; inventorySlot < getSize(); inventorySlot++) {
				if(getSlot(inventorySlot) != null) {
					equipMenu.options.add("["+inventorySlot+"] " + getSlot(inventorySlot).getName());
				}
			}
<<<<<<< Updated upstream
			equipMenu.options.add("Which slot do you want to equip? [0-9] [back]");
=======
			
			if (this.isEmpty()) { // if this(inventory) is empty
				equipMenu.message("You have nothing in your backpack. [BACK]");
			}else {
				equipMenu.prompt.add("Which slot do you want to equip? [0-9] [back]");
			}
>>>>>>> Stashed changes
			
			equipMenu.display();
			
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
							equipMenu.message("You equip your " + getSlot(slot).getName() + ".");
							player.equip(slot);
						}else {
							equipMenu.message("You can't equip a " + player.getInventory().getSlot(slot).getName());
						}
					}else {
						equipMenu.message("There's nothing in that inventory slot.");
					}
				}else {
					equipMenu.message("You don't know what "+optionSelection+" means.");
				}
				break;
			}
		}
	}

	public boolean isEmpty() {
		sortInventory();
		if(inventory[0] == null) {
			return true;
		}
		return false;
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
