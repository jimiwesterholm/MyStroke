package com.example.jimi.mystroke.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.Globals;
import com.example.jimi.mystroke.R;
import com.example.jimi.mystroke.SearchWatcher;
import com.example.jimi.mystroke.models.Exercise;
import com.example.jimi.mystroke.models.ExerciseSection;
import com.example.jimi.mystroke.models.Imagery;
import com.example.jimi.mystroke.models.Patient;
import com.example.jimi.mystroke.models.PatientListExercise;
import com.example.jimi.mystroke.models.PatientListImagery;
import com.example.jimi.mystroke.tasks.AsyncResponse;
import com.example.jimi.mystroke.tasks.GetExercisesNotFromIdsTask;
import com.example.jimi.mystroke.tasks.GetExercisesTask;
import com.example.jimi.mystroke.tasks.GetPatientListImageriesTask;
import com.example.jimi.mystroke.tasks.GetPatientsTask;
import com.example.jimi.mystroke.tasks.GetSectionExercisesExceptTask;
import com.example.jimi.mystroke.tasks.GetImageriesTask;
import com.example.jimi.mystroke.tasks.GetPatientListExercisesTask;
import com.example.jimi.mystroke.tasks.GetSectionsNotFromIdsTask;
import com.example.jimi.mystroke.tasks.GetSectionsTask;
import com.example.jimi.mystroke.tasks.RecordsToAppDatabaseTask;

import java.util.ArrayList;
import java.util.List;

public class AddToListActivity extends AppCompatActivity implements AsyncResponse, AdapterView.OnItemClickListener {
    private Toolbar toolbar;
    private Button addButton;
    private String pId;
    private ArrayAdapter adapter;
    private String[] listExerciseIds;
    private List<Exercise> exercises;
    private ListView list;
    private EditText search;
    private TextView label;
    private boolean imagerySelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_list);
        list = findViewById(R.id.list);
        new GetPatientListExercisesTask(this, pId, AppDatabase.getDatabase(getApplicationContext())).execute();

        setSupportActionBar(toolbar);
        toolbar = findViewById(R.id.toolbar);
        search = findViewById(R.id.searchEditText);
        label = findViewById(R.id.labelTextView);
        label.setText(R.string.exercise_list_label);

        pId = getIntent().getStringExtra("EXTRA_PATIENT_ID");
        addButton = findViewById(R.id.addButton);
    }

    private void itemsToListView() {
        list.setAdapter(adapter);
        list.setOnItemClickListener(this);
        search.addTextChangedListener(new SearchWatcher(adapter, search));
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_def, menu);
        return true;
    }

    @Override
    public void respond(int var, Object... objects) {
        switch (var) {  //TODO: only show items not in patients' lists
            case GetPatientListExercisesTask.var:
                List<PatientListExercise> patientListExercises = (List<PatientListExercise>) objects[0];
                String[] exerciseIDs = new String[patientListExercises.size()];
                for (int i = 0; i < patientListExercises.size(); i++) {
                    exerciseIDs[i] = patientListExercises.get(i).getExercise().getId();
                }
                listExerciseIds = exerciseIDs;
                new GetExercisesNotFromIdsTask(this, exerciseIDs, AppDatabase.getDatabase(getApplicationContext())).execute();
                break;
            case GetExercisesNotFromIdsTask.var:
                exercises = (List<Exercise>) objects[0];
        }
    }

    //TODO input validation, lots of it
    protected void onAddButtonClicked(View view) {
        Exercise exercise = (Exercise) list.getSelectedItem();
        EditText message = findViewById(R.id.guideText);
        new RecordsToAppDatabaseTask(getString(R.string.patient_list_exercise), getApplicationContext()).execute(new PatientListExercise(pId, exercise.getId(), message.getText().toString()));
        exercises.remove(exercise);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
