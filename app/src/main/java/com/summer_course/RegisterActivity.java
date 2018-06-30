package com.summer_course;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.facebook.AccessToken;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.summer_course.utils.Constants;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = RegisterActivity.class.getSimpleName();

    private Spinner roleSpinner;
    private Button submitButton;

    /* Firebase Realtime Database instance */
    private FirebaseDatabase mFirebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        roleSpinner = findViewById(R.id.role_spinner);
        submitButton = findViewById(R.id.btnSubmit);

        /* Initialize Firebase Database */
        mFirebaseDatabase = FirebaseDatabase.getInstance();

        addItemsToSpinner();
        addListenerToSubmitButton();
    }

    /**
     * Populate the spinner using an Adapter
     */
    private void addItemsToSpinner() {
        ArrayAdapter<String> rolesAdapter = new ArrayAdapter<>(
                this, R.layout.roles_spinner_item, Constants.USER_ROLES);

        roleSpinner.setAdapter(rolesAdapter);
    }

    /**
     * Starts the dashboard activity
     */
    private void changeToDashboardActivity() {
        Intent dashboardIntent = new Intent(Constants.DASHBOARD_ACTIVITY);
        startActivity(dashboardIntent);
    }

    /**
     * Adds a listener to the submit button to add the user to the database
     */
    private void addListenerToSubmitButton() {
        submitButton.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                int selectedItemPosition = roleSpinner.getSelectedItemPosition();
                String userID = AccessToken.getCurrentAccessToken().getUserId();

                User newUser = new User(selectedItemPosition, false);

                mFirebaseDatabase.getReference().child(Constants.USERS_DATABASE).child(userID).
                    setValue(newUser, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError,
                                               DatabaseReference databaseReference) {

                            if (databaseError != null) {
                                Log.d(TAG, "An error occured when writing to database: " +
                                        databaseError.getMessage());
                            } else {
                                changeToDashboardActivity();
                            }

                        }
                    });
            }
        });
    }

    private static class User {

        private int type;
        private boolean validated;

        public User() {
            this.type = 0;
            this.validated = false;
        }

        public User(int type, boolean validated) {
            this.type = type;
            this.validated = validated;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public boolean isValidated() {
            return validated;
        }

        public void setValidated(boolean validated) {
            this.validated = validated;
        }
    }

}
