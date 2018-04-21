package com.example.jimi.mystroke.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jimi.mystroke.ListLinkAdapter;
import com.example.jimi.mystroke.R;
import com.example.jimi.mystroke.models.Patient;
import com.example.jimi.mystroke.tasks.AsyncResponse;
import com.example.jimi.mystroke.tasks.GetPatientsByTherapistTask;

import java.util.List;

public class TherapistListActivity extends AppCompatActivity implements AsyncResponse {
    private String tId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_therapist_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tId = getIntent().getStringExtra("EXTRA_THERAPIST_ID");
        if(tId == null) {
            finish();
        }
        new GetPatientsByTherapistTask(getApplicationContext(), this, tId);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        TextView title = findViewById(R.id.list_content).findViewById(R.id.labelTextView);
        title.setText(R.string.patients);
    }

    private AdapterView.OnItemClickListener mMessageClickedHandler = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id)
        {
            Patient selected = (Patient) parent.getAdapter().getItem(position);
            Intent intent = new Intent(getBaseContext(), PatientActivity.class);
            intent.putExtra("EXTRA_PATIENT_ID", selected.getId());
            startActivity(intent);
        }
    };

    private void itemsToListView(List<Patient> items, AdapterView.OnItemClickListener listener) {
        ListLinkAdapter<Patient> adapter = new ListLinkAdapter<Patient>(this, items);
        ListView listView = findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(mMessageClickedHandler);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_def, menu);
        return true;
    }

    @Override
    public void respond(int var, Object... objects) {
        switch (var) {
            case GetPatientsByTherapistTask.var:
                itemsToListView((List<Patient>) objects[0], mMessageClickedHandler);
                break;
        }
    }
}
