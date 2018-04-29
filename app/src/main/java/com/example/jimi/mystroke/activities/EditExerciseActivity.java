package com.example.jimi.mystroke.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.Globals;
import com.example.jimi.mystroke.R;
import com.example.jimi.mystroke.models.Assessment;
import com.example.jimi.mystroke.models.Exercise;
import com.example.jimi.mystroke.models.ExerciseSection;
import com.example.jimi.mystroke.tasks.AsyncResponse;
import com.example.jimi.mystroke.tasks.GetExerciseByIdTask;
import com.example.jimi.mystroke.tasks.GetSectionsTask;
import com.example.jimi.mystroke.tasks.RecordsToAppDatabaseTask;

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
        //exercise.setAid(assessment.getId());  TODO uncomment
        exercise.setDescription(description.getText().toString());
        exercise.setName(title.getText().toString());

        switch (view.getId()) {
            case (R.id.finish_button):
                new RecordsToAppDatabaseTask(getString(R.string.exercise), getApplicationContext()).execute(exercise);
                intent = new Intent(getApplicationContext(), AddMediaOrFinishedActivity.class);
                intent.putExtra("EXTRA_EXERCISE_ID", exercise.getId());
            case (R.id.add_exercise_button):
                finish();
        }
        if (intent != null) startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_back:
                Intent intent;
                if(Globals.getInstance().isLoggedAsPatient() == 1) {
                    intent = new Intent(this, PatientHomeActivity.class);
                } else {
                    intent = new Intent(this, TherapistHomeActivity.class);
                }
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                return true;
            case R.id.action_log_out:
                Globals.getInstance().setUser(null);
                Globals.getInstance().setPatientOb(null);
                Globals.getInstance().setTherapistOb(null);
                Intent intent2 = new Intent(this, LoginActivity.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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



