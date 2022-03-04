package com.rech.rpg.map;

import java.util.Scanner;

import com.rech.rpg.Player;


/**
 * Wilderness locations are areas usually containing enemies/animals/people/items/puzzles/etc
 * @author Nolan DeMatteis
 *
 */
public class Wilderness extends Location{

	public Wilderness(String name, String description) {
		super(name, description);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void interact(Scanner input, Player player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getSurroundings() {
		// TODO Auto-generated method stub
		return null;
	}

}
