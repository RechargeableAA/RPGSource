package com.rech.rpg.map.shop;

import java.util.Scanner;

import com.rech.rpg.Main;
import com.rech.rpg.Menu;
import com.rech.rpg.Player;
import com.rech.rpg.item.Inventory;
import com.rech.rpg.item.Potion;
//import com.rech.rpg.map.TownGenerator;

public class PotionShop extends Shop{

	public PotionShop(String shopName, Potion[] potions) {
		super(shopName, potions);
	}

	
	
	@Override
	public void interact(Scanner input, Player player) {
		Potion shopInventory[] = {
				Potion.minorHealth,
				Potion.standardHealth,
				Potion.majorMana
		};
		
		Menu potShop = new Menu(shopName);
		while(true) {
			potShop.clearPrompts();
			potShop.setMenuInfo("Hello "+player.getName()+"... A potion for all your needs... \n" +
								"------------Potions-------------"); // quest?
			potShop.addPrompt("1", shopInventory[0].getName());
			potShop.addPrompt("2", shopInventory[1].getName());
			potShop.addPrompt("3", shopInventory[2].getName());
							
			potShop.addPrompt("back");
			potShop.display();
			potShop.clearMessage();
			
			String in = input.nextLine(); //has to be taken as a string to catch the 'back' command. is parsed as an int after.
			
			switch(in.toUpperCase()) {
			case "BACK":
				return;
			default:
				if(in.matches("[0-9]+")) { // selection was an item
					int selection = Integer.parseInt(in)-1; // -1 to match starting with 0 in arrays
					
					if(selection < shopInventory.length && selection != -1) {
					
						if (player.getInventory().getCoins() >= shopInventory[selection].getCost()) {
							potShop.clearPrompts();
							potShop.setMenuInfo("Ah, a " + shopInventory[selection].getName() + " that'll be " + shopInventory[selection].getCost() + " coins.\n" + 
												"You have "+player.getInventory().getCoins()+" coins right now.");
							potShop.addPrompt("y/n", "Pay the shopkeep " + shopInventory[selection].getCost() + " coins?");
							potShop.display();
							
							if(input.nextLine().equalsIgnoreCase("y")) {
								while(true)	{
									if (!Main.player.getInventory().isFull()) { 
										player.getInventory().pickup(shopInventory[selection]);
										player.getInventory().loseCoins(shopInventory[selection].getCost());
										potShop.message("You give the shopkeep "+shopInventory[selection].getCost()+" coins and receive the " + shopInventory[selection].getName() + ".");
										break;
									}else {
										potShop.clearPrompts();
										potShop.message("Your backpack is full!\nDo you want to drop something to make room for it? [y/n]");
										in = input.nextLine();
										
										if (in.equalsIgnoreCase("y")){
											player.getInventory().dropItemMenu(player, input);
										}else if (in.equalsIgnoreCase("n") || in.equalsIgnoreCase("back")){
											potShop.clearMessage();
											break;
										}else {
											potShop.message("I'm not sure what you mean by " + in.toUpperCase() + ".");
										}
									}
								}	
							}else { // player responded something other than y to "do you want to buy"
								
							}
						}else { // player does not have enough money
							potShop.message("You can't afford that!");
						}
					
					}else { // selection was not within the shop's item array
						potShop.message("I'm afraid that this is all I have to offer you right now.");
					}
				}else { // selection was not an item
					potShop.message("I'm sorry, I didnt catch that.");
				}
				break;
			}
		}
	}			
}