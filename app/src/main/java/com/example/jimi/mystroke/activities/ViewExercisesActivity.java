package com.example.jimi.mystroke.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
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
    private ListView listView;
    private Exercise selected;

    //TODO: Convert from arrayadapter/listview to recyclerview?
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_view_list);
        super.onCreate(savedInstanceState);
        listView = findViewById(R.id.list);
        if(Globals.getInstance().isLoggedAsPatient() == 1) {
            new GetExercisesBySectionAndPatientTask(AppDatabase.getDatabase(getApplicationContext()), this, getIntent().getExtras().getString("EXTRA_SECTION"), Globals.getInstance().getPatientOb().getId()).execute();
        } else {
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabAdd);
            if(Globals.getInstance().getTherapistOb().getPosition().equals("admin")) {
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        fabOnClick();
                    }
                });
            } else {
                fab.setVisibility(View.GONE);
            }
            new GetExercisesBySectionTask(AppDatabase.getDatabase(getApplicationContext()), this, getIntent().getExtras().getString("EXTRA_SECTION")).execute();
        }

        /*toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == Activity.RESULT_OK) {
                    String actionCode = data.getStringExtra("EXTRA_ACTION");
                    switch (actionCode) {
                        case ("edit"):
                            Intent intent = new Intent(this, EditExerciseActivity.class);
                            intent.putExtra("EXTRA_EXERCISE_ID", selected.getId());
                            startActivity(intent);
                            break;
                        case("view"):
                            viewExercise();
                            break;
                        default:
                            //Error message
                            break;
                    }
                } else {
                    //Smth
                }
                break;
        }
    }

    private AdapterView.OnItemClickListener mMessageClickedHandler = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id)
        {
            selected = (Exercise) parent.getAdapter().getItem(position);
            if (Globals.getInstance().isLoggedAsPatient() != 1 && Globals.getInstance().getTherapistOb().getPosition().equals("admin")) {
                Intent intent = new Intent(ViewExercisesActivity.this, ExerciseClickedOptionsActivity.class);
                startActivityForResult(intent, 1);
            } else {
                viewExercise();
            }
        }
    };

    private void fabOnClick() {
        startActivity(new Intent(this, AddExerciseActivity.class));
    }

    private void viewExercise() {
        Intent intent = new Intent(this, ExerciseActivity.class);
        intent.putExtra("EXTRA_EXERCISE_ID", selected.getId());
        startActivity(intent);
    }

    private void itemsToListView(List<Exercise> items, AdapterView.OnItemClickListener listener) {
        ListLinkAdapter<Exercise> adapter = new ListLinkAdapter<Exercise>(this, items);
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
