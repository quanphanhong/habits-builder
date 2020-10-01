package com.example.habitsbuilder.Database;

import java.io.Serializable;

public class HabitRank implements Serializable {
    private  long RankId;
    private  String Name;
    private  String Description;
    private  String Image;

    public HabitRank(){};

    public HabitRank(String name, String des, String img){
        Name = name;
        Description = des;
        Image = img;
    }

    public long getRankId() {
        return RankId;
    }

    public String getName() {
        return Name;
    }

    public String getDescription() {
        return Description;
    }

    public String getImage() {
        return Image;
    }
}
