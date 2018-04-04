package com.example.jimi.mystroke.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.example.jimi.mystroke.R;

public class RegisterTherapistEnterCodeActivity extends AppCompatActivity {
    private EditText codeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_therapist_enter_code);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        codeEditText = findViewById(R.id.enterCodeEditText);
    }

    protected  void onEnterCodeButtonClick(View view) {
        String code = codeEditText.getText().toString();
        if(validate(code)) {
            Intent intent = new Intent(this, RegisterTherapistActivity.class);
            intent.putExtra("EXTRA_CODE", code);
        } else {
            //TODO error msg
        }
    }

    private boolean validate(String code) {
        //TODO all of this
        return true;
    }

}
