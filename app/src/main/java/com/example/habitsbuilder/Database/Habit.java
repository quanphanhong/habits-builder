package com.example.habitsbuilder.Database;

import java.io.Serializable;
import java.util.Date;

public class Habit implements Serializable {
    private int ID;
    private String Name;
    private String Description;
    private String CreatedDate;
    private int Frequency;
    private int Streak;
    private int RankID;
    private int State;

    public Habit() {}

    public Habit(String Name, String Description, String CreatedDate, int Frequency, int Streak, int RankID, int State) {
        this.Name = Name;
        this.Description = Description;
        this.CreatedDate = CreatedDate;
        this.Frequency = Frequency;
        this.Streak = Streak;
        this.RankID = RankID;
        this.State = State;
    }

    public int GetHabitId(){return ID;}
    public String GetHabitName(){return Name;}
    public String GetHabitDes(){return Description;}
    public String GetHabitCreatedDate(){return CreatedDate;}
    public int GetFrequency() {return Frequency;}
    public int GetHabitStreak(){return Streak;}
    public int GetHabitRankId(){return RankID;}
    public int GetHabitState(){return State;}

    public void SetHabitId(int id){ID = id;}
    public void SetHabitName(String name){Name = name;}
    public void SetHabitDes(String des){Description = des;}
    public void SetHabitCreatedDate(String date){CreatedDate = date;}
    public void SetFrequency(int frequency){Frequency = frequency;}
    public void SetHabitStreak(int streak){Streak = streak;}
    public void SetHabitRankId(int rankId){RankID = rankId;}
    public void SetHabitState(int state){State = state;}


}
