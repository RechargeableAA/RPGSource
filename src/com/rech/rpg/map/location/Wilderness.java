package com.rech.rpg.map.location;

import java.util.Scanner;

import com.rech.rpg.entity.Enemy;
import com.rech.rpg.entity.Entity;
import com.rech.rpg.entity.Player;
import com.rech.rpg.entity.combat.Combat;
import com.rech.rpg.entity.menu.Menu;
import com.rech.rpg.map.location.Location.EntityComponent;


/**
 * Wilderness locations are areas usually containing enemies/animals/people/items/puzzles/etc
 * @author Nolan DeMatteis
 *
 */
public class Wilderness extends Location{
	protected EntityComponent entComp;
	
	Wilderness(String name, String description) {
		super(name, description);
		entComp = new EntityComponent();
	}
	
	public static Wilderness generateWilderness() {
		Wilderness newWild = new Wilderness("Forest", "There's trees.");
		
		newWild.entComp.getEntities().add(new Enemy("Enemy", Enemy.Race.HUMAN));
		
		return newWild;
	}

/**
 * 	@Override
	public void interact(Scanner input, Player player) {
		switch(directionSelection) {
		case NORTH:
			Combat combat = new Combat();
			combat.enterCombat(player, entComp, input);
		break;
		default:
			System.out.println("There's nothing in that direction.");
		break;
	
		}
	}
 */

	@Override
	public String getSurroundings() {			// Currently a place holder
		String surroundings = "There's ";
		for(int enCount = 0; enCount < entComp.getEntities().size(); enCount++) {
			if(entComp.getEntities().get(enCount).getClass().isInstance(Enemy.class)) {
				surroundings += "a " + entComp.getEntities().get(enCount).getName();
			}
		}
		return surroundings;
	}
	
	@Override
	public void locationMenu(Player player, Scanner input) {
		Combat combat = new Combat();
		combat.enterCombat(player, (Enemy)(entComp.getEntities().get(0)), input);
		/**Menu locationMenu = new Menu(getName().toUpperCase());
		
		while(true) {
			locationMenu.clearPrompts();
			locationMenu.setMenuInfo(getDescription() + " " + getSurroundings());
			
			for(int enCount = 0; enCount < enemies.length; enCount++) {
				locationMenu.addPrompt(enCount+"", enemies[enCount].getName());
			}
			
			locationMenu.addPrompt("BACK");
			
			locationMenu.display(true);
			String optionSelection = input.nextLine().toUpperCase();
			
			switch(optionSelection) {
			
				case "BACK":
					return;
				default: 
					if(Location.directionEnumContains(optionSelection)) {
						Direction direction = Direction.valueOf(optionSelection);
						interact(input, direction, player); // I dont like having to pass the direction to the next menu, but thats the only solution i have atm
					}else {
						locationMenu.message("You don't know what " + optionSelection + " means.", input);
					}
					break;
			}
		}
		**/
	}

}
