package com.example.jimi.mystroke.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.R;
import com.example.jimi.mystroke.models.Patient;
import com.example.jimi.mystroke.models.PatientListImagery;
import com.example.jimi.mystroke.tasks.AsyncResponse;
import com.example.jimi.mystroke.tasks.GetPatientTask;
import com.example.jimi.mystroke.tasks.GetPatientsTask;

public class PatientActivity extends AppCompatActivity implements AsyncResponse {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patient);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        int pId = getIntent().getExtras().getInt("EXTRA_PATIENT_ID");
        new GetPatientTask(this, pId, getApplicationContext()).execute();
        //TODO this is obv not ok

    }

    @Override
    public void respond(int var, Object... objects) {
        Intent intent = new Intent(getApplicationContext(), PatientListActivity.class);
        Patient patient = (Patient) objects[0];
        intent.putExtra("EXTRA_PATIENT_ID", patient.getPid());
        startActivity(intent);
    }
}
