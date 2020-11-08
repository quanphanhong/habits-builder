package com.example.habitsbuilder.Database;

import java.io.Serializable;
import java.util.Date;

public class Habit implements Serializable {
    private int ID;
    private String Name;
    private String Description;
    private String CreatedDate;
    private int Frequency;
    private int Point;
    private int RankID;
    private int Score;

    public Habit() {}

    public Habit(String Name, String Description, String CreatedDate, int Frequency, int Streak, int RankID, int Score) {
        this.Name = Name;
        this.Description = Description;
        this.CreatedDate = CreatedDate;
        this.Frequency = Frequency;
        this.Point = Streak;
        this.RankID = RankID;
        this.Score = Score;
    }

    public int GetHabitId(){return ID;}
    public String GetHabitName(){return Name;}
    public String GetHabitDes(){return Description;}
    public String GetHabitCreatedDate(){return CreatedDate;}
    public int GetFrequency() {return Frequency;}
    public int GetPoint(){return Point;}
    public int GetHabitRankId(){return RankID;}
    public int GetScore(){return Score;}

    public void SetHabitId(int id){ID = id;}
    public void SetHabitName(String name){Name = name;}
    public void SetHabitDes(String des){Description = des;}
    public void SetHabitCreatedDate(String date){CreatedDate = date;}
    public void SetFrequency(int frequency){Frequency = frequency;}
    public void SetPoint(int point){Point = point;}
    public void SetHabitRankId(int rankId){RankID = rankId;}
    public void SetScore(int score){Score = score;}


}
