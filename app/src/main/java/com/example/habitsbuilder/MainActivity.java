package com.example.habitsbuilder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    LinearLayout habit_list;
    BottomNavigationView navigationView;
    NavHostFragment fragment;
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        menuItemsChanged();
    }

    private void init() {
        habit_list = (LinearLayout) findViewById(R.id.habit_list);
        navigationView = (BottomNavigationView) findViewById(R.id.navigation_bar);

        fragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        navController = Navigation.findNavController(this, R.id.fragment);
    }

    private void menuItemsChanged() {
        navigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.DailyTask:
                                navController.navigate(R.id.dailyTaskFragment);
                                break;
                            case R.id.HabitList:
                                navController.navigate(R.id.habitListFragment);
                                break;
                            case R.id.NewHabit:
                                openNewHabitActivity();
                                break;
                            case R.id.Pet:
                                navController.navigate(R.id.petsFragment);
                                break;
                        }
                        return true;
                    }
                }
        );
    }

    private void openNewHabitActivity() {
        Intent intent = new Intent(this, NewHabit.class);
        startActivity(intent);
    }

    public void addingHabitClicked(View view) {
        openNewHabitActivity();
    }
}