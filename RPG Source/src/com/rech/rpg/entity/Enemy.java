package com.rech.rpg.entity;

import java.util.Random;
import com.rech.rpg.item.Weapon;
import com.rech.rpg.item.Inventory;
import com.rech.rpg.Player;

public class Enemy extends Entity{ //do I have to extend enemy? not sure how you want me to code this
	
	private String race; //do I need a race object? does everything have to be an object? Am I allowed to use arrays? are object-arrays a thing?
	
	private String metalResistance; //is there still a metal array? or is it a player.getEquipped().weapon.material sort of thing?
	//private String magicResistance; have to figure out this class before I try to figure out a magic class with elements. 
	
	private String name; //is this necessary? does it pull from entity? how do I tell? am I supposed to make specific monsters in the entity class or in here?

	// How do i make an inventory? or do I just import an inventory object? or is it an entity inventory? or a new object?
	
	/*protected Beast(String primary, String secondary) {
		i want to assign the primary and secondary 'weapon action' so i can make another entity. for ex, jaguar("bite", "scratch") do you want me to make another class for this?
	}*/
	
	
	//i wanted to make a humanoid, that can hold weapons and a beast that has 'built-in weapons'. I dont' know what I'm doing, are these things already implemented? Do i make it in ~this~ class? is this even necessary?
	
	/*protected void Beast (String name) { //I dont' know what I'm doing, do i call name from entity? do i get level, resistance, luck etc from entity? what if I dont need all of that?
		//I don't know how the attributes work.
	}*/
	
	//Entity Beast = new Entity(); //
	
	//I dont' know what I'm doing, are these ^^^ objects necessary, or is everything just an entity?
	
	//I dont' know what I'm doing, I have no idea how to go about importing the attributes, would changing them mess up player?
	
	//I dont know if I'd have to make an object to make a class that returns a weapon object... 
	
	//I so badly just want to make anything. I can't keep up with what objects are named what, what method does what. I have 8-10 classes open at a time whenever I want to find a single variable. :(


	String[] humanList = new String[]{"Beggar", "Thief", "Bandit", "Raider", "Marauder"};
	String[] knightList = new String[]{"Guard", "Bronze Knight", "Wrought Knight", "Royal Knight", "Dark Knight"};
	String[] orcList = new String[]{"Orc Bandit", "Orc Warrior", "Orc Bezerker", "Orc Juggernaught", "Orc Warlord"};
	String[] beastList = new String[]{"Boar", "Wolf", "Alpha Wolf", "Bobcat", "Bear", "Sepeant", "Prowler"}; //not sure how to make these into objects, because i dont know how entity works.
	
}
