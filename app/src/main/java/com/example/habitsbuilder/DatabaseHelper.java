package com.example.habitsbuilder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.navigation.Navigator;

import com.example.habitsbuilder.Database.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "HabitManager.db";

    private static final String CREATE_HABIT_RANK_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS HABITRANK (" +
            "RankID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "Name TEXT NOT NULL, " +
            "Description TEXT, " +
            "Image TEXT, " +
            "AchieveScore INTEGER" +
            ")";

    private static final String CREATE_HABIT_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS HABIT (" +
            "HabitID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "Name TEXT NOT NULL, " +
            "Description TEXT NOT NULL, " +
            "CreatedDate TEXT, " +
            "Frequency INTEGER, " +
            "Point INTEGER, " +
            "RankID DECIMAL, " +
            "State INTEGER, " +
            "FOREIGN KEY(RankID) REFERENCES HABITRANK(RankID)" +
            ")";

    private static final String CREATE_HABIT_DAY_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS HABITDAY (" +
            "HabitID INTEGER, " +
            "Date TEXT, " +
            "State INTEGER," +
            "PRIMARY KEY(HabitID, Date)," +
            "FOREIGN KEY(HabitID) REFERENCES HABIT(HabitID)" +
            ")";

    private static final String CREATE_TREES_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS TREES(" +
            "TreeID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "Name TEXT NOT NULL, " +
            "Level INTEGER, " +
            "State INTEGER" +
            ")";

    private static final String CREATE_REWARDS_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS REWARDS(" +
            "RewardID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "TreeID INTEGER, " +
            "WaterAmount INTEGER, " +
            "FOREIGN KEY(TreeID) REFERENCES TREES(TreeID)" +
            ")";

    private static final String CREATE_ACHIEVEMENTS_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS ACHIEVEMENTS(" +
            "AchievementID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "Name TEXT NOT NULL, " +
            "Description TEXT, " +
            "RewardID INTEGER, " +
            "Image TEXT, " +
            "State INTEGER, " +
            "FOREIGN KEY(RewardID) REFERENCES REWARDS(RewardID)" +
            ")";

    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public DatabaseHelper(@Nullable Context context, @Nullable String name, int version, @NonNull SQLiteDatabase.OpenParams openParams) {
        super(context, name, version, openParams);
    }

    public DatabaseHelper(Context context)  {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_HABIT_RANK_TABLE_QUERY);
        db.execSQL(CREATE_HABIT_TABLE_QUERY);
        db.execSQL(CREATE_HABIT_DAY_TABLE_QUERY);
        db.execSQL(CREATE_TREES_TABLE_QUERY);
        db.execSQL(CREATE_REWARDS_TABLE_QUERY);
        db.execSQL(CREATE_ACHIEVEMENTS_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop table
        db.execSQL("DROP TABLE IF EXISTS ACHIEVEMENTS");
        db.execSQL("DROP TABLE IF EXISTS REWARDS");
        db.execSQL("DROP TABLE IF EXISTS TREES");
        db.execSQL("DROP TABLE IF EXISTS HABITDAY");
        db.execSQL("DROP TABLE IF EXISTS HABIT");
        db.execSQL("DROP TABLE IF EXISTS HABITRANK");

        // Recreate
        onCreate(db);
    }

    public void createDefaultRanksIfNeeded() {
        int count = this.getRankCount();
        if (count == 0) {
            for (int i = 1; i <= 10; i++) {
                HabitRank rank = new HabitRank("Rank " + String.valueOf(i), "Rank " + String.valueOf(i), "ic_lv" + String.valueOf(i), i * 1000);
                this.addRank(rank);
            }
        }
    }
    final int COUNT_ACHIEVEMENT = 12;
    public void createDefaultAchievementsIfNeeded() {

        String name[] = new String[COUNT_ACHIEVEMENT + 1];
        String description[] = new String[COUNT_ACHIEVEMENT + 1];
        for(int i=1; i<=COUNT_ACHIEVEMENT; i++){
            switch (i){
                case 1:
                    name[i] = "Good Start";
                    description[i] = "Having a level 1 habit";
                    break;
                case 2:
                    name[i] = "Warrior";
                    description[i] = "Having a level 3 habit";
                    break;
                case 3:
                    name[i] = "Persistence";
                    description[i] = "Having a level 5";
                    break;
                case 4:
                    name[i] = "Never Give Up";
                    description[i] = "Having a level 10 habit";
                    break;
                case 5:
                    name[i] = "Silver Habit";
                    description[i] = "Having three level 1 habits";
                    break;
                case 6:
                    name[i] = "Super Warrior";
                    description[i] = "Having three level 3 habits";
                    break;
                case 7:
                    name[i] = "Control habit";
                    description[i] = "Having three level 5 habits";
                    break;
                case 8:
                    name[i] = "Nothing Is Difficult";
                    description[i] = "Having three level 10 habits";
                    break;
                case 9:
                    name[i] = "Golden Habit";
                    description[i] = "Having five level 1 habits";
                    break;
                case 10:
                    name[i] = "Superman";
                    description[i] = "Having five level 3 habits";
                    break;
                case 11:
                    name[i] = "Iron Will";
                    description[i] = "Having five level 5 habits";
                    break;
                case 12:
                    name[i] = "Master habit";
                    description[i] = "Having five level 10 habits";
                    break;
            }
        }

        int count = this.getAchievementCount();
        if (count == 0){
            for(int i = 1; i <= COUNT_ACHIEVEMENT; i++){
                Achievements ach = new Achievements(name[i],description[i], i,"ic_achievement" + String.valueOf(i), 0 );
                this.addAchievements(ach);
            }
        }
    }

    public void createDefaultRewardsIfNeeded(){
        int count = this.getRewardCount();
        if(count == 0){
            for(int i = 1; i <=COUNT_ACHIEVEMENT; i++){
                Rewards rewards = new Rewards(i, i, 500);
                Log.i("reward id", String.valueOf(rewards.getRewardID()));
                this.addRewards(rewards);
            }
        }
    }

    public int getRewardCount(){
        String countQuery = "SELECT  * FROM REWARDS";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        // return count
        return count;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createHabitDayItems() {
        List<Habit> habitList = this.getAllHabit();
        for (Habit habit : habitList) {
            if (habit.GetHabitState() == 0) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                LocalDate startDate = LocalDate.parse(habit.GetHabitCreatedDate(), formatter);
                LocalDate endDate = startDate.now().plusDays(1);

                for(LocalDate currentdate = startDate; currentdate.isBefore(endDate) || currentdate.isEqual(endDate); currentdate = currentdate.plusDays(1)){
                    String comparedDateString = currentdate.format(formatter);
                    if (getHabitDayCount(habit.GetHabitId(), comparedDateString) == 0) {
                        HabitDay habitDay = new HabitDay(habit.GetHabitId(), comparedDateString, 0);
                        this.addHabitDay(habitDay);
                    }
                }
            }
        }
    }

    public void addHabit(Habit habit){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("Name", habit.GetHabitName());
        values.put("Description", habit.GetHabitDes());
        values.put("CreatedDate", habit.GetHabitCreatedDate().toString());
        values.put("Frequency", habit.GetFrequency());
        values.put("Point", habit.GetPoint());
        values.put("State", habit.GetHabitState());
        values.put("RankID", habit.GetHabitRankId());

        db.insert("HABIT", null, values);
        db.close();
    }

    public void addHabitDay(HabitDay habitday){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("HabitID", habitday.getHabitId());
        values.put("Date", habitday.getDate());
        values.put("State", habitday.getState());

        db.insert("HABITDAY", null, values);
        db.close();
    }

    public void addAchievements(Achievements ach){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("RewardID", ach.GetAchRewId());
        values.put("Name", ach.GetAchName());
        values.put("Image", ach.GetAchImg());
        values.put("Description", ach.GetAchDes());
        values.put("State", ach.GetAchState());


        db.insert("ACHIEVEMENTS", null, values);
        db.close();
    }

    public void addRewards(Rewards rewards){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("WaterAmount", rewards.getWaterAmount());
        values.put("TreeID", rewards.getRewardID());

        db.insert("REWARDS", null, values);
        db.close();
    }

    public void addTree(Tree tree){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("Name", tree.getName());
        values.put("Level", tree.getLevel());
        values.put("State", tree.getState());

        db.insert("TREES", null, values);
        db.close();
    }

    public void addRank(HabitRank rank) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("Name", rank.getName());
        values.put("Description", rank.getDescription());
        values.put("Image", rank.getImage());
        values.put("AchieveScore", rank.getAchieveScore());

        db.insert("HABITRANK", null, values);
        db.close();
    }

    public List<Habit> getAllHabit(){
        List<Habit> habitList = new ArrayList<Habit>();
        String selectQuery = "SELECT * FROM HABIT";
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Habit habit = new Habit();
                habit.SetHabitId(Integer.parseInt(cursor.getString(0)));
                habit.SetHabitName(cursor.getString(1));
                habit.SetHabitDes(cursor.getString(2));
                habit.SetHabitCreatedDate(cursor.getString(3));
                habit.SetFrequency(Integer.parseInt(cursor.getString(4)));
                habit.SetPoint(cursor.getInt(5));
                habit.SetHabitRankId(cursor.getInt(6));
                habit.SetHabitState(cursor.getInt(7));

                // Adding habit to list
                habitList.add(habit);
            } while (cursor.moveToNext());

        }
        return habitList;
    }

    public List<HabitDay> getAllHabitOfDay(String Date){
        List<HabitDay> habitList = new ArrayList<HabitDay>();
        String selectQuery = "SELECT * FROM HABITDAY WHERE Date='" + Date + "'";
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HabitDay habitDay = new HabitDay();
                habitDay.setHabitId(Integer.parseInt(cursor.getString(0)));
                habitDay.setDate(cursor.getString(1));
                habitDay.setState(Integer.parseInt(cursor.getString(2)));

                // Adding habit to list
                habitList.add(habitDay);
            } while (cursor.moveToNext());

        }
        return habitList;
    }

    public int getHabitDayCount(int HabitID, String Date) {
        String countQuery = "SELECT  * FROM HABITDAY WHERE HabitID=" + HabitID + " AND Date='" + Date + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        Log.i("habit id", String.valueOf(HabitID));
        Log.i("habit date", Date);
        Log.i("habitday count", String.valueOf(count));

        // return count
        return count;
    }

    public List<Achievements> getAllAchievements(){
        List<Achievements> AchievementList = new ArrayList<Achievements>();
        String selectQuery = "SELECT * FROM ACHIEVEMENTS";
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Achievements ach = new Achievements();
                ach.SetAchId(Integer.parseInt(cursor.getString(0)));
                ach.SetAchName(cursor.getString(1));
                ach.SetAchDes(cursor.getString(2));
                ach.SetAchRewId(cursor.getInt(3));
                ach.SetAchImg(cursor.getString(4));
                ach.SetAchState(cursor.getInt(5));


                // Adding habit to list
                AchievementList.add(ach);
            } while (cursor.moveToNext());

        }
        return AchievementList;
    }

    public List<Rewards> getAllRewards(){
        List<Rewards> RewardList = new ArrayList<Rewards>();
        String selectQuery = "SELECT * FROM REWARDS";
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Rewards rewards = new Rewards();
                rewards.setRewardID(Integer.parseInt(cursor.getString(0)));
                rewards.setTreeID(Integer.parseInt(cursor.getString(1)));
                rewards.setWaterAmount(Integer.parseInt(cursor.getString(2)));



                // Adding habit to list
                RewardList.add(rewards);
            } while (cursor.moveToNext());

        }
        return RewardList;
    }

    public Rewards getReward(int id){
        String query = "SELECT * FROM REWARDS WHERE RewardID=" + id;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            try {
                Rewards reward = new Rewards();
                reward.setRewardID(Integer.parseInt(cursor.getString(0)));
                reward.setTreeID(Integer.parseInt(cursor.getString(1)));
                reward.setWaterAmount(Integer.parseInt(cursor.getString(2)));

                return reward;
            } catch (Exception ex) {
                Log.i("Error", "Error while loading rank from database");
            }
        }

        return null;

    }
    public List<Tree> getAllTree(){
        List<Tree> TreeList = new ArrayList<Tree>();
        String selectQuery = "SELECT * FROM TREES";
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Tree tree = new Tree();
                tree.setTreeId(Integer.parseInt(cursor.getString(0)));
                tree.setName(cursor.getString(1));
                tree.setLastWateringDateTime(cursor.getString(2));
                tree.setLevel(cursor.getInt(3));
                tree.setPoint(cursor.getInt(4));
                tree.setState(cursor.getInt(5));


                // Adding habit to list
                TreeList.add(tree);
            } while (cursor.moveToNext());

        }
        return TreeList;
    }

    public void deleteTree(Tree tree){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("TREES", "TreeId=?", new String[]{String.valueOf(tree.getTreeId())});
        db.close();
    }

    public HabitRank getRank(int id) {

        String query = "SELECT * FROM HABITRANK WHERE RankID=" + id;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            try {
                HabitRank rank = new HabitRank();
                rank.setRankId(Integer.parseInt(cursor.getString(0)));
                rank.setName(cursor.getString(1));
                rank.setDescription(cursor.getString(2));
                rank.setImage(cursor.getString(3));
                rank.setAchieveScore(Integer.parseInt(cursor.getString(4)));

                return rank;
            } catch (Exception ex) {
                Log.i("Error", "Error while loading rank from database");
            }
        }

        return null;
    }

    public List<String> getHabitDateById(int id) {
        List<String> result = new ArrayList<String>();
        result.clear();

        String selectQuery = "SELECT Date FROM HABITDAY WHERE HabitID='" + id + "' AND State = 1";
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                result.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        return result;
    }

    public Habit getHabit(int id) {

        String query = "SELECT * FROM HABIT WHERE HabitID=" + id;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            try {
                Habit habit = new Habit();
                habit.SetHabitId(Integer.parseInt(cursor.getString(0)));
                habit.SetHabitName(cursor.getString(1));
                habit.SetHabitDes(cursor.getString(2));
                habit.SetHabitCreatedDate(cursor.getString(3));
                habit.SetFrequency(cursor.getInt((4)));
                habit.SetPoint(cursor.getInt(5));
                habit.SetHabitRankId(cursor.getInt(6));
                habit.SetHabitState(cursor.getInt(7));

                return habit;
            } catch (Exception ex) {
                Log.i("Error", "Error while loading rank from database");
            }
        }

        return null;
    }

    public int getRankCount() {
        String countQuery = "SELECT  * FROM HABITRANK";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        // return count
        return count;
    }

    public int getAchievementCount(){
        String countQuery = "SELECT * FROM ACHIEVEMENTS";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();

        cursor.close();

        // return count
        return count;
    }

    public void deleteHabit(Habit habit){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("HABITDAY", "HabitID=?", new String[]{String.valueOf(habit.GetHabitId())});
        db.delete("HABIT", "HabitID=?", new String[]{String.valueOf(habit.GetHabitId())});
        db.close();
    }

    public void deleteAchievement(Achievements ach){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("ACHIEVEMENTS", "AchievementID=?", new String[]{String.valueOf(ach.GetAchId())});
        db.close();
    }

    public void deleteRank(HabitRank rank) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("HABITRANK", "RankID=?", new String[]{String.valueOf(rank.getRankId())});
    }

    public void updateHabit(Habit habit){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("Name", habit.GetHabitName());
        values.put("Description", habit.GetHabitDes());
        values.put("CreatedDay", habit.GetHabitCreatedDate().toString());
        values.put("Point", habit.GetPoint());
        values.put("State", habit.GetHabitState());
        values.put("RankID", habit.GetHabitRankId());

        db.update("HABIT", values, "HabitID=?", new String[]{String.valueOf(habit.GetHabitId())});
        db.close();
    }

    public void updateTree(Tree tree){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("Name", tree.getName());
        values.put("Level", tree.getLevel());
        values.put("State", tree.getState());

        db.update("TREES", values, "TreeId=?", new String[]{String.valueOf(tree.getTreeId())});
        db.close();
    }

    public void updateAchievement(Achievements ach){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("Name", ach.GetAchName());
        values.put("Image", ach.GetAchImg());
        values.put("Description", ach.GetAchDes());
        values.put("State", ach.GetAchState());
        values.put("RewardID", ach.GetAchRewId());

        db.update("ACHIEVEMENTS", values, "AchievementID=?", new String[]{String.valueOf(ach.GetAchId())});
        db.close();
    }

    public void updateRank(HabitRank rank) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("Name", rank.getName());
        values.put("Description", rank.getDescription());
        values.put("Image", rank.getImage());
        values.put("AchieveScore", rank.getAchieveScore());

        db.update("HABITRANK", values, "RankID=?", new String[]{String.valueOf(rank.getRankId())});
    }
}
