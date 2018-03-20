package com.example.jimi.mystroke.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.jimi.mystroke.R;
import com.example.jimi.mystroke.models.Patient;
import com.example.jimi.mystroke.tasks.AsyncResponse;
import com.example.jimi.mystroke.tasks.GetPatientTask;

public class PatientActivity extends AppCompatActivity implements AsyncResponse {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patient);
        Toolbar toolbar = findViewById(R.id.toolbar);

        String pId = getIntent().getStringExtra("EXTRA_PATIENT_ID");
        new GetPatientTask(this, pId, getApplicationContext()).execute();
        //TODO this is obv not ok

    }

    @Override
    public void respond(int var, Object... objects) {
        Intent intent = new Intent(getApplicationContext(), PatientListActivity.class);
        Patient patient = (Patient) objects[0];
        intent.putExtra("EXTRA_PATIENT_ID", patient.getId());
        startActivity(intent);
    }
}
