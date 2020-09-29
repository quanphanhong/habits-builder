package com.example.habitsbuilder.Database;

import java.io.Serializable;

public class Tree implements Serializable {
    private long TreeId;
    private String Name;
    private int Level;
    private int State;
    private int Point;
    private String LastWateringDateTime;

    public Tree(){};

    public Tree(String name, int level, int state, int point, String lastw){
        Name = name;
        Level = level;
        State = state;
        Point = point;
        LastWateringDateTime = lastw;
    }

    public long getTreeId() {
        return TreeId;
    }

    public String getName() {
        return Name;
    }

    public int getLevel() {
        return Level;
    }

    public int getPoint() {
        return Point;
    }

    public int getState() {
        return State;
    }

    public String getLastWateringDateTime() {
        return LastWateringDateTime;
    }


    public void setName(String name) {
        Name = name;
    }

    public void setLevel(int level) {
        Level = level;
    }

    public void setPoint(int point) {
        Point = point;
    }

    public void setState(int state) {
        State = state;
    }

    public void setLastWateringDateTime(String lastWateringDateTime) {
        LastWateringDateTime = lastWateringDateTime;
    }

    public void setTreeId(long treeId) {
        TreeId = treeId;
    }
}
