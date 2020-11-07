package com.example.habitsbuilder;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.habitsbuilder.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyHabitListRecyclerViewAdapter extends RecyclerView.Adapter<MyHabitListRecyclerViewAdapter.ViewHolder> implements View.OnClickListener{

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
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.mHabitName.setText(mValues.get(position).habit_name);
        holder.mHabitDescription.setText(mValues.get(position).habit_description);
        holder.mHabitStartingDay.setText(mValues.get(position).habit_starting_day);
        holder.mTextLevel.setText(mValues.get(position).habit_rank);

        switch (mValues.get(position).habit_rank_image) {
            case "ic_lv1":
                holder.mImageLevel.setImageResource(R.drawable.ic_lv1);
                break;
            case "ic_lv2":
                holder.mImageLevel.setImageResource(R.drawable.ic_lv2);
                break;
            case "ic_lv3":
                holder.mImageLevel.setImageResource(R.drawable.ic_lv3);
                break;
            case "ic_lv4":
                holder.mImageLevel.setImageResource(R.drawable.ic_lv4);
                break;
            case "ic_lv5":
                holder.mImageLevel.setImageResource(R.drawable.ic_lv5);
                break;
            case "ic_lv6":
                holder.mImageLevel.setImageResource(R.drawable.ic_lv6);
                break;
            case "ic_lv7":
                holder.mImageLevel.setImageResource(R.drawable.ic_lv7);
                break;
            case "ic_lv8":
                holder.mImageLevel.setImageResource(R.drawable.ic_lv8);
                break;
            case "ic_lv9":
                holder.mImageLevel.setImageResource(R.drawable.ic_lv9);
                break;
            case "ic_lv10":
                holder.mImageLevel.setImageResource(R.drawable.ic_lv10);
                break;
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), HabitDetail.class);
                Bundle bundle = new Bundle();
                bundle.putInt("habitID", mValues.get(position).id);
                intent.putExtras(bundle);

                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), HabitDetail.class);
        Bundle bundle = new Bundle();
        bundle.putInt("habitID", v.getId());
        intent.putExtras(bundle);

        v.getContext().startActivity(intent);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;
        public final TextView mHabitName;
        public final TextView mHabitDescription;
        public final TextView mHabitStartingDay;
        public final TextView mTextLevel;
        public final ImageView mImageLevel;
        public DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mHabitName = (TextView) view.findViewById(R.id.habit_name);
            mHabitDescription = (TextView) view.findViewById(R.id.habit_description);
            mHabitStartingDay = (TextView) view.findViewById(R.id.habit_starting_day);
            mTextLevel = (TextView) view.findViewById(R.id.text_level);
            mImageLevel = (ImageView) view.findViewById(R.id.image_level);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTextLevel.getText() + "'";
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), HabitDetail.class);
            Bundle bundle = new Bundle();
            bundle.putInt("habitID", v.getId());
            intent.putExtras(bundle);
            v.getContext().startActivity(intent);
        }
    }
}