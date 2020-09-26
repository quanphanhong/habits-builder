package com.example.habitsbuilder;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.habitsbuilder.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyHabitListRecyclerViewAdapter extends RecyclerView.Adapter<MyHabitListRecyclerViewAdapter.ViewHolder> {

    private final List<DummyItem> mValues;

    public MyHabitListRecyclerViewAdapter(List<DummyItem> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mHabitName.setText(mValues.get(position).habit_name);
        holder.mHabitDescription.setText(mValues.get(position).habit_description);
        holder.mHabitStartingDay.setText(mValues.get(position).habit_starting_day);
        holder.mTextLevel.setText(mValues.get(position).text_level);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mHabitName;
        public final TextView mHabitDescription;
        public final TextView mHabitStartingDay;
        public final TextView mTextLevel;
        public DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mHabitName = (TextView) view.findViewById(R.id.habit_name);
            mHabitDescription = (TextView) view.findViewById(R.id.habit_description);
            mHabitStartingDay = (TextView) view.findViewById(R.id.habit_starting_day);
            mTextLevel = (TextView) view.findViewById(R.id.text_level);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTextLevel.getText() + "'";
        }
    }
}