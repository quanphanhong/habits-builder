package com.example.habitsbuilder;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.habitsbuilder.Database.Habit;
import com.example.habitsbuilder.Database.HabitDay;
import com.example.habitsbuilder.dummy.DummyContent.DummyHabitDay;

import java.util.List;

public class MyCheckListRecyclerViewAdapter extends RecyclerView.Adapter<MyCheckListRecyclerViewAdapter.ViewHolder> {

    private final List<DummyHabitDay> mValues;

    public MyCheckListRecyclerViewAdapter(List<DummyHabitDay> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_checklist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        if (mValues.get(position).habit_state == 1)
            holder.mHabitDayCheckBox.setChecked(true);
        else
            holder.mHabitDayCheckBox.setChecked(false);
        holder.mHabitDayContent.setText(mValues.get(position).habit_name);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final CheckBox mHabitDayCheckBox;
        public final TextView mHabitDayContent;
        public DummyHabitDay mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mHabitDayCheckBox = (CheckBox) view.findViewById(R.id.habit_day_checkbox);
            mHabitDayCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    HabitDay habitDay = new HabitDay(mItem.id, mItem.habit_day, mItem.habit_state);
                    if (isChecked)
                        habitDay.setState(1);
                    else
                        habitDay.setState(0);
                    buttonView.setChecked(isChecked);
                    DailyTaskFragment.updateHabitDay(habitDay);
                    //DailyTaskFragment.updateHabitList();
                }
            });
            mHabitDayContent = (TextView) view.findViewById(R.id.habit_day_text_view);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mHabitDayContent.getText() + "'";
        }
    }
}