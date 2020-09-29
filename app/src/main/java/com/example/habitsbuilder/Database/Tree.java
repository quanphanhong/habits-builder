package com.example.habitsbuilder.Database;

import java.io.Serializable;

public class Tree implements Serializable {
    private long TreeId;
    private String Name;
    private int Level;
    private int State;
    private int Point;
    private String LastWateringDateTime;

    Tree(){};

    Tree(String name, int level, int state, int point, String lastw){
        Name = name;
        Level = level;
        State = state;
        Point = point;
        LastWateringDateTime = lastw;
    }
}
