package com.example.jimi.mystroke.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.R;
import com.example.jimi.mystroke.models.Exercise;
import com.example.jimi.mystroke.tasks.GetExerciseByIdTask;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExerciseActivity extends AppCompatActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        GetExerciseByIdTask gebit = new GetExerciseByIdTask(AppDatabase.getDatabase(getApplicationContext()), getIntent().getStringExtra("EXTRA_EXERCISE_ID"));
        Future<Exercise> fut = Executors.newSingleThreadExecutor().submit(gebit);
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
}
