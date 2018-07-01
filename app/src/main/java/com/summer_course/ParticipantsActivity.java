package com.summer_course;

import android.support.design.widget.TabLayout;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.summer_course.adapters.ParticipantsAdapter;
import com.summer_course.database_classes.ParticipantsAndOrganisersList;
import com.summer_course.database_classes.User;
import com.summer_course.utils.Constants;
import java.util.ArrayList;
import java.util.List;

/**
 * @author George Cristian
 *
 * The activity where both the participants and organisers are displayed (in separate tabs)
 *
 */
public class ParticipantsActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participants);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Setting up the back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_arrow);

        //Create the query to get all the people from the database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        Query query = databaseReference.child(Constants.USERS_DATABASE);

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);

        final TabLayout tabLayout = findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        //Add the listener to the firebase database query
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<User> participantsList = new ArrayList<>();
                List<User> organisersList = new ArrayList<>();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    int userType = ds.child("type").getValue(Integer.class);
                    String userName = ds.child("name").getValue(String.class);
                    String profilePicString = ds.child("profilePicString").getValue(String.class);
                    boolean validated = ds.child("validated").getValue(Boolean.class);

                    User tempUser = new User(userType, validated, userName, profilePicString);

                    if (userType == 0) {
                        participantsList.add(tempUser);
                    } else {
                        organisersList.add(tempUser);
                    }
                }

                ParticipantsAndOrganisersList personsList =
                        new ParticipantsAndOrganisersList(participantsList, organisersList);

                // Create the adapter that will return a fragment for each of the two
                // primary sections of the activity.
                mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), personsList);

                mViewPager.setAdapter(mSectionsPagerAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        /*getMenuInflater().inflate(R.menu.menu_participants, menu);
        return true;*/
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";
        private static final String ARG_PEOPLE_NAMES = "people_names";
        private static final String ARG_PEOPLE_PICS = "people_pics";

        private static final int PARTICIPANTS_TAB = 1;
        private static final int ORGANISERS_TAB = 2;

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int tabNumber,
                                                      ParticipantsAndOrganisersList personsList) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, tabNumber);
            ArrayList<String> peopleNames;
            ArrayList<String> peoplePics;

            if (tabNumber == PARTICIPANTS_TAB) {
                peopleNames = personsList.getParticipantsNames();
                peoplePics = personsList.getParticipantsPics();
            } else {
                peopleNames = personsList.getOrganisersNames();
                peoplePics = personsList.getOrganisersPics();
            }

            //Send the names and pictures as arguments to the new fragment
            args.putStringArrayList(ARG_PEOPLE_NAMES, peopleNames);
            args.putStringArrayList(ARG_PEOPLE_PICS, peoplePics);

            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_participants, container, false);

            Bundle argumentsBundle = getArguments();

            GridView gridView = rootView.findViewById(R.id.grid_view_participants);

            //Retrieve the names and the pictures from the arguments bundle
            ArrayList<String> peopleNames = argumentsBundle.getStringArrayList(ARG_PEOPLE_NAMES);
            ArrayList<String> peoplePics = argumentsBundle.getStringArrayList(ARG_PEOPLE_PICS);

            BaseAdapter gridViewAdapter = new ParticipantsAdapter(inflater, peopleNames, peoplePics);

            gridView.setAdapter(gridViewAdapter);

            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private ParticipantsAndOrganisersList personsList;

        public SectionsPagerAdapter(FragmentManager fm, ParticipantsAndOrganisersList personsList) {
            super(fm);
            this.personsList = personsList;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1, this.personsList);
        }

        @Override
        public int getCount() {
            // Show two total tabs.
            return 2;
        }
    }
}
