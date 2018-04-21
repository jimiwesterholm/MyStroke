package com.example.jimi.mystroke.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.PasswordSalting;
import com.example.jimi.mystroke.R;
import com.example.jimi.mystroke.models.Patient;
import com.example.jimi.mystroke.models.RegisterCode;
import com.example.jimi.mystroke.models.User;
import com.example.jimi.mystroke.tasks.RecordsToAppDatabaseTask;

public class RegisterPatientActivity extends AppCompatActivity {
    private Patient newPatient;
    private User newUser;
    private EditText emailText;
    private EditText passText;
    private EditText firstNameText;
    private EditText lastNameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_patient);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void upload(User user, Patient patient) {
        new RecordsToAppDatabaseTask("user", AppDatabase.getDatabase(getApplicationContext())).execute(user);
        new RecordsToAppDatabaseTask("patient", AppDatabase.getDatabase(getApplicationContext())).execute(patient);
    }

    private void generateObjectsFromCode(RegisterCode registerCode) {
        if(registerCode.getAccess() != 0 || registerCode.getExpires() > System.currentTimeMillis()) return;
        String[] passAndHash = PasswordSalting.hashNew(passText.getText().toString().toCharArray());
        String email = emailText.getText().toString();
        String fName = firstNameText.getText().toString();
        String sName = lastNameText.getText().toString();
        newUser = new User(email, passAndHash[1], passAndHash[0], 1, 0, email, fName, sName);
        newPatient = new Patient(newUser.getId(), registerCode.getCreatorId(), 1);
    }
}
