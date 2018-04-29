package com.example.jimi.mystroke.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.Globals;
import com.example.jimi.mystroke.R;
import com.example.jimi.mystroke.models.User;
import com.example.jimi.mystroke.tasks.RecordsToAppDatabaseTask;

public class ProfileActivity extends AppCompatActivity {
    //private Toolbar toolbar;
    private EditText[] editFields = new EditText[3];
    private String[] savedValues = new String[3];
    private Button editButton;
    private Button confirmButton;
    private Button passButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.activity_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Globals globals = Globals.getInstance();
        View view = findViewById(R.id.content_profile);
        passButton = findViewById(R.id.buttonPassword);
        editButton = findViewById(R.id.buttonCancel);
        confirmButton = findViewById(R.id.buttonConfirmChanges);
        view = view.findViewById(R.id.editProfileForm);
        TextView username = view.findViewById(R.id.labelTextView);
        username.setText(globals.getUser().getUsername());
        editFields[0] = view.findViewById(R.id.setFirstName).findViewById(R.id.editValueText);
        editFields[0].setText(Globals.getInstance().getUser().getFirstName());
        editFields[0].setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        editFields[0].addTextChangedListener(new ChangeWatcher());

        editFields[1] = view.findViewById(R.id.setSecondName).findViewById(R.id.editValueText);
        editFields[1].setText(Globals.getInstance().getUser().getLastName());
        editFields[1].setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        editFields[1].addTextChangedListener(new ChangeWatcher());

        editFields[2] = view.findViewById(R.id.setUsername).findViewById(R.id.editValueText);
        editFields[2].setText(Globals.getInstance().getUser().getEmail());
        editFields[2].setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS);
        editFields[2].addTextChangedListener(new ChangeWatcher());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_def, menu);
        return true;
    }


    public void cancelButtonOnClick(View view) {
        /*
        editFields[0].setInputType(InputType.TYPE_NULL);
        editFields[1].setInputType(InputType.TYPE_NULL);
        editFields[2].setInputType(InputType.TYPE_NULL);
        for (EditText editText : editFields) editText.setEnabled(false);
        */
        confirmButton.setVisibility(View.GONE);
        editButton.setVisibility(View.GONE);

        //Reset input
        editFields[0].setText(Globals.getInstance().getUser().getFirstName());
        editFields[1].setText(Globals.getInstance().getUser().getLastName());
        editFields[2].setText(Globals.getInstance().getUser().getEmail());
    }

    public void confirmButtonOnClick(View view) {
        User user = Globals.getInstance().getUser();
        //user.setUsername(editFields[0].getText().toString());
        savedValues[0] = editFields[0].getText().toString();
        user.setFirstName(savedValues[0]);
        savedValues[1] = editFields[1].getText().toString();
        user.setLastName(savedValues[1]);
        savedValues[2] = editFields[2].getText().toString();
        user.setEmail(savedValues[2]);
        Globals.getInstance().setUser(user);

        RecordsToAppDatabaseTask recordsToAppDatabaseTask = new RecordsToAppDatabaseTask("user", getApplicationContext());
        recordsToAppDatabaseTask.execute(user);

        cancelButtonOnClick(editButton);
    }

    private void textChanged() {
        if(textSameAsSaved()) {
            confirmButton.setVisibility(View.GONE);
            editButton.setVisibility(View.GONE);
        } else {
            editButton.setVisibility(View.VISIBLE);
            confirmButton.setVisibility(View.VISIBLE);
        }
    }

    private boolean textSameAsSaved() {
        if(editFields[0].getText().toString().equals(savedValues[0]) && editFields[1].getText().toString().equals(savedValues[1]) && editFields[2].getText().toString().equals(savedValues[2])) {
            return true;
        }
        return false;
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


    private class ChangeWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            textChanged();
        }
    }
}
