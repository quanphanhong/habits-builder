package com.example.habitsbuilder.Database;

import java.io.Serializable;

public class HabitRank implements Serializable {
    private  long RankId;
    private  String Name;
    private  String Description;
    private  String Image;

    HabitRank(){};

    HabitRank(String name, String des, String img){
        Name = name;
        Description = des;
        Image = img;
    }
}
