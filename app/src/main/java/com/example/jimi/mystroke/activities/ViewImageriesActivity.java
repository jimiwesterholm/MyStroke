package com.example.jimi.mystroke.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jimi.mystroke.Globals;
import com.example.jimi.mystroke.R;
import com.example.jimi.mystroke.models.Imagery;
import com.example.jimi.mystroke.tasks.AsyncResponse;
import com.example.jimi.mystroke.tasks.GetImageriesTask;

import java.util.List;

public class ViewImageriesActivity extends AppCompatActivity implements AsyncResponse {
    private Toolbar toolbar;

    //TODO: Convert from arrayadapter/listview to recyclerview?
    //TODO: Obviously make everything imageries
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new GetImageriesTask(getApplicationContext(), this).execute();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list);

        /*toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        TextView title = findViewById(R.id.list_content).findViewById(R.id.labelTextView);
        title.setText(R.string.imageryBut);
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
            intent.putExtra("EXTRA_IMAGERY_ID", clicked.getId());
            startActivity(intent);
        }
    };

    private void itemsToListView(List<Imagery> items, AdapterView.OnItemClickListener listener) {
        ArrayAdapter<Imagery> adapter = new ArrayAdapter<Imagery>(this, R.layout.sample_list_element_view, items);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_back:
                Intent intent;
                if(Globals.getInstance().isLoggedAsPatient() == 1) {
                    intent = new Intent(this, PatientHomeActivity.class);
                } else {
                    intent = new Intent(this, TherapistHomeActivity.class);
                }
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                return true;
            case R.id.action_log_out:
                Globals.getInstance().setUser(null);
                Globals.getInstance().setPatientOb(null);
                Globals.getInstance().setTherapistOb(null);
                Intent intent2 = new Intent(this, LoginActivity.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void respond(int var, Object... objects) {
        itemsToListView((List<Imagery>) objects[0], imageryClickedHandler);
    }
}
