package com.example.jimi.mystroke.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.jimi.mystroke.Globals;
import com.example.jimi.mystroke.R;
import com.example.jimi.mystroke.models.Patient;
import com.example.jimi.mystroke.models.Therapist;
import com.example.jimi.mystroke.models.User;
import com.example.jimi.mystroke.tasks.AsyncResponse;
import com.example.jimi.mystroke.tasks.GetTherapistTask;
import com.example.jimi.mystroke.tasks.GetUserTask;

public class TherapistActivity extends AppCompatActivity implements AsyncResponse{
    private String tId;
    private Therapist therapist;
    private User user;
    private TextView nameText;
    private TextView adminText;
    private TextView emailText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_therapist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tId = getIntent().getStringExtra("EXTRA_THERAPIST_ID");
        View view = findViewById(R.id.include);
        nameText = view.findViewById(R.id.descriptionLabelTextView);
        emailText = view.findViewById(R.id.emailTextView);
        adminText = view.findViewById(R.id.adminTextView);

        new GetTherapistTask(this, tId, getApplicationContext()).execute();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_def, menu);
        return true;
    }
    @Override
    public void respond(int var, Object... objects) {
        switch (var) {
            case GetUserTask.var:
                user = (User) objects[0];
                nameText.setText(user.toString());
                emailText.setText(user.getEmail());
                break;
            case GetTherapistTask.var:
                therapist = (Therapist) objects[0];
                if(therapist.getPosition().equals("admin")) {
                    adminText.setText(R.string.is_admin);
                }
                new GetUserTask(getApplicationContext(), this, therapist.getUserID()).execute();
                break;
        }
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


    protected void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.deleteTherapistButton:    //TODO start activity for result - yes/no? - then delete or do nothing
                break;
            case R.id.listButton:
                intent = new Intent(this, TherapistListActivity.class);
        }
        if(intent!=null) {
            intent.putExtra("EXTRA_THERAPIST_ID", tId);
            startActivity(intent);
        }
    }
}
