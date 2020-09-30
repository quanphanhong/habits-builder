package com.example.habitsbuilder.dummy;

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
    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    private static final int COUNT = 10;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static DummyItem createDummyItem(int position) {
        return new DummyItem(String.valueOf(position), "Habit No." + position, "Description No." + position,"Level No." + position, "Start: Day No." + position, makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public final String id;
        public final String habit_name;
        public final String habit_description;
        public final String habit_starting_day;
        public final String text_level;
        public final String details;

        public DummyItem(String id, String habit_name, String habit_description, String text_level, String habit_starting_day, String details) {
            this.id = id;
            this.habit_name = habit_name;
            this.habit_description = habit_description;
            this.habit_starting_day = habit_starting_day;
            this.text_level = text_level;
            this.details = details;
        }

        @Override
        public String toString() {
            return details;
        }
    }

    /**
     * An array of sample (dummy) items.
     */
    public static final List<DummyAchievement> DummyAchievement_ITEMS = new ArrayList<DummyAchievement>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, DummyAchievement> DummyAchievement_ITEM_MAP = new HashMap<String, DummyAchievement>();

    private static final int DummyAchievement_COUNT = 10;

    static {
        // Add some sample items.
        for (int i = 1; i <= DummyAchievement_COUNT; i++) {
            DummyAchievement_addItem(DummyAchievement_createDummyItem(i));
        }
    }

    private static void DummyAchievement_addItem(DummyAchievement item) {
        DummyAchievement_ITEMS.add(item);
        DummyAchievement_ITEM_MAP.put(item.id, item);
    }

    private static DummyAchievement DummyAchievement_createDummyItem(int position) {
        return new DummyAchievement(String.valueOf(position), "Achievement No." + position, "Level Description No." + position, position, position, DummyAchievement_makeDetails(position));
    }

    private static String DummyAchievement_makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyAchievement {
        public final String id;
        public final String achievement_name;
        public final String achievement_description;
        public final long achievement_rewards_treeid;
        public final int achievement_rewards_water;
        public final String details;

        public DummyAchievement(String id, String achievement_name, String achievement_description, long achievement_rewards_treeid, int achievement_rewards_water, String details) {
            this.id = id;
            this.achievement_name = achievement_name;
            this.achievement_description = achievement_description;
            this.achievement_rewards_treeid = achievement_rewards_treeid;
            this.achievement_rewards_water = achievement_rewards_water;
            this.details = details;
        }

        @Override
        public String toString() {
            return details;
        }
    }

}