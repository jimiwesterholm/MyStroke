package com.example.jimi.mystroke.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.jimi.mystroke.R;
import com.example.jimi.mystroke.databinding.ActivityFeedbackLeftBinding;
import com.example.jimi.mystroke.databinding.ActivityMediaSubmittedBinding;

public class MediaSubmittedActivity extends AppCompatActivity implements Button.OnClickListener {
    private ActivityMediaSubmittedBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = android.databinding.DataBindingUtil.setContentView(this, R.layout.activity_media_submitted);
        binding.setOnClick(this);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(getApplicationContext(), PatientHomeActivity.class));
    }
}
