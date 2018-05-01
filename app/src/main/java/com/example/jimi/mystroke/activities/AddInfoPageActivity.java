package com.example.jimi.mystroke.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.Globals;
import com.example.jimi.mystroke.R;
import com.example.jimi.mystroke.models.HelpPage;
import com.example.jimi.mystroke.tasks.AsyncResponse;
import com.example.jimi.mystroke.tasks.GetHelpPagesByIds;
import com.example.jimi.mystroke.tasks.RecordsToAppDatabaseTask;

import java.util.List;

public class AddInfoPageActivity extends AppCompatActivity implements AsyncResponse {
    private Button addButton;
    private EditText contentText;
    private EditText titleText;
    private Spinner parentSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.activity_add_info_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        addButton = findViewById(R.id.addInfoPageButton);
        View view = findViewById(R.id.addInfoPageForm);
        contentText = view.findViewById(R.id.contentEditText);
        titleText = view.findViewById(R.id.setInfoTitle).findViewById(R.id.editValueText);
        parentSpinner = view.findViewById(R.id.infoParentSpinner).findViewById(R.id.spinner);
        new GetHelpPagesByIds(AppDatabase.getDatabase(getApplicationContext()), this).execute();
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


    protected void onAddInfoPageButtonClick(View view) {
        String title = titleText.getText().toString();
        if(title.length() > 0) {
            String content = contentText.getText().toString();
            HelpPage parent = (HelpPage) parentSpinner.getSelectedItem();
            HelpPage helpPage = new HelpPage(content, title, parent.getId());
            new RecordsToAppDatabaseTask("help_page", getApplicationContext()).execute(helpPage);
        } else {
            //TODO error message
        }
    }

    private void itemsToListView(ArrayAdapter adapter) {
        parentSpinner.setAdapter(adapter);
    }

    @Override
    public void respond(int var, Object... objects) {
        switch (var) {
            case GetHelpPagesByIds.var:
                List<HelpPage> helpPages = (List<HelpPage>) objects[0];
                itemsToListView(new ArrayAdapter<HelpPage>(this, R.layout.support_simple_spinner_dropdown_item, helpPages.toArray(new HelpPage[0])));
                break;
        }
    }

}
