package com.rech.rpg;

import java.util.ArrayList;
import java.util.Scanner;

import com.rech.rpg.entity.Player;
import com.rech.rpg.gamestate.GameState;
import com.rech.rpg.gamestate.Intro;
import com.rech.rpg.map.Map;

/*
TODO:
- Make a save file to return to previous position - DONE
- integrate inventory - DONE
- integrate shops - DONE
- make an method that makes random names for enemies
- side quests and main quest
- fast traveling
- make a max level cap for skills
- level restrictions?

- Shields
- Spells (attacks + enchantment)
*/

public class Main{

	//game safely shutoff variable
	private static boolean running = true;

	//Player object instance, used to reference everything about the player, ie inventory, map position
	private static Player player;
	
	//Map object instance, used to reference anything in or about the map, ie map size, locations on the map
	public static Map map;
	
	private static GameState gs;
	private static ArrayList<GameState> prevGS = new ArrayList<GameState>(); // store previous game states to return after finishing another game state; linear path mainmenu > inventory > equip > etc
	
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);

		enterGameState(new Intro(input));

		while(running) {
			input = new Scanner(System.in); // need to reset scanner, otherwise an 'ENTER' is passed to the menu
			gs.update();
		}

		input.close();
	}

	/**
	 * Clear console with a repeated new line character. Print is completely instantly, rather then a for loop with .println
	 */
	public static final void clearScreen() {
		System.out.println("\n".repeat(30));
	}
	
	public static Player getPlayer() {
		return player;
	}

	public static void setPlayer(Player newPlayer){player = newPlayer;}

	public static Map getMap(){return map;}
	public static void setMap(Map newMap){map = newMap;}

	/**
	 * Stores a state to be returned to by a gamestate's update loop that returns true. Useful for entering Inventory state, where the typing back could return to combat or mainmenu, it returns to whichever is saved
	 * @param gs - Gamestate to store
	 */
	public static void saveState(GameState gs){
		prevGS.add(gs);
	}

	/**
	 * return to the last saved gamestate. Removes the last saved gamestate from the top of the list
	 */
	public static void returnToPrevState(){
		GameState temp = prevGS.get(prevGS.size()-1);
		prevGS.remove(prevGS.size()-1);
		enterGameState(temp);
	}

	/**
	 * Enters a new gamestate, runs that gamestate's enter method. Sets main's gamestate so it's update method is ran on the next gameloop
	 * @param gameState
	 */
	public static void enterGameState(GameState gameState){
		gs = gameState;
		gs.enter();
	}

}