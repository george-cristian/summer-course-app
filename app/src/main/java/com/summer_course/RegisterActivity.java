package com.summer_course;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.summer_course.database_classes.User;
import com.summer_course.utils.BitmapUtils;
import com.summer_course.utils.Constants;
import com.summer_course.utils.FacebookUtils;
import org.json.JSONException;
import org.json.JSONObject;

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
                /* Create Facebook Graph Request to get the name */
                GraphRequest facebookGraphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {
                            String userName = "";
                            try {
                                userName = object.getString("name");
                            } catch (JSONException e) {
                                Log.e(TAG, "Error at graph request");
                            }

                            /* Create a new User object with the type, profile pic and name */
                            String userID = AccessToken.getCurrentAccessToken().getUserId();
                            int selectedItemPosition = roleSpinner.getSelectedItemPosition();
                            Bitmap userProfilePic = FacebookUtils.getFacebookProfilePic(userID);
                            String profilePicString = BitmapUtils.getStringFromBitmap(userProfilePic);

                            User newUser = new User(selectedItemPosition, false, userName,
                                    profilePicString);

                            addUserToDatabaseAndGoToDashboard(newUser);
                        }
                    });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "name,picture");
                facebookGraphRequest.setParameters(parameters);
                facebookGraphRequest.executeAsync();

            }
        });
    }

    private void addUserToDatabaseAndGoToDashboard(User newUser) {
        String userID = AccessToken.getCurrentAccessToken().getUserId();

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

}
