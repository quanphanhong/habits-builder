package com.example.habitsbuilder.Database;

import java.io.Serializable;
import java.util.Date;

public class HabitDay implements Serializable {
    private long HabitId;
    private Date Date;
    private int State;

    public HabitDay(){};

    public HabitDay(Date date, int state){
        Date = date;
        State = state;
    }

}
