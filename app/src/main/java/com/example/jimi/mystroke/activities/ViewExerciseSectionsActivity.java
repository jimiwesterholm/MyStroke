package com.example.jimi.mystroke.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
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
        if(globals.isLoggedAsPatient() == 1) {
            Patient patient = Globals.getInstance().getPatientOb();
            if (patient != null) {
                new GetSectionsTask(getApplicationContext(), this, patient.getId()).execute();
            } else {
                new GetPatientByUserIdTask(this, Globals.getInstance().getUser().getId(), getApplicationContext()).execute();
            }
        } else {
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabAdd);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        }

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView title = findViewById(R.id.list_content).findViewById(R.id.labelTextView);
        title.setText(R.string.exerciseBut);

        //TODO: Check for alerts, indicate where
    }

    @Override
    protected void onResume() {
        Patient patient = Globals.getInstance().getPatientOb();
        if(patient != null) {
            new GetSectionsTask(getApplicationContext(), this, patient.getId()).execute();
        } else {
            new GetPatientByUserIdTask(this, Globals.getInstance().getUser().getId(), getApplicationContext()).execute();
        }
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
}
