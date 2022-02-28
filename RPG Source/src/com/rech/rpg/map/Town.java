package com.rech.rpg.map;

import java.util.ArrayList;
import java.util.Random;

import com.rech.rpg.item.Potion;
import com.rech.rpg.map.shop.PotionShop;
import com.rech.rpg.map.shop.Shop;

public class Town extends Location{

	public static ArrayList<Town> allTowns = new ArrayList<Town>();
	
	String townName;
	String townDescription;
	Shop shops[];
	
	public Town(String name, String description, Shop[] shops) {
		super(name);
		allTowns.add(this);
		
		shops[0] = new PotionShop("Alman's Potions", new Potion[] {Potion.minorHealth, Potion.standardHealth, Potion.minorMana});
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
	
	
	@Override
	public String getSurroundings() {
		return townDescription;
	}
	
	//This is a variable laid out to look like a class to be easier to read
	public static final Town GRAYDRIFT = new Town(
		"Graydrift", //name
		"You are currently in the town of Graydrift", // description
		new Shop[] { // array of shops
				new PotionShop("Alman's Potions", new Potion[] { // a potion shop with an array for inventory
						Potion.minorHealth, 
						Potion.standardHealth, 
						Potion.minorMana
				})
		}
	);


		
		
		
		
}
