package com.rech.rpg.entity;

public class Stat {

    private int increment;
    private int defVal;
    public int value;
    private int currentMaximum;

    public Stat(int increment, int defaultValue){
        this.increment = increment;
        this.defVal = defaultValue;
    }

    /**
     * Increment a stat's maximum by its stat increment value, and restore it to full
     */
    public void levelUpStat() {
        this.currentMaximum += increment;
        this.value = currentMaximum;
    }
    public void levelUpStat(int levels) {
        this.currentMaximum += increment*levels;
        this.value = currentMaximum;
    }

    public int getDefaultValue() {
        return defVal;
    }

    public int getIncrement() {
        return increment;
    }

    public void setCurrentMaximum(int currentMaximum) {
        this.currentMaximum = currentMaximum;
    }

    public int getCurrentMaximum() {
        return currentMaximum;
    }
}
