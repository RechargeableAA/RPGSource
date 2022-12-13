package com.rech.rpg.map.shop;

import java.util.Random;
import java.util.Scanner;

import com.rech.rpg.Main;
import com.rech.rpg.entity.Player;
import com.rech.rpg.entity.menu.Menu;
import com.rech.rpg.item.Item;
import com.rech.rpg.item.Weapon;

public class Shop {
	String name;
	String shopKeepDialogue;
	Item[] items;
	shopType type;
	
	/**
	 * Pool of shop types to be used during town generation
	 * @author Nolan
	 *
	 */
	public static enum shopType {
		WEAPON,
		POTION
		};
	
	/**
	 * Should not call this directly; should be referenced by shops with a type. Could add a method somewhere for shops without a specific type
	 * @param shopName
	 * @param shopKeepDialogue
	 * @param shopItems
	 */
	public Shop(String shopName, String shopKeepDialogue, Item[] shopItems) {
		this.name = shopName;
		this.shopKeepDialogue = shopKeepDialogue;
		this.items = shopItems;
	}
	
	public static Shop generateShop(shopType type) {
		switch(type) {
		case POTION: //needs potionshop generation
			return WeaponShop.generateShop(3);
		case WEAPON:
			return WeaponShop.generateShop(3);
		default:
			return WeaponShop.generateShop(0);
		}
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
			shop.display(true);
			
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
							shop.display(true);
							
							if(input.nextLine().equalsIgnoreCase("y")) {
								while(true)	{
									if (!player.getInventory().isFull()) { 
										player.getInventory().pickup(this.items[selection]);
										player.getInventory().loseCoins(this.items[selection].getCost());
										shop.message("You give the shopkeep "+this.items[selection].getCost()+" coins and receive the " + this.items[selection].getName() + ".", input);
										break;
									}else {
										shop.clearPrompts();
										shop.message("Your backpack is full!\nDo you want to drop something to make room for it? [y/n]", input);
										in = input.nextLine();
										
										if (in.equalsIgnoreCase("y")){
											player.getInventory().dropItemMenu(player, input);
										}else if (in.equalsIgnoreCase("n") || in.equalsIgnoreCase("back")){
											break;
										}else {
											shop.message("I'm not sure what you mean by " + in.toUpperCase() + ".", input);
										}
									}
								}	
							}else { // player responded something other than y to "do you want to buy"
								
							}
						}else { // player does not have enough money
							shop.message("You can't afford that!", input);
						}
					
					}else { // selection was not within the shop's item array
						shop.message("I'm afraid that this is all I have to offer you right now.", input);
					}
				}else { // selection was not an item
					shop.message("I'm sorry, I didnt catch that.", input);
				}
				break;
			}
		}
	}
	
	public String getName() {
		return name;
	}
	
	public Item[] getItems() {
		return items;
	}
	
	public static class WeaponShop{
		/**
		 * Generate a weapon typed shop
		 * @return
		 */
		public static Shop generateShop(int maxRarity) {
			Random rand = new Random();
			Item items[] = new Item[1+rand.nextInt(4)];
			for(int i = 0; i < items.length; i++) {
				items[i] = Weapon.generateNewWeapon(maxRarity, maxRarity);
			}
			
			return new Shop("Weapon Shop", "We sell weapons and weapon accessories", items);
		}
	}
	
}
