package com.example.jimi.mystroke.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.jimi.mystroke.R;

public class AddMediaOrFinishedActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_media_or_finished);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addMedia:
                Intent intent = new Intent(this, AddExerciseMediaActivity.class);
                intent.putExtra("EXTRA_EXERCISE_ID", getIntent().getStringExtra("EXTRA_EXERCISE_ID"));
                startActivity(intent);
                break;
            case R.id.finishedButton:
                finish();
                break;
        }
    }

}
