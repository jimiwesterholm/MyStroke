package com.example.jimi.mystroke.activities;

import android.content.Intent;
import android.databinding.DataBindingComponent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.jimi.mystroke.Globals;
import com.example.jimi.mystroke.R;
import com.example.jimi.mystroke.databinding.ActivityFeedbackLeftBinding;
import com.example.jimi.mystroke.models.Patient;

public class FeedbackLeftActivity extends AppCompatActivity implements Button.OnClickListener {
    private ActivityFeedbackLeftBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = android.databinding.DataBindingUtil.setContentView(this, R.layout.activity_feedback_left);
        binding.setOnClick(this);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
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


    @Override
    public void onClick(View v) {
        startActivity(new Intent(getApplicationContext(), PatientHomeActivity.class));
    }
}
