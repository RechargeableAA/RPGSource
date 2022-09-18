package com.rech.rpg.map.location;


import java.util.Scanner;
import java.util.Random;

import com.rech.rpg.Menu;
import com.rech.rpg.entity.Player;
import com.rech.rpg.item.Potion;
import com.rech.rpg.map.Map.Direction;
import com.rech.rpg.map.shop.PotionShop;
import com.rech.rpg.map.shop.Shop;

/**
 * Towns are civilized locations stored on the map. They contain shops, and quests in the future. Enemy interactions are non-existent (or very rare maybe?)
 * @author Nolan DeMatteis
 *
 */
public class Town extends Location{
	
	String townName;
	String surroundings;
	Shop shops[];
	
	private Town(String name, String description, String surroundings, Shop[] shops) {
		super(name, description);
		this.surroundings = surroundings;
		this.shops = shops;
	}
	
	@Override
	public String getSurroundings() {
		return surroundings;
	}

	@Override
	public void interact(Scanner input, Direction directionSelection, Player player) {
		System.out.println("No interactions set for town " + this.getName());
	}
	
	@Override
	public void locationMenu(Player player, Scanner input) {
		Menu locationMenu = new Menu(getName().toUpperCase());
		
		while(true) {
			locationMenu.clearPrompts();
			locationMenu.setMenuInfo(getDescription() + " " + getSurroundings());
			locationMenu.addPrompt("BACK");
			
			locationMenu.display(true);
			String optionSelection = input.nextLine().toUpperCase();
			
			switch(optionSelection) {
			
				case "BACK":
					return;
				default: 
					if(Location.directionEnumContains(optionSelection)) {
						Direction direction = Direction.valueOf(optionSelection);
						interact(input, direction, player); // I dont like having to pass the direction to the next menu, but thats the only solution i have atm
					}else {
						locationMenu.message("You don't know what " + optionSelection + " means.");
					}
					break;
			}
		}
	}
	
	/**
	 * TOWN GENERATION - NOT COMPLETED
	 */
	
	public static Town generateTown() {
		String name = generateTownName(0, 0);
		Town generatedTown = new Town(
				name, //name
				"You are currently in the town of "+name, // description
				"There is a shop to the [NORTH].", // whats around
				new Shop[] { // array of shops
						new Shop("Alman's Potions",
								"A potion for all your needs...", 
								new Potion[] { // a potion shop with an array for inventory
								Potion.minorHealth, 
								Potion.standardHealth, 
								Potion.minorMana
						})
				}
				){
					@Override
					public void interact(Scanner input, Direction directionSelection, Player player) { // settings what can be interacted with within the town
						
						switch(directionSelection) {
						case NORTH:
							this.shops[0].interact(input, player);
						break;
						default:
							System.out.println("There's nothing in that direction.");
						break;
					
						}
					}
				};
			
			return generatedTown;
	}
	
	
	public static String generateTownName(int firstName, int lastName) {
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
