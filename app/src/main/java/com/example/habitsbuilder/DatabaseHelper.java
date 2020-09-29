package com.example.habitsbuilder;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.habitsbuilder.Database.*;

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
                "NAME TEXT NOT NULL, " +
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
}
