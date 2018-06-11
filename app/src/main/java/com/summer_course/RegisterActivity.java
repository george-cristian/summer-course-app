package com.summer_course;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = RegisterActivity.class.getSimpleName();
    private static final String[] USER_ROLES = {"Participant", "Core-team", "Volunteer", "Admin"};

    private Spinner roleSpinner;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        addItemsToSpinner();

    }

    private void addItemsToSpinner() {
        roleSpinner = findViewById(R.id.role_spinner);

        ArrayAdapter<String> rolesAdapter = new ArrayAdapter<>(
                this, R.layout.roles_spinner_item, USER_ROLES);

        roleSpinner.setAdapter(rolesAdapter);
    }

}
