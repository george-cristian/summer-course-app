package com.summer_course;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.summer_course.adapters.DashboardAdapter;

public class DashboardActivity extends AppCompatActivity {

    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        setupGridView();
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

}
