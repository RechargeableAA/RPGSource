package com.rech.rpg.item;

public class Spellbook extends Item{
		
	//TODO enchantments will extend spellbooks, but I want to try to not have to pass over item attributes
	
	private final Spell spell;
		
	/**
	 * Spell book item itself
	 * @param itemName - Name of the Spell in quotes, followed by 'Spellbook'
	 * @param cost - Cost in shop
	 * @param stackable - boolean
	 * @param spell - Which spell it directly connects to.
	 */
	public Spellbook(String itemName, int cost, Spell spell) { 
		super(itemName, cost, false);
		this.spell = spell;
	}

	/**
	 * testing variables.
	 */
	public void sayTest() {
		System.out.println("This a " + Spellbook.this.getName() + ". This costs $"+ Spellbook.this.getCost() + ". It causes " + Spellbook.this.spell.power + " " + Spellbook.this.spell.getElement().toString() + " damage. ");
	}
	
	//SPELLBOOKS
	public static final Spellbook fireBallSB = new Spellbook("\"Fire Ball\" Spellbook", 80, Spell.fireBall);
		
	public static class Spell{
			
		private enum Element{
			FIRE, 		EARTH, 		WIND, 		WATER, 	//tier 1
			LIGHTNING, 	METAL, 		KENESIS, 	ICE, 	//tier 2
			PLASMA, 	QUANTUM, 	GRAVITY,	TIME  	//tier 3
		}
		
		private final Element element;
		public int power;
		public int manaCost;
		
		
		public Element getElement() {
			return element;
		}
		
		/**
		 * Spell itself (part of spellbook) EA: you launch 3 'fire balls' at your openent
		 * @param element - Enum in spellbook (ALLCAPS)
		 * @param power - unaltered damage value
		 * @param manaCost
		 */
		public Spell(Element element, int power, int manaCost) {
			this.element = element;
			this.power = power;
			this.manaCost = manaCost;
		}
		

		
		//SPELLS
		public static final Spell fireBall = new Spell(Element.FIRE, 30, 25);
	
	}
	
	
}
