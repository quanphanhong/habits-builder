package com.example.habitsbuilder;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import com.example.habitsbuilder.Database.Habit;
import com.example.habitsbuilder.Database.HabitDay;
import com.example.habitsbuilder.dummy.DummyContent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DailyTaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DailyTaskFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private CalendarView calendar;

    public DailyTaskFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DailyTaskFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DailyTaskFragment newInstance(String param1, String param2) {
        DailyTaskFragment fragment = new DailyTaskFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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
        return inflater.inflate(R.layout.fragment_daily_task, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        calendar = (CalendarView) view.findViewById(R.id.calendar);
        updateHabitList();
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                updateHabitList();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void updateHabitList() {
        Log.i("habit list updated", "habit list has been updated");
        DatabaseHelper db = new DatabaseHelper(getContext());
        db.createHabitDayItems();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dayGettingHabitFrom = sdf.format(new Date(calendar.getDate()));
        List<HabitDay> habitDays = db.getAllHabitOfDay(dayGettingHabitFrom);

        //List<HabitDay> habitDays = new ArrayList<HabitDay>();
        //habitDays.add(new HabitDay(1, "1", 1));

        DummyContent.DummyHabitDay_ITEMS.clear();
        DummyContent.DummyHabitDay_ITEM_MAP.clear();

        for (HabitDay habitDay : habitDays) {
            DummyContent.DummyHabitDay_addItem(DummyContent.DummyHabitDay_createDummyItem(habitDay, db.getHabit(habitDay.getHabitId())));
            //DummyContent.DummyHabitDay_addItem(DummyContent.DummyHabitDay_createDummyItem(habitDay, new Habit("abc", "fsafd", "safds,", 5, 1, 1)));
        }
    }
}