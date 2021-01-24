package com.example.habitsbuilder;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.habitsbuilder.Database.Habit;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class NewHabit extends AppCompatActivity {
    public static final int DEFAULT_RANK_ID = 1;
    public static final int DEFAULT_SCORE = 0;

    EditText et_habitName;
    EditText et_description;
    EditText et_startingDate;
    Spinner sp_frequency;
    LinearLayout panel_alert;
    Switch sw_alert;
    TextView tv_alertTime;

    Habit inputHabit = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_habit);

        init();
        prepFrequency();
        loadInfo();
    }

    private void init() {
        et_habitName = (EditText) findViewById(R.id.habit_name_edit_text);
        et_description = (EditText) findViewById(R.id.habit_description_edit_text);
        et_startingDate = (EditText) findViewById(R.id.habit_starting_day_edit_text);
        sp_frequency = (Spinner) findViewById(R.id.habit_frequency_spinner);
        panel_alert = (LinearLayout) findViewById(R.id.habit_alert_time_layout);
        tv_alertTime = (TextView) findViewById(R.id.alert_time_text_view);
        sw_alert = (Switch) findViewById(R.id.habit_alert_switch);
        panel_alert.setVisibility(View.INVISIBLE);
        sw_alertChangedListener();
    }

    private void loadInfo() {
        if (getIntent().getExtras() != null)
        {
            inputHabit = (Habit) getIntent().getSerializableExtra("pre_input");
            et_habitName.setText(inputHabit.GetHabitName());
            et_description.setText(inputHabit.GetHabitDes());
            et_startingDate.setText(inputHabit.GetHabitCreatedDate());
            sp_frequency.setSelection(inputHabit.GetFrequency() - 1);
            if (inputHabit.GetReminder() == 0) sw_alert.setChecked(false);
            else sw_alert.setChecked(true);
            tv_alertTime.setText(inputHabit.GetReminderTime());
        }
    }

    private void sw_alertChangedListener() {
        sw_alert.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    panel_alert.setVisibility(View.INVISIBLE);
                } else {
                    panel_alert.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void prepFrequency() {
        String values[] = {"1", "2", "3", "4", "5", "6", "7"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                values
        );

        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);

        sp_frequency.setAdapter(adapter);
        sp_frequency.setBackground(null);
    }

    private static int currentDay = 0;
    private static int currentMonth = 0;
    private static int currentYear = 0;

    public void datePickerClicked(View view) {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.date_picker, null);

        final DatePicker datePicker = alertLayout.findViewById(R.id.date_picker);
        // datePicker.updateDate(currentYear, currentMonth, currentDay);

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Choose Starting Date");
        alert.setView(alertLayout);
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getBaseContext(), "You've just canceled the process!", Toast.LENGTH_SHORT).show();
            }
        });
        alert.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String currentTime;

                currentDay = datePicker.getDayOfMonth();
                currentMonth = datePicker.getDayOfMonth();
                currentYear = datePicker.getYear();

                if (datePicker.getDayOfMonth() < 10)
                    currentTime = "0" + datePicker.getDayOfMonth() + "/";
                else
                    currentTime = datePicker.getDayOfMonth() + "/";

                if (datePicker.getMonth() + 1 < 10)
                    currentTime += "0" + (datePicker.getMonth() + 1) + "/";
                else
                    currentTime += (datePicker.getMonth() + 1) + "/";

                currentTime += datePicker.getYear();

                et_startingDate.setText(currentTime);
            }
        });

        AlertDialog dialog = alert.create();
        dialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void timePickerClicked(View view) {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.time_picker, null);
        final TimePicker timePicker = alertLayout.findViewById(R.id.time_picker);

        // Set default Time Picker dialog time
        timePicker.setIs24HourView(true);
        timePicker.setHour(Integer.parseInt(tv_alertTime.getText().subSequence(0, 2).toString()));
        timePicker.setMinute(Integer.parseInt(tv_alertTime.getText().subSequence(3, 5).toString()));

        // Build alert layout
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Choose Reminding Time");
        alert.setView(alertLayout);
        alert.setCancelable(false);

        // ========== Updating or Aborting changing time ============
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getBaseContext(), "You've just canceled the process!", Toast.LENGTH_SHORT).show();
            }
        });

        alert.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String currentTime;

                if (timePicker.getHour() < 10)
                    currentTime = "0" + timePicker.getHour() + ":";
                else
                    currentTime = timePicker.getHour() + ":";

                if (timePicker.getMinute() < 10)
                    currentTime += "0" + timePicker.getMinute();
                else
                    currentTime += timePicker.getMinute();

                tv_alertTime.setText(currentTime);
            }
        });
        // =============================================================

        // Create and display Time Picker dialog
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    public void newHabitClicked(View view) {
        // Get information from EditTexts and Spinner
        String habitName = et_habitName.getText().toString();
        String habitDescription = et_description.getText().toString();
        String habitStartingDate = et_startingDate.getText().toString();
        int frequency = Integer.parseInt(sp_frequency.getSelectedItem().toString());

        // Reminder Time string to be saved in Database
        String reminderTime = tv_alertTime.getText().toString();

        // Set Reminder state: 0 - Not set | 1 - Set
        int reminderState;
        if (sw_alert.isChecked()) reminderState = 1;
        else reminderState = 0;

        // In those cases, saving habit would be cancelled
        if (habitName.equals("") || frequency < 0 || frequency > 7 || habitStartingDate.equals(""))
            return;

        // Create a new Database
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        db.createDefaultRanksIfNeeded();

        // Create new habit object and fill it with information gathered above
        Habit new_habit = new Habit(
                habitName,
                habitDescription,
                habitStartingDate,
                frequency,
                DEFAULT_RANK_ID,
                DEFAULT_SCORE,
                reminderState,
                reminderTime
        );

        // If current habit has been created, get Habit Id to update it
        if (inputHabit != null)
            new_habit.SetHabitId(inputHabit.GetHabitId());

        //db.addHabit(new_habit);
        Intent resultIntent = new Intent();
        resultIntent.putExtra("result", new_habit);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    public void exitButtonClicked(View view) {
        Intent resultIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, resultIntent);
        finish();
    }
}