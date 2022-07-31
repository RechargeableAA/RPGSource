package com.rech.rpg.map.shop;

import java.util.Scanner;

import com.rech.rpg.Player;
import com.rech.rpg.item.Item;

public abstract class Shop {
	String shopName;
	Item[] items;
	
	public Shop(String shopName, Item[] shopItems) {
		this.shopName = shopName;
		this.items = shopItems;
	}
	
	public abstract void interact(Scanner input, Player player);
	
	public Item[] getItems() {
		return items;
	}
	
}
