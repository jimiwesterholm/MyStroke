package com.example.jimi.mystroke.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioGroup;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.Globals;
import com.example.jimi.mystroke.R;
import com.example.jimi.mystroke.models.PatientAssessesExercise;
import com.example.jimi.mystroke.tasks.RecordsToAppDatabaseTask;

import java.sql.Date;
import java.sql.Time;
import java.time.Instant;

public class ExerciseFeedbackActivity extends AppCompatActivity {
    RadioGroup radioGroup;
    private String eID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_feedback);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        radioGroup = findViewById(R.id.feedbackRadio);

        eID = getIntent().getStringExtra("EXTRA_EXERCISE_ID");
        if(eID == null) {
            startActivity(new Intent(getApplicationContext(), PatientHomeActivity.class));
        }
    }

    protected void submitOnClick(View view) {
        switch(radioGroup.getCheckedRadioButtonId()) {
            case R.id.feedbackRadioBtn1:
                submit(1);
                break;
            case R.id.feedbackRadioBtn2:
                submit(2);
                break;
            case R.id.feedbackRadioBtn3:
                submit(3);
                break;
            case R.id.feedbackRadioBtn4:
                submit(4);
                break;
            case R.id.feedbackRadioBtn5:
                submit(5);
                break;
            case -1:
                //TODO please select a value msg
        }
    }

    private void submit(int f) {
        PatientAssessesExercise feedback = new PatientAssessesExercise(Globals.getInstance().getPatientOb().getId(), eID, f);
        new RecordsToAppDatabaseTask(getString(R.string.patient_assesses_exercise), AppDatabase.getDatabase(getApplicationContext())).execute(feedback);
    }

}
