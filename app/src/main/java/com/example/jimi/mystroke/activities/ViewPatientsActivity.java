package com.example.jimi.mystroke.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jimi.mystroke.ListLinkAdapter;
import com.example.jimi.mystroke.R;
import com.example.jimi.mystroke.models.Patient;
import com.example.jimi.mystroke.tasks.AsyncResponse;
import com.example.jimi.mystroke.tasks.GetPatientsTask;

import java.util.List;

public class ViewPatientsActivity extends AppCompatActivity implements AsyncResponse {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patients);
        new GetPatientsTask(getApplicationContext(), this).execute();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        TextView title = findViewById(R.id.list_content).findViewById(R.id.labelTextView);
        title.setText(R.string.patientTitle);

        //TODO: Check for alerts, indicate where
    }

    @Override
    protected void onResume() {
        super.onResume();

        //TODO: Convert to use recyclerview insttead of listview
        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.sample_list_element_view, sections.toArray(new String[0]));
        ListView listView = (ListView) findViewById(R.id.exercises);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(mMessageClickedHandler);*/
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
        itemsToListView((List<Patient>) objects[0], mMessageClickedHandler);
    }

}