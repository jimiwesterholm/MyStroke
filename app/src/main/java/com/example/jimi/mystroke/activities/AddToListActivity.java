package com.example.jimi.mystroke.activities;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.R;
import com.example.jimi.mystroke.models.Exercise;
import com.example.jimi.mystroke.models.ExerciseSection;
import com.example.jimi.mystroke.models.Imagery;
import com.example.jimi.mystroke.models.Patient;
import com.example.jimi.mystroke.models.PatientListExercise;
import com.example.jimi.mystroke.models.PatientListImagery;
import com.example.jimi.mystroke.tasks.AsyncResponse;
import com.example.jimi.mystroke.tasks.GetPatientsTask;
import com.example.jimi.mystroke.tasks.GetSectionExercisesExceptTask;
import com.example.jimi.mystroke.tasks.GetExercisesBySectionTask;
import com.example.jimi.mystroke.tasks.GetImageriesTask;
import com.example.jimi.mystroke.tasks.GetPatientListExercisesTask;
import com.example.jimi.mystroke.tasks.GetSectionsFromIdsTask;
import com.example.jimi.mystroke.tasks.GetSectionsTask;
import com.example.jimi.mystroke.tasks.RecordsToAppDatabase;

import java.util.ArrayList;
import java.util.List;

public class AddToListActivity extends AppCompatActivity implements AsyncResponse, AdapterView.OnItemSelectedListener {
    private Toolbar toolbar;
    private String chosenSection;
    private boolean imagerySelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new GetPatientsTask(getApplicationContext(), this).execute();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_list);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void itemsToListView(Spinner spinner, ArrayAdapter adapter) {
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_def, menu);
        return true;
    }

    @Override
    public void respond(int var, Object... objects) {
        Spinner spinner = null;
        switch (var) {  //TODO: only show items not in patients' lists
            case GetSectionsTask.var:
                List<ExerciseSection> exerciseSections = (List<ExerciseSection>) objects[0];
                spinner = (Spinner) findViewById(R.id.chooseSectionSpinner);
                itemsToListView((Spinner) findViewById(R.id.chooseSectionSpinner), new ArrayAdapter<ExerciseSection>(this, R.layout.support_simple_spinner_dropdown_item, exerciseSections.toArray(new ExerciseSection[0])));
                break;
            case GetImageriesTask.var:
                List<Imagery> imageries = (List<Imagery>) objects[0];
                spinner = (Spinner) findViewById(R.id.chooseItemSpinner);
                itemsToListView((Spinner) findViewById(R.id.chooseItemSpinner), new ArrayAdapter<Imagery>(this, R.layout.support_simple_spinner_dropdown_item, imageries.toArray(new Imagery[0])));
                break;/*
            case GetExercisesBySectionTask.var:
                List<Exercise> exercises = (List<Exercise>) objects[0];
                itemsToListView((Spinner) findViewById(R.id.chooseItemSpinner), new ArrayAdapter<Exercise>(this, R.layout.sample_list_element_view, exercises.toArray(new Exercise[0])));
                break;*/
            case GetPatientListExercisesTask.var:
                List<PatientListExercise> patientListExercises = (List<PatientListExercise>) objects[0];
                int[] exerciseIDs = new int[patientListExercises.size()];
                for (int i = 0; i < patientListExercises.size(); i++) {
                    exerciseIDs[i] = patientListExercises.get(i).getEID();
                }
                new GetSectionsFromIdsTask(this, exerciseIDs, AppDatabase.getDatabase(getApplicationContext())).execute();
                //new GetSectionExercisesExceptTask(this, exerciseIDs, chosenSection, AppDatabase.getDatabase(getApplicationContext())).execute();
                break;
            case GetSectionExercisesExceptTask.var:
                List<Exercise> results = (List<Exercise>) objects[0];
                spinner = (Spinner) findViewById(R.id.chooseItemSpinner);
                itemsToListView(spinner, new ArrayAdapter<Exercise>(this, R.layout.support_simple_spinner_dropdown_item, results.toArray(new Exercise[0])));
                //Make spinner visible
                break;
            case GetSectionsFromIdsTask.var:
                List<String> sections = (List<String>) objects[0];
                sections.add(getString(R.string.imageryBut));
                spinner = (Spinner) findViewById(R.id.chooseSectionSpinner);
                itemsToListView(spinner, new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, sections.toArray(new String[0])));
                break;
            case GetPatientsTask.var:
                List<Patient> patients = (List<Patient>) objects[0];
                spinner = (Spinner) findViewById(R.id.choosePatientSpinner);
                itemsToListView(spinner, new ArrayAdapter<Patient>(this, R.layout.support_simple_spinner_dropdown_item, patients.toArray(new Patient[0])));
        }
    }

    public void onPatientButtonClicked(View view) {
        Spinner spinner = (Spinner) findViewById(R.id.choosePatientSpinner);
        Patient patient = (Patient) spinner.getSelectedItem();
        new GetPatientListExercisesTask(this, patient.getPid(), AppDatabase.getDatabase(getApplicationContext())).execute();
    }

    public void onSectionButtonClicked(View view) {
        Spinner spinner = (Spinner) findViewById(R.id.chooseSectionSpinner);
        String section = (String) spinner.getSelectedItem();
        chosenSection = section;
        if(section.equals(getString(R.string.imageryBut))) {
            new GetImageriesTask(getApplicationContext(), this).execute();
        } else {
            new GetExercisesBySectionTask(AppDatabase.getDatabase(getApplicationContext()), this, section).execute();
        }
    }

    public void onItemButtonClicked(View view) {

    }

    public void onAddButtonClicked(View view) {
        Spinner pSpinner = (Spinner) findViewById(R.id.choosePatientSpinner);
        Spinner sSpinner = (Spinner) findViewById(R.id.chooseSectionSpinner);
        Spinner iSpinner = (Spinner) findViewById(R.id.chooseItemSpinner);
        Patient patient = (Patient) pSpinner.getSelectedItem();
        if(sSpinner.getSelectedItem().equals(getString(R.string.imageryBut))) {
            //TODO sort out private key sit. - store both sqlite and mysql keys, for new objects mysql key is null - can use to determine if they've been sent to db yet!
            //ALSO when deleting, if global db id == 0, you're sorted, just delete from SQLite

            new RecordsToAppDatabase(getString(R.string.patient_list_imagery), AppDatabase.getDatabase(getApplicationContext())).execute(new PatientListImagery());
        } else {
            new RecordsToAppDatabase(getString(R.string.patient_list_exercise), )
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.chooseSectionSpinner:
                //Hide chooseItemSpinner

                /*String section = (String) parent.getItemAtPosition(position);
                chosenSection = section;
                if(section.equals(getString(R.string.imageryBut))) {
                    new GetImageriesTask(getApplicationContext(), this);
                } else {
                    new GetExercisesBySectionTask(AppDatabase.getDatabase(getApplicationContext()), this, section);
                }*/
                break;
            case R.id.chooseItemSpinner:    //Maybe no need to do anything??
                break;
            case R.id.choosePatientSpinner:
                //Reset other spinners, if needed
/*
                Patient patient = (Patient) parent.getItemAtPosition(position);
                new GetPatientListExercisesTask(this, patient.getPid(), AppDatabase.getDatabase(getApplicationContext()));*/
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
