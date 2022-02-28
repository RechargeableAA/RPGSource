package com.rech.rpg.map;

import java.util.ArrayList;

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
