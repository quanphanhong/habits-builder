package com.example.habitsbuilder.Database;

import java.io.Serializable;
import java.util.Date;

public class HabitDay implements Serializable {
    private int HabitId;
    private Date Date;
    private int State;

    public HabitDay(){};

    public HabitDay(Date date, int state){
        Date = date;
        State = state;
    }

    public int getHabitId(){return HabitId;}
    public Date getDate(){return Date;}
    public int getState(){return State;}

    public void setHabitId(int habitId){HabitId = habitId;}
    public void setDate(Date date){Date = date;}
    public void setState(int state){State = state;}
}
