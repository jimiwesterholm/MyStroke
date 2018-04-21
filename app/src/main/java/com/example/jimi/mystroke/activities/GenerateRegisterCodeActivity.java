package com.example.jimi.mystroke.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.Globals;
import com.example.jimi.mystroke.R;
import com.example.jimi.mystroke.models.RegisterCode;
import com.example.jimi.mystroke.models.Therapist;
import com.example.jimi.mystroke.tasks.RecordsToAppDatabaseTask;

import java.util.UUID;

public class GenerateRegisterCodeActivity extends AppCompatActivity {
    private RadioGroup accountRadio;
    private Button copyClipBtn;
    private TextView codeTextView;
    private int access;
    private Therapist therapist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_register_code);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        accountRadio = findViewById(R.id.accountTypeRadioGroup);
        codeTextView = findViewById(R.id.codeTextView);
        copyClipBtn = findViewById(R.id.copyToClipboardButton);
        access = 0;
        Globals globals = Globals.getInstance();
        therapist = globals.getTherapistOb();
        if(therapist != null) {
            if(therapist.getPosition().equals("admin")) {
                accountRadio.setVisibility(View.VISIBLE);
                findViewById(R.id.radioInstructionText).setVisibility(View.VISIBLE);
            } else {
                accountRadio.setVisibility(View.GONE);
            }
        } else {
            //TODO find therapist
        }


    }

    private String createCode() {
        String code = UUID.randomUUID().toString();
        RegisterCode registerCode = new RegisterCode(code, access, therapist.getId());
        new RecordsToAppDatabaseTask("register_code", AppDatabase.getDatabase(getApplicationContext())).execute(registerCode);
        return code;
    }

    public void copyToClipboardOnClick(View view) {

    }

    public void generateCodeOnClick(View view) {
        codeTextView.setText(createCode());
        copyClipBtn.setVisibility(View.VISIBLE);
    }
}
