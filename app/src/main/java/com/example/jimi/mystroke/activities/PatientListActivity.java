package com.example.jimi.mystroke.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.R;
import com.example.jimi.mystroke.models.Exercise;
import com.example.jimi.mystroke.models.Imagery;
import com.example.jimi.mystroke.models.PatientListExercise;
import com.example.jimi.mystroke.models.PatientListImagery;
import com.example.jimi.mystroke.tasks.AsyncResponse;
import com.example.jimi.mystroke.tasks.DeletePatientListExerciseTask;
import com.example.jimi.mystroke.tasks.DeletePatientListImageryTask;
import com.example.jimi.mystroke.tasks.GetPatientListExercisesTask;
import com.example.jimi.mystroke.tasks.GetPatientListImageriesTask;

import java.util.List;

public class PatientListActivity extends AppCompatActivity implements AsyncResponse {
    private ListView list;
    private EditText search;
    private TextView title;
    private int pId;
    private PatientListExercise exercise;
    private PatientListImagery imagery;
    private boolean imageryOn;
    private Button switchButton;
    private List<PatientListImagery> patientImageries;
    private List<PatientListExercise> patientListExercises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);
        pId = getIntent().getExtras().getInt("EXTRA_PATIENT_ID");
        list = findViewById(R.id.list);
        new GetPatientListExercisesTask(this, pId, AppDatabase.getDatabase(getApplicationContext())).execute();
        new GetPatientListImageriesTask(this, pId, AppDatabase.getDatabase(getApplicationContext())).execute();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        search = findViewById(R.id.searchEditText);
        title = findViewById(R.id.titleText);
        title.setText(R.string.exercise_list);
    }

    private void itemsToListView(ArrayAdapter adapter, AdapterView.OnItemClickListener listener) {
        list.setAdapter(adapter);
        list.setOnItemClickListener(listener);
        search.addTextChangedListener(new SearchWatcher(adapter));
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
            case GetPatientListImageriesTask.var:
                patientImageries = (List<PatientListImagery>) objects[0];
                //title.setText(R.string.imagery_list);
                //itemsToListView(new ArrayAdapter<Imagery>(this, R.layout.support_simple_spinner_dropdown_item, patientImageries.toArray(new Imagery[0])), imageryClickedHandler);
                break;
            case GetPatientListExercisesTask.var:
                patientListExercises = (List<PatientListExercise>) objects[0];
                //title.setText(R.string.exercise_list);
                itemsToListView(new ArrayAdapter<PatientListExercise>(this, R.layout.support_simple_spinner_dropdown_item, patientListExercises.toArray(new PatientListExercise[0])), exerciseClickedHandler);
        }
    }

    public void onChangeListButtonClick(View view) {
        Button button = (Button) view;
        if(imageryOn) {
            title.setText(R.string.imagery_list);
            itemsToListView(new ArrayAdapter<PatientListImagery>(this, R.layout.support_simple_spinner_dropdown_item, patientImageries.toArray(new PatientListImagery[0])), imageryClickedHandler);
            button.setText(R.string.exerciseBut);
            imageryOn = false;
        } else {
            title.setText(R.string.exercise_list);
            itemsToListView(new ArrayAdapter<PatientListExercise>(this, R.layout.support_simple_spinner_dropdown_item, patientListExercises.toArray(new PatientListExercise[0])), exerciseClickedHandler);
            button.setText(R.string.imageryBut);
            imageryOn = true;
        }
    }

    public void onAddListItemButtonClick(View view) {
        Intent intent = new Intent(getApplicationContext(), AddToListActivity.class);
        intent.putExtra("EXTRA_PATIENT_ID", pId);
        startActivity(intent);
    }

    public void onDeleteListItemButtonClick(View view) {
        if(imageryOn) {
            if(imagery == null) {
                //TODO error message
                return;
            }
            new DeletePatientListImageryTask(this, AppDatabase.getDatabase(getApplicationContext())).execute(imagery);
        } else {
            if(exercise == null) {
                //TODO error message
                return;
            }
            new DeletePatientListExerciseTask(this, AppDatabase.getDatabase(getApplicationContext())).execute(exercise);
        }
    }

    private class SearchWatcher implements TextWatcher {
        private ArrayAdapter adapter;

        private SearchWatcher(ArrayAdapter adapter) {
            this.adapter = adapter;
        }

        @Override
        public void afterTextChanged(Editable arg0) {
            // TODO Auto-generated method stub
            adapter.getFilter().filter(search.getText().toString());
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1,
                                      int arg2, int arg3) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                  int arg3) {
            // TODO Auto-generated method stub
        }
    }

}
