package com.example.jimi.mystroke.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.jimi.mystroke.R;
import com.example.jimi.mystroke.models.PatientListImagery;

public class PatientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patient);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //TODO this is obv not ok
        Intent intent = new Intent(getApplicationContext(), PatientListActivity.class);
        intent.putExtra("EXTRA_PATIENT_ID", getIntent().getExtras().getInt("EXTRA_PATIENT_ID"));
        startActivity(intent);
    }

}
