package com.example.jimi.mystroke.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.R;
import com.example.jimi.mystroke.models.Exercise;
import com.example.jimi.mystroke.tasks.AsyncResponse;
import com.example.jimi.mystroke.tasks.GetExerciseByIdTask;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExerciseActivity extends AppCompatActivity implements AsyncResponse{
    private Toolbar toolbar;


    // TODO if youtube: https://stackoverflow.com/questions/574195/android-youtube-app-play-video-intent

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new GetExerciseByIdTask(AppDatabase.getDatabase(getApplicationContext()), this, getIntent().getStringExtra("EXTRA_EXERCISE_ID")).execute();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_def, menu);
        return true;
    }

    @Override
    public void respond(int var, Object... objects) {

    }
}
