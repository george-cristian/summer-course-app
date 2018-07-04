package com.summer_course;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.summer_course.utils.Constants;
import com.summer_course.utils.SummerCourseApplication;

import java.io.File;
import java.io.IOException;

public class SurvivalGuideActivity extends AppCompatActivity {

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survival_guide);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_arrow);

        boolean isCurrentUserValidated = ((SummerCourseApplication)getApplication()).isCurrentUserValidated();

        TextView errorTextView = findViewById(R.id.tv_error_sg);
        Button btnDownloadSG = findViewById(R.id.btn_download_sg);
        progressBar = findViewById(R.id.pb_loading_indicator);

        if (isCurrentUserValidated == false) {
            errorTextView.setVisibility(View.VISIBLE);
            btnDownloadSG.setEnabled(false);
        } else {
            errorTextView.setVisibility(View.INVISIBLE);

            btnDownloadSG.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    downloadSurvivalGuide();
                }
            });
        }

    }

    private void downloadSurvivalGuide() {
        progressBar.setVisibility(View.VISIBLE);

        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference().child("SurvivalGuide_SC18_Bucharest.pdf");

        try {
            final File localFile = File.createTempFile(Constants.SURVIVAL_GUIDE_NAME, "pdf");
            storageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(SurvivalGuideActivity.this,
                            "File downloaded succesfully", Toast.LENGTH_LONG).show();

                    Context context = SurvivalGuideActivity.this;
                    Uri uri = FileProvider.getUriForFile(context, "com.summer_course", localFile);
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(uri, "application/pdf");
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    SurvivalGuideActivity.this.startActivity(intent);
                }
            });
        } catch (IOException e) {

        }


    }
}
