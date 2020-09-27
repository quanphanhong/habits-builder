package com.example.habitsbuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Switch;

public class NewHabit extends AppCompatActivity {

    Spinner sp_frequency;

    LinearLayout panel_alert;
    Switch sw_alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_habit);

        init();
        prepFrequency();
    }

    private void init() {
        sp_frequency = (Spinner) findViewById(R.id.habit_frequency_spinner);

        panel_alert = (LinearLayout) findViewById(R.id.habit_reminding_time_setting);
        sw_alert = (Switch) findViewById(R.id.habit_alert_switch);
        panel_alert.setVisibility(View.INVISIBLE);
        sw_alertChangedListener();

    }

    private void sw_alertChangedListener() {
        sw_alert.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) panel_alert.setVisibility(View.INVISIBLE);
                else panel_alert.setVisibility(View.VISIBLE);
            }
        });
    }

    private void prepFrequency() {
        String values[] = {"1 days", "2 days", "3 days", "4 days", "5 days", "6 days", "7 days"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                values
        );

        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);

        sp_frequency.setAdapter(adapter);
    }

    public void exitButtonClicked(View view) {
        finish();
    }
}