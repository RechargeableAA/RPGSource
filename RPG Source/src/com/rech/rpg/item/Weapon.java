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
		
		//prevent caps from exceeding the total amount of materials/types
		if(materialCap > Material.allMaterials.size()) {materialCap = Material.allMaterials.size();}
		if(typeCap > Material.allMaterials.size()) {typeCap = Type.allWeaponTypes.size();}
		
		Material weaponMat = Material.allMaterials.get(rand.nextInt(materialCap));
		Type weaponType = Type.allWeaponTypes.get(rand.nextInt(typeCap));
		String weaponName = weaponMat.getName() + " " + weaponType.getName();
		int damage = weaponMat.damageModifier + weaponType.damageModifier;
		int cost = weaponMat.costModifier + weaponType.costModifier;
		
		return new Weapon(weaponName, cost, weaponMat, weaponType, damage);
		
	}
	
	public int getDamage() {
		return dmg;
	}
	
	
	// Static pre-made weapons
	public static final Weapon fist = new Weapon("fists", 0, Material.unarmed, Type.unarmed, 1);
	public static final Weapon bsGreatSword = new Weapon("Blue-Steel Greatsword", 800, Material.blueSteel, Type.greatSword, 30);
	public static final Weapon adminBlade = new Weapon("Admin Blade", 9999999, Material.shadowSteel, Type.dagger, 99999);
	
	
	
	
	
	//Materials - This is another class within this class. This is because since materials and types will only be used with weapons, and they're so simple
	// theres no real need to make another class
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
		protected static final Material bronze = new Material("bronze", 3, 8);
		protected static final Material pigIron = new Material("pig iron", 4, 12);
		protected static final Material iron = new Material("iron", 5, 16);
		protected static final Material steel = new Material("steel", 7, 20);
		protected static final Material blueSteel = new Material("blue steel", 9, 26);
		protected static final Material bloodIron = new Material("blood iron", 10, 33);
		protected static final Material shadowSteel = new Material("shadow steel", 12, 41);
		protected static final Material celestialSteel = new Material("celestial steel", 14, 65);
		protected static final Material demonSteel = new Material("demon steel", 16, 88);
		protected static final Material luminSteel = new Material("lumin steel", 18, 94);
		protected static final Material mortemSteel = new Material("mortem steel", 22, 135);
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
		protected static final Type dagger = new Type("dagger", 1, 3);
		protected static final Type rapier = new Type("rapier", 2, 6);
		protected static final Type shortSword = new Type("short sword", 2, 10);
		protected static final Type mace = new Type("mace", 3, 14);
		protected static final Type scimitar = new Type("scimitar", 4, 15);
		protected static final Type longSword = new Type("long sword", 5, 21);
		protected static final Type greatSword = new Type("great sword", 8, 36);
		protected static final Type katana = new Type("katana", 7, 27);
		protected static final Type hatchet = new Type("hatchet", 3, 15);
		protected static final Type battleAxe = new Type("battle axe", 6, 30);
		protected static final Type greatAxe = new Type("great axe", 8, 35);
	}
}
