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
	
	
	public String getSurroundings() {
		return surroundings;
	}
	
	
	
	@Override
	public void interact(Scanner input, Player player) {
		System.out.println("No interactions set for town " + this.name);
	}
}
