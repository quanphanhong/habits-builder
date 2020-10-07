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
        holder.mWaterAmount.setText(mValues.get(position).achievement_rewards_water.toString());
        //holder.mContentView.setText(mValues.get(position).achievement_image);
        switch (mValues.get(position).achievement_image) {
            case "ic_achievement1":
                holder.mImageView.setImageResource(R.drawable.ic_achievement_1);
                break;
            case "ic_achievement2":
                holder.mImageView.setImageResource(R.drawable.ic_achievement_2);
                break;
            case "ic_achievement3":
                holder.mImageView.setImageResource(R.drawable.ic_achievement_3);
                break;
            case "ic_achievement4":
                holder.mImageView.setImageResource(R.drawable.ic_achievement_4);
                break;
            case "ic_achievement5":
                holder.mImageView.setImageResource(R.drawable.ic_achievement_5);
                break;
            case "ic_achievement6":
                holder.mImageView.setImageResource(R.drawable.ic_achievement_6);
                break;
            case "ic_achievement7":
                holder.mImageView.setImageResource(R.drawable.ic_achievement_7);
                break;
            case "ic_achievement8":
                holder.mImageView.setImageResource(R.drawable.ic_achievement_8);
                break;
            case "ic_achievement9":
                holder.mImageView.setImageResource(R.drawable.ic_achievement_9);
                break;
            case "ic_achievement10":
                holder.mImageView.setImageResource(R.drawable.ic_achievement_10);
                break;
            case "ic_achievement11":
                holder.mImageView.setImageResource(R.drawable.ic_achievement_11);
                break;
            case "ic_achievement12":
                holder.mImageView.setImageResource(R.drawable.ic_achievement_12);
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
        public final TextView mWaterAmount;
        public DummyAchievement mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.content);
            mImageView = (ImageView) view.findViewById(R.id.image_ach);
            mWaterAmount = (TextView) view.findViewById(R.id.water_amount_text_view);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}