package com.example.jimi.mystroke.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.Globals;
import com.example.jimi.mystroke.R;
import com.example.jimi.mystroke.models.RegisterCode;
import com.example.jimi.mystroke.tasks.AsyncResponse;
import com.example.jimi.mystroke.tasks.FindRegisterCodeTask;

public class RegisterTherapistEnterCodeActivity extends AppCompatActivity implements AsyncResponse {
    private EditText codeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_therapist_enter_code);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        codeEditText = findViewById(R.id.enterCodeEditText);
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

    protected  void onEnterCodeButtonClick(View view) {
        String code = codeEditText.getText().toString();
        if(validate(code)) {
            new FindRegisterCodeTask(AppDatabase.getDatabase(getApplicationContext()), this, code).execute();
        } else {
            //error msg
        }
    }

    private boolean validate(String code) {
        return code.length()==36;
    }

    @Override
    public void respond(int var, Object... objects) {
        switch (var) {
            case FindRegisterCodeTask.var:
                RegisterCode code = (RegisterCode) objects[0];
                if(code != null && System.currentTimeMillis() < code.getExpires()) {
                    Intent intent;
                    if (code.getAccess() == 0) {
                        intent = new Intent(this, RegisterPatientActivity.class);
                    } else {
                        intent = new Intent(this, RegisterTherapistActivity.class);
                    }
                    intent.putExtra("EXTRA_CODE", code.getId());
                    startActivity(intent);
                } else {
                    //TODO errors
                }
                break;
        }
    }
}
