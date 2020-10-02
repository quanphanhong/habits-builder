package com.example.habitsbuilder.dummy;

import com.example.habitsbuilder.Database.Achievements;
import com.example.habitsbuilder.Database.Habit;
import com.example.habitsbuilder.Database.HabitRank;
import com.example.habitsbuilder.Database.Rewards;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<Integer, DummyItem> ITEM_MAP = new HashMap<Integer, DummyItem>();

    public static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public static DummyItem createDummyItem(Habit habit, HabitRank rank) {
        return new DummyItem(habit.GetHabitId(), habit.GetHabitName(), habit.GetHabitDes(), habit.GetHabitStreak(), rank.getName(), rank.getImage(), habit.GetHabitRankId(), habit.GetHabitCreatedDate());
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public final int id;
        public final String habit_name;
        public final String habit_description;
        public final String habit_starting_day;
        public final int habit_streak;
        public final String habit_rank;
        public final String habit_rank_image;
        public final int habit_state;

        public DummyItem(int id, String habit_name, String habit_description, int habit_streak, String habit_rank, String habit_rank_image, int habit_state, String habit_starting_day) {
            this.id = id;
            this.habit_name = habit_name;
            this.habit_description = habit_description;
            this.habit_streak = habit_streak;
            this.habit_rank = habit_rank;
            this.habit_rank_image = habit_rank_image;
            this.habit_starting_day = habit_starting_day;
            this.habit_state = habit_state;
        }
    }

    /**
     * An array of sample (dummy) items.
     */
    public static final List<DummyAchievement> DummyAchievement_ITEMS = new ArrayList<DummyAchievement>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<Integer, DummyAchievement> DummyAchievement_ITEM_MAP = new HashMap<Integer, DummyAchievement>();

    public static void DummyAchievement_addItem(DummyAchievement item) {
        DummyAchievement_ITEMS.add(item);
        DummyAchievement_ITEM_MAP.put(item.id, item);
    }

    public static DummyAchievement DummyAchievement_createDummyItem(Achievements achievements) {
        Rewards rewards = new Rewards();
        return new DummyAchievement(achievements.GetAchId(), achievements.GetAchName(), achievements.GetAchDes(), rewards.getTreeID(), rewards.getWaterAmount(), achievements.GetAchImg(), achievements.GetAchState());
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyAchievement {
        public final int id;
        public final String achievement_name;
        public final String achievement_description;
        public final int achievement_rewards_treeid;
        public final int achievement_rewards_water;
        public final String achievement_image;
        public final int achievement_state;

        public DummyAchievement(int id, String achievement_name, String achievement_description, int achievement_rewards_treeid, int achievement_rewards_water, String achievement_image, int achievement_state) {
            this.id = id;
            this.achievement_name = achievement_name;
            this.achievement_description = achievement_description;
            this.achievement_rewards_treeid = achievement_rewards_treeid;
            this.achievement_rewards_water = achievement_rewards_water;
            this.achievement_image = achievement_image;
            this.achievement_state = achievement_state;
        }
    }

}