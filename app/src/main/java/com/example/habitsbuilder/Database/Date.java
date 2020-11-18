package com.example.habitsbuilder.Database;

public class Date {
    private int DateID;
    private int Day;
    private int Month;
    private int Year;
    private int WeekYear;

    public int getDateID() {
        return DateID;
    }

    public int getDay() {
        return Day;
    }

    public int getMonth() {
        return Month;
    }

    public int getYear() {
        return Year;
    }

    public int getWeekYear() {
        return WeekYear;
    }

    public void setDateID(int dateID) {
        DateID = dateID;
    }

    public void setDay(int day) {
        Day = day;
    }

    public void setMonth(int month) {
        Month = month;
    }

    public void setYear(int year) {
        Year = year;
    }

    public void setWeekYear(int weekYear) {
        WeekYear = weekYear;
    }

    // builder

    public Date dateID(int _dateid) {
        this.DateID = _dateid;
        return this;
    }

    public Date day(int _day) {
        this.Day = _day;
        return this;
    }

    public Date month(int _month) {
        this.Month = _month;
        return this;
    }

    public Date year(int _year) {
        this.Year = _year;
        return this;
    }

    public Date weekyear(int _weekyear) {
        this.WeekYear = _weekyear;
        return this;
    }
}
