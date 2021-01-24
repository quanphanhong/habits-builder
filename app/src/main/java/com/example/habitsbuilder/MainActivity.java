package com.example.habitsbuilder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
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

import java.sql.Time;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //LinearLayout habit_list;
    BottomNavigationView navigationView;
    NavHostFragment fragment;
    NavController navController;

    TextView mainTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        // Update habit list and achievement list
        updateHabitList();
        updateAchievementList();

        // Switch between fragments when chosen menu item changed
        menuItemsChanged();

        // Check and push reminder
        pushingReminders();
    }

    private void init() {
        //habit_list = (LinearLayout) findViewById(R.id.habit_list);
        navigationView = (BottomNavigationView) findViewById(R.id.navigation_bar);
        fragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        navController = Navigation.findNavController(this, R.id.fragment);
        mainTitle = (TextView) findViewById(R.id.main_title);

        DataAccess.setContext(getApplicationContext());
    }

    public static void updateHabitList() {
        List<Habit> habitList = DataAccess.getAllHabit();
        DummyContent.ITEMS.clear();
        DummyContent.ITEM_MAP.clear();
        for (Habit habit : habitList){
            DummyContent.addItem(DummyContent.createDummyItem(habit, DataAccess.getRank(habit.GetHabitRankId())));
        }
        HabitListFragment.updateRecyclerView();
    }

    private void pushingReminders()
    {
        for (Habit habit : DataAccess.getAllHabit())
        {
            if (habit.GetReminder() == 1)
                scheduleNotification(getNotification("Have you done this habit - " + habit.GetHabitName()),
                        getNextRemindingTimeSpan(habit.GetReminderTime()));
        }
    }

    private long getNextRemindingTimeSpan(String time)
    {
        // Initialize now and next reminding time
        Calendar now = Calendar.getInstance();
        Calendar nextRemindingTime = Calendar.getInstance();

        // Get current time (day, month, year, hour, minute)
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
        int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int currentMinute = Calendar.getInstance().get(Calendar.MINUTE);

        // Get time string and split it to reminding hour and minute
        int remindingHour = Integer.parseInt(time.subSequence(0, 2).toString());
        int remindingMinute = Integer.parseInt(time.subSequence(3, 5).toString());

        // Set current time for now variable
        now.set(currentYear, currentMonth, currentDay, currentHour, currentMinute);

        // Firstly, set reminding time as now
        nextRemindingTime = now;

        // If in this day, reminding time has passed, add 1 more day to next reminding time
        if (currentHour > remindingHour || (currentHour == remindingHour && currentMinute >= remindingMinute))
            nextRemindingTime.add(Calendar.DATE, 1);

        // Re-set reminding hour and minute
        nextRemindingTime.set(Calendar.HOUR_OF_DAY, remindingHour);
        nextRemindingTime.set(Calendar.MINUTE, remindingMinute);

        // Get time span to reminding time
        return nextRemindingTime.getTimeInMillis() - now.getTimeInMillis();
    }

    public static void updateAchievementList() {
        // Get achievements from Database
        List<Achievements> achievementsList = DataAccess.getAllAchievements();

        // Clear all achievements in DummyContent
        DummyContent.DummyAchievement_ITEMS.clear();
        DummyContent.DummyAchievement_ITEM_MAP.clear();

        // Add achievements into DummyContent
        for (Achievements achievement : achievementsList){
            DummyContent.DummyAchievement_addItem(DummyContent.DummyAchievement_createDummyItem(achievement, DataAccess.getReward(achievement.GetAchRewId())));
        }
    }

    private void menuItemsChanged() {
        navigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            // Daily Task
                            case R.id.DailyTask:
                                mainTitle.setText(R.string.mode1);
                                navController.navigate(R.id.dailyTaskFragment);
                                break;
                            // Habit List
                            case R.id.HabitList:
                                mainTitle.setText(R.string.mode2);
                                navController.navigate(R.id.habitListFragment);
                                break;
                            /*case R.id.NewHabit:
                                openNewHabitActivity();
                                break;
                            case R.id.Pet:
                                mainTitle.setText(R.string.mode4);
                                navController.navigate(R.id.petsFragment);
                                break;
                            case R.id.Achievements:
                                mainTitle.setText(R.string.mode5);
                                navController.navigate(R.id.achievementsFragment2);
                                break;*/
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

        // When new habit should be added
        if (requestCode == 100) {
            if (resultCode == Activity.RESULT_OK) {
                Habit habit = (Habit) data.getSerializableExtra("result");

                // Insert new habit that parsed from New Habit
                DataAccess.insertHabit(habit);

                // Update Habit list
                updateHabitList();
                DailyTaskFragment.updateHabitList();
                updateAchievementList();
            }
        }
    }


    private void scheduleNotification (Notification notification, long delay) {
        // Create a new Notification intent
        Intent notificationIntent = new Intent( this, NotificationPublisher. class );

        // Put notification info to Notification intent
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);

        PendingIntent pendingIntent = PendingIntent.
                getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Calculate reminder time
        long futureInMillis = SystemClock.elapsedRealtime() + delay;

        // Get alarm service
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

        // Notify if alarm manager cannot be get successfully
        assert alarmManager != null;

        // Set alarm
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }
    private Notification getNotification (String content) {
        // Build a notification
        // Create a notification builder
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default" );

        builder.setContentTitle("Habit Reminder"); // Notification title
        builder.setContentText(content); // Notification content
        builder.setSmallIcon(R.drawable.ic_launcher_foreground); // Display icon in Notification
        builder.setAutoCancel(true);
        builder.setChannelId("10001");

        return builder.build();
    }
}