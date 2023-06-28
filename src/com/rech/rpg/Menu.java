package com.rech.rpg;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * A standardized way to make menus. The primary purpose is to have the "You dont know what..." messages to appear below the options
 */
public class Menu {
	/**
	 * Text appearing at the very top of the menu, like options or questions.
	 */
	private ArrayList<Prompt> prompts;
	
	//Information important to menu options, like the location you're in
	private String menuInfo;
	private String menuTitle;
	
	private static final int defaultMLL = 50;
	private static int maxLineLength = defaultMLL;
	
	private String menuBorder = "|";
	private String titleDivider = "-";
	/**
	 * 		|			menuTitle
	 * 		--------------------------------
	 * 		|			menuInfo
	 * 		|
	 * 		|[prompt] - prompt description
	 * 		|[prompt] - prompt description	 
	 * 		|[prompt] - prompt description
	 *		|
	 * 		|message message message message								---- Appear as the method is called
	 * 		|alert alert alert alert alert [Requires an enter to proceed]	---- Appear as the method is called
	 * 
	 * 
	 */
	
	
	
	/**
	 * Create an empty menu
	 * @param menuTitle - Name displayed above options
	 */
	public Menu(String menuTitle) {
		prompts = new ArrayList<Prompt>();
		this.menuTitle = menuTitle;
	}
	
	/**
	 * Print the menu name, its options, and any messages in that order
	 */
	public void display() {
		Main.clearScreen();
		
		//Menu title
		System.out.print(new String(new char[(maxLineLength/2)-(menuTitle.length()/2)-1]).replace("\0", " ")); // center menu title with spaces
		System.out.println(menuTitle);
		
		//Menu info
		if(menuInfo != null && menuInfo != ""){
			System.out.println(menuInfo);
		}
			
		//Menu prompts
		for(Prompt prompt : prompts) {
			System.out.print(prompt.getTitle());
			System.out.println(prompt.getDescription());
		}

		
	}
	
	/**
	 * Set information about the current menu
	 * @param menuInfo
	 */
	public void setMenuInfo(String menuInfo) {
		this.menuInfo = menuInfo;
	}
	
	/**
	 * Clear the menu's info
	 */
	public void clearMenuInfo() {
		menuInfo = "";
	}
	
	/**
	 * Clears menuinfo, prompts, and messages
	 */
	public void clearAll() {
		prompts.clear();
		menuInfo = "";
	}
	
	/**
	 * Add a prompt/option to the menu
	 * @param promptTitle
	 * @param promptDescription
	 */
	public void addPrompt(String promptTitle, String promptDescription) {
		prompts.add(new Prompt("["+promptTitle.toUpperCase()+"] - ", promptDescription));
	}
	public void addPrompt(String promptTitle) {
		prompts.add(new Prompt("["+promptTitle.toUpperCase()+"]", ""));
	}
	
	
	/**
	 * Remove all prompts from menu
	 */
	public void clearPrompts() {
		prompts.clear();
	}

	/**
	 * Alerts that appear after menu, displayed directly after prompts. Used to alert the user of a result of an option or lack of options
	 * @EX: "You have nothing in your inventory. [Press enter]"
	 *
	 * Waits for user input which is returned as a string, for use in options or just to allow the user to see the message and press enter
	 *
	 * @param message
	 * @return - user's input after the message is displayed
	 */
	public String alert(String message, Scanner input) {
		if(message != null && message != ""){
			System.out.println(message);
		}
		maxLineLength = defaultMLL; //this is so you can set the MLL back to default so you can change it dynamically, and it will revert after the message.
		return input.nextLine();
	}

	/**
	 * Messages that appear after menu, displayed directly after prompts. Used to give additional info/context
	 * @EX: "You have 50 skill points"
	 *
	 * Does NOT wait for user input
	 *
	 * @param message
	 * @return - user's input after the message is displayed
	 */
	public void message(String message) {
		if(message != null && message != ""){
			System.out.println(message);
		}
		maxLineLength = defaultMLL; //this is so you can set the MLL back to default so you can change it dynamically, and it will revert after the message.
	}

	/**
	 * Options contained in the menu.
	 * Consists of a title for the option and its description
	 * @author Nolan DeMatteis
	 *
	 */
	private class Prompt {
		private String title, 
					   description;
		protected Prompt(String title, String description) {
			this.title = title;
			this.description = description;
		}
		
		public String getTitle() {
			return title;
		}
		
		public String getDescription() {
			return description;
		}
	}
}
