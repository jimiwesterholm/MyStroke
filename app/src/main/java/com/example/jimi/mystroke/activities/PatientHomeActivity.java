package com.example.jimi.mystroke.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import com.example.jimi.mystroke.R;
import com.example.jimi.mystroke.databinding.ContentPatientMainBinding;

public class PatientHomeActivity extends AppCompatActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_patient_main);
        ContentPatientMainBinding binding = DataBindingUtil.setContentView(this, R.layout.content_patient_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Set onclick listeners
        binding.setExClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exClick(v);
            }
        });
        binding.setTrClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trClick(v);
            }
        });
        binding.setMsgClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msgClick(v);
            }
        });
        binding.setInClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inClick(v);
            }
        });
        binding.setPrClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prClick(v);
            }
        });
        binding.setExClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exClick(v);
            }
        });
    }

    public void exClick(View view) {
        Intent intent = new Intent(getApplicationContext(), ViewExerciseSectionsActivity.class);
        startActivity(intent);
    }

    public void trClick(View view) {
        Intent intent = new Intent(getApplicationContext(), TrackerActivity.class);
        startActivity(intent);
    }

    public void msgClick(View view) {
        Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
        startActivity(intent);
    }

    public void inClick(View view) {
        Intent intent = new Intent(getApplicationContext(), HelpActivity.class);
        startActivity(intent);
    }

    public void prClick(View view) {
        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_def, menu);
        return true;
    }

    /*
    Don't allow back button TODO uncomment
     */
    /*@Override
    public void onBackPressed() {
    }*/
}
