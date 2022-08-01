package com.rech.rpg;

import java.util.ArrayList;

/**
 * A standardized way to make menus. The primary purpose is to have the "You dont know what..." messages to appear below the options
 * @author Nolan DeMatteis
 *
 */
public class Menu {
	/**
	 * Text appearing at the very top of the menu, like options or questions.
	 */
	private ArrayList<Prompt> prompts;
	
	//Informational message printed after options, this is usually "You dont know what..." when user enters an incorrect input
	private String message = "";
	
	//Information important to menu options, like the location you're in
	private String menuInfo;
	private String menuTitle;
	
	/**
	 * 			menuTitle
	 * 			menuInfo
	 * [prompt] - prompt description
	 * [prompt] - prompt description	 
	 * [prompt] - prompt description
	 * 
	 * message message message message
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
		System.out.println("\t" + menuTitle);
		if(menuInfo != null && menuInfo != ""){ System.out.println(menuInfo); }
		for(Prompt prompt : prompts) {
			System.out.println(prompt.title + prompt.description);
		}
		System.out.println(message);
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
	 * Add a prompt/option to the menu
	 * @param promptTitle
	 * @param promptDescription
	 */
	public void addPrompt(String promptTitle, String promptDescription) {
		prompts.add(new Prompt("["+promptTitle.toUpperCase()+"]", " - "+promptDescription));
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
	 * Messages that appear after menus/options.
	 * EX: "You payed 5 gp for that."
	 * @param message
	 */
	public void message(String message) {
		this.message = message;
		display();
	}
	
	/**
	 * Clears the menu message line
	 */
	public void clearMessage() {
		message = "";
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
