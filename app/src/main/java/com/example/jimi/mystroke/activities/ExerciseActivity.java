package com.example.jimi.mystroke.activities;

import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.R;
import com.example.jimi.mystroke.databinding.ExerciseBinding;
import com.example.jimi.mystroke.models.Exercise;
import com.example.jimi.mystroke.tasks.AsyncResponse;
import com.example.jimi.mystroke.tasks.GetExerciseByIdTask;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExerciseActivity extends AppCompatActivity implements AsyncResponse{
    private Toolbar toolbar;
    private Exercise exercise;
    private ExerciseBinding binding;

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

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void imageButtonOnClick() {

    }

    private void videoButtonOnClick() {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_def, menu);
        return true;
    }

    @Override
    public void respond(int var, Object... objects) {
        switch(var) {
            case GetExerciseByIdTask.var:
                exercise = (Exercise) objects[0];
                binding.setDescriptionText(exercise.getDescription());
                ImageView imageView = findViewById(R.id.content).findViewById(R.id.videoImageButton).findViewById(R.id.imageView);
                Glide.with(this).load(Exercise.youTubeFromVidId(exercise.getVideo())).into(imageView);
        }
    }

    /*
    <variable name="thumbnail" type="Drawable"/>
        <variable name="videoButtonOnClick" type="android.view.View.OnClickListener"/>
        <variable name="imageButtonOnClick" type="android.view.View.OnClickListener"/>
        <variable name="descriptionText" type="String" />
     */
}
