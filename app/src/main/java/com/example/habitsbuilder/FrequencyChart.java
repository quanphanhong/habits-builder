package com.example.habitsbuilder;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.habitsbuilder.Database.Frequency;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FrequencyChart#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FrequencyChart extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static List<Frequency> frequencies = new ArrayList<>();

    private String mParam1;
    private String mParam2;

    private static BarChart frequencyChart;

    public FrequencyChart() {
        // Required empty public constructor
    }

    public static void setListOfFrequencies(List<Frequency> freq) {
        frequencies = freq;
        if (frequencies.size() > 0) drawChart();
    }

    public static FrequencyChart newInstance(String param1, String param2) {
        FrequencyChart fragment = new FrequencyChart();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_frequency_chart, container, false);
        frequencyChart = view.findViewById(R.id.frequencyChart);
        return view;
    }

    private static void drawChart() {
        ArrayList numberOfDays = new ArrayList();
        for (int i = 0; i < frequencies.size(); i++) {
            numberOfDays.add(new BarEntry(i, frequencies.get(i).getNumberOfDays()));
        }

        BarDataSet barDataSet = new BarDataSet(numberOfDays, "Days");
        BarData barData = new BarData(barDataSet);
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        frequencyChart.animateY(5000);
        frequencyChart.setData(barData);
        frequencyChart.setMaxVisibleValueCount(1500);
        frequencyChart.setDrawBarShadow(true);
        frequencyChart.setFitBars(true);
        frequencyChart.setVisibility(View.VISIBLE);
        frequencyChart.invalidate();
    }
}