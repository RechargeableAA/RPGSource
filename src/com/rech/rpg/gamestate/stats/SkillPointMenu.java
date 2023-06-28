package com.rech.rpg.gamestate.stats;

import com.rech.rpg.Main;
import com.rech.rpg.Menu;
import com.rech.rpg.entity.Entity;
import com.rech.rpg.entity.Player;
import com.rech.rpg.gamestate.GameState;

import java.util.HashMap;
import java.util.Scanner;

public class SkillPointMenu implements GameState {
    Player pl;
    Scanner inp;
    Menu skillsMenu = new Menu("SKILLS MENU");
    private static HashMap<String, String> statShorthandConversion = new HashMap<String, String>();


    public SkillPointMenu(Player player, Scanner input){
        pl = player;
        inp = input;

        //Shorthand population
        statShorthandConversion.put("STR", "STRENGTH");
        statShorthandConversion.put("DEF", "DEFENSE");
        statShorthandConversion.put("DGE", "DODGE");
        statShorthandConversion.put("LCK", "LUCK");
        statShorthandConversion.put("MGC", "MAGIC");
        statShorthandConversion.put("RST", "RESISTANCE");
    }
    @Override
    public void enter() {
        skillsMenu.clearAll();

        skillsMenu.setMenuInfo("You have "+pl.getPoints()+" skill point(s) to spend. Enter the name of the skill you want to add points to. \n"
                             + "Once added, they cannot be reset without a fee. \n"
                             + "[STR][DEF][DGE][LCK][MGC][RST]");
        skillsMenu.addPrompt("HELP", "show descriptions for each stat.");
        skillsMenu.addPrompt("BACK", "go back to the previous prompt.");

        skillsMenu.display(false);
    }

    @Override
    public void update() {
        String selection = inp.next().toString();

        if (selection.equalsIgnoreCase("back")) {
            Main.returnToPrevState();
        }else if (isStringAStat(selection)) { //verifies if input was a stat
            verifySkillPoint(Entity.Stats.valueOf(selection), inp);
            enter();
        }else if(statShorthandConversion.containsKey(selection.toUpperCase())){ // interpret shorthand and pass to verify skill point
            verifySkillPoint(Entity.Stats.valueOf(statShorthandConversion.get(selection.toUpperCase())), inp);
            enter();
        }else if (selection.equalsIgnoreCase("help")) {//checking input for menu related navigation
            skillsMenu.alert(
                    "STRENGTH = melee damage modifier\r\n"
                            + "DEFENSE = how much incoming damage is reduced\r\n"
                            + "DODGE = chance to negate damage all together\r\n"
                            + "LUCK = modifies how many coins and materials you can gain.\r\n"
                            + "MAGIC = how powerful spells will be, will use spell books that work like swords with elemental bonuses and healing\r\n"
                            + "RESISTANCE = like defense, but against magic/status effects",
                    inp
            );
            enter(); // display skill options after help message
        }else {
            skillsMenu.alert("You don't know what "+selection+" means.", inp);
        }
    }

    /**
     * Verifies that the user wants to add a skill point to a given skill, and that they have enough points
     */
    private void verifySkillPoint(Entity.Stats stat, Scanner input){
        String selection = "";
        input.nextLine(); // clear empty return

        try {
            System.out.println("Enter the amount of points to add to " + stat.name());
            System.out.println("[0-999] [BACK]");
            selection = input.nextLine();
            int amount = Integer.parseInt(selection); //converts string to int
            if (amount <= pl.getPoints()) {
                pl.addSkillPoint(stat, amount);
                skillsMenu.alert("You've advanced your "+stat.toString()+ " by " +amount+".\nYour "+stat.name()+" is now level "+pl.getStat(stat)+".\nYou have "+pl.getPoints()+" points left.", inp);
            }else{
                skillsMenu.alert("You don't have enough points for that!", inp);
            }

        }catch (Exception e) { //parse error
            if (!selection.equals("back")) {
                System.out.println("Enter a number.");
                verifySkillPoint(stat, input);
            }
        }
    }

    /**
     * Verifies if a string input is the same name as a stat
     * @param rawStatString
     * @return true if string equals the name of a stat. Ignores case
     */
    private static boolean isStringAStat(String rawStatString) {
        for (Entity.Stats s : Entity.Stats.values()) {
            if (s.name().equalsIgnoreCase(rawStatString)) {
                return true;
            }
        }
        return false;
    }

}
