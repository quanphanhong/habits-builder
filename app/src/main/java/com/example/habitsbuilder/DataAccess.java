package com.example.habitsbuilder;

import android.content.Context;
import com.example.habitsbuilder.Database.*;
import com.example.habitsbuilder.Database.HabitRank;

import java.util.List;

public class DataAccess {
    private static DatabaseHelper db;
    private static Context context;

    public static void setContext(Context con) {
        context = con;
        db = new DatabaseHelper(context);
        db.createDefaultAchievementsIfNeeded();
        db.createDefaultRanksIfNeeded();
        db.createDefaultRewardsIfNeeded();
    }

    public static void insertHabit(Habit habit) {
        if (db == null) return;
        db.addHabit(habit);
    }

    public static List<Habit> getAllHabit() {
        if (db == null) return null;
        return db.getAllHabit();
    }

    public static Habit getHabit(int id) {
        if (db == null) return null;
        return db.getHabit(id);
    }

    public static HabitRank getRank(int id) {
        if (db == null) return null;
        return db.getRank(id);
    }

    public static List<Achievements> getAllAchievements() {
        if (db == null) return null;
        return db.getAllAchievements();
    }

    public static Rewards getReward(int id) {
        if (db == null) return null;
        return db.getReward(id);
    }

    public static void updateHabit(Habit habit) {
        if (db == null) return;
        db.updateHabit(habit);
    }

    public static void deleteHabit(Habit habit) {
        if (db == null) return;
        db.deleteHabit(habit);
    }

}
