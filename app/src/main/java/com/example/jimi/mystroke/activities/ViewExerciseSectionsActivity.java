package com.example.jimi.mystroke.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.Globals;
import com.example.jimi.mystroke.ListLinkAdapter;
import com.example.jimi.mystroke.R;
import com.example.jimi.mystroke.models.ExerciseSection;
import com.example.jimi.mystroke.models.Patient;
import com.example.jimi.mystroke.models.Therapist;
import com.example.jimi.mystroke.tasks.AsyncResponse;
import com.example.jimi.mystroke.tasks.GetPatientByUserIdTask;
import com.example.jimi.mystroke.tasks.GetSectionsTask;

import java.util.List;

public class ViewExerciseSectionsActivity extends AppCompatActivity implements AsyncResponse {
    private Toolbar toolbar;

    //TODO: Convert from arrayadapter/listview to recyclerview?
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list);
        Globals globals = Globals.getInstance();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabAdd);
        if(globals.isLoggedAsPatient() == 1) {
            Patient patient = globals.getPatientOb();
            fab.setVisibility(View.GONE);
            if (patient != null) {
                new GetSectionsTask(getApplicationContext(), this, patient.getId()).execute();
            } else {
                new GetPatientByUserIdTask(this, globals.getUser().getId(), getApplicationContext()).execute();
            }
        } else {
            Therapist therapist = globals.getTherapistOb();
            if(therapist != null) {
                new GetSectionsTask(this, this, null).execute();
                if(therapist.getPosition().equals("admin")) {
                    fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            fabOnClick();
                        }
                    });
                } else {
                    fab.setVisibility(View.GONE);
                }
            } else {
                //TODO find therapist
            }
        }

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView title = findViewById(R.id.list_content).findViewById(R.id.labelTextView);
        title.setText(R.string.exerciseBut);

        //TODO: Check for alerts, indicate where
    }

    @Override
    protected void onResume() {/*
        Patient patient = Globals.getInstance().getPatientOb();
        if(patient != null) {
            new GetSectionsTask(getApplicationContext(), this, patient.getId()).execute();
        } else {
            new GetPatientByUserIdTask(this, Globals.getInstance().getUser().getId(), getApplicationContext()).execute();
        }*/
        super.onResume();

        //TODO: Convert to use recyclerview instead of listview
    }

    private AdapterView.OnItemClickListener mMessageClickedHandler = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id)
        {
            ExerciseSection section = (ExerciseSection) parent.getAdapter().getItem(position);
            Intent intent;
            if(section.getName().equals("Imageries")) {
                intent = new Intent(getApplicationContext(), ViewImageriesActivity.class);
            } else {
                intent = new Intent(getApplicationContext(), ViewExercisesActivity.class);
                intent.putExtra("EXTRA_SECTION", section.getName());
            }
            startActivity(intent);
        }
    };

    private void fabOnClick() {
        startActivity(new Intent(this, AddExerciseActivity.class));
    }

    private void itemsToListView(List<ExerciseSection> items, AdapterView.OnItemClickListener listener) {
        ListLinkAdapter<ExerciseSection> adapter = new ListLinkAdapter<ExerciseSection>(this, items);
        ListView listView = findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(listener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_def, menu);
        return true;
    }

    @Override
    public void respond(int var, Object... objects) {
        switch (var) {
            case GetSectionsTask.var:
                itemsToListView((List<ExerciseSection>) objects[0], mMessageClickedHandler);
                break;
            case GetPatientByUserIdTask.var:
                Patient patient = (Patient) objects[0];
                Globals.getInstance().setPatientOb(patient);
                new GetSectionsTask(getApplicationContext(), this, patient.getId()).execute();
                break;
        }
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


}
