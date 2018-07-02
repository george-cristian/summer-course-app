package com.summer_course.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.summer_course.R;
import com.summer_course.adapters.ScheduleItemsAdapter;

/**
 * A placeholder fragment containing a simple view.
 */
public class ScheduleFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public ScheduleFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ScheduleFragment newInstance(int sectionNumber) {
        ScheduleFragment fragment = new ScheduleFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        /**
         * TODO Aici trebuie sa trimit ca si argument la fragment lista de eventuri din ziua respectiva
         */
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_schedule, container, false);

        TextView textView = rootView.findViewById(R.id.section_label);
        textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

        RecyclerView recyclerView = rootView.findViewById(R.id.rv_schedule);
        LinearLayoutManager layoutManager = new LinearLayoutManager(rootView.getContext());
        recyclerView.setLayoutManager(layoutManager);

        /**
         * TODO Aici la adapter trebuie sa ii trimit lista de eventuri pentru ziua respectiva
         */
        ScheduleItemsAdapter scheduleItemsAdapter = new ScheduleItemsAdapter();
        recyclerView.setAdapter(scheduleItemsAdapter);

        return rootView;
    }
}
