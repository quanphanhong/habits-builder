package com.example.habitsbuilder.Database;

import java.io.Serializable;
import java.util.Date;

public class HabitDay implements Serializable {
    private int HabitId;
    private String Date;
    private int State;

    public HabitDay(){};

    public HabitDay(int HabitId, String date, int state) {
        this.HabitId = HabitId;
        Date = date;
        State = state;
    }

    public int getHabitId(){return HabitId;}
    public String getDate(){return Date;}
    public int getState(){return State;}

    public void setHabitId(int habitId){HabitId = habitId;}
    public void setDate(String date){Date = date;}
    public void setState(int state){State = state;}
}
