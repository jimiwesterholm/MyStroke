package com.example.jimi.mystroke.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
import com.example.jimi.mystroke.tasks.GetExerciseByIdTask;
import com.example.jimi.mystroke.tasks.GetSectionsTask;

import java.util.ArrayList;
import java.util.List;

public class EditExerciseActivity extends AppCompatActivity implements AsyncResponse {
    //TODO just copy add exercise, except fill in values and update don't add
    private AutoCompleteTextView sectionEditText;
    private Spinner assessmentSpinner;
    private Exercise exercise;
    private List<Assessment> assessmentList;
    private ArrayAdapter<String> sectionArrayAdapter;
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
        View sectionView = view.findViewById(R.id.section_text);
        sectionEditText = sectionView.findViewById(R.id.editValueText);
        TextView sectionLabel = sectionView.findViewById(R.id.labelView);
        sectionLabel.setText(R.string.section);

        String eid = getIntent().getStringExtra("EXTRA_EXERCISE_ID");
        if(eid != null) {
            new GetExerciseByIdTask(AppDatabase.getDatabase(getApplicationContext()), this, eid).execute();
        } else {
            startActivity(new Intent(getApplicationContext(), TherapistHomeActivity.class));
        }
        new GetSectionsTask(getApplicationContext(), this, null).execute();

        View assessmentView = view.findViewById(R.id.assessment_spinner);
        assessmentSpinner = assessmentView.findViewById(R.id.spinner);
        TextView assessmentLabel = assessmentView.findViewById(R.id.spinnerLabel);
        assessmentLabel.setText(R.string.performance_metric);
        //TODO: Get these once assessments are more complete

        title = view.findViewById(R.id.titleInclude).findViewById(R.id.editValueText);
        description = view.findViewById(R.id.descriptionEditText);

        Button addButton = findViewById(R.id.add_button);
        Button mediaButton = findViewById(R.id.media_button);
        addButton.setText(R.string.update);
        mediaButton.setText(R.string.edit_media);
    }

    private void itemsToAdapter(AutoCompleteTextView textView, ArrayAdapter adapter) {
        textView.setAdapter(adapter);
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

        String exerciseSection = sectionEditText.getText().toString();
        Assessment assessment = (Assessment) assessmentSpinner.getSelectedItem();
        exercise.setSection(exerciseSection);
        //exercise.setAid(assessment.getId());
        exercise.setDescription(description.getText().toString());
        exercise.setName(title.getText().toString());
        //Exercise exercise = new Exercise(description.getText().toString(), exerciseSection.toString(), title.getText().toString(), assessment.getId());

        //TODO: remove, fix weird dropdown menu location, use actual values
        switch (view.getId()) {
            case (R.id.media_button):
                intent = new Intent(getApplicationContext(), AddExerciseMediaActivity.class);
                intent.putExtra("EXTRA_EXERCISE_ID", exercise.getId());
            case (R.id.add_button):
                //TODO change recordsToApp-- to upsert everything?
                //new RecordsToAppDatabaseTask(getString(R.string.exercise), AppDatabase.getDatabase(getApplicationContext())).execute(exercise);
        }
        if (intent != null) startActivity(intent);
    }

    @Override
    public void respond(int var, Object... objects) {
        switch (var) {
            case GetSectionsTask.var:
                List<ExerciseSection> sectionList = (List<ExerciseSection>) objects[0];
                sectionList.remove(sectionList.size()-1);
                String[] sections = new String[sectionList.size()];
                for (int i = 0; i < sectionList.size(); i++) {
                    sections[i] = sectionList.get(i).getName();
                }
                sectionArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, sections);
                itemsToAdapter(sectionEditText, sectionArrayAdapter);
                break;
            case GetExerciseByIdTask.var:
                exercise = (Exercise) objects[0];
                sectionEditText.setText(exercise.getSection());
                title.setText(exercise.getName());
                description.setText(exercise.getDescription());
        }
    }
}



