package com.summer_course;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.summer_course.database_classes.ScheduleEvent;
import com.summer_course.utils.Constants;

public class EventProfileActivity extends AppCompatActivity {

    private ScheduleEvent scheduleEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        scheduleEvent = intent.getParcelableExtra(Constants.SCHEDULE_EVENT);
        getSupportActionBar().setTitle(scheduleEvent.getTitle());

        ImageView eventImage = findViewById(R.id.event_image_view);
        TextView descriptionTextView = findViewById(R.id.tv_event_description);
        TextView durationTextView = findViewById(R.id.tv_event_duration);
        TextView typeTextView = findViewById(R.id.tv_event_type);

        switch (scheduleEvent.getType()) {
            case 1:
                eventImage.setImageResource(R.drawable.academic_image);
                typeTextView.setText("Academic");
                break;
            case 2:
                eventImage.setImageResource(R.drawable.party_image);
                typeTextView.setText("Party");
                break;
            case 3:
                eventImage.setImageResource(R.drawable.leisure_image);
                typeTextView.setText("Leisure");
                break;
        }

        descriptionTextView.setText(scheduleEvent.getDescription());
        durationTextView.setText(scheduleEvent.getHours());

    }

}
