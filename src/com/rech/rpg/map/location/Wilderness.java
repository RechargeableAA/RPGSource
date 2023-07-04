package com.rech.rpg.map.location;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;

import com.rech.rpg.Main;
import com.rech.rpg.entity.Enemy;
import com.rech.rpg.entity.Entity;
import com.rech.rpg.entity.Player;
import com.rech.rpg.gamestate.GameState;
import com.rech.rpg.gamestate.location.WildernessMenu;


/**
 * Wilderness locations are areas usually containing enemies/animals/people/items/puzzles/etc
 */
public class Wilderness extends Location {
	private final EntityComponent entComp;
	private static final int 
	maxEnemies = 4,
	EnemyLevelRange = 2;
	
	Wilderness(String name, String description) {
		super(name, description);
		entComp = new EntityComponent();
	}

	@Override
	public GameState getGameState() {return new WildernessMenu();}

	public static Location generate(Main RPGS) {
		Wilderness newWild = new Wilderness("Forest", "There's trees.");

		newWild.getEntComp().getEntities().addAll(newWild.generateEnemies(RPGS.getPlayer())); //generate enemies
		newWild.surroundings = newWild.generateSurroundings();

		return newWild;
	}

	private ArrayList<Enemy> generateEnemies(Player player){
		Random rand = new Random();
		ArrayList<Enemy> newEnemies = new ArrayList<Enemy>();

		for(int enemies = rand.nextInt(maxEnemies); enemies > 0; enemies--) {
			Enemy newEnemy = Enemy.Null;
			int enemyGenTimeout = 2000;
			while(newEnemy == Enemy.Null || (player.getLevel() - newEnemy.getLevel()) < -EnemyLevelRange || (player.getLevel() - newEnemy.getLevel()) > EnemyLevelRange) { // keep generating until u find one within level range
				newEnemy = Enemy.getEnemiesList().get(rand.nextInt(Enemy.getEnemiesList().size()));
				enemyGenTimeout--;
				if(enemyGenTimeout <= 0){
					newEnemy = Enemy.Null;
					break;
				}
			}
			newEnemies.add(newEnemy); // randomly pick an enemy from list of enemies
		}

		return newEnemies;
	}

	private String generateSurroundings(){
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
				boolean startsWithVowel = vowels.matcher(ent.getName().subSequence(0, 1)).matches();
				//check for a vowel

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

	/**
	 * Lists enemies and surroundings like someone speaking
	 * @return
	 */
	@Override
	public String getSurroundings() {
		return surroundings;
	}

	public EntityComponent getEntComp() {
		return entComp;
	}

}
