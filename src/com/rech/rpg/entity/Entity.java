package com.rech.rpg.entity;

import com.rech.rpg.item.Inventory;
import com.rech.rpg.item.Weapon;

public class Entity {

    private Stat health = new Stat(10, 50);
    private Stat mana = new Stat(10, 10);
    private Stat strength = new Stat(1, 1);
    private Stat defense = new Stat(1, 1);
    private Stat dodge = new Stat(1, 1);
    private Stat luck = new Stat(1, 1);
    private Stat magic = new Stat(1, 1);
    private Stat resistance = new Stat(1, 1);

    public enum Stats {HEALTH, MANA, STRENGTH, DEFENSE, DODGE, LUCK, MAGIC, RESISTANCE}

    //5 points earned per level.
    private static final int POINTSPERLEVEL = 5;


    protected String name;
    //Equipped item
    protected Weapon equipped;
    // Inventory 		Use for inventory for player, used for drops for enemies
    protected Inventory inventory;

    /**
     * Use entity defaults
     *
     * @param name - Name displayed when entity is in combat
     */
    protected Entity(String name) {
        this.name = name;

        //initializing inventory
        inventory = new Inventory();
    }

    protected Entity(String name, int maxHealth, int strength, int defense, int dodge, int luck, int magic, int maxMana, int resistance) {
        this.name = name;
        this.health.setCurrentMaximum(maxHealth);
        this.mana.setCurrentMaximum(maxMana);
        this.strength.setCurrentMaximum(strength);
        this.defense.setCurrentMaximum(defense);
        this.dodge.setCurrentMaximum(dodge);
        this.luck.setCurrentMaximum(luck);
        this.magic.setCurrentMaximum(magic);
        this.resistance.setCurrentMaximum(resistance);

        restoreAllStatsToMax();

        //initializing inventory
        inventory = new Inventory();
    }

    public void restoreAllStatsToMax(){
        health.value = health.getCurrentMaximum();
        mana.value = mana.getCurrentMaximum();
        strength.value = strength.getCurrentMaximum();
        defense.value = defense.getCurrentMaximum();
        dodge.value = dodge.getCurrentMaximum();
        luck.value = luck.getCurrentMaximum();
        magic.value = magic.getCurrentMaximum();
        resistance.value = resistance.getCurrentMaximum();
    }

    /**
     * Calculate entity level
     * Calculates how many points were spent on the entity by dividing its stats by the default level increments, and dividing that by how many points an entity gets per level up
     *
     * @return entity's calculate level
     */
    public int getLevel() {
        double skillPointsSpent = 1;
        //Divide current level by it's default. Mana and Health are divided by their 'INC' value, since their defaults are way higher than their level increment
        for (Stats stat : Stats.values()) {
            skillPointsSpent += ((double) getStat(stat).getCurrentMaximum()/ getStat(stat).getDefaultValue()) - 1; // -1 to not count initial level as an overall level
        }

        //This is the basic equation to get the level
        return (int) Math.ceil(skillPointsSpent / POINTSPERLEVEL);
    }

    public Weapon getEquipped() {
        return equipped;
    }

    public String getName() {
        return name;
    }

    public Stat getStat(Stats stat){
        return switch(stat){
            case HEALTH -> defense;
            case MANA -> mana;
            case STRENGTH -> strength;
            case DEFENSE -> defense;
            case DODGE -> dodge;
            case LUCK -> luck;
            case MAGIC -> magic;
            case RESISTANCE -> resistance;
        };
    }

    public int attackDamage() {
        return equipped.getDamage() + (equipped.getDamage() * (strength.value / 10));
    }

    public Inventory getInventory() {
        return inventory;
    }

    public static int getPOINTSPERLEVEL() {
        return POINTSPERLEVEL;
    }

}
