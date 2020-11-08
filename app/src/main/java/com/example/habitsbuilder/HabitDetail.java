package com.example.habitsbuilder;

import androidx.annotation.Nullable;
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
    private TextView tv_description;
    private TextView tv_createdDate;
    private TextView tv_frequency;
    private TextView tv_rank;
    private TextView tv_point;


    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_detail);

        init();
        LoadHabit(savedInstanceState);
        displayInfo();
    }

    private void init() {
        tv_Title = findViewById(R.id.habit_name);
        tv_description = findViewById(R.id.tv_description);
        tv_createdDate = findViewById(R.id.tv_created_date);
        tv_frequency = findViewById(R.id.tv_frequency);
        tv_rank = findViewById(R.id.tv_rank);
        tv_point = findViewById(R.id.tv_point);
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

    private void displayInfo() {
        tv_Title.setText(habit.GetHabitName());
        tv_description.setText(habit.GetHabitDes());
        tv_createdDate.setText(habit.GetHabitCreatedDate());
        tv_frequency.setText(String.valueOf(habit.GetFrequency()) + " " + getResources().getString(R.string.habit_detail_info_frequency_des));
        tv_rank.setText(String.valueOf(db.getRank(habit.GetHabitRankId()).getName()));
        tv_point.setText(" (" + String.valueOf(habit.GetPoint()) + ")");
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

    public void editHabitClicked(View view) {
        Intent intent = new Intent(this, NewHabit.class);
        intent.putExtra("pre_input", habit);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100)
        {
            if (resultCode == RESULT_OK)
            {
                habit = (Habit) data.getSerializableExtra("result");
                db.updateHabit(habit);
                displayInfo();
            }
        }
    }

    public void returnClicked(View view) {
        finish();
    }

}