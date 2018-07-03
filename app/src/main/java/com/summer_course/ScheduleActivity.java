package com.summer_course;

import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.summer_course.adapters.ScheduleSpinnerAdapter;
import com.summer_course.database_classes.ScheduleEvent;
import com.summer_course.fragments.ScheduleFragment;
import com.summer_course.utils.Constants;
import com.summer_course.utils.DateUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author George Cristian
 *
 * Activity in which the schedule is displayed.
 */
public class ScheduleActivity extends AppCompatActivity {

    private Spinner daysSpinner;
    private Toolbar activityToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        activityToolbar = findViewById(R.id.schedule_toolbar);
        setSupportActionBar(activityToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //Setting up the back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_arrow);

        // Setup spinner
        daysSpinner = findViewById(R.id.schedule_spinner);

        //Create a query to get all the events from the database
        this.getEventsFromDatabase();


    }

    /**
     * Run query to get events from database and populate the spinner and the interior fragments
     * with data from the database.
     */
    private void getEventsFromDatabase() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        Query query = databaseReference.child(Constants.EVENTS_DATABASE);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                List<ScheduleEvent> eventsList = new ArrayList<>();

                //Iterating through all the events and creating a list of them
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String eventID = ds.getKey();
                    String title = ds.child("title").getValue(String.class);
                    String description = ds.child("description").getValue(String.class);
                    Long startTimestamp = ds.child("start").getValue(Long.class);
                    Long stopTimestamp = ds.child("stop").getValue(Long.class);
                    int type = ds.child("type").getValue(Integer.class);

                    ScheduleEvent event =
                            new ScheduleEvent(eventID, title, description, startTimestamp, stopTimestamp, type);
                    eventsList.add(event);
                }

                //Sorting the list of events by start time
                Collections.sort(eventsList, new Comparator<ScheduleEvent>() {
                    @Override
                    public int compare(ScheduleEvent o1, ScheduleEvent o2) {
                        return o1.getStartTime().compareTo(o2.getStartTime());
                    }
                });

                //Getting an array of strings which represent individual days
                String[] scheduleDays = DateUtils.getAvailableDaysFromEventsList(eventsList);

                daysSpinner.setAdapter(new ScheduleSpinnerAdapter(
                        activityToolbar.getContext(), scheduleDays));

                //Obtaining a list of lists of events organised by each day
                //Each list has all the events of one single day
                final ArrayList<ArrayList<ScheduleEvent>> orderedEventsList =
                        DateUtils.getOrderedListOfEvents(eventsList);

                daysSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        // When the given dropdown item is selected, show its contents in the
                        // container view.
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.schedule_container,
                                        ScheduleFragment.newInstance(position + 1, orderedEventsList.get(position)))
                                .commit();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void onResume() {
        super.onResume();

        //Retrieve again the events
        this.getEventsFromDatabase();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
