package com.rech.rpg.gamestate.stats;

import com.rech.rpg.Main;
import com.rech.rpg.Menu;
import com.rech.rpg.entity.Entity;
import com.rech.rpg.entity.Player;
import com.rech.rpg.gamestate.GameState;

/**
 * Gamestate used to view and modify the player's stats
 */
public class SkillsMenu implements GameState {
    Menu statsMenu = new Menu("STATISTICS");

    @Override
    public void enter(Main RPGS) {
        Player plyr = RPGS.getPlayer(); // store to variable to make it easier to read

        statsMenu.setMenuInfo("Name: \t\t"+RPGS.getPlayer().getName() +
                "\nLevel: \t\t"+plyr.getLevel() +
                "\nEXP: \t\t"+plyr.getExp()+"/"+ plyr.getLevelUpXP() + "\n\n" +
                "Health | Mana | Strength | Defense | Dodge | Luck | Magic | Resistance \n"
                + plyr.getHealth()+"/"+RPGS.getPlayer().getStat(Entity.Stats.MAXHEALTH) + "   " //@TODO the spacing here will be fucked up as soon as a number goes to 10s, need to make a feature in menu class to handle this
                + plyr.getMana()+"/"+RPGS.getPlayer().getStat(Entity.Stats.MAXMANA) + "      "
                + plyr.getStat(Entity.Stats.STRENGTH) + "          "
                + plyr.getStat(Entity.Stats.DEFENSE) + "        "
                + plyr.getStat(Entity.Stats.DODGE) + "      "
                + plyr.getStat(Entity.Stats.LUCK) + "       "
                + plyr.getStat(Entity.Stats.MAGIC) + "         "
                + plyr.getStat(Entity.Stats.RESISTANCE)+"\n"); //@TODO this menu feature needs to be explicitly created in the menu class, since numbers will move the entire line when going from 0-10 10-100 etc

        statsMenu.addPrompt("HELP", "show descriptions for each stat.");
        statsMenu.addPrompt("BACK", "go back to the previous prompt.");

        statsMenu.display();
        //level up info
        if (plyr.getPoints() > 0) {
            statsMenu.message("You have " +plyr.getPoints()+ " skill points to spend!  [LEVELUP] - to spend points.");
        }

    }

    @Override
    public void update(Main RPGS) {

        String selection = RPGS.getInput().nextLine();

        switch(selection.toUpperCase()){
            case "LEVELUP":
                if (RPGS.getPlayer().getPoints() <= 0) {
                    statsMenu.alert("You don't have any points to spend on skills.", RPGS.getInput());
                }else{
                    RPGS.enterGameState(new SkillPointMenu());
                }
                break;
            case "BACK":
                RPGS.returnToPrevState();
                return;
            case "HELP":
                statsMenu.alert(
                          "STRENGTH = melee damage modifier\r\n"
                                + "DEFENSE = how much incoming damage is reduced\r\n"
                                + "DODGE = chance to negate damage all together\r\n"
                                + "LUCK = modifies how many coins and materials you can gain.\r\n"
                                + "MAGIC = how powerful spells will be, will use spell books that work like swords with elemental bonuses and healing\r\n"
                                + "RESISTANCE = like defense, but against magic/status effects",
                        RPGS.getInput()
                );
                statsMenu.display();
                break;
            default:
                statsMenu.display();
                //level up info
                if (RPGS.getPlayer().getPoints() > 0) {
                    statsMenu.message("You have " +RPGS.getPlayer().getPoints()+ " skill points to spend!  [LEVELUP] - to spend points.");
                }
                statsMenu.message("You don't know what " + selection + " means.");
                break;
        }
    }


}
