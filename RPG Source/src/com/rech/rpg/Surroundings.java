package com.rech.rpg;
import java.util.Scanner;

import com.rech.rpg.item.Potion;
import com.rech.rpg.item.Weapon;

import java.util.Arrays;
import java.util.List;

public class Surroundings {

	public static boolean isInvFull = false;

	private static String[] localAreas = new String[4]; // allocates 3 total options to configure per town. also used in individual methods to use in dialogue
	private static String[] localInput = new String[4]; // the actual typable input, is here so that player can't enter an input that isn't available in a certain town. last output needs to be BACK
	//"Graydrift", "Greenville", "Kilrock", "Bleakhost", "Duskwood", "Stormvalley", "Summerfelt", "Nevershore", "Southport", "Direwatch"

	public static void setLocation(Player player) {
		if (player.getLocation().equals("Graydrift")) {
			player.setSector(0);
			localAreas[0] = "Blacksmith"; localInput[0] = "blacksmith";
			localAreas[1] = "Gavin's Home"; localInput[1] = "gavin";
			localAreas[2] = "Potion Seller"; localInput[2] = "potions";
			localInput[3] = "back";
			System.out.println("You are currently in the town of Graydrift.\nTo the West, there is the [BLACKSMITH]. Just North of me, is [GAVIN]'s Home. To the East is the [POTIONS] Seller.\n[BACK]");
		}//TODO add other location options

		List<String> validInput = Arrays.asList(localInput);
		Scanner input = new Scanner(System.in);
		String in = input.nextLine().toString();

		if (validInput.contains(in) == true) {//will have all valid inputs for any town, but is narrowed down by 'localInputs'
			if (in.equals("back")) {
				return;
			}else if (in.equals("blacksmith")) {
				shopWeapons(player, "Blacksmith");
			}else if (in.equals("potions")) {
				shopPotions(player);
			}//TODO add all possible inputs. potion sellers, mines, banks, etc
		}else {
			System.out.println("You don't know what "+in+" means.");
			setLocation(player);
		}
	}


	public static void shopWeapons(Player player, String shopName) { //if statement for locations to sell different items
		Weapon weaponsForSale[] = new Weapon[3];
			System.out.println("\n\n\nWelcome to the "+player.getLocation()+" "+shopName+" traveler!");
			System.out.println("\nCoins: "+player.getCoins()+"gp\n");
			
		int x = 0;
		if (player.getLocation().equals("Graydrift")){ // show currently contains very expensive weapons, need to create more static weapons
			for (x = 0; x < 3; ++x) {
				weaponsForSale[x] = Weapon.generateNewWeapon(2, 2);
				System.out.println("["+x+"] " + weaponsForSale[x].getName());
			}
		}
		
		Scanner input;
		while(true) { // repeats option selection w/o a showmenu variable, just need to break; the loop or enter another method
			input = new Scanner(System.in);
			System.out.println("What are you lookin' to buy? [0-" +weaponsForSale.length+ "]" +"[BACK]");
			
			String optionSelection = input.nextLine(); 
			
			//catching string to int error, also handles option for back. probably not the best way to do this
			int intSelection = 0;
			try {
				intSelection = Integer.valueOf(optionSelection);
			}catch(NumberFormatException e) {
				if(optionSelection.equalsIgnoreCase("back")) {
					return;
				}else {
					System.out.println("You don't know  what "+optionSelection+" means.");
				}
			}
			
			if(intSelection >= 0 && intSelection < weaponsForSale.length) {
				System.out.println("Are you sure you want to buy a " + weaponsForSale[intSelection].getName() + "? [y/n]");
				if(input.next().equalsIgnoreCase("y")) {
					if(player.getCoins() >= weaponsForSale[intSelection].getCost()) {
						System.out.println("You hand over " + weaponsForSale[intSelection].getCost() + " coins.");
						System.out.println("You place the " + weaponsForSale[intSelection].getName() + " into your inventory.");
						player.setCoins(player.getCoins()-weaponsForSale[intSelection].getCost());
						player.getInventory().pickup(weaponsForSale[intSelection]);
						weaponsForSale[intSelection] = null;
					}
				}
			}
		}
			
		

	}
	
	public static void shopPotions(Player player) {
		Potion shopInventory[] = {
				Potion.minorHealth,
				Potion.standardHealth,
				Potion.minorMana
		};
		
		// bool to keep the shopping look going
		boolean stillShopping = true;
		Scanner input = new Scanner(System.in);
		do {
			System.out.println("\n\nHello "+player.getName()+"... A potion for all your needs..."); // quest?
			System.out.println("------------Potions-----------");
			System.out.println("[1] + "+shopInventory[0].getName());
			System.out.println("[2] + "+shopInventory[1].getName());
			System.out.println("[3] + "+shopInventory[2].getName());
	
			System.out.println("\nWhich would you like to buy? [1-3] [back]");
			
			int selection = input.nextInt()-1; // subtract 1 to correspond with array index, needs error handling
			
			// the beauty of using objects! every selection contained into one code block
			System.out.println("Ah, a " + shopInventory[selection].getName() + " that'll be " + shopInventory[selection].getCost());
			System.out.println("Pay the shopkeep " + shopInventory[selection].getCost() + "? [y/n]");
			if(input.next().equalsIgnoreCase("y")) {
				player.getInventory().pickup(shopInventory[selection]);
			}
			
			System.out.println("Keep shopping? [y/n]");
			stillShopping = input.next().equalsIgnoreCase("y"); // quick way to set still shopping to true/false
		}while(stillShopping);
		
	}

}
