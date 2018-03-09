package com.example.jimi.mystroke.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.jimi.mystroke.tasks.GetPatientListImageriesTask;
import com.example.jimi.mystroke.tasks.GetPatientsTask;
import com.example.jimi.mystroke.tasks.GetSectionExercisesExceptTask;
import com.example.jimi.mystroke.tasks.GetImageriesTask;
import com.example.jimi.mystroke.tasks.GetPatientListExercisesTask;
import com.example.jimi.mystroke.tasks.GetSectionsNotFromIdsTask;
import com.example.jimi.mystroke.tasks.GetSectionsTask;
import com.example.jimi.mystroke.tasks.RecordsToAppDatabase;

import java.util.ArrayList;
import java.util.List;

public class AddToListActivity extends AppCompatActivity implements AsyncResponse, AdapterView.OnItemSelectedListener {
    private Toolbar toolbar;
    private Spinner patientSpinner;
    private Spinner sectionSpinner;
    private Spinner itemSpinner;
    private Button addButton;
    private Button patientButton;
    private Button sectionButton;
    private Button itemButton;
    private int[] listExerciseIds;
    private List<Imagery> imageries;
    private boolean imagerySelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_list);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        patientSpinner = findViewById(R.id.choosePatientSpinner);
        sectionSpinner = findViewById(R.id.chooseSectionSpinner);
        itemSpinner = findViewById(R.id.chooseItemSpinner);

        new GetPatientsTask(getApplicationContext(), this).execute();

        addButton = findViewById(R.id.addButton);
        patientButton = findViewById(R.id.patientButton);
        sectionButton = findViewById(R.id.sectionButton);
        itemButton = findViewById(R.id.itemButton);
    }

    private void itemsToListView(Spinner spinner, ArrayAdapter adapter) {
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        spinner.setVisibility(View.VISIBLE);
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
                itemsToListView(sectionSpinner, new ArrayAdapter<ExerciseSection>(this, R.layout.support_simple_spinner_dropdown_item, exerciseSections.toArray(new ExerciseSection[0])));
                break;
            case GetImageriesTask.var://TODO change so items on list not fetched
                imageries = (List<Imagery>) objects[0];
                Patient patient = (Patient) patientSpinner.getSelectedItem();
                new GetPatientListImageriesTask(this, patient.getPid(), AppDatabase.getDatabase(getApplicationContext())).execute();
                break;
            case GetPatientListImageriesTask.var:
                List<PatientListImagery> patientListImageries = (List<PatientListImagery>) objects[0];
                List<Imagery> patientImageries = new ArrayList<Imagery>();
                for (PatientListImagery patientListImagery : patientListImageries) {
                    patientImageries.add(patientListImagery.getImagery());
                }
                imageries.removeAll(patientImageries);
                itemsToListView(itemSpinner, new ArrayAdapter<Imagery>(this, R.layout.support_simple_spinner_dropdown_item, imageries.toArray(new Imagery[0])));
                break;
            case GetPatientListExercisesTask.var:
                List<PatientListExercise> patientListExercises = (List<PatientListExercise>) objects[0];
                int[] exerciseIDs = new int[patientListExercises.size()];
                for (int i = 0; i < patientListExercises.size(); i++) {
                    exerciseIDs[i] = patientListExercises.get(i).getExercise().getSQLiteId();
                }
                listExerciseIds = exerciseIDs;
                new GetSectionsNotFromIdsTask(this, exerciseIDs, AppDatabase.getDatabase(getApplicationContext())).execute();
                break;
            case GetSectionExercisesExceptTask.var:
                List<Exercise> results = (List<Exercise>) objects[0];
                itemsToListView(itemSpinner, new ArrayAdapter<Exercise>(this, R.layout.support_simple_spinner_dropdown_item, results.toArray(new Exercise[0])));
                //Make spinner visible
                break;
            case GetSectionsNotFromIdsTask.var:
                List<String> sections = (List<String>) objects[0];
                sections.add(getString(R.string.imageryBut));
                itemsToListView(sectionSpinner, new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, sections.toArray(new String[0])));
                //new GetSectionExercisesExceptTask(this, listExerciseIds, (String) sectionSpinner.getSelectedItem(), AppDatabase.getDatabase(getApplicationContext())).execute();
                break;
            case GetPatientsTask.var:
                List<Patient> patients = (List<Patient>) objects[0];
                itemsToListView(patientSpinner, new ArrayAdapter<Patient>(this, R.layout.support_simple_spinner_dropdown_item, patients.toArray(new Patient[0])));
                int pId = getIntent().getIntExtra("EXTRA_PATIENT_ID", -1);
                if(pId != -1) {
                    for (int i = 0; i < patients.size(); i++) {
                        if(patients.get(i).getPid() == pId) {
                            patientSpinner.setSelection(i);
                            onPatientButtonClicked(patientSpinner);
                            break;
                        }
                    }
                }
        }
    }

    private void onPatientButtonClicked(View view) {
        if (patientSpinner.isEnabled()) {
            Patient patient = (Patient) patientSpinner.getSelectedItem();
            new GetPatientListExercisesTask(this, patient.getPid(), AppDatabase.getDatabase(getApplicationContext())).execute();
            sectionSpinner.setVisibility(View.VISIBLE);
            sectionButton.setVisibility(View.VISIBLE);
            patientSpinner.setEnabled(false);
            //Button text change
        } else {
            sectionSpinner.setVisibility(View.GONE);
            sectionSpinner.setEnabled(true);
            sectionButton.setVisibility(View.GONE);
            itemSpinner.setVisibility(View.GONE);
            itemSpinner.setEnabled(true);
            itemButton.setVisibility(View.GONE);
            addButton.setVisibility(View.GONE);
            patientSpinner.setEnabled(true);
        }


    }

    public void onSectionButtonClicked(View view) {
        if (sectionSpinner.isEnabled()) {
            String section = (String) sectionSpinner.getSelectedItem();
            if(section.equals(getString(R.string.imageryBut))) {
                new GetImageriesTask(getApplicationContext(), this).execute();
            } else {
                new GetSectionExercisesExceptTask(this, listExerciseIds, section, AppDatabase.getDatabase(getApplicationContext())).execute();
            }
            itemSpinner.setVisibility(View.VISIBLE);
            itemButton.setVisibility(View.VISIBLE);
            sectionSpinner.setEnabled(false);
        } else {
            itemSpinner.setVisibility(View.GONE);
            itemSpinner.setEnabled(true);
            itemButton.setVisibility(View.GONE);
            addButton.setVisibility(View.GONE);
            sectionSpinner.setEnabled(true);
        }

    }

    public void onItemButtonClicked(View view) {
        //Print out description of exercise/imagery1
        if (itemSpinner.isEnabled()) {
            addButton.setVisibility(View.VISIBLE);
            itemSpinner.setEnabled(false);
        } else {
            addButton.setVisibility(View.GONE);
            itemSpinner.setEnabled(true);
        }
    }

    //TODO input validation, lots of it
    public void onAddButtonClicked(View view) {
        Patient patient = (Patient) patientSpinner.getSelectedItem();
        if(sectionSpinner.getSelectedItem().equals(getString(R.string.imageryBut))) {
            Imagery imagery = (Imagery) itemSpinner.getSelectedItem();
            new RecordsToAppDatabase(getString(R.string.patient_list_imagery), AppDatabase.getDatabase(getApplicationContext())).execute(new PatientListImagery(patient.getPid(), imagery.getImageryID()));
        } else {
            Exercise exercise = (Exercise) itemSpinner.getSelectedItem();
            EditText message = findViewById(R.id.guideText);
            new RecordsToAppDatabase(getString(R.string.patient_list_exercise), AppDatabase.getDatabase(getApplicationContext())).execute(new PatientListExercise(patient.getPid(), exercise.getEid(), message.getText().toString()));
        }
        patientSpinner.setEnabled(true);
        sectionSpinner.setVisibility(View.GONE);
        sectionSpinner.setEnabled(true);
        sectionButton.setVisibility(View.GONE);
        itemSpinner.setVisibility(View.GONE);
        itemSpinner.setEnabled(true);
        itemButton.setVisibility(View.GONE);
        addButton.setVisibility(View.GONE);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
