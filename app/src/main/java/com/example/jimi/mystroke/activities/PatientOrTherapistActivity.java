package com.example.jimi.mystroke.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.jimi.mystroke.Globals;
import com.example.jimi.mystroke.R;

public class PatientOrTherapistActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_or_therapist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void onPatientClick(View view) {
        //Intent intent = new Intent(getApplicationContext(), PatientHomeActivity.class);
        Intent intent = new Intent(getApplicationContext(), AddExerciseMediaActivity.class);
        Globals.getInstance().setLoggedAsPatient(1);
        startActivity(intent);
    }

    public void onTherapistClick(View view) {
        Intent intent = new Intent(getApplicationContext(), TherapistHomeActivity.class);
        Globals.getInstance().setLoggedAsPatient(0);
        startActivity(intent);
    }

}
