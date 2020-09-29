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

    public long GetHabitId(){return ID;}
    public String GetHabitName(){return Name;}
    public String GetHabitDes(){return Description;}
    public Date GetHabitCreatedDate(){return CreatedDate;}
    public int GetHabitStreak(){return Streak;}
    public long GetHabitRankId(){return RankID;}
    public int GetHabitState(){return State;}

    public String SetHabitName(String name){
        Name = name;
        return Name;
    }
    public String SetHabitDes(String des){
        Description = des;
        return Description;
    }
    public Date SetHabitCreatedDate(Date date){
        CreatedDate = date;
        return CreatedDate;
    }
    public int SetHabitStreak(int streak){
        Streak = streak;
        return Streak;
    }
    public long SetHabitRankId(long rankId){
        RankID = rankId;
        return RankID;
    }
    public int SetHabitState(int state){
        State = state;
        return State;
    }


}
