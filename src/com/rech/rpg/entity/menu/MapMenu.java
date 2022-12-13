package com.rech.rpg.entity.menu;

import java.awt.Point;
import java.util.Scanner;

import com.rech.rpg.map.Map;

public class MapMenu {
	public static void OpenMenu(Scanner input, Map map) {
		Menu mapMenu = new Menu("Map");
		
		boolean closeMenu = false;
		while(!closeMenu) {
			mapMenu.clearPrompts();
			
			mapMenu.setMenuInfo("You are currently in " + map.getLocation(map.getPlayerPosition()).getName());
			mapMenu.addPrompt("W", "Travel North to " + map.getLocation(new Point(map.getPlayerPosition().x, map.getPlayerPosition().y +1)).getName());
			mapMenu.addPrompt("A", "Travel West to " + map.getLocation(new Point(map.getPlayerPosition().x -1, map.getPlayerPosition().y)).getName());
			mapMenu.addPrompt("S", "Travel South to " + map.getLocation(new Point(map.getPlayerPosition().x, map.getPlayerPosition().y -1)).getName());
			mapMenu.addPrompt("D", "Travel East to " + map.getLocation(new Point(map.getPlayerPosition().x +1, map.getPlayerPosition().y)).getName());
			mapMenu.addPrompt("BACK");
		
			mapMenu.display(false);
			
			String optionSelection = input.nextLine().toString();
			
			switch(optionSelection.toUpperCase()) {
			case "W":
				mapMenu.message("You travel North to " + map.getLocation(new Point(map.getPlayerPosition().x, map.getPlayerPosition().y +1)).getName(), input);
				map.movePlayer(new Point(map.getPlayerPosition().x, map.getPlayerPosition().y +1));
				closeMenu = true;
			break;
			case "A":
				mapMenu.message("You travel West to " + map.getLocation(new Point(map.getPlayerPosition().x-1, map.getPlayerPosition().y)).getName(), input);
				map.movePlayer(new Point(map.getPlayerPosition().x-1, map.getPlayerPosition().y));
				closeMenu = true;
			break;
			case "S":
				mapMenu.message("You travel South to " + map.getLocation(new Point(map.getPlayerPosition().x, map.getPlayerPosition().y -1)).getName(), input);
				map.movePlayer(new Point(map.getPlayerPosition().x, map.getPlayerPosition().y -1));
				closeMenu = true;
			break;
			case "D":
				mapMenu.message("You travel East to " + map.getLocation(new Point(map.getPlayerPosition().x+1, map.getPlayerPosition().y)).getName(), input);
				map.movePlayer(new Point(map.getPlayerPosition().x+1, map.getPlayerPosition().y));
				closeMenu = true;
			case "BACK":
				closeMenu = true;
			break;
			default:
				mapMenu.message(optionSelection+" is not an option", input);
			break;
			}
		}
	}
}
