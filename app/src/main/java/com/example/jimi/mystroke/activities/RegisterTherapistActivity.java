package com.example.jimi.mystroke.activities;

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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.Globals;
import com.example.jimi.mystroke.R;
import com.example.jimi.mystroke.models.Therapist;
import com.example.jimi.mystroke.models.User;
import com.example.jimi.mystroke.tasks.RecordsToAppDatabaseTask;

import java.util.UUID;

public class RegisterTherapistActivity extends AppCompatActivity {
    private EditText[] editFields = new EditText[5];
    private Button editButton;
    private Button confirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.activity_register_therapist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        confirmButton = findViewById(R.id.buttonConfirm);
        View view = findViewById(R.id.editProfileForm);
        TextView label = view.findViewById(R.id.labelTextView);
        label.setText(getText(R.string.register));
        editFields[0] = view.findViewById(R.id.setFirstName).findViewById(R.id.editValueText);
        editFields[0].setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

        editFields[1] = view.findViewById(R.id.setSecondName).findViewById(R.id.editValueText);
        editFields[1].setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

        editFields[2] = view.findViewById(R.id.setEmail).findViewById(R.id.editValueText);
        editFields[2].setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS);
        editFields[2].setHint(R.string.physio_email_hint);

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
            String uuidT = UUID.randomUUID().toString();
            String pass = saltPass(editFields[3].getText().toString());
            new RecordsToAppDatabaseTask("user", AppDatabase.getDatabase(getApplicationContext())).execute(new User(uuid, editFields[2].getText().toString(), pass, salt, 0, 1, editFields[2].getText().toString(), editFields[0].getText().toString(), editFields[1].getText().toString()));
            new RecordsToAppDatabaseTask("therapist", AppDatabase.getDatabase(getApplicationContext())).execute(new Therapist(uuidT, uuid, "therapist", 1));
        }
    }

    private String saltPass(String s) {
        return s;//TODO finish!
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
