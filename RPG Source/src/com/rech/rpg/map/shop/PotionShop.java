package com.rech.rpg.map.shop;

import java.util.Scanner;

import com.rech.rpg.Main;
import com.rech.rpg.Player;
import com.rech.rpg.item.Inventory;
import com.rech.rpg.item.Potion;
import com.rech.rpg.map.TownGenerator;

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
		
		// bool to keep the shopping look going
		boolean stillShopping = true;
		do {
			System.out.println("\n\nHello "+player.getName()+"... A potion for all your needs..."); // quest?
			System.out.println("------------Potions-----------");
			System.out.println("[1] + "+shopInventory[0].getName()
							+"\n[2] + "+shopInventory[1].getName()
							+"\n[3] + "+shopInventory[2].getName());
	
			System.out.println("\nWhich would you like to buy? [1-3] [back]");
			
			String in = input.nextLine(); //has to be taken as a string to catch the 'back' command. is parsed as an int after.
			int selection = 0;
			
			try {
				selection = Integer.parseInt(in)-1;// subtract 1 to correspond with array index
			}catch (Exception InputMismatchException) {
				if (in.equals("back")) {
					TownGenerator.populateTown(); //TownGenerator class isnt finished. so this is a placeholder for now. not sure where to return the player to
				}else {
					System.out.println("I'm sorry, I didnt catch that.");
					interact(input, player);
				}
			}
			
			try {
				// the beauty of using objects! every selection contained into one code block
				System.out.println("Ah, a " + shopInventory[selection].getName() + " that'll be " + shopInventory[selection].getCost() + " coins.");
				System.out.println("Pay the shopkeep " + shopInventory[selection].getCost() + " coins? [y/n]\nYou have "+player.getCoins()+" coins right now.");
				if(input.next().equalsIgnoreCase("y")) {
					if (player.getCoins() >= shopInventory[selection].getCost()) { //need to see if theres room in the inventory before taking money
						if (Main.player.getInventory().isFull() == false) {
							player.getInventory().pickup(shopInventory[selection]);
							player.loseCoins(shopInventory[selection].getCost());
							System.out.println("You give the shopkeep "+shopInventory[selection].getCost()+" coins and recieve the " + shopInventory[selection] + ".");	
						}
					}else {
						System.out.println("You can't afford that!");
					}
					
				}
			}
			catch (Exception indexOutOfBounds) { //catches if player enters a word instead of a number, or if index-outofbounds
				//if (selection > shopInventory.length+1 || selection < shopInventory.length+1)  {
					System.out.println("\nI'm afraid that this is all I have to offer you right now.");
				//}
				interact(input, player);
			}
			
			System.out.println("Keep shopping? [y/n]");
			stillShopping = input.next().equalsIgnoreCase("y"); // quick way to set still shopping to true/false
		}while(stillShopping);
		
	}			
}
