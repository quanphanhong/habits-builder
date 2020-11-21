package com.example.habitsbuilder.Database;

public class Frequency {
    public String week;
    public int numberOfDays;

    public Frequency(String w, int d) {week = w; numberOfDays = d;}

    public void setWeek(String week) {this.week = week;}
    public void setNumberOfDays(int numDays) {this.numberOfDays = numDays;}

    public String getWeek() {return week;}
    public int getNumberOfDays() {return numberOfDays;}
}
