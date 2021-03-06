package com.example.habitsbuilder;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.habitsbuilder.Database.Habit;

import java.util.List;

public class HabitDetail extends AppCompatActivity {

    private Habit habit;

    private final int HABIT_BEST_STREAK_ID = 0;
    private final int HABIT_CURRENT_STREAK_ID = 1;

    private TextView tv_Title;
    private TextView tv_description;
    private TextView tv_createdDate;
    private TextView tv_frequency;
    private TextView tv_rank;
    private TextView tv_point;
    private TextView tv_streak;

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
        tv_streak = findViewById(R.id.tv_streak);
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

        habit = DataAccess.getHabit(id);
        tv_Title.setText(habit.GetHabitName());
    }

    private void displayInfo() {
        tv_Title.setText(habit.GetHabitName());
        tv_description.setText(habit.GetHabitDes());
        tv_createdDate.setText(habit.GetHabitCreatedDate());
        tv_frequency.setText(String.valueOf(habit.GetFrequency()) + " " + getResources().getString(R.string.habit_detail_info_frequency_des));
        tv_rank.setText(String.valueOf(DataAccess.getRank(habit.GetHabitRankId()).getName()));
        tv_point.setText(" (" + String.valueOf(habit.GetScore()) + ")");
        List<Integer> streaks = DataAccess.getStreaks(habit.GetHabitId());
        tv_streak.setText("Current: " + streaks.get(HABIT_CURRENT_STREAK_ID) + " | Best: " + streaks.get(HABIT_BEST_STREAK_ID));

        FrequencyChart.setListOfFrequencies(DataAccess.getFrequencyByWeek(habit.GetHabitId()));
    }

    public void deleteHabitClicked(View view) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        DataAccess.deleteHabit(habit);
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
                DataAccess.updateHabit(habit);
                displayInfo();
            }
        }
    }

    public void returnClicked(View view) {
        finish();
    }

}