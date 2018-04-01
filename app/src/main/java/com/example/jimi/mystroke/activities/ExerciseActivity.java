package com.example.jimi.mystroke.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.Globals;
import com.example.jimi.mystroke.R;
import com.example.jimi.mystroke.databinding.ActivityExerciseBinding;
import com.example.jimi.mystroke.models.Exercise;
import com.example.jimi.mystroke.models.PatientListExercise;
import com.example.jimi.mystroke.tasks.AsyncResponse;
import com.example.jimi.mystroke.tasks.GetExerciseByIdTask;
import com.example.jimi.mystroke.tasks.GetPatientListExerciseTask;
import com.example.jimi.mystroke.tasks.GetPatientListExercisesTask;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExerciseActivity extends AppCompatActivity implements AsyncResponse{
    private Toolbar toolbar;
    private Exercise exercise;
    private ActivityExerciseBinding binding;

    // TODO if youtube: https://stackoverflow.com/questions/574195/android-youtube-app-play-video-intent

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_exercise);
        new GetExerciseByIdTask(AppDatabase.getDatabase(getApplicationContext()), this, getIntent().getStringExtra("EXTRA_EXERCISE_ID")).execute();


        binding.setVideoButtonOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoButtonOnClick();
            }
        });
        binding.setImageButtonOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageButtonOnClick();
            }
        });
/*
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
    }

    private void imageButtonOnClick() {
    }

    //This method is from https://stackoverflow.com/questions/574195/android-youtube-app-play-video-intent - by Roger Garzon Nieto
    private void videoButtonOnClick() {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + exercise.getVideo()));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(Exercise.youTubeFromVidId(exercise.getVideo())));
        try {
            this.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            this.startActivity(webIntent);
        }
    }

    protected void onFeedbackButtonClick(View view) {
        Intent intent = new Intent(getApplicationContext(), ExerciseFeedbackActivity.class);
        intent.putExtra("EXTRA_EXERCISE_ID", exercise.getId());
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_def, menu);
        return true;
    }

    @Override
    public void respond(int var, Object... objects) {
        switch(var) {
            case GetPatientListExerciseTask.var:
                List<PatientListExercise> res = (List<PatientListExercise>) objects[0];
                PatientListExercise patientListExercise = res.get(0);
                if(patientListExercise.getMessage() != null) {
                    binding.setPatientInstruction(patientListExercise.getMessage());
                    View view = findViewById(R.id.content).findViewById(R.id.exerciseDescription);
                    view.findViewById(R.id.exerciseDivider).setVisibility(View.VISIBLE);
                    view.findViewById(R.id.patientInstructionTextView).setVisibility(View.VISIBLE);
                }
                break;
            case GetExerciseByIdTask.var:
                exercise = (Exercise) objects[0];
                binding.setDescriptionLabelText(exercise.getName());
                binding.setDescriptionText(exercise.getDescription());
                View videoView = findViewById(R.id.content).findViewById(R.id.videoImageButton);
                if(exercise.getVideo() != null) {
                    ImageView imageView = videoView.findViewById(R.id.imageView);
                    Glide.with(this).load(Exercise.thumbnailFromVidId(exercise.getVideo())).into(imageView);
                } else {
                    videoView.setVisibility(View.GONE);
                }
                if(Globals.getInstance().isLoggedAsPatient() == 1) {
                    new GetPatientListExerciseTask(this, Globals.getInstance().getPatientOb().getId(), exercise.getId(), AppDatabase.getDatabase(getApplicationContext())).execute();
                }
        }
    }

    /*
    <variable name="thumbnail" type="Drawable"/>
        <variable name="videoButtonOnClick" type="android.view.View.OnClickListener"/>
        <variable name="imageButtonOnClick" type="android.view.View.OnClickListener"/>
        <variable name="descriptionText" type="String" />
     */
}
