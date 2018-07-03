package com.summer_course;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ViewSwitcher;

import com.summer_course.database_classes.ScheduleEvent;
import com.summer_course.utils.Constants;

public class EventProfileActivity extends AppCompatActivity {

    private ScheduleEvent scheduleEvent;

    private ViewSwitcher viewSwitcherDescription;

    private Button btnCommitChanges;
    private Button btnEditStartTime;
    private Button btnEditEndTime;

    private TextView startTimeTextView;
    private TextView endTimeTextView;

    private TextView errorTextView;

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
        startTimeTextView = findViewById(R.id.tv_event_start_time);
        endTimeTextView = findViewById(R.id.tv_event_end_time);
        TextView typeTextView = findViewById(R.id.tv_event_type);

        EditText descriptionEditView = findViewById(R.id.et_event_description);

        errorTextView = findViewById(R.id.tv_error_hours);
        btnCommitChanges = findViewById(R.id.btn_edit_event);

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
        startTimeTextView.setText(scheduleEvent.getStartTimeAsString());
        endTimeTextView.setText(scheduleEvent.getEndTimeAsString());
        descriptionEditView.setText(scheduleEvent.getDescription());

        viewSwitcherDescription = findViewById(R.id.view_switcher_description);
        viewSwitcherDescription.showNext();

        btnEditStartTime = findViewById(R.id.btn_edit_start_time);
        btnEditEndTime = findViewById(R.id.btn_edit_end_time);
        this.addListenersToEditTimeButtons();

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void addListenersToEditTimeButtons() {

        btnEditStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String text;
                        if (minute == 0) {
                            text = hourOfDay + ":00";
                        } else {
                            text = hourOfDay + ":" + minute;
                        }

                        scheduleEvent.setEventStartTime(hourOfDay, minute);
                        startTimeTextView.setText(text);
                        validateHours();
                    }
                };

                TimePickerDialog startTimePicker = new TimePickerDialog(EventProfileActivity.this,
                        timeSetListener, scheduleEvent.getStartHour(), scheduleEvent.getStartMinutes(), true);

                startTimePicker.show();

            }
        });

        btnEditEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String text;
                        if (minute == 0) {
                            text = hourOfDay + ":00";
                        } else {
                            text = hourOfDay + ":" + minute;
                        }

                        scheduleEvent.setEventEndTime(hourOfDay, minute);
                        endTimeTextView.setText(text);
                        validateHours();
                    }
                };

                TimePickerDialog endTimePicker = new TimePickerDialog(EventProfileActivity.this,
                        timeSetListener, scheduleEvent.getEndHour(), scheduleEvent.getEndMinutes(), true);

                endTimePicker.show();

            }
        });

    }

    public void validateHours() {

        if (scheduleEvent.areHoursValid() == false) {
            errorTextView.setVisibility(View.VISIBLE);
            btnCommitChanges.setEnabled(false);
            btnCommitChanges.setBackgroundColor(ContextCompat.getColor(this, R.color.gray));
        } else {
            errorTextView.setVisibility(View.INVISIBLE);
            btnCommitChanges.setEnabled(true);
            btnCommitChanges.setBackgroundColor(ContextCompat.getColor(this, R.color.bestColor));
        }

    }

}
