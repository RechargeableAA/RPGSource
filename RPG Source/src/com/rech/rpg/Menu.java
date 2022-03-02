package com.rech.rpg;

import java.util.ArrayList;

/**
 * A standardized way to make menus. The primary purpose is to have the "You dont know what..." messages to appear below the options
 * @author Nolan DeMatteis
 *
 */
public class Menu {
	//Options to be displayed
	public ArrayList<String> prompt;
	//Informational message printed after options, this is usually "You dont know what..." when user enters an incorrect input
	private String message = "";
	private String menuTitle;
	
	/**
	 * Create an empty menu
	 * @param menuTitle - Name displayed above options
	 */
	public Menu(String menuTitle) {
		prompt = new ArrayList<String>();
		this.menuTitle = menuTitle;
	}
	
	/**
	 * Print the menu name, its options, and any messages
	 */
	public void display() {
		Main.clearScreen();
		System.out.println("\t" + menuTitle + "\n");
		for(String option : prompt) {
			System.out.println(option);
		}
		
		System.out.println(message);
		message = "";
	}
	
	/**
	 * Create a message to appear below the options. The message is cleared after every display()
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}
