package com.summer_course;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

/**
 * @author George Cristian
 */
public class LoginActivity extends AppCompatActivity {

    private final String TAG = LoginActivity.class.getSimpleName();

    private final String DASHBOARD_ACTIVITY = "com.summer_course.DashboardActivity";
    private final String REGISTER_ACTIVITY = "com.summer_course.RegisterActivity";

    /* The facebook login button */
    private LoginButton loginButton;

    /* Callback manager used for login purposes */
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton = findViewById(R.id.facebook_login_button);

        /* Initialize facebook SDK */
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        LoginManager.getInstance().logOut();

        /* Initialize CallbackManager */
        callbackManager = CallbackManager.Factory.create();

        loginButton.registerCallback(callbackManager, new CustomFacebookCallback());

    }

    /**
     * Starts the dashboard activity
     */
    private void changeToDashboardActivity() {
        Intent dashboardIntent = new Intent(DASHBOARD_ACTIVITY);
        startActivity(dashboardIntent);
    }

    /**
     * Starts the register activity
     */
    private void changeToRegisterActivity() {
        Intent registerIntent = new Intent(REGISTER_ACTIVITY);
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
            /*Verifica daca utilizatorul se afla la prima logare:
            * - da -> il trimite catre screen-ul de register
            * - nu -> il trimite catre dashboard
            * id-ul userului va fi in loginResult: loginResult.getAccessToken().getUserID()*/

            final boolean userExists = checkIfUserExists(loginResult.getAccessToken().getUserId());

            if (userExists == true) {
                changeToDashboardActivity();
            } else {
                changeToRegisterActivity();
            }

        }

        @Override
        public void onCancel() {
            /* Il intoarce la screen-ul de login */
            Log.i(TAG, "Login process canceled");
        }

        @Override
        public void onError(FacebookException e) {
            /* Il intoarce la screen-ul de login */
            Log.e(TAG, "Login process error" + e.getMessage());
        }

        /**
         * Verify in database if the user already exists
         *
         * @param userID The users facebook ID to search
         * @return If the user is already in the database or not
         */
        private boolean checkIfUserExists(String userID) {
            return true;
        }

    }
}
