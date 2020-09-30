package com.example.habitsbuilder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

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

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query;

        // Create Habit Table
        query = "CREATE TABLE IF NOT EXISTS HABIT (" +
                "HabitID DECIMAL PRIMARY KEY AUTOINCREMENT, " +
                "Name TEXT NOT NULL, " +
                "Description TEXT NOT NULL, " +
                "CreatedDate TEXT, " +
                "Streak INT, " +
                "RankID DECIMAL, " +
                "State INT" +
                ")";

        db.execSQL(query);

        // Create HabitRank Table
        query = "CREATE TABLE IF NOT EXISTS HABITRANK (" +
                "RankID DECIMAL PRIMARY KEY AUTOINCREMENT, " +
                "Name TEXT NOT NULL, " +
                "Description TEXT, " +
                "Image TEXT" +
                ")";

        db.execSQL(query);

        // Create HabitDay Table
        query = "CREATE TABLE IF NOT EXISTS HABITDAY (" +
                "HabitID DECIMAL, " +
                "Date TEXT, " +
                "State INT," +
                "PRIMARY KEY(HabitID, Date)," +
                "FOREIGN KEY(HabitID) REFERENCES HABIT(HabitID)" +
                ")";

        db.execSQL(query);

        // Create Trees Table
        query = "CREATE TABLE IF NOT EXISTS TREES(" +
                "TreeId DECIMAL PRIMARY KEY AUTOINCREMENT, " +
                "Name TEXT NOT NULL, " +
                "Level INT, " +
                "State INT" +
                ")";

        db.execSQL(query);

        // Create Rewards Table
        query = "CREATE TABLE IF NOT EXISTS REWARDS(" +
                "RewardId DECIMAL PRIMARY KEY AUTOINCREMENT, " +
                "TreeID DECIMAL, " +
                "WaterAmount INT, " +
                "FOREIGN KEY(TreeID) REFERENCES TREES(TreeID)" +
                ")";

        db.execSQL(query);

        // Create Achievements Table
        query = "CREATE TABLE IF NOT EXISTS ACHIEVEMENTS(" +
                "AchievementID DECIMAL PRIMARY KEY AUTOINCREMENT, " +
                "Name TEXT NOT NULL, " +
                "Description TEXT, " +
                "RewardID DECIMAL, " +
                "Image TEXT, " +
                "State INT, " +
                "FOREIGN KEY(RewardID) REFERENCES REWARDS(RewardID)" +
                ")";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addHabit(Habit habit){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Name", habit.GetHabitName());
        values.put("Description", habit.GetHabitDes());
        values.put("CreatedDay", habit.GetHabitCreatedDate().toString());
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

    public List<Habit>getAllHabit(){
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
                habit.SetHabitRankId(cursor.getLong(5));
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
                ach.SetAchRewId(cursor.getLong(5));


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

}
