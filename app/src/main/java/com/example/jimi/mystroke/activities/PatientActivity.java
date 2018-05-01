package com.example.jimi.mystroke.activities;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.jimi.mystroke.tasks.GetPatientTask;
import com.example.jimi.mystroke.tasks.GetTherapistTask;
import com.example.jimi.mystroke.tasks.GetUserTask;

import org.w3c.dom.Text;

public class PatientActivity extends AppCompatActivity implements AsyncResponse {
    private String pId;
    private Patient patient;
    private TextView nameText;
    private TextView therapistText;
    private TextView emailText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patient);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        View view = findViewById(R.id.include);
        nameText = view.findViewById(R.id.descriptionLabelTextView);
        therapistText = view.findViewById(R.id.therapistNameTextView);
        emailText = view.findViewById(R.id.emailTextView);

        pId = getIntent().getStringExtra("EXTRA_PATIENT_ID");
        new GetPatientTask(this, pId, getApplicationContext()).execute();
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
        switch (var) {
            case GetPatientTask.var:
                patient = (Patient) objects[0];
                new GetUserTask(getApplicationContext(), this, patient.getUserID());
                break;
            case GetUserTask.var:
                if(nameText.getText().length() > 0) {
                    therapistText.setText(((User)objects[0]).toString());
                } else{
                    patient.setUser((User) objects[0]);
                    nameText.setText(patient.toString());
                    emailText.setText(patient.getUser().getEmail());
                }
            case GetTherapistTask.var:
                Therapist therapist = (Therapist) objects[0];
                new GetUserTask(getApplicationContext(), this, therapist.getUserID());
        }
    }

    protected void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.assessmentButton: //TODO link
                break;
            case R.id.deleteTherapistButton:
                intent = new Intent(this, ChatActivity.class);
                break;
            case R.id.listButton:
                intent = new Intent(this, PatientListActivity.class);
        }
        if(intent!=null) {
            intent.putExtra("EXTRA_PATIENT_ID", pId);
            startActivity(intent);
        }
    }
}
