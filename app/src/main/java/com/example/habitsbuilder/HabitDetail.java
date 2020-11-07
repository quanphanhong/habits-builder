package com.example.habitsbuilder;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.habitsbuilder.Database.Habit;

public class HabitDetail extends AppCompatActivity {

    private Habit habit;

    private TextView tv_Title;

    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_detail);

        init();
        LoadHabit(savedInstanceState);
    }

    private void init() {
        tv_Title = findViewById(R.id.habit_name);
    }

    private void LoadHabit(Bundle savedInstanceState) {
        int id = -1;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                id = -1;
            } else {
                id = extras.getInt("habitID");
            }
        } else {
            id = (int) savedInstanceState.getSerializable("habitID");
        }

        db = new DatabaseHelper(this);
        habit = db.getHabit(id);

        tv_Title.setText(habit.GetHabitName());
    }

    public void deleteHabitClicked(View view) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        db.deleteHabit(habit);
                        MainActivity.updateHabitList();
                        DailyTaskFragment.updateHabitList();
                        MainActivity.updateAchievementList();
                        finish();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }
}