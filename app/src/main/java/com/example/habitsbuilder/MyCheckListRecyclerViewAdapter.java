package com.example.habitsbuilder;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.util.Log;
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
                    int stateAfter = 0;
                    if (isChecked) stateAfter = 1;
                    //Log.i("State", String.valueOf(habitDay.getState()));
                    if (habitDay.getState() == stateAfter) return;

                    if (isChecked)
                    {
                        habitDay.setState(1);
                        mItem.habit_state = 1;
                    }
                    else
                    {
                        habitDay.setState(0);
                        mItem.habit_state = 0;
                    }
                    buttonView.setChecked(isChecked);
                    DailyTaskFragment.updateHabitDay(habitDay);

                    // Update Habit Score
                    List<HabitDay> habitDayList = DataAccess.getHabitDayByHabitId(habitDay.getHabitId());
                    Habit habit = DataAccess.getHabit(habitDay.getHabitId());

                    for (int i = 0; i < habitDayList.size(); i++) {
                        if (habitDayList.get(i).getHabitId() == habitDay.getHabitId() && habitDayList.get(i).getDate().equals(habitDay.getDate())) {
                            int streakBefore = 0, leftSum = 0;
                            for (int j = i - 1; j >= 0; j--) {
                                if (habitDayList.get(j).getState() == 1) {
                                    streakBefore++;
                                    leftSum += streakBefore * 10;
                                }
                                else break;
                            }

                            int streakAfter = 0, rightSum = 0;
                            for (int j = i + 1; j < habitDayList.size(); j++) {
                                if (habitDayList.get(j).getState() == 1) {
                                    streakAfter++;
                                    rightSum += streakAfter * 10;
                                }
                                else break;
                            }

                            int sum = 0;
                            for (int j = i - streakBefore; j <= i + streakAfter; j++)
                                sum += (j - (i - streakBefore) + 1) * 10;

                            if (isChecked)
                                sum = sum - (leftSum + rightSum);
                            else
                                sum = leftSum + rightSum - sum;

                            habit.SetScore(habit.GetScore() + sum);
                            DataAccess.updateHabit(habit);

                            break;
                        }
                    }
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