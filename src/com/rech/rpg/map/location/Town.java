package com.rech.rpg.map.location;


import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;

import com.rech.rpg.Menu;
import com.rech.rpg.entity.Player;
import com.rech.rpg.map.shop.Shop;

/**
 * Towns are civilized locations stored on the map. They contain shops, and quests in the future. Enemy interactions are non-existent (or very rare maybe?)
 * @author Nolan DeMatteis
 *
 */
public class Town extends Location{
	
	String townName;
	ArrayList<Shop> shops;
	
	Town(String name, String description) {
		super(name, description);
		shops = new ArrayList<Shop>(); // max of 4 shops for the 4 directions
	}
	
	/**
	 * TOWN GENERATION - NOT COMPLETED
	 */
	
	public static Town generateTown() {
		String name = generateTownName(0, 0);
		Town generatedTown = new Town(
				name, //name
				"You are currently in the town of "+name // description
		);
		
		Random rand = new Random();
		int shopAmount = rand.nextInt(Shop.shopType.values().length);
		ArrayList<Shop.shopType> currentTypesInLocation = new ArrayList<Shop.shopType>(); // kind of a dirty way to make sure there are no dupe shops types within location
		for(int i = 0; i < shopAmount; i++) {
			Shop.shopType currentType = Shop.shopType.values()[rand.nextInt(Shop.shopType.values().length)]; // pick a random type
			while(currentTypesInLocation.contains(currentType)) { // check if that type is already generated
				currentType = Shop.shopType.values()[rand.nextInt(Shop.shopType.values().length)]; // Pick a random type until we find one that is new; this is bad because it could take a while depending on how many types we have; also if there is an error somehow this could run indefinitely
			}
			
			generatedTown.shops.add(Shop.generateShop(currentType));
			currentTypesInLocation.add(currentType); // add to list of already generated types
		}
		
		generatedTown.generateSurroundingsText();
		return generatedTown;
	}
	
	@Override
	public void locationMenu(Player player, Scanner input) {
		Menu locationMenu = new Menu(getName().toUpperCase());
		
		while(true) {
			locationMenu.clearPrompts();
			locationMenu.setMenuInfo(getDescription() + ". " + getSurroundings());
			for(Shop shop : shops) {
				locationMenu.addPrompt(shops.indexOf(shop)+"", shop.getName());
			}
			locationMenu.addPrompt("BACK");
			
			locationMenu.display(true);
			String optionSelection = input.nextLine().toUpperCase();
			
			//0-9
			try {
				if(Integer.valueOf(optionSelection) < shops.size()) {
					shops.get(Integer.valueOf(optionSelection)).interact(input, player);
				}
				
			//Back or anything else
			}catch(NumberFormatException nfe) {
				if(optionSelection.toUpperCase().equals("BACK")) {
					return;
				}else {
					locationMenu.alert("You don't know what " + optionSelection + " means.", input);
				}
			}
		}
	}
	
	private String generateSurroundingsText() {
		if(!shops.isEmpty()) {
			if(shops.size() == 1) {
				surroundings = "There's a shop nearby.";
			}else {
				surroundings = "There's some shops nearby.";
			}
		}
		return surroundings;
	}
	
	//Probably should remove the 0,0 case and just give random parameters instead. The 0,0 random case requires knowing how the method works, instead of just trusting it
	private static String generateTownName(int firstName, int lastName) {
		String[] stringA = new String[] {"Gray", "Green", "Kil", "Bleak", "Dusk", "Storm", "Summer", "Never", "Dire", "North", "East", "South", "West"}; //13 total
		String[] stringB = new String[] {"drift", "ville", "rock", "stone", "host", "wood", "valley", "felt", "shore", "beach", "port", "watch"}; //12 total
		Random rand = new Random(); //can change to 13, figured length of A would always be larger than B
		String townName;
		
		try {
			if (firstName == 0 && lastName == 0) { //0,0 will ask to make a random town. any other number will pick a specific name based on the arrays
				townName = stringA[rand.nextInt(stringA.length)]+stringB[rand.nextInt(stringB.length)];
			}else {
				townName = stringA[firstName]+stringB[lastName];//placeholder for final name
			}
			
			//"You are currently in the town of Graydrift.\\nTo the West, there is the [BLACKSMITH]. Just North of me, is [GAVIN]'s Home. To the East is the [POTIONS] Seller.\\n[BACK]\"
			
			return townName;
			
		}catch (ArrayIndexOutOfBoundsException IOB){
			IOB.printStackTrace();
			return null;
		}
		
	}

}
