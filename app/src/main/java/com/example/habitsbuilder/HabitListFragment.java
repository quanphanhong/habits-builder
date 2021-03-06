package com.example.habitsbuilder;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.habitsbuilder.Database.Habit;
import com.example.habitsbuilder.Database.HabitDay;
import com.example.habitsbuilder.dummy.DummyContent;

/**
 * A fragment representing a list of Items.
 */
public class HabitListFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public HabitListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static HabitListFragment newInstance(int columnCount) {
        HabitListFragment fragment = new HabitListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    public static void updateRecyclerView() {
        if (adapter != null) {
            synchronized (adapter) {
                adapter.notifyDataSetChanged();
            }
        }
    }

    public static Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    private static MyHabitListRecyclerViewAdapter adapter = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        context = getContext();

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            adapter = new MyHabitListRecyclerViewAdapter(DummyContent.ITEMS);

            recyclerView.setAdapter(adapter);
        }
        return view;
    }
}