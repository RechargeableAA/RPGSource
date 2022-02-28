package com.rech.rpg.map;

import java.util.ArrayList;

import java.util.Scanner;
import java.util.Random;


import com.rech.rpg.Player;
import com.rech.rpg.item.Potion;
import com.rech.rpg.map.shop.PotionShop;
import com.rech.rpg.map.shop.Shop;

public class Town extends Location{

	public static ArrayList<Town> allTowns = new ArrayList<Town>();
	
	String townName;
	String surroundings;
	Shop shops[];
	
	public Town(String name, String description, String surroundings, Shop[] shops) {
		super(name, description);
		this.surroundings = surroundings;
		this.shops = shops;
		allTowns.add(this);
		
	}
	
	
	public static String generateTown(int firstName, int lastName) {
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
			return townName;
		}catch (ArrayIndexOutOfBoundsException IOB){
			IOB.printStackTrace();
			return "ERROR"; //can set to GrayDrift if preferred.
		}
	}
	
	
	public String getSurroundings() {
		return surroundings;
	}
	
	
	
	@Override
	public void interact(Scanner input, Player player) {
		System.out.println("No interactions set for town " + this.name);
	}
	
	
	//Graydrift
	public static final Town GRAYDRIFT = new Town(
		"Graydrift", //name
		"You are currently in the town of Graydrift", // description
		"There is a shop to the north.", // whats around
		new Shop[] { // array of shops
				new PotionShop("Alman's Potions", new Potion[] { // a potion shop with an array for inventory
						Potion.minorHealth, 
						Potion.standardHealth, 
						Potion.minorMana
				})
		}
	) {
		@Override
		public void interact(Scanner input, Player player) { // settings what can be interacted with within the town
			
			switch(Location.userInputToDirection(input)) {
			case NORTH:
				this.shops[0].interact(input, player);
			break;
			default:
				System.out.println("There's nothing in that direction.");
			break;
			
			}
		}
	};

		
}
