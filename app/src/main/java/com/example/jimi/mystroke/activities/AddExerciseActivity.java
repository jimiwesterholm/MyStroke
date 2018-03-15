package com.example.jimi.mystroke.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.R;
import com.example.jimi.mystroke.models.Assessment;
import com.example.jimi.mystroke.models.Exercise;
import com.example.jimi.mystroke.models.ExerciseSection;
import com.example.jimi.mystroke.tasks.AsyncResponse;
import com.example.jimi.mystroke.tasks.GetSectionsTask;
import com.example.jimi.mystroke.tasks.RecordsToAppDatabaseTask;

import java.util.ArrayList;
import java.util.List;

public class AddExerciseActivity extends AppCompatActivity implements AsyncResponse, AdapterView.OnItemSelectedListener {
    private Spinner sectionSpinner;
    private Spinner assessmentSpinner;
    private Button addButton;
    private Button mediaButton;
    private List<ExerciseSection> sectionList;
    private List<Assessment> assessmentList;
    private ArrayAdapter<ExerciseSection> sectionArrayAdapter;
    private ArrayAdapter<Assessment> assessmentArrayAdapter;
    private EditText title;
    private EditText description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        View view = findViewById(R.id.include_edit_add);
        View sectionView = view.findViewById(R.id.section_spinner);
        sectionSpinner = sectionView.findViewById(R.id.spinner);
        TextView sectionLabel = sectionView.findViewById(R.id.spinnerLabel);
        sectionLabel.setText(R.string.section);

        new GetSectionsTask(getApplicationContext(), this, null).execute();

        View assessmentView = view.findViewById(R.id.assessment_spinner);
        assessmentSpinner = assessmentView.findViewById(R.id.spinner);
        TextView assessmentLabel = assessmentView.findViewById(R.id.spinnerLabel);
        assessmentLabel.setText(R.string.performance_metric);
        //TODO: Get these once assessments are more complete

        title = view.findViewById(R.id.titleInclude).findViewById(R.id.editValueText);
        description = view.findViewById(R.id.descriptionEditText);

        addButton = findViewById(R.id.add_button);
        mediaButton = findViewById(R.id.media_button);
    }

    private void itemsToListView(Spinner spinner, ArrayAdapter adapter) {
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    private List<Integer> verifyInput() {
        List<Integer> results = new ArrayList<Integer>();
        if(TextUtils.isEmpty(title.getText().toString())) {
            results.add(title.getId());
        }
        if(TextUtils.isEmpty(description.getText().toString())) {
            results.add(description.getId());
        }
        //TODO: (maybe) Spinner default values + check that they've been changed?
        return results;
    }

    protected void addExerciseOnClick(View view) {
        if (!verifyInput().isEmpty()) return;
        Intent intent = null;
        ExerciseSection exerciseSection = (ExerciseSection) sectionSpinner.getSelectedItem();
        Assessment assessment = (Assessment) assessmentSpinner.getSelectedItem();
        //Exercise exercise = new Exercise(description.getText().toString(), exerciseSection.toString(), title.getText().toString(), assessment.getAid());

        //TODO: remove, fix weird dropdown menu location, use actual values
        Exercise exercise = new Exercise(description.getText().toString(), exerciseSection.toString(), title.getText().toString(), "70dab8d0-262f-11e8-b467-0ed5f89f718b");
        switch (view.getId()) {
            case (R.id.media_button):
                exercise.setToDelete(true);
                intent = new Intent(getApplicationContext(), AddExerciseMediaActivity.class);
                intent.putExtra("EXTRA_EXERCISE_ID", exercise.getEid());
                break;
            case (R.id.add_button):
                new RecordsToAppDatabaseTask(getString(R.string.exercise), AppDatabase.getDatabase(getApplicationContext())).execute(exercise);
        }
        if (intent != null) startActivity(intent);
    }

    @Override
    public void respond(int var, Object... objects) {
        switch (var) {
            case GetSectionsTask.var:
                sectionList = (List<ExerciseSection>) objects[0];
                sectionList.remove(sectionList.size()-1);
                sectionArrayAdapter = new ArrayAdapter<ExerciseSection>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, sectionList.toArray(new ExerciseSection[0]));
                itemsToListView(sectionSpinner, sectionArrayAdapter);
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
