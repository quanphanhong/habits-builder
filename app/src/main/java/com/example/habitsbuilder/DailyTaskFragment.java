package com.example.habitsbuilder;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.habitsbuilder.Database.Habit;
import com.example.habitsbuilder.Database.HabitDay;
import com.example.habitsbuilder.dummy.DummyContent;

import java.text.SimpleDateFormat;
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
    //private CalendarView calendar;

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

    private static Context context;
    private static FragmentManager childFragmentManager;
    private static CalendarView calendar;

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

        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.replace(R.id.habit_list_fragment, new CheckListFragment());
        ft.commit();

        context = getContext();
        childFragmentManager = getChildFragmentManager();

        updateHabitList();
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Calendar cal = Calendar.getInstance();
                cal.set(year, month, dayOfMonth);
                calendar.setDate(cal.getTimeInMillis());

                updateHabitList();
            }
        });
    }

    public static String pickedHabitDate = "";

    public static void updateHabitDay(HabitDay habitDay) {
        if (context == null) return;
        DatabaseHelper db = new DatabaseHelper(context);
        db.updateHabitDay(habitDay);
    }

    public static List<HabitDay> getHabitDayByHabitId(int id) {
        if (context == null) return null;
        DatabaseHelper db = new DatabaseHelper(context);
        return db.getHabitDayByHabitId(id);
    }

    public static Habit getHabitById(int id) {
        if (context == null) return null;
        DatabaseHelper db = new DatabaseHelper(context);
        return db.getHabit(id);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void updateHabitList() {
        if (context == null) return;
        DatabaseHelper db = new DatabaseHelper(context);
        db.createHabitDayItems();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        pickedHabitDate = sdf.format(new Date(calendar.getDate()));
        List<HabitDay> habitDays = db.getAllHabitOfDay(pickedHabitDate);

        DummyContent.DummyHabitDay_ITEMS.clear();
        DummyContent.DummyHabitDay_ITEM_MAP.clear();

        for (HabitDay habitDay : habitDays) {
            DummyContent.DummyHabitDay_addItem(DummyContent.DummyHabitDay_createDummyItem(habitDay, db.getHabit(habitDay.getHabitId())));
        }

        try {
            FragmentTransaction ft = childFragmentManager.beginTransaction();
            ft.replace(R.id.habit_list_fragment, new CheckListFragment());
            ft.commit();
        } catch (Exception ex) {
            Log.i("Exception", ex.getMessage());
        }
    }
}