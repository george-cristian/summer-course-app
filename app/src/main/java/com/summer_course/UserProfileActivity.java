package com.summer_course;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.summer_course.database_classes.User;
import com.summer_course.utils.BitmapUtils;
import com.summer_course.utils.Constants;


import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileActivity extends AppCompatActivity {

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Toolbar toolbar = findViewById(R.id.user_profile_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_arrow);

        Intent intent = getIntent();
        this.user = intent.getParcelableExtra(Constants.USER_PROFILE);
        getSupportActionBar().setTitle(user.getName());

        CircleImageView profileImage = findViewById(R.id.image_user_profile);
        profileImage.setImageBitmap(BitmapUtils.getBitmapFromString(user.getProfilePicString()));

        TextView userProfileType = findViewById(R.id.tv_user_profile_type);
        switch (user.getType()) {
            case 0:
                userProfileType.setText("Participant");
                break;
            case 1:
                userProfileType.setText("Volunteer");
                break;
            case 2:
                userProfileType.setText("Core Team Member");
                break;
            case 3:
                userProfileType.setText("Admin");
                break;
        }

        Button viewFacebookButton = findViewById(R.id.btn_view_facebook_user);
        viewFacebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String profileLink = Constants.FACEBOOK_LINK + user.getUserID();
                Intent facebookIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(profileLink));
                startActivity(facebookIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
