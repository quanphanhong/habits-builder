package com.example.habitsbuilder.Database;

import com.example.habitsbuilder.DatabaseHelper;

import java.io.Serializable;
import java.util.Date;

public class Habit implements Serializable {
    private int ID;
    private String Name;
    private String Description;
    private String CreatedDate;
    private int Frequency;
    private int RankID;
    private int Score;
    private int Reminder;
    private String ReminderTime;

    public Habit() {}

    public Habit(String Name, String Description, String CreatedDate, int Frequency, int RankID, int Score, int Reminder, String ReminderTime) {
        this.Name = Name;
        this.Description = Description;
        this.CreatedDate = CreatedDate;
        this.Frequency = Frequency;
        this.RankID = RankID;
        this.Score = Score;
        this.Reminder = Reminder;
        this.ReminderTime = ReminderTime;
    }

    public int GetHabitId(){return ID;}
    public String GetHabitName(){return Name;}
    public String GetHabitDes(){return Description;}
    public String GetHabitCreatedDate(){return CreatedDate;}
    public int GetFrequency() {return Frequency;}
    public int GetHabitRankId(){return RankID;}
    public int GetScore(){return Score;}
    public int GetReminder(){return Reminder;}
    public String GetReminderTime(){return ReminderTime;}

    public void SetHabitId(int id){ID = id;}
    public void SetHabitName(String name){Name = name;}
    public void SetHabitDes(String des){Description = des;}
    public void SetHabitCreatedDate(String date){CreatedDate = date;}
    public void SetFrequency(int frequency){Frequency = frequency;}
    public void SetHabitRankId(int rankId){RankID = rankId;}
    public void SetScore(int score){Score = score;}
    public void SetReminder(int reminder){Reminder = reminder;}
    public void SetReminderTime(String reminderTime){ReminderTime = reminderTime;}
}
