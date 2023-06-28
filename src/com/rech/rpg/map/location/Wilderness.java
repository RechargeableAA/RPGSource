package com.rech.rpg.map.location;

import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;

import com.rech.rpg.Main;
import com.rech.rpg.Menu;
import com.rech.rpg.entity.Enemy;
import com.rech.rpg.entity.Entity;
import com.rech.rpg.entity.Player;
import com.rech.rpg.entity.combat.Combat;


/**
 * Wilderness locations are areas usually containing enemies/animals/people/items/puzzles/etc
 * @author Nolan DeMatteis
 *
 */
public class Wilderness extends Location{
	protected EntityComponent entComp;
	private static final int 
	maxEnemies = 4,
	EnemyLevelRange = 2;		;
	
	Wilderness(String name, String description) {
		super(name, description);
		entComp = new EntityComponent();
	}
	
	public static Wilderness generateWilderness() {
		Wilderness newWild = new Wilderness("Forest", "There's trees.");
		Random rand = new Random();
		
		// generate enemies
		for(int enemies = rand.nextInt(maxEnemies); enemies > 0; enemies--) {
			Enemy newEnemy = Enemy.Null;
			while(newEnemy == Enemy.Null || (Main.getPlayer().getLevel() - newEnemy.getLevel()) < -EnemyLevelRange || (Main.getPlayer().getLevel() - newEnemy.getLevel()) > EnemyLevelRange) { // keep generating until u find one within level range
				newEnemy = Enemy.getEnemiesList().get(rand.nextInt(Enemy.getEnemiesList().size()));
			}
			newWild.entComp.getEntities().add(newEnemy); // randomly pick an enemy from list of enemies

		}
		
		return newWild;
	}

	@Override
	public String getSurroundings() {
		String surroundings;
		if(entComp.getEntities().isEmpty()) {
			surroundings = "There's nothing else here.";
		}else {
			surroundings = "There's ";
		}
		
		// organically list enemies
		for(Entity ent : entComp.getEntities()) {
			if(ent instanceof Enemy) { // every enemy for loop
				
				//check for vowel
				Pattern vowels = Pattern.compile("[aeiou]", Pattern.CASE_INSENSITIVE);
				boolean startsWithVowel = false;
				if(vowels.matcher(ent.getName().subSequence(0, 1)).matches()) { startsWithVowel = true; }//literally check for a vowel. inglish is stupid
				
				//an or a
				if(startsWithVowel) {
					surroundings += "an " + ent.getName();
				}else {
					surroundings += "a " + ent.getName();
				}
				
				//commas and ands
				if(entComp.getEntities().size() - entComp.getEntities().indexOf(ent) > 2) {surroundings += ", ";} // if there are more enemies to be listed after this one
				else if(entComp.getEntities().size() - entComp.getEntities().indexOf(ent) > 1) {surroundings += " and ";}
				else {surroundings += ".";}
			}
		}
		
		
		return surroundings;
	}
	
	@Override
	public void locationMenu(Player player, Scanner input) {
		Menu lcMenu = new Menu(this.getName());
		
		while(true) {
			lcMenu.clearPrompts();
			lcMenu.setMenuInfo(getDescription() + ". " + getSurroundings());
			
			// add enemy ooptions
			for(Entity ent : entComp.getEntities()) {
				if(ent instanceof Enemy) {  // every enemy for loop
					lcMenu.addPrompt(""+entComp.getEntities().indexOf(ent), ent.getName() + " Lvl. " +ent.getLevel());
				}
			}
			
			lcMenu.addPrompt("BACK");
			
			lcMenu.display(true);
			String optionSelection = input.nextLine().toUpperCase();
			
			//0-9
			try {
				if(Integer.valueOf(optionSelection) < entComp.getEntities().size()) { // this is gonna cause an error or let u fight normal npcs but imma just let it happen
					if(new Combat().enterCombat(player, (Enemy) entComp.getEntities().get(Integer.valueOf(optionSelection)), input)) { // player wins combat
						entComp.getEntities().remove((int)(Integer.valueOf(optionSelection)));
					}else { // player runs or dies
						
					}
				}
				
			//Back or anything else
			}catch(NumberFormatException nfe) {
				if(optionSelection.toUpperCase().equals("BACK")) {
					return;
				}else {
					lcMenu.alert("You don't know what " + optionSelection + " means.", input);
				}
			}
		}
	}

}
