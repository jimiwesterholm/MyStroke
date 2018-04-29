package com.example.jimi.mystroke.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
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

public class ExerciseFeedbackActivity extends AppCompatActivity implements View.OnClickListener {
    RadioGroup radioGroup;
    private String eID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.activity_exercise_feedback);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        radioGroup = findViewById(R.id.feedbackRadio);

        findViewById(R.id.submitFeedbackbutton).setOnClickListener(this);
        eID = getIntent().getStringExtra("EXTRA_EXERCISE_ID");
        if(eID == null) {
            startActivity(new Intent(getApplicationContext(), PatientHomeActivity.class));
        }
    }

    public void onClick(View view) {
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_back:
                Intent intent;
                if(Globals.getInstance().isLoggedAsPatient() == 1) {
                    intent = new Intent(this, PatientHomeActivity.class);
                } else {
                    intent = new Intent(this, TherapistHomeActivity.class);
                }
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                return true;
            case R.id.action_log_out:
                Globals.getInstance().setUser(null);
                Globals.getInstance().setPatientOb(null);
                Globals.getInstance().setTherapistOb(null);
                Intent intent2 = new Intent(this, LoginActivity.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void submit(int f) {
        PatientAssessesExercise feedback = new PatientAssessesExercise(Globals.getInstance().getPatientOb().getId(), eID, f);
        new RecordsToAppDatabaseTask(getString(R.string.patient_assesses_exercise), getApplicationContext()).execute(feedback);
        startActivity(new Intent(getApplicationContext(), FeedbackLeftActivity.class));
        //TODO move to get result? change records to appdatabase to give result?
    }

}
