package com.example.habitsbuilder;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.habitsbuilder.dummy.DummyContent.DummyAchievement;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyAchievement}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyAchievementRecyclerViewAdapter extends RecyclerView.Adapter<MyAchievementRecyclerViewAdapter.ViewHolder> {

    private final List<DummyAchievement> mValues;

    public MyAchievementRecyclerViewAdapter(List<DummyAchievement> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_achievements, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).achievement_name);
        holder.mContentView.setText(mValues.get(position).achievement_description);
        //holder.mContentView.setText(mValues.get(position).achievement_image);
        switch (mValues.get(position).achievement_image) {
            case "1":
                holder.mImageView.setImageResource(R.drawable.ic_achievement_1);
                break;
            case "2":
                holder.mImageView.setImageResource(R.drawable.ic_achievement_2);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final ImageView mImageView;
        public DummyAchievement mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.content);
            mImageView = (ImageView) view.findViewById(R.id.image_ach);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}