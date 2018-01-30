package com.example.jimi.mystroke.activities;

import android.app.ActionBar;
import android.app.LoaderManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.example.jimi.mystroke.R;

/**
 * Created by jimi on 12/12/2017.
 */

public class PatientHomeActivity extends AppCompatActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void buttonPress(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case (R.id.buttonExercise):
                intent = new Intent(getApplicationContext(), ViewExercisesActivity.class);
                break;
            case (R.id.buttonImagery):
                intent = new Intent(getApplicationContext(), ImageryActivity.class);
                break;
            case (R.id.buttonTracker):
                intent = new Intent(getApplicationContext(), TrackerActivity.class);
                break;
            case (R.id.buttonAssessment):
                intent = new Intent(getApplicationContext(), AssessmentActivity.class);
                break;
            case (R.id.buttonProfile):
                intent = new Intent(getApplicationContext(), ProfileActivity.class);
                break;
            case (R.id.buttonHelp):
                intent = new Intent(getApplicationContext(), HelpActivity.class);
                break;
            default:
                return;
        }
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_def, menu);
        return true;
    }
}
