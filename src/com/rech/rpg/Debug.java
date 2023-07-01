package com.rech.rpg;

import com.rech.rpg.entity.Enemy;
import com.rech.rpg.item.Weapon;
import com.rech.rpg.map.location.Wilderness;

import java.util.Scanner;

/**
 * Obligatory debug menu :). Enter by typing "true" into any input
 */
public class Debug {

    Menu debug = new Menu("UBER HAXXORT DEBUGZ");

    public void enter(Main RPGS) {
        debug.clearAllMenu();

        debug.addPrompt(
      "                            ,--.\n" +
                "                           {    }\n" +
                "                           K,   }\n" +
                "                          /  ~Y`\n" +
                "                     ,   /   /\n" +
                "                    {_'-K.__/\n" +
                "                      `/-.__L._\n" +
                "                      /  ' /`\\_}\n" +
                "                     /  ' /\n" +
                "             ____   /  ' /\n" +
                "      ,-'~~~~    ~~/  ' /_\n" +
                "    ,'             ``~~~  ',\n" +
                "   (                        Y\n" +
                "  {                         I\n" +
                " {      -                    `,\n" +
                " |       ',                   )\n" +
                " |        |   ,..__      __. Y\n" +
                " |    .,_./  Y ' / ^Y   J   )|\n" +
                " \\           |' /   |   |   ||\n" +
                "  \\          L_/    . _ (_,.'(\n" +
                "   \\,   ,      ^^\"\"' / |      )\n" +
                "     \\_  \\          /,L]     /\n" +
                "       '-_~-,       ` `   ./`\n" +
                "          `'{_            )\n" +
                "              ^^\\..___,.--`     ");
        debug.addPrompt("0", "add dark knight to current location - must be wilderness or ull crash");
        debug.addPrompt("1", "pickup admin blade");

        debug.display();

        Scanner input = new Scanner(System.in);

        String optionSelection = input.nextLine();

        //Logic for menu
        if(optionSelection.equalsIgnoreCase("0")) {Wilderness.class.cast(RPGS.getCurrentLocation()).getEntComp().spawnEntity(Enemy.darkKnight); }
        else if (optionSelection.equalsIgnoreCase("1")){RPGS.getPlayer().getInventory().pickup(Weapon.adminBlade);}
        else {
            debug.display();
            debug.message("\nYou don't know what the fuck '"+optionSelection+"' means.\n");
        }

        debug.message("doing option " + optionSelection);
    }
}
