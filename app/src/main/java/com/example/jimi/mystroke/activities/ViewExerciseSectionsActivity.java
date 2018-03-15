package com.example.jimi.mystroke.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.Globals;
import com.example.jimi.mystroke.R;
import com.example.jimi.mystroke.models.Exercise;
import com.example.jimi.mystroke.models.ExerciseSection;
import com.example.jimi.mystroke.tasks.AsyncResponse;
import com.example.jimi.mystroke.tasks.GetExercisesBySectionTask;
import com.example.jimi.mystroke.tasks.GetSectionsTask;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static android.content.ContentValues.TAG;

public class ViewExerciseSectionsActivity extends AppCompatActivity implements AsyncResponse {
    private Toolbar toolbar;

    //TODO: Convert from arrayadapter/listview to recyclerview?
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new GetSectionsTask(getApplicationContext(), this, Globals.getInstance().getUser().getPatientOb().getPid()).execute();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView title = findViewById(R.id.titleText);
        title.setText(R.string.exerciseBut);

        //TODO: Check for alerts, indicate where
    }

    @Override
    protected void onResume() {
        GetSectionsTask gst = new GetSectionsTask(getApplicationContext(), this, Globals.getInstance().getUser().getPatientOb().getPid());
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
        ArrayAdapter<ExerciseSection> adapter = new ArrayAdapter<ExerciseSection>(this, R.layout.sample_list_element_view, items.toArray(new ExerciseSection[0]));
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
        itemsToListView((List<ExerciseSection>) objects[0], mMessageClickedHandler);
    }
}
