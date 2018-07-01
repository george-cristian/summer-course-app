package com.summer_course;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.summer_course.utils.Constants;

/**
 * @author George Cristian
 */
public class LoginActivity extends AppCompatActivity {

    private final String TAG = LoginActivity.class.getSimpleName();

    /* The facebook login button */
    private LoginButton loginButton;

    private TextView loginTextView;
    private ProgressBar progressBar;

    /* Callback manager used for login purposes */
    private CallbackManager callbackManager;

    /* Firebase Realtime Database instance */
    private FirebaseDatabase mFirebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton = findViewById(R.id.facebook_login_button);
        progressBar = findViewById(R.id.pb_loading_indicator);
        loginTextView = findViewById(R.id.tv_login);

        /* Initialize facebook SDK */
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        /* Initialize Firebase Database */
        mFirebaseDatabase = FirebaseDatabase.getInstance();

        LoginManager.getInstance().logOut();

        /* Initialize CallbackManager */
        callbackManager = CallbackManager.Factory.create();

        loginButton.setReadPermissions("public_profile");
        loginButton.registerCallback(callbackManager, new CustomFacebookCallback());
    }

    /**
     * Starts the dashboard activity
     */
    private void changeToDashboardActivity() {
        Intent dashboardIntent = new Intent(Constants.DASHBOARD_ACTIVITY);
        startActivity(dashboardIntent);
    }

    /**
     * Starts the register activity
     */
    private void changeToRegisterActivity() {
        Intent registerIntent = new Intent(Constants.REGISTER_ACTIVITY);
        startActivity(registerIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Custom Facebook callback used for login
     */
    private class CustomFacebookCallback implements FacebookCallback<LoginResult> {

        @Override
        public void onSuccess(LoginResult loginResult) {

            /* Display progress bar and make button and text view invisible */
            progressBar.setVisibility(View.VISIBLE);
            loginButton.setVisibility(View.INVISIBLE);
            loginTextView.setVisibility(View.INVISIBLE);

            String userID = loginResult.getAccessToken().getUserId();

            /* Query Firebase Realtime Database to check if the user id exists */
            mFirebaseDatabase.getReference().child(Constants.USERS_DATABASE).child(userID).
                addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        progressBar.setVisibility(View.INVISIBLE);
                        if (dataSnapshot.exists()) {
                            changeToDashboardActivity();
                        } else {
                            changeToRegisterActivity();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d(TAG, "Database check cancelled");
                    }
                });
        }

        @Override
        public void onCancel() {
            Log.i(TAG, "Login process canceled");
        }

        @Override
        public void onError(FacebookException e) {
            Log.e(TAG, "Login process error" + e.getMessage());
        }

    }
}
