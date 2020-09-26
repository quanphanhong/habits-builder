package com.example.habitsbuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

public class NewHabit extends AppCompatActivity {

    Spinner sp_frequency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_habit);

        init();

        prepFrequency();

    }

    private void init() {
        sp_frequency = (Spinner) findViewById(R.id.habit_frequency_spinner);
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
}