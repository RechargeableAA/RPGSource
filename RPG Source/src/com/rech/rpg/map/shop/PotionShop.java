package com.rech.rpg.map.shop;

import java.util.Scanner;

import com.rech.rpg.Player;
import com.rech.rpg.item.Potion;

public class PotionShop extends Shop{

	public PotionShop(String shopName, Potion[] potions) {
		super(shopName, potions);
	}

	@Override
	public void interact(Scanner input, Player player) {
		Potion shopInventory[] = {
				Potion.minorHealth,
				Potion.standardHealth,
				Potion.minorMana
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
			
			int selection = input.nextInt()-1; // subtract 1 to correspond with array index
			
			try {
				// the beauty of using objects! every selection contained into one code block
				System.out.println("Ah, a " + shopInventory[selection].getName() + " that'll be " + shopInventory[selection].getCost() + " coins.");
				System.out.println("Pay the shopkeep " + shopInventory[selection].getCost() + " coins? [y/n]");
				if(input.next().equalsIgnoreCase("y")) {
					player.getInventory().pickup(shopInventory[selection]);
				}
			}
			catch (Exception e) { //catches if player enters a word instead of a number, or if index-outofbounds
				if (selection > 2 || selection < 0) {
					System.out.println("\nI'm afraid that this is all I have to offer you right now.");
				}else {
					System.out.println("\nI'm sorry, I didn't catch that.");
				}
				interact(input, player);
			}
			
			System.out.println("Keep shopping? [y/n]");
			stillShopping = input.nextLine().equalsIgnoreCase("y"); // quick way to set still shopping to true/false
		}while(stillShopping);
		
	}			
}
