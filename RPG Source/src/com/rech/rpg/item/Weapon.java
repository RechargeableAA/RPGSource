package com.rech.rpg.item;

import java.util.ArrayList;
import java.util.Random;

/*
 * Weapon class are items that are equippable and usable as weapons
 */
public class Weapon extends Item{
	
	//Attributes specific to weapons
	private int dmg;
	private Material material;
	private Type type;
	protected Weapon(String itemName, int cost, Material material, Type type, int dmg) {
		super(itemName, cost, false);
		
		this.dmg = dmg;
		this.material = material;
		this.type = type;
	}
	
	/**
	 * Temporary item for use elsewhere in code - ie tusks of a boar, doesn't need material or type
	 * @param itemName - Name of item
	 * @param damage - damage weapon can inflict
	 * @param cost - Cost of the item
	 */
	public Weapon(String itemName, int damage, int cost) {
		super(itemName, cost, false);

	}

	public static Weapon generateNewWeapon(int materialCap, int typeCap) {
		Random rand = new Random();
		
		try {
			Material weaponMat = Material.allMaterials.get(rand.nextInt(materialCap));
			Type weaponType = Type.allWeaponTypes.get(rand.nextInt(typeCap));
			String weaponName = weaponMat.getName() + " " + weaponType.getName();
			int damage = weaponMat.damageModifier + weaponType.damageModifier;
			int cost = weaponMat.costModifier + weaponType.costModifier;
			
			return new Weapon(weaponName, cost, weaponMat, weaponType, damage);
		}catch(ArrayIndexOutOfBoundsException IOB) { //Returns the default fist if material/type parameters exceed total in their arrays
			IOB.printStackTrace();
			return fist;
		}
	}
	
	public int getDamage() {
		return dmg;
	}
	
	
	// Static pre-made weapons
	public static final Weapon fist = new Weapon("fists", 0, Material.unarmed, Type.unarmed, 1);
	public static final Weapon testWeapon = new Weapon("Tester-Sword", 1, Material.iron, Type.dagger, 30);
	public static final Weapon adminBlade = new Weapon("Admin Blade", 9999999, Material.shadowSteel, Type.dagger, 99999);
	
	
	
	
	
	/**
	 * Materials - This is another class within this class. This is because since materials and types will only be used with weapons, and they're so simple
	 * there's no real need to make another class
	 * @author Nolan DeMatteis
	 *
	 */
	private static class Material{
		String name;
		int damageModifier;
		int costModifier;
		
		//list of all created materials
		private static ArrayList<Material> allMaterials = new ArrayList<Material>();
		
		protected Material(String name, int dmgMod, int costMod) {
			this.name = name;
			damageModifier = dmgMod;
			costModifier = costMod;
			allMaterials.add(this);
		}
		
		/**
		 * Create a new Material without a name; This is NOT added to allMaterials
		 * @param dmgMod - damage added to completed weapon
		 * @param costMod - cost added to completed weapon
		 */
		protected Material(int dmgMod, int costMod) {
			damageModifier = dmgMod;
			costModifier = costMod;
		}
		
		public int getDamageModifier() {
			return damageModifier;
		}
		
		public int getCostModifier() {
			return damageModifier;
		}
		
		public String getName() {
			return name;
		}		
		
		
		// Materials
		protected static final Material unarmed = new Material(1, 0);
		protected static final Material bronze = new Material("Bronze", 3, 8);
		protected static final Material pigIron = new Material("Pig-Iron", 4, 12);
		protected static final Material iron = new Material("Iron", 5, 16);
		protected static final Material steel = new Material("Steel", 7, 20);
		protected static final Material blueSteel = new Material("Blue-Steel", 9, 26);
		protected static final Material bloodIron = new Material("Blood-Iron", 10, 33);
		protected static final Material shadowSteel = new Material("Shadow-Steel", 12, 41);
		protected static final Material celesteel = new Material("Celesteel", 14, 65);
		protected static final Material demonite = new Material("Demonite", 16, 88);
		protected static final Material luminium = new Material("Luminium", 18, 94);
		protected static final Material mortemMetal = new Material("Mortem-Metal", 22, 135);
	}
	
	// Type - used only within weapon, identical to materials atm, though gives room to add other modifiers ei. damage speed
	private static class Type{
		String name;
		int damageModifier;
		int costModifier;
		
		//list of all created materials
		private static ArrayList<Type> allWeaponTypes = new ArrayList<Type>();
		
		protected Type(String name, int dmgMod, int costMod) {
			this.name = name;
			damageModifier = dmgMod;
			costModifier = costMod;
			allWeaponTypes.add(this);
		}
		
		/**
		 * Create a new Type without a name; This is NOT added to allWeaponTypes
		 * @param dmgMod - damage added to completed weapon
		 * @param costMod - cost added to completed weapon
		 */
		protected Type(int dmgMod, int costMod) {
			damageModifier = dmgMod;
			costModifier = costMod;
		}
		
		public int getDamageModifier() {
			return damageModifier;
		}
		
		public int getCostModifier() {
			return damageModifier;
		}
		
		public String getName() {
			return name;
		}
		
		
		// Types
		protected static final Type unarmed = new Type(0, 0);
		protected static final Type dagger = new Type("Dagger", 1, 3);
		protected static final Type rapier = new Type("Rapier", 2, 6);
		protected static final Type shortSword = new Type("Short sword", 2, 10);
		protected static final Type mace = new Type("Mace", 3, 14);
		protected static final Type scimitar = new Type("Scimitar", 4, 15);
		protected static final Type longSword = new Type("Long Sword", 5, 21);
		protected static final Type greatSword = new Type("Greatsword", 8, 36);
		protected static final Type katana = new Type("Katana", 7, 27);
		protected static final Type hatchet = new Type("Hatchet", 3, 15);
		protected static final Type battleAxe = new Type("Battle-axe", 6, 30);
		protected static final Type greatAxe = new Type("Great Hammer", 8, 35);
	}
}
