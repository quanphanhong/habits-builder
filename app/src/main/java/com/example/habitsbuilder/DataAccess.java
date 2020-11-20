package com.example.habitsbuilder;

import android.content.Context;
import com.example.habitsbuilder.Database.*;
import com.example.habitsbuilder.Database.HabitRank;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

    public static List<HabitDay> getHabitDayByHabitId(int id) {
        if (db == null) return null;
        return db.getHabitDayByHabitId(id);
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

    public static List<List<HabitDay>> separateHabitDaysIntoWeek(List<HabitDay> habitDays) {
        List<List<HabitDay>> habitWeeks = new ArrayList<>();
        habitWeeks.add(new ArrayList<HabitDay>());

        for (HabitDay habitDay : habitDays) {
            try {
                // convert String to Calendar
                Calendar date = Calendar.getInstance();
                date.setTime(new SimpleDateFormat("dd/MM/yyyy").parse(habitDay.getDate()));

                // get day of week (monday, tuesday...) in order to calc streak
                int dayOfWeek = date.get(Calendar.DAY_OF_WEEK);
                habitWeeks.get(habitWeeks.size() - 1).add(habitDay);

                // if ('day' is the last day of a week) -> start a new week!
                if (dayOfWeek == Calendar.SUNDAY) habitWeeks.add(new ArrayList<HabitDay>());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return habitWeeks;
    }

    // get the longest streak of a habit
    public static List<Integer> getStreaks(int habitID) {
        List<Integer> result = new ArrayList<>();
        result.clear();

        // get frequency
        Habit habit = getHabit(habitID);
        int frequency = habit.GetFrequency();
        // get habit weeks
        List<List<HabitDay>> habitWeeks = separateHabitDaysIntoWeek(getHabitDayByHabitId(habitID));

        int longestStreak = 0, currentStreak = 0;

        for (int i = 0; i < habitWeeks.size(); i++) {
            // streak 'til this day
            int currentStreakInWeek = 0;
            // streak starting from the beginning of this week
            int startingStreakInWeek = 0, endedStartingStreakInWeek = 0;
            // count number of succeeded days in this week
            int totalCompletedHabitInWeek = 0;
            for (HabitDay habitDay : habitWeeks.get(i)) {
                if (habitDay.getState() == 0) {
                    currentStreakInWeek = 0;
                    endedStartingStreakInWeek = 1;
                } else {
                    totalCompletedHabitInWeek++;
                    currentStreakInWeek++;
                    if (endedStartingStreakInWeek == 0) startingStreakInWeek++;
                    if (currentStreakInWeek > longestStreak) longestStreak = currentStreakInWeek;
                }
            }
            // if this week's target is reached
            if (totalCompletedHabitInWeek >= frequency) {
                currentStreak += habitWeeks.get(i).size();
                if (currentStreak > longestStreak) longestStreak = currentStreak;
            } else {
                if (currentStreak + startingStreakInWeek > longestStreak)
                    longestStreak = currentStreak + startingStreakInWeek;
                currentStreak = currentStreakInWeek;
            }
        }
        result.add(longestStreak);
        result.add(currentStreak);

        return result;
    }

}
