package com.example.jimi.mystroke.activities;

import android.content.Intent;
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
import com.example.jimi.mystroke.models.Exercise;
import com.example.jimi.mystroke.tasks.AsyncResponse;
import com.example.jimi.mystroke.tasks.GetExercisesBySectionAndPatientTask;
import com.example.jimi.mystroke.tasks.GetExercisesBySectionTask;

import java.util.List;

public class ViewExercisesActivity extends AppCompatActivity implements AsyncResponse {
    private Toolbar toolbar;

    //TODO: Convert from arrayadapter/listview to recyclerview?
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(Globals.getInstance().isLoggedAsPatient() == 1) {
            new GetExercisesBySectionAndPatientTask(AppDatabase.getDatabase(getApplicationContext()), this, getIntent().getExtras().getString("EXTRA_SECTION"), Globals.getInstance().getPatientOb().getId()).execute();
        } else {
            new GetExercisesBySectionTask(AppDatabase.getDatabase(getApplicationContext()), this, getIntent().getExtras().getString("EXTRA_SECTION")).execute();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView title = findViewById(R.id.list_content).findViewById(R.id.labelTextView);
        title.setText(R.string.exerciseBut);

        //TODO: Check for alerts, indicate where
    }

    @Override
    protected void onResume() {
        new GetExercisesBySectionTask(AppDatabase.getDatabase(getApplicationContext()), this, getIntent().getExtras().getString("EXTRA_SECTION")).execute();
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
            Exercise selected = (Exercise) parent.getAdapter().getItem(position);
            Intent intent = new Intent(getBaseContext(), ExerciseActivity.class);
            intent.putExtra("EXTRA_EXERCISE_ID", selected.getId());
            startActivity(intent);
        }
    };

    private void itemsToListView(List<Exercise> items, AdapterView.OnItemClickListener listener) {
        ListLinkAdapter<Exercise> adapter = new ListLinkAdapter<Exercise>(this, items);
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
            case GetExercisesBySectionAndPatientTask.var:
            case GetExercisesBySectionTask.var:
                itemsToListView((List<Exercise>) objects[0], mMessageClickedHandler);
                break;
        }
    }
}
