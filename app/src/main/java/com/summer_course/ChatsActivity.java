package com.summer_course;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.summer_course.utils.Constants;
import com.summer_course.utils.SummerCourseApplication;

import java.util.List;

public class ChatsActivity extends AppCompatActivity {

    private Button btnOrganisersChat;
    private Button btnPaxChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);
        Toolbar toolbar = findViewById(R.id.toolbar_chats);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_arrow);

        boolean isCurrentUserValidated = ((SummerCourseApplication)getApplication()).isCurrentUserValidated();
        int currentUserType = ((SummerCourseApplication)getApplication()).getCurrentUserType();

        TextView errorTextView = findViewById(R.id.tv_error_chats);
        btnOrganisersChat = findViewById(R.id.btn_view_organisers_chat);
        btnPaxChat = findViewById(R.id.btn_view_pax_chat);

        this.addListenersToButtons();

        if (isCurrentUserValidated == true) {
            errorTextView.setVisibility(View.INVISIBLE);

            if (currentUserType == Constants.PARTICIPANT) {
                btnOrganisersChat.setEnabled(false);
            }
        } else {
            errorTextView.setVisibility(View.VISIBLE);
            btnOrganisersChat.setEnabled(false);
            btnPaxChat.setEnabled(false);
        }
    }

    private void addListenersToButtons() {

        btnOrganisersChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPackageExisted("com.whatsapp") == true) {
                    Intent intentWhatsApp = new Intent(Intent.ACTION_VIEW);
                    intentWhatsApp.setData(Uri.parse(Constants.ORGANISERS_CHAT_LINK));
                    intentWhatsApp.setPackage("com.whatsapp");
                    startActivity(intentWhatsApp);
                } else {
                    Toast.makeText(ChatsActivity.this, "Please install WhatsApp", Toast.LENGTH_LONG).show();
                }

            }
        });

        btnPaxChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPackageExisted("com.whatsapp") == true) {
                    Intent intentWhatsApp = new Intent(Intent.ACTION_VIEW);
                    intentWhatsApp.setData(Uri.parse(Constants.PAX_CHAT_LINK));
                    intentWhatsApp.setPackage("com.whatsapp");
                    startActivity(intentWhatsApp);
                } else {
                    Toast.makeText(ChatsActivity.this, "Please install WhatsApp", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public boolean isPackageExisted(String targetPackage){
        List<ApplicationInfo> packages;
        PackageManager pm;

        pm = getPackageManager();
        packages = pm.getInstalledApplications(0);
        for (ApplicationInfo packageInfo : packages) {
            if(packageInfo.packageName.equals(targetPackage))
                return true;
        }
        return false;
    }

}
