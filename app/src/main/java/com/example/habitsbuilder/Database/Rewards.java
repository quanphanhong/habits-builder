package com.example.habitsbuilder.Database;

import java.io.Serializable;

public class Rewards implements Serializable {
    private long RewID;
    private long TreeID;
    private int WaterAmount;

    public Rewards(){};

    public Rewards(int wateramount){
        WaterAmount = wateramount;
    }
}