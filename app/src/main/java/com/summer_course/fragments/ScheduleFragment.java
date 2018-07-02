package com.summer_course.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.summer_course.R;
import com.summer_course.adapters.ScheduleItemsAdapter;
import com.summer_course.database_classes.ScheduleEvent;

import java.util.ArrayList;

/**
 * @author George Cristian
 *
 * Fragment which contains the {@link RecyclerView} used in the {@link com.summer_course.ScheduleActivity}.
 */
public class ScheduleFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String ARG_EVENTS_LIST = "events_list";

    public ScheduleFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ScheduleFragment newInstance(int sectionNumber, ArrayList<ScheduleEvent> eventsList) {
        ScheduleFragment fragment = new ScheduleFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        //Sending the events list as an argument to the fragment
        args.putParcelableArrayList(ARG_EVENTS_LIST, eventsList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_schedule, container, false);

        Bundle argumentsBundle = getArguments();

        //Setting up the RecyclerView
        RecyclerView recyclerView = rootView.findViewById(R.id.rv_schedule);
        LinearLayoutManager layoutManager = new LinearLayoutManager(rootView.getContext());
        recyclerView.setLayoutManager(layoutManager);

        //Obtaining the events list from the bundle of arguments
        ArrayList<ScheduleEvent> eventsList = argumentsBundle.getParcelableArrayList(ARG_EVENTS_LIST);
        ScheduleItemsAdapter scheduleItemsAdapter = new ScheduleItemsAdapter(eventsList);
        recyclerView.setAdapter(scheduleItemsAdapter);

        //Adding a divider between events
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        return rootView;
    }
}
