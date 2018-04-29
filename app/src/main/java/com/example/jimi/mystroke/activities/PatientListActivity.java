package com.example.jimi.mystroke.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.Globals;
import com.example.jimi.mystroke.R;
import com.example.jimi.mystroke.SearchWatcher;
import com.example.jimi.mystroke.models.PatientListExercise;
import com.example.jimi.mystroke.models.PatientListImagery;
import com.example.jimi.mystroke.tasks.AsyncResponse;
import com.example.jimi.mystroke.tasks.DeletePatientListExercisesTask;
import com.example.jimi.mystroke.tasks.DeletePatientListImageriesTask;
import com.example.jimi.mystroke.tasks.GetPatientListExercisesTask;
import com.example.jimi.mystroke.tasks.GetPatientListImageriesTask;

import java.util.List;

public class PatientListActivity extends AppCompatActivity implements AsyncResponse {
    private ListView list;
    private EditText search;
    private TextView title;
    private TextView label;
    private String pId;
    private PatientListExercise exercise;
    private PatientListImagery imagery;
    private boolean imageryOn;
    private Button switchButton;
    //private List<PatientListImagery> patientImageries;
    private List<PatientListExercise> patientExercises;
    private ArrayAdapter<PatientListExercise> exerciseArrayAdapter;
    private ArrayAdapter<PatientListImagery> imageryArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);
        pId = getIntent().getStringExtra("EXTRA_PATIENT_ID");
        list = findViewById(R.id.list);
        View view = findViewById(R.id.searchable_list);
        new GetPatientListExercisesTask(this, pId, AppDatabase.getDatabase(getApplicationContext())).execute();
        //new GetPatientListImageriesTask(this, pId, AppDatabase.getDatabase(getApplicationContext())).execute();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        search = findViewById(R.id.searchEditText);
        title = findViewById(R.id.titleText);
        label = findViewById(R.id.labelTextView);
        label.setText(R.string.exercise_list_label);
        title.setText(R.string.exercise_list);
        imageryOn = false;
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

    private void itemsToListView(ArrayAdapter adapter, AdapterView.OnItemClickListener listener) {
        list.setAdapter(adapter);
        list.setOnItemClickListener(listener);
        search.addTextChangedListener(new SearchWatcher(adapter, search));
    }

    private AdapterView.OnItemClickListener exerciseClickedHandler = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id)
        {
            exercise = (PatientListExercise) parent.getAdapter().getItem(position);
            //Change colour etc.
        }
    };

    private AdapterView.OnItemClickListener imageryClickedHandler = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id)
        {
            imagery = (PatientListImagery) parent.getAdapter().getItem(position);
            //Change colour etc.
        }
    };

    @Override
    public void respond(int var, Object... objects) {
        switch(var) {
            /*case GetPatientListImageriesTask.var:
                patientImageries = (List<PatientListImagery>) objects[0];
                imageryArrayAdapter = new ArrayAdapter<PatientListImagery>(this, R.layout.support_simple_spinner_dropdown_item, patientImageries.toArray(new PatientListImagery[0]));
                break;*/
            case GetPatientListExercisesTask.var:
                patientExercises = (List<PatientListExercise>) objects[0];
                //title.setText(R.string.exercise_list);
                exerciseArrayAdapter = new ArrayAdapter<PatientListExercise>(this, R.layout.support_simple_spinner_dropdown_item, patientExercises.toArray(new PatientListExercise[0]));
                itemsToListView(exerciseArrayAdapter, exerciseClickedHandler);
        }
    }

    /*public void onChangeListButtonClick(View view) {
        Button button = (Button) view;
        if(!imageryOn) {
            title.setText(R.string.imagery_list);
            itemsToListView(imageryArrayAdapter, imageryClickedHandler);
            button.setText(R.string.exerciseBut);
            label.setText(R.string.imageryBut);
            imageryOn = true;
        } else {
            title.setText(R.string.exercise_list);
            itemsToListView(exerciseArrayAdapter, exerciseClickedHandler);
            button.setText(R.string.imageryBut);
            label.setText(R.string.exerciseBut);
            imageryOn = false;
        }
    }*/

    public void onAddListItemButtonClick(View view) {
        Intent intent = new Intent(this, AddToListActivity.class);
        intent.putExtra("EXTRA_PATIENT_ID", pId);
        startActivity(intent);
    }

    public void onDeleteListItemButtonClick(View view) {
        /*if(imageryOn) {
            if(imagery == null) {
                //TODO error message
                return;
            }
            new DeletePatientListImageriesTask(this, AppDatabase.getDatabase(getApplicationContext())).execute(imagery);
            imageryArrayAdapter.remove(imagery);
            imagery = null;
        } else {*/
            if(exercise == null) {
                //TODO error message
                return;
            }
            new DeletePatientListExercisesTask(this, AppDatabase.getDatabase(getApplicationContext())).execute(exercise);
            exerciseArrayAdapter.remove(exercise);
            exercise = null;
        //}
    }
}