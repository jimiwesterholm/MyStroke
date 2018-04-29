package com.example.jimi.mystroke.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.Globals;
import com.example.jimi.mystroke.PasswordSalting;
import com.example.jimi.mystroke.R;
import com.example.jimi.mystroke.models.Patient;
import com.example.jimi.mystroke.models.RegisterCode;
import com.example.jimi.mystroke.models.User;
import com.example.jimi.mystroke.tasks.AsyncResponse;
import com.example.jimi.mystroke.tasks.RecordsToAppDatabaseTask;
import com.example.jimi.mystroke.tasks.FindRegisterCodeTask;

import java.util.UUID;

public class RegisterPatientActivity extends AppCompatActivity implements AsyncResponse {
    private Patient newPatient;
    private User newUser;
    private EditText emailText;
    private EditText passText;
    private EditText firstNameText;
    private EditText lastNameText;
    private EditText[] editFields = new EditText[5];
    private Button editButton;
    private Button confirmButton;
    private RegisterCode code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.activity_register_patient);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        new FindRegisterCodeTask(AppDatabase.getDatabase(getApplicationContext()), this, getIntent().getStringExtra("EXTRA_CODE")).execute();

        confirmButton = findViewById(R.id.buttonConfirm);
        View view = findViewById(R.id.editProfileForm);
        TextView label = view.findViewById(R.id.labelTextView);
        label.setText(getText(R.string.register));
        editFields[0] = view.findViewById(R.id.setFirstName).findViewById(R.id.editValueText);
        editFields[0].setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

        editFields[1] = view.findViewById(R.id.setSecondName).findViewById(R.id.editValueText);
        editFields[1].setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

        editFields[2] = view.findViewById(R.id.setUsername).findViewById(R.id.editValueText);
        editFields[2].setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS);

        View view2 = findViewById(R.id.editPasswordForm);
        editFields[3] = view2.findViewById(R.id.setPassword).findViewById(R.id.editValueText);
        editFields[3].setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        editFields[3].addTextChangedListener(new CustomWatcher());

        editFields[4] = view2.findViewById(R.id.confirmPassword).findViewById(R.id.editValueText);
        editFields[4].setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        editFields[4].addTextChangedListener(new CustomWatcher());
    }

    protected void onConfirmClick(View view) {
        View failedOn = validate();
        if(failedOn == null) {
            String salt = "AAAA";   //TODO do for real
            String uuid = UUID.randomUUID().toString();
            String uuidP = UUID.randomUUID().toString();
            String pass = saltPass(editFields[3].getText().toString());
            new RecordsToAppDatabaseTask("user", getApplicationContext()).execute(new User(uuid, editFields[2].getText().toString(), pass, salt, 0, 1, editFields[2].getText().toString(), editFields[0].getText().toString(), editFields[1].getText().toString()));
            new RecordsToAppDatabaseTask("therapist", getApplicationContext()).execute(new Patient(uuidP, uuid, code.getCreatorId(), 1));
        }
    }

    private String saltPass(String s) {
        return s;//TODO finish!
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


    private View validate() {   //TODO more, esp for email
        String fName = editFields[0].getText().toString();
        String sName = editFields[1].getText().toString();
        String email = editFields[2].getText().toString();
        String pass = editFields[3].getText().toString();
        String passConf = editFields[4].getText().toString();
        if(TextUtils.isEmpty(fName)) {
            return editFields[0];
        }
        if(TextUtils.isEmpty(sName)) {
            return editFields[1];
        }
        if(TextUtils.isEmpty(email)) {
            return editFields[2];
        }
        if(TextUtils.isEmpty(pass)) {
            return editFields[3];
        }
        if(TextUtils.isEmpty(passConf)) {
            return editFields[4];
        }
        return null;
    }

    @Override
    public void respond(int var, Object... objects) {
        switch (var) {
            case FindRegisterCodeTask.var:
                code = (RegisterCode) objects[0];
        }
    }

    private void upload(User user, Patient patient) {
        new RecordsToAppDatabaseTask("user", getApplicationContext()).execute(user);
        new RecordsToAppDatabaseTask("patient", getApplicationContext()).execute(patient);
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

    private class CustomWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if(s != editFields[3].getText() && s != editFields[4].getText()) {
                //TODO error msg
            }
        }
    }
}
