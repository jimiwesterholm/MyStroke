package com.example.jimi.mystroke.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.R;
import com.example.jimi.mystroke.daos.ExerciseDao;
import com.example.jimi.mystroke.models.Exercise;
import com.example.jimi.mystroke.tasks.AsyncResponse;
import com.example.jimi.mystroke.tasks.GetExercisesBySectionTask;
import com.example.jimi.mystroke.tasks.GetSectionsTask;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static android.content.ContentValues.TAG;

public class ViewExercisesActivity extends AppCompatActivity implements AsyncResponse {
    private Toolbar toolbar;

    //TODO: Convert from arrayadapter/listview to recyclerview?
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        GetSectionsTask gst = new GetSectionsTask(getApplicationContext(), this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_exercises);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //TODO: Check for alerts, indicate where
    }

    @Override
    protected void onResume() {
        GetSectionsTask gst = new GetSectionsTask(getApplicationContext(), this);
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
            String section = (String) parent.getAdapter().getItem(position);

            if(section.equals("Imageries")) {

            } else {

            }

            //TODO: convert to AsyncTask
            GetExercisesBySectionTask gest = new GetExercisesBySectionTask(AppDatabase.getDatabase(getApplicationContext()), section);
            Future<List<Exercise>> future = Executors.newSingleThreadExecutor().submit(gest);
            ListView listView = (ListView) findViewById(R.id.exercises);
            List<Exercise> exercises = null;
            try {
                do {
                    exercises = future.get();
                } while (!future.isDone());
            } catch (Exception ex) {
                Log.e(TAG, ex.getMessage());
            }
            //TODO: asynctask with loading bar, NOT that^ shite
            //https://stackoverflow.com/questions/12575068/how-to-get-the-result-of-onpostexecute-to-main-activity-because-asynctask-is-a

            ArrayAdapter<Exercise> adapter = new ArrayAdapter<Exercise>(getApplicationContext(), R.layout.sample_list_element_view, exercises.toArray(new Exercise[0]));
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(exerciseClickedHandler);
        }
    };

    private AdapterView.OnItemClickListener exerciseClickedHandler = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            Exercise clicked = (Exercise) parent.getAdapter().getItem(position);
            Intent intent = new Intent(getBaseContext(), ExerciseActivity.class);
            intent.putExtra("EXTRA_EXERCISE_ID", clicked.getEid());
            startActivity(intent);
        }
    };

    private void itemsToListView(List<String> items, AdapterView.OnItemClickListener listener) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.sample_list_element_view, items.toArray(new String[0]));
        ListView listView = (ListView) findViewById(R.id.exercises);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(listener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_def, menu);
        return true;
    }

    @Override
    public void respond(Object... objects) {
        itemsToListView((List<String>) objects[0], mMessageClickedHandler);
    }
}
