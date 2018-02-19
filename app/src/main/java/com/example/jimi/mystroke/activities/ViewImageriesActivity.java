package com.example.jimi.mystroke.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.R;
import com.example.jimi.mystroke.models.Exercise;
import com.example.jimi.mystroke.models.Imagery;
import com.example.jimi.mystroke.tasks.AsyncResponse;
import com.example.jimi.mystroke.tasks.GetExercisesBySectionTask;
import com.example.jimi.mystroke.tasks.GetImageriesTask;
import com.example.jimi.mystroke.tasks.GetSectionsTask;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static android.content.ContentValues.TAG;

public class ViewImageriesActivity extends AppCompatActivity implements AsyncResponse {
    private Toolbar toolbar;

    //TODO: Convert from arrayadapter/listview to recyclerview?
    //TODO: Obviously make everything imageries
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new GetImageriesTask(getApplicationContext(), this).execute();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //TODO: Check for alerts, indicate where
    }

    @Override
    protected void onResume() {
        GetImageriesTask git = new GetImageriesTask(getApplicationContext(), this);
        git.execute();
        super.onResume();

        //TODO: Convert to use recyclerview instead of listview
        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.sample_list_element_view, sections.toArray(new String[0]));
        ListView listView = (ListView) findViewById(R.id.exercises);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(mMessageClickedHandler);*/
    }

    private AdapterView.OnItemClickListener imageryClickedHandler = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            Imagery clicked = (Imagery) parent.getAdapter().getItem(position);
            Intent intent = new Intent(getBaseContext(), ImageryActivity.class);
            intent.putExtra("EXTRA_IMAGERY_ID", clicked.getImageryID());
            startActivity(intent);
        }
    };

    private void itemsToListView(List<Imagery> items, AdapterView.OnItemClickListener listener) {
        ArrayAdapter<Imagery> adapter = new ArrayAdapter<Imagery>(this, R.layout.sample_list_element_view, items);
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
        itemsToListView((List<Imagery>) objects[0], imageryClickedHandler);
    }
}
