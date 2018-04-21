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
import com.example.jimi.mystroke.models.Therapist;
import com.example.jimi.mystroke.tasks.AsyncResponse;
import com.example.jimi.mystroke.tasks.GetTherapistTask;
import com.example.jimi.mystroke.tasks.GetTherapistsTask;
import com.example.jimi.mystroke.tasks.GetTherapistsWithUsersTask;

import java.util.List;

public class ViewTherapistsActivity extends AppCompatActivity implements AsyncResponse {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_therapists);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        new GetTherapistsWithUsersTask(this, getApplicationContext()).execute();
        View view = findViewById(R.id.list_content);
        TextView title = view.findViewById(R.id.labelTextView);
        title.setText(R.string.patientTitle);
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
            Therapist selected = (Therapist) parent.getAdapter().getItem(position);
            Intent intent = new Intent(getBaseContext(), TherapistActivity.class);
            intent.putExtra("EXTRA_THERAPIST_ID", selected.getId());
            startActivity(intent);
        }
    };

    private void itemsToListView(List<Therapist> items, AdapterView.OnItemClickListener listener) {
        ListLinkAdapter<Therapist> adapter = new ListLinkAdapter<Therapist>(this, items);
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
        itemsToListView((List<Therapist>) objects[0], mMessageClickedHandler);
    }

}
