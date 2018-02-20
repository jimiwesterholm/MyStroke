package com.example.jimi.mystroke.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.R;
import com.example.jimi.mystroke.models.Exercise;
import com.example.jimi.mystroke.models.ExerciseSection;
import com.example.jimi.mystroke.models.Imagery;
import com.example.jimi.mystroke.models.PatientListExercise;
import com.example.jimi.mystroke.tasks.AsyncResponse;
import com.example.jimi.mystroke.tasks.GetSectionExercisesExceptTask;
import com.example.jimi.mystroke.tasks.GetExercisesBySectionTask;
import com.example.jimi.mystroke.tasks.GetImageriesTask;
import com.example.jimi.mystroke.tasks.GetPatientListExercisesTask;
import com.example.jimi.mystroke.tasks.GetSectionsTask;

import java.util.ArrayList;
import java.util.List;

public class AddToListActivity extends AppCompatActivity implements AsyncResponse, AdapterView.OnItemSelectedListener {
    private Toolbar toolbar;
    private String chosenSection;
    private boolean imagerySelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new GetSectionsTask(getApplicationContext(), this).execute();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_list);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void itemsToListView(Spinner spinner, ArrayAdapter adapter) {
        spinner.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_def, menu);
        return true;
    }

    @Override
    public void respond(int var, Object... objects) {
        switch (var) {  //TODO: only show items not in patients' lists
            case GetSectionsTask.var:
                List<ExerciseSection> exerciseSections = (List<ExerciseSection>) objects[0];
                itemsToListView((Spinner) findViewById(R.id.chooseSectionSpinner), new ArrayAdapter<ExerciseSection>(this, R.layout.sample_list_element_view, exerciseSections.toArray(new ExerciseSection[0])));
                break;
            case GetImageriesTask.var:
                List<Imagery> imageries = (List<Imagery>) objects[0];
                itemsToListView((Spinner) findViewById(R.id.chooseItemSpinner), new ArrayAdapter<Imagery>(this, R.layout.sample_list_element_view, imageries.toArray(new Imagery[0])));
                break;
            case GetExercisesBySectionTask.var:
                List<Exercise> exercises = (List<Exercise>) objects[0];
                itemsToListView((Spinner) findViewById(R.id.chooseItemSpinner), new ArrayAdapter<Exercise>(this, R.layout.sample_list_element_view, exercises.toArray(new Exercise[0])));
                break;
            case GetPatientListExercisesTask.var:
                List<PatientListExercise> patientListExercises = (List<PatientListExercise>) objects[0];
                int[] exerciseIDs = new int[patientListExercises.size()];
                for (int i = 0; i < patientListExercises.size(); i++) {
                    exerciseIDs[i] = patientListExercises.get(i).getEID();
                }
                new GetSectionExercisesExceptTask(this, exerciseIDs, chosenSection, AppDatabase.getDatabase(getApplicationContext())).execute();
                break;
            case GetSectionExercisesExceptTask.var:
                List<Exercise> results = (List<Exercise>) objects[0];
                itemsToListView((Spinner) findViewById(R.id.chooseItemSpinner), new ArrayAdapter<Exercise>(this, R.layout.sample_list_element_view, results.toArray(new Exercise[0])));
                break;
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.chooseSectionSpinner:
                //Reset other spinners
                ExerciseSection section = (ExerciseSection) parent.getItemAtPosition(position);
                chosenSection = section.getName();
                if(section.getName().equals("Imageries")) {
                    new GetImageriesTask(getApplicationContext(), this);
                } else {
                    new GetExercisesBySectionTask(AppDatabase.getDatabase(getApplicationContext()), this, section.getName());
                }
                break;
            case R.id.chooseItemSpinner:
                break;
            case R.id.choosePatientSpinner:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
