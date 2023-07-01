package com.rech.rpg;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import com.rech.rpg.entity.Player;
import com.rech.rpg.gamestate.DebugMenu;
import com.rech.rpg.gamestate.GameState;
import com.rech.rpg.gamestate.IntroMenu;
import com.rech.rpg.map.location.Location;

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

//@TODO Migrate the menu class into gamestate and make its methods protected

public class Main{
	private static final String debugKeyword = "deb";
	public static final double versionNumber = 0.01;
	//game safely shutoff variable
	private boolean running = true;
	//Player object instance, used to reference everything about the player, ie inventory, map position
	private Player player;
	private Location currentLocation;
	private GameState gs;
	private Scanner input;
	private ArrayList<GameState> prevGS = new ArrayList<GameState>(); // store previous game states to return after finishing another game state; linear path mainmenu > inventory > equip > etc

	/**
	 * Primary game object, used to reference important game-wide objects, variables, methods. Its a neat little package to pass to methods
	 */
	private static Main RPGS; //RPG Source

	public static void main(String[] args) {
		RPGS = new Main();

		RPGS.input = new Scanner(System.in);

		RPGS.enterGameState(new IntroMenu());

		//Gameloop
		while(RPGS.running) {
			RPGS.gs.update(RPGS);
		}

		RPGS.input.close();
	}

	public Scanner getInput(){
		//catch debug keyword
		if(input.findInLine(debugKeyword) != null){
			new DebugMenu().enter(RPGS);
		}
		
		return input;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player newPlayer){player = newPlayer;}

	public Location getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(Location currentLocation) {
		this.currentLocation = currentLocation;
	}

	/**
	 * Stores a state to be returned to by the returnToPrevState method.
	 * @EX entering Inventory state, where the typing back could return to combat or mainmenu, it returns to whichever is saved
	 * @param gs - Gamestate to store
	 */
	public void saveState(GameState gs){
		prevGS.add(gs);
	}

	/**
	 * return to the last saved gamestate. Removes the last saved gamestate from the top of the list
	 */
	public void returnToPrevState(){
		GameState temp = prevGS.get(prevGS.size()-1);
		prevGS.remove(prevGS.size()-1);
		enterGameState(temp);
	}

	/**
	 * Enters a new gamestate, runs that gamestate's enter method. Sets main's gamestate so it's update method is ran on the next gameloop
	 * @param gameState
	 */
	public void enterGameState(GameState gameState){
		gs = gameState;
		gs.enter(RPGS);
	}

}