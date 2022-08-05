package com.rech.rpg.map.shop;

import java.util.Scanner;

import com.rech.rpg.Main;
import com.rech.rpg.Menu;
import com.rech.rpg.entity.Player;
import com.rech.rpg.item.Item;

public class Shop {
	String name;
	String shopKeepDialogue;
	Item[] items;
	
	public Shop(String shopName, String shopKeepDialogue, Item[] shopItems) {
		this.name = shopName;
		this.shopKeepDialogue = shopKeepDialogue;
		this.items = shopItems;
	}
	
	public void interact(Scanner input, Player player) {
		Menu shop = new Menu(name);
		while(true) {
			shop.clearPrompts();
			shop.setMenuInfo("Hello " + player.getName() + "... " + shopKeepDialogue +
								"\n------------Potions-------------"); // quest?
			
			for(int item = 0; item < this.items.length; item++) {
				shop.addPrompt(""+(item+1), this.items[item].getName());
			}
							
			shop.addPrompt("back");
			shop.display();
			shop.clearMessage();
			
			String in = input.nextLine(); //has to be taken as a string to catch the 'back' command. is parsed as an int after.
			
			switch(in.toUpperCase()) {
			case "BACK":
				return;
			default:
				if(in.matches("[0-9]+")) { // selection was an item
					int selection = Integer.parseInt(in)-1; // -1 to match starting with 0 in arrays
					
					if(selection < this.items.length && selection != -1) {
					
						if (player.getInventory().getCoins() >= this.items[selection].getCost()) {
							shop.clearPrompts();
							shop.setMenuInfo("Ah, a " + this.items[selection].getName() + " that'll be " + this.items[selection].getCost() + " coins.\n" + 
												"You have "+player.getInventory().getCoins()+" coins right now.");
							shop.addPrompt("y/n", "Pay the shopkeep " + this.items[selection].getCost() + " coins?");
							shop.display();
							
							if(input.nextLine().equalsIgnoreCase("y")) {
								while(true)	{
									if (!player.getInventory().isFull()) { 
										player.getInventory().pickup(this.items[selection]);
										player.getInventory().loseCoins(this.items[selection].getCost());
										shop.message("You give the shopkeep "+this.items[selection].getCost()+" coins and receive the " + this.items[selection].getName() + ".");
										break;
									}else {
										shop.clearPrompts();
										shop.message("Your backpack is full!\nDo you want to drop something to make room for it? [y/n]");
										in = input.nextLine();
										
										if (in.equalsIgnoreCase("y")){
											player.getInventory().dropItemMenu(player, input);
										}else if (in.equalsIgnoreCase("n") || in.equalsIgnoreCase("back")){
											shop.clearMessage();
											break;
										}else {
											shop.message("I'm not sure what you mean by " + in.toUpperCase() + ".");
										}
									}
								}	
							}else { // player responded something other than y to "do you want to buy"
								
							}
						}else { // player does not have enough money
							shop.message("You can't afford that!");
						}
					
					}else { // selection was not within the shop's item array
						shop.message("I'm afraid that this is all I have to offer you right now.");
					}
				}else { // selection was not an item
					shop.message("I'm sorry, I didnt catch that.");
				}
				break;
			}
		}
	}
	
	public Item[] getItems() {
		return items;
	}
	
}
