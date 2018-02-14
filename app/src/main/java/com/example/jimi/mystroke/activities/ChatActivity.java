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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jimi.mystroke.R;
import com.example.jimi.mystroke.tasks.AsyncResponse;

import java.util.List;

public class ChatActivity extends AppCompatActivity implements AsyncResponse {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //TODO: Task to find all messages

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView title = (TextView) findViewById(R.id.titleText);
        title.setText(R.string.title_activity_chat);
    }

    private AdapterView.OnItemClickListener mMessageClickedHandler = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            /*Exercise clicked = (Exercise) parent.getAdapter().getItem(position);
            Intent intent = new Intent(getBaseContext(), ExerciseActivity.class);
            intent.putExtra("EXTRA_EXERCISE_ID", clicked.getEid());
            startActivity(intent);*/
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
