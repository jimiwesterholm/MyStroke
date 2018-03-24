package com.example.jimi.mystroke.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioGroup;

import com.example.jimi.mystroke.R;

public class ExerciseFeedbackActivity extends AppCompatActivity {
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_feedback);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        radioGroup = findViewById(R.id.feedbackRadio);
    }

    private void submitOnClick() {
        switch(radioGroup.getCheckedRadioButtonId()) {
            case R.id.feedbackRadioBtn1:
                break;
            case R.id.feedbackRadioBtn2:
                break;
            case R.id.feedbackRadioBtn3:
                break;
            case R.id.feedbackRadioBtn4:
                break;
            case R.id.feedbackRadioBtn5:
                break;
            case -1:
                //TODO select value msg
        }
    }

    private void submit(int f) {

    }

}
