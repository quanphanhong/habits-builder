package com.example.habitsbuilder.dummy;

import android.util.Log;

import com.example.habitsbuilder.Database.Achievements;
import com.example.habitsbuilder.Database.Habit;
import com.example.habitsbuilder.Database.HabitDay;
import com.example.habitsbuilder.Database.HabitRank;
import com.example.habitsbuilder.Database.Rewards;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DummyContent {
    // Dummy Item
    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();
    public static final Map<Integer, DummyItem> ITEM_MAP = new HashMap<Integer, DummyItem>();

    public static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public static DummyItem createDummyItem(Habit habit, HabitRank rank) {
        return new DummyItem(habit.GetHabitId(), habit.GetHabitName(), habit.GetHabitDes(), habit.GetScore(), rank.getName(), rank.getImage(), habit.GetHabitRankId(), habit.GetHabitCreatedDate());
    }

    public static class DummyItem {
        public final int id;
        public final String habit_name;
        public final String habit_description;
        public final String habit_starting_day;
        public final int habit_point;
        public final String habit_rank;
        public final String habit_rank_image;
        public final int habit_state;

        public DummyItem(int id, String habit_name, String habit_description, int habit_point, String habit_rank, String habit_rank_image, int habit_state, String habit_starting_day) {
            this.id = id;
            this.habit_name = habit_name;
            this.habit_description = habit_description;
            this.habit_point = habit_point;
            this.habit_rank = habit_rank;
            this.habit_rank_image = habit_rank_image;
            this.habit_starting_day = habit_starting_day;
            this.habit_state = habit_state;
        }
    }


    // Dummy Achievement
    public static final List<DummyAchievement> DummyAchievement_ITEMS = new ArrayList<DummyAchievement>();
    public static final Map<Integer, DummyAchievement> DummyAchievement_ITEM_MAP = new HashMap<Integer, DummyAchievement>();

    public static void DummyAchievement_addItem(DummyAchievement item) {
        DummyAchievement_ITEMS.add(item);
        DummyAchievement_ITEM_MAP.put(item.id, item);
    }

    public static DummyAchievement DummyAchievement_createDummyItem(Achievements achievements, Rewards rewards) {
        Log.i("reward water amount", String.valueOf(rewards.getWaterAmount()));
        return new DummyAchievement(achievements.GetAchId(), achievements.GetAchName(), achievements.GetAchDes(), rewards.getTreeID(), rewards.getWaterAmount(), achievements.GetAchImg(), achievements.GetAchState());
    }

    public static class DummyAchievement {
        public final int id;
        public final String achievement_name;
        public final String achievement_description;
        public final Integer achievement_rewards_treeid;
        public final Integer achievement_rewards_water;
        public final String achievement_image;
        public final int achievement_state;

        public DummyAchievement(int id, String achievement_name, String achievement_description, Integer achievement_rewards_treeid, Integer achievement_rewards_water, String achievement_image, int achievement_state) {
            this.id = id;
            this.achievement_name = achievement_name;
            this.achievement_description = achievement_description;
            this.achievement_rewards_treeid = achievement_rewards_treeid;
            this.achievement_rewards_water = achievement_rewards_water;
            this.achievement_image = achievement_image;
            this.achievement_state = achievement_state;
        }
    }


    // Dummy HabitDay
    public static final List<DummyHabitDay> DummyHabitDay_ITEMS = new ArrayList<DummyHabitDay>();
    public static final Map<Integer, DummyHabitDay> DummyHabitDay_ITEM_MAP = new HashMap<Integer, DummyHabitDay>();

    public static void DummyHabitDay_addItem(DummyHabitDay item) {
        DummyHabitDay_ITEMS.add(item);
        DummyHabitDay_ITEM_MAP.put(item.id, item);
    }

    public static DummyHabitDay DummyHabitDay_createDummyItem(HabitDay habitDay, Habit habit) {
        return new DummyHabitDay(habitDay.getHabitId(), habit.GetHabitName(), habitDay.getDate(), habitDay.getState());
    }

    public static class DummyHabitDay {
        public final int id;
        public final String habit_name;
        public final String habit_day;
        public final int habit_state;

        public DummyHabitDay(int id, String habit_name, String habit_day, int habit_state) {
            this.id = id;
            this.habit_name = habit_name;
            this.habit_day = habit_day;
            this.habit_state = habit_state;
        }
    }
}