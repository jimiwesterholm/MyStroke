package com.example.jimi.mystroke.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

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

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void buttonPress(View view) {
        Intent intent;
        switch (view.getId()) {
            case (R.id.buttonExercises):
                intent = new Intent(getApplicationContext(), ViewExerciseSectionsActivity.class);
                break;
            case (R.id.buttonChat):
                intent = new Intent(getApplicationContext(), ChatActivity.class);
                break;
            case (R.id.buttonPatient):
                intent = new Intent(getApplicationContext(), TrackerActivity.class);
                break;
            case (R.id.buttonAssessment):
                intent = new Intent(getApplicationContext(), AssessmentActivity.class);
                break;
            case (R.id.buttonProfile):
                intent = new Intent(getApplicationContext(), ProfileActivity.class);
                break;
            case (R.id.buttonHelp):
                //intent = new Intent(getApplicationContext(), HelpActivity.class);
                intent = new Intent(getApplicationContext(), AddToListActivity.class);
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
