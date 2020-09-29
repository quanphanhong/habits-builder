package com.example.habitsbuilder.Database;

import java.io.Serializable;
import java.util.Date;

public class Habit implements Serializable {
    private long ID;
    private String Name;
    private String Description;
    private String CreatedDate;
    private int Streak;
    private long RankID;
    private int State;

    public Habit() {}

    public Habit(String Name, String Description, String CreatedDate, int Streak, long RankID, int State) {
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
    public String GetHabitCreatedDate(){return CreatedDate;}
    public int GetHabitStreak(){return Streak;}
    public long GetHabitRankId(){return RankID;}
    public int GetHabitState(){return State;}

    public void SetHabitId(long id){ID = id;}
    public void SetHabitName(String name){Name = name;}
    public void SetHabitDes(String des){Description = des;}
    public void SetHabitCreatedDate(String date){CreatedDate = date;}
    public void SetHabitStreak(int streak){Streak = streak;}
    public void SetHabitRankId(long rankId){RankID = rankId;}
    public void SetHabitState(int state){State = state;}


}
