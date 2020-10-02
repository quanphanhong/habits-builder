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

import java.text.SimpleDateFormat;
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
            "Image TEXT" +
            ")";

    private static final String CREATE_HABIT_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS HABIT (" +
            "HabitID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "Name TEXT NOT NULL, " +
            "Description TEXT NOT NULL, " +
            "CreatedDate TEXT, " +
            "Streak INTEGER, " +
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
            "TreeID DECIMAL, " +
            "WaterAmount INTEGER, " +
            "FOREIGN KEY(TreeID) REFERENCES TREES(TreeID)" +
            ")";

    private static final String CREATE_ACHIEVEMENTS_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS ACHIEVEMENTS(" +
            "AchievementID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "Name TEXT NOT NULL, " +
            "Description TEXT, " +
            "RewardID DECIMAL, " +
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
                HabitRank rank = new HabitRank("Rank " + String.valueOf(i), "Rank " + String.valueOf(i), "ic_lv" + String.valueOf(i));
                this.addRank(rank);
            }
        }
    }

    public void createDefaultAchievementsIfNeeded() {
        int count = this.getAchievementCount();
        if(count == 0){
            for(int i = 1; i <= 2; i++){
                Achievements ach = new Achievements("Achievement " + String.valueOf(i),"Achievement " + String.valueOf(i), i,"ic_achievement_" + String.valueOf(i), 0 );
                this.addAchievements(ach);
            }
        }
    }

    public void addHabit(Habit habit){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("Name", habit.GetHabitName());
        values.put("Description", habit.GetHabitDes());
        values.put("CreatedDate", habit.GetHabitCreatedDate().toString());
        values.put("Streak", habit.GetHabitStreak());
        values.put("State", habit.GetHabitState());
        values.put("RankID", habit.GetHabitRankId());

        db.insert("HABIT", null, values);
        db.close();
    }
    public void addAchievements(Achievements ach){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Name", ach.GetAchName());
        values.put("Image", ach.GetAchImg());
        values.put("Description", ach.GetAchDes());
        values.put("State", ach.GetAchState());
        values.put("RewardID", ach.GetAchRewId());

        db.insert("ACHIEVEMENTS", null, values);
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
                habit.SetHabitStreak(cursor.getInt(4));
                habit.SetHabitRankId(cursor.getInt(5));
                habit.SetHabitState(cursor.getInt(6));

                // Adding habit to list
                habitList.add(habit);
            } while (cursor.moveToNext());

        }
        return habitList;
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
                ach.SetAchImg(cursor.getString(3));
                ach.SetAchState(cursor.getInt(4));
                ach.SetAchRewId(cursor.getInt(5));


                // Adding habit to list
                AchievementList.add(ach);
            } while (cursor.moveToNext());

        }
        return AchievementList;
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

                return rank;
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
        values.put("Streak", habit.GetHabitStreak());
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

        db.update("HABITRANK", values, "RankID=?", new String[]{String.valueOf(rank.getRankId())});
    }
}
