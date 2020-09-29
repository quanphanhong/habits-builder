package com.example.habitsbuilder;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class NewHabit extends AppCompatActivity {

    Spinner sp_frequency;
    LinearLayout panel_alert;
    Switch sw_alert;
    TextView tv_alertTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_habit);

        init();
        prepFrequency();
    }

    private void init() {
        sp_frequency = (Spinner) findViewById(R.id.habit_frequency_spinner);
        panel_alert = (LinearLayout) findViewById(R.id.habit_alert_time_layout);
        tv_alertTime = (TextView) findViewById(R.id.alert_time_text_view);
        sw_alert = (Switch) findViewById(R.id.habit_alert_switch);
        panel_alert.setVisibility(View.INVISIBLE);
        sw_alertChangedListener();
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
        String values[] = {"1 day", "2 days", "3 days", "4 days", "5 days", "6 days", "7 days"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                values
        );

        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);

        sp_frequency.setAdapter(adapter);
    }

    public void datePickerClicked(View view) {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.date_picker, null);

        final DatePicker datePicker = alertLayout.findViewById(R.id.date_picker);
        //timePicker.setIs24HourView(true);
        //timePicker.setHour(Integer.parseInt(tv_alertTime.getText().subSequence(0, 2).toString()));
        //timePicker.setMinute(Integer.parseInt(tv_alertTime.getText().subSequence(3, 5).toString()));

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

                if (datePicker.getDayOfMonth() < 10)
                    currentTime = "0" + datePicker.getDayOfMonth() + ":";
                else
                    currentTime = datePicker.getDayOfMonth() + ":";

                if (datePicker.getMonth() < 10)
                    currentTime += "0" + datePicker.getMonth();
                else
                    currentTime += datePicker.getMonth();

                tv_alertTime.setText(currentTime);
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
        timePicker.setIs24HourView(true);
        timePicker.setHour(Integer.parseInt(tv_alertTime.getText().subSequence(0, 2).toString()));
        timePicker.setMinute(Integer.parseInt(tv_alertTime.getText().subSequence(3, 5).toString()));

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Choose Reminding Time");
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

        AlertDialog dialog = alert.create();
        dialog.show();
    }

    public void exitButtonClicked(View view) { finish(); }
}