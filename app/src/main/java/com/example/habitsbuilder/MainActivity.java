package com.example.habitsbuilder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.habitsbuilder.Database.Achievements;
import com.example.habitsbuilder.Database.Habit;
import com.example.habitsbuilder.Database.HabitDay;
import com.example.habitsbuilder.Database.HabitRank;
import com.example.habitsbuilder.dummy.DummyContent;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    //LinearLayout habit_list;
    BottomNavigationView navigationView;
    NavHostFragment fragment;
    NavController navController;

    TextView mainTitle;

    static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        updateHabitList();
        updateAchievementList();
        menuItemsChanged();
    }

    private void init() {
        //habit_list = (LinearLayout) findViewById(R.id.habit_list);
        navigationView = (BottomNavigationView) findViewById(R.id.navigation_bar);

        fragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        navController = Navigation.findNavController(this, R.id.fragment);

        mainTitle = (TextView) findViewById(R.id.main_title);
        context = getApplicationContext();
    }

    public static void updateHabitList() {
        DatabaseHelper db = new DatabaseHelper(context);
        db.createDefaultRanksIfNeeded();
        List<Habit> habitList = db.getAllHabit();
        DummyContent.ITEMS.clear();
        DummyContent.ITEM_MAP.clear();
        for (Habit habit : habitList){
            DummyContent.addItem(DummyContent.createDummyItem(habit, db.getRank(habit.GetHabitRankId())));
        }
        HabitListFragment.updateRecyclerView();
    }

    public static void updateAchievementList() {
        DatabaseHelper db = new DatabaseHelper(context);
        db.createDefaultRewardsIfNeeded();
        db.createDefaultAchievementsIfNeeded();

        List<Achievements> achievementsList = db.getAllAchievements();

        DummyContent.DummyAchievement_ITEMS.clear();
        DummyContent.DummyAchievement_ITEM_MAP.clear();

        for (Achievements achievement : achievementsList){
            DummyContent.DummyAchievement_addItem(DummyContent.DummyAchievement_createDummyItem(achievement, db.getReward(achievement.GetAchRewId())));
        }
    }

    public static HabitRank getRank(int id) {
        DatabaseHelper db = new DatabaseHelper(context);
        return db.getRank(id);
    }

    private void menuItemsChanged() {
        navigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.DailyTask:
                                mainTitle.setText(R.string.mode1);
                                navController.navigate(R.id.dailyTaskFragment);
                                break;
                            case R.id.HabitList:
                                mainTitle.setText(R.string.mode2);
                                navController.navigate(R.id.habitListFragment);
                                break;
                            case R.id.NewHabit:
                                openNewHabitActivity();
                                break;
                            case R.id.Pet:
                                mainTitle.setText(R.string.mode4);
                                navController.navigate(R.id.petsFragment);
                                break;
                            case R.id.Achievements:
                                mainTitle.setText(R.string.mode5);
                                navController.navigate(R.id.achievementsFragment2);
                                break;
                        }
                        return true;
                    }
                }
        );
    }

    private void openNewHabitActivity() {
        Intent intent = new Intent(this, NewHabit.class);
        startActivityForResult(intent, 100);
    }

    public void addingHabitClicked(View view) {
        openNewHabitActivity();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100) {
            if (resultCode == Activity.RESULT_OK) {
                Habit habit = (Habit) data.getSerializableExtra("result");
                DatabaseHelper db = new DatabaseHelper(context);
                db.addHabit(habit);
                updateHabitList();
                DailyTaskFragment.updateHabitList();
                updateAchievementList();
            }
        }
    }
}