package com.example.habitsbuilder.Database;

import java.io.Serializable;
import java.util.Date;

public class Habit implements Serializable {
    private long ID;
    private String Name;
    private String Description;
    private Date CreatedDate;
    private int Streak;
    private long RankID;
    private int State;

    public Habit() {}

    public Habit(String Name, String Description, Date CreatedDate, int Streak, long RankID, int State) {
        this.Name = Name;
        this.Description = Description;
        this.CreatedDate = CreatedDate;
        this.Streak = Streak;
        this.RankID = RankID;
        this.State = State;
    }

}
