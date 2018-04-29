package com.example.jimi.mystroke.activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
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
        View view = findViewById(R.id.generateInclude);
        accountRadio = view.findViewById(R.id.accountTypeRadioGroup);
        codeTextView = view.findViewById(R.id.codeTextView);
        copyClipBtn = view.findViewById(R.id.copyToClipboardButton);
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

    private String createCode() {
        String code = UUID.randomUUID().toString();
        RegisterCode registerCode = new RegisterCode(code, access, therapist.getId());
        new RecordsToAppDatabaseTask("register_code", getApplicationContext()).execute(registerCode);
        return code;
    }

    public void copyToClipboardOnClick(View view) {
        ClipData clipData = ClipData.newPlainText(getString(R.string.register_code), codeTextView.getText().toString());
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        clipboardManager.setPrimaryClip(clipData);

    }

    public void generateCodeOnClick(View view) {
        codeTextView.setText(createCode());
        copyClipBtn.setVisibility(View.VISIBLE);
    }
}
