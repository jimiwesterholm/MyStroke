package com.example.jimi.mystroke.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.Globals;
import com.example.jimi.mystroke.R;
import com.example.jimi.mystroke.models.Patient;
import com.example.jimi.mystroke.tasks.AsyncResponse;
import com.example.jimi.mystroke.tasks.GetPatientByUserIdTask;

import java.util.List;

public class TrackerActivity extends AppCompatActivity implements AsyncResponse {
    private Toolbar toolbar;
    private Patient patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker);
        /*toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        patient = Globals.getInstance().getPatientOb();
        if(patient == null) {
            new GetPatientByUserIdTask(this, Globals.getInstance().getUser().getId(), getApplicationContext()).execute();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_def, menu);
        return true;
    }

    @Override
    public void respond(int var, Object... objects) {
        switch (var) {
            case GetPatientByUserIdTask.var:
                patient = (Patient) objects[0];
                Globals.getInstance().setPatientOb(patient);
                break;
        }
    }
}
