package com.rech.rpg.gamestate.stats;

import com.rech.rpg.Main;
import com.rech.rpg.Menu;
import com.rech.rpg.entity.Entity;
import com.rech.rpg.entity.Player;
import com.rech.rpg.gamestate.GameState;

import java.util.Scanner;

/**
 * Gamestate used to view and modify the player's stats
 */
public class StatsMenu implements GameState {
    private Player pl;
    private Scanner inp;
    Menu statsMenu = new Menu("STATISTICS");

    public StatsMenu(Player player, Scanner input){
        pl = player;
        inp = input;
    }
    @Override
    public void enter() {
        statsMenu.setMenuInfo("Name: \t\t"+pl.getName() +
                "\nLevel: \t\t"+pl.getLevel() +
                "\nEXP: \t\t"+pl.getExp()+"/"+ pl.getLevelUpXP() + "\n\n" +
                "Health | Mana | Strength | Defense | Dodge | Luck | Magic | Resistance \n"
                + pl.getHealth()+"/"+pl.getStat(Entity.Stats.MAXHEALTH) + "   " //@TODO the spacing here will be fucked up as soon as a number goes to 10s, need to make a feature in menu class to handle this
                + pl.getMana()+"/"+pl.getStat(Entity.Stats.MAXMANA) + "      "
                + pl.getStat(Entity.Stats.STRENGTH) + "          "
                + pl.getStat(Entity.Stats.DEFENSE) + "        "
                + pl.getStat(Entity.Stats.DODGE) + "      "
                + pl.getStat(Entity.Stats.LUCK) + "       "
                + pl.getStat(Entity.Stats.MAGIC) + "         "
                + pl.getStat(Entity.Stats.RESISTANCE)+"\n"); //@TODO this menu feature needs to be explicitly created in the menu class, since numbers will move the entire line when going from 0-10 10-100 etc

        statsMenu.addPrompt("HELP", "show descriptions for each stat.");
        statsMenu.addPrompt("BACK", "go back to the previous prompt.");

        statsMenu.display(false);

    }

    @Override
    public void update() {
        //level up info
        if (pl.getPoints() > 0) {
            statsMenu.message("You have " +pl.getPoints()+ " skill points to spend!  [LEVELUP] - to spend points.");
        }else {
            //	skillMenu(input);
        }

        String selection = inp.nextLine();

        switch(selection.toUpperCase()){
            case "LEVELUP":
                if (pl.getPoints() <= 0) {
                    statsMenu.alert("You don't have any points to spend on skills.", inp);
                }else{
                    Main.enterGameState(new SkillPointMenu(pl, inp));
                }
                break;
            case "BACK":
                Main.returnToPrevState();
                return;
            case "HELP":
                statsMenu.alert(
                        "STRENGTH = melee damage modifier\r\n"
                                + "DEFENSE = how much incoming damage is reduced\r\n"
                                + "DODGE = chance to negate damage all together\r\n"
                                + "LUCK = modifies how many coins and materials you can gain.\r\n"
                                + "MAGIC = how powerful spells will be, will use spell books that work like swords with elemental bonuses and healing\r\n"
                                + "RESISTANCE = like defense, but against magic/status effects",
                        inp
                );
                break;
            default:
                statsMenu.alert("You don't know what " + selection + " means.", inp);
                break;
        }
    }


}
