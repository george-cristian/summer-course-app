package com.summer_course;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.summer_course.adapters.DashboardAdapter;
import com.summer_course.adapters.ValidateUsersAdapter;
import com.summer_course.database_classes.User;
import com.summer_course.utils.Constants;

import java.util.ArrayList;

/**
 * @author George Cristian
 *
 * Activity that displays buttons and a list of users to be validated.
 */
public class DashboardActivity extends AppCompatActivity {

    private GridView gridView;

    /**
     * View used to display the users that need to be validated
     */
    private RecyclerView validateUsersRecyclerView;

    private TextView tvUsersToBeValidated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        tvUsersToBeValidated = findViewById(R.id.tv_users_to_be_validated);

        setupGridView();
        setupValidateUsersRecyclerView();
    }

    private void setupGridView() {
        gridView = findViewById(R.id.grid_view_dashboard);

        gridView.setAdapter(new DashboardAdapter(this));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),
                        "An item was clicked", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setupValidateUsersRecyclerView() {
        validateUsersRecyclerView = findViewById(R.id.rv_validate_users);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        validateUsersRecyclerView.setLayoutManager(linearLayoutManager);

        //Set divider between the items in the view
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(validateUsersRecyclerView.getContext(),
                        linearLayoutManager.getOrientation());
        validateUsersRecyclerView.addItemDecoration(dividerItemDecoration);

        //Query the database to get the list of users to be validated
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(Constants.USERS_DATABASE).orderByChild("validated").equalTo(false).
                addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<User> usersToBeValidated = new ArrayList<>();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String userID = ds.getKey();
                    String userName = ds.child("name").getValue(String.class);
                    String profilePicString = ds.child("profilePicString").getValue(String.class);
                    int type = ds.child("type").getValue(Integer.class);
                    boolean validated = ds.child("validated").getValue(Boolean.class);

                    User user = new User(userID, type, validated, userName, profilePicString);
                    usersToBeValidated.add(user);
                }

                //Check if the are no users to be validated
                if (usersToBeValidated.isEmpty() == true) {
                    tvUsersToBeValidated.setVisibility(View.INVISIBLE);
                    validateUsersRecyclerView.setVisibility(View.INVISIBLE);
                } else {
                    //Send the list of users to be validated to the RecyclerView adapter
                    validateUsersRecyclerView.setAdapter(new ValidateUsersAdapter(usersToBeValidated));
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
