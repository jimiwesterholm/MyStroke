package com.example.jimi.mystroke.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
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
import com.example.jimi.mystroke.tasks.GetSectionsTask;
import com.example.jimi.mystroke.tasks.RecordsToAppDatabaseTask;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AddExerciseActivity extends AppCompatActivity implements AsyncResponse {
    private AutoCompleteTextView sectionEditText;
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
        DataBindingUtil.setContentView(this, R.layout.activity_add_exercise);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        View view = findViewById(R.id.include_edit_add);
        View sectionView = view.findViewById(R.id.section_text);
        sectionEditText = sectionView.findViewById(R.id.editValueText);
        TextView sectionLabel = sectionView.findViewById(R.id.labelView);
        sectionLabel.setText(R.string.section);

        new GetSectionsTask(getApplicationContext(), this, null).execute();

        View assessmentView = view.findViewById(R.id.assessment_spinner);
        assessmentSpinner = assessmentView.findViewById(R.id.spinner);
        TextView assessmentLabel = assessmentView.findViewById(R.id.spinnerLabel);
        assessmentLabel.setText(R.string.performance_metric);
        //TODO: Get these once assessments are more complete

        title = view.findViewById(R.id.titleInclude).findViewById(R.id.editValueText);
        description = view.findViewById(R.id.descriptionEditText);

        addButton = findViewById(R.id.add_exercise_button);
        mediaButton = findViewById(R.id.finish_button);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_back:
                Intent intent;
                intent = new Intent(this, TherapistHomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                return true;
            case R.id.action_log_out:
                Intent intent2 = new Intent(this, LoginActivity.class);
                Globals.getInstance().setUser(null);
                Globals.getInstance().setPatientOb(null);
                Globals.getInstance().setTherapistOb(null);
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void addExerciseOnClick(View view) {
        if (!verifyInput().isEmpty()) return;
        Intent intent = null;

        Assessment assessment = (Assessment) assessmentSpinner.getSelectedItem();
        String id = UUID.randomUUID().toString();
        String desc = description.getText().toString();
        String sect = sectionEditText.getText().toString();
        String name = title.getText().toString();
        String assessmentId = "70dab8d0-262f-11e8-b467-0ed5f89f718b";    //TODO get actual value

        //TODO: fix weird dropdown menu location (? - slightly futurier past Jimi)
        switch (view.getId()) {
            case (R.id.finish_button):
                Exercise exercise = new Exercise(id, description.getText().toString(), sectionEditText.getText().toString(), title.getText().toString(), assessmentId, null);
                new RecordsToAppDatabaseTask(getString(R.string.exercise), getApplicationContext()).execute(exercise);
                intent = new Intent(getApplicationContext(), AddMediaOrFinishedActivity.class);
                intent.putExtra("EXTRA_EXERCISE_ID", id);
                break;
            case (R.id.add_exercise_button):
                finish();
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
                itemsToAdapter(sectionEditText, sectionArrayAdapter);
                break;
        }
    }
}
