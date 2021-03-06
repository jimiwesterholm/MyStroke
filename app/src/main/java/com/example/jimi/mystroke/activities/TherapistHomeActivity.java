package com.example.jimi.mystroke.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import com.example.jimi.mystroke.R;
import com.example.jimi.mystroke.models.Patient;

public class TherapistHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_therapist_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    /*
    Don't allow back button TODO uncomment
     */
    /*@Override
    public void onBackPressed() {
    }*/

    public void buttonPress(View view) {
        Intent intent;
        switch (view.getId()) {
            /*
            TODO read up on the links below, change all intents etc. to match
            https://stackoverflow.com/questions/10347184/difference-and-when-to-use-getapplication-getapplicationcontext-getbasecon
            https://qph.ec.quoracdn.net/main-qimg-2fed965d5a2ce6a67bfe24e0042a345d      THIS IS SIMPLE + GOOD, FOLLOW!
            https://www.quora.com/What-is-the-difference-between-getBaseContext-getContext-and-this
            https://stackoverflow.com/questions/10641144/difference-between-getcontext-getapplicationcontext-getbasecontext-and/15175006
             */
            case (R.id.buttonExercises):
                //intent = new Intent(this, AddExerciseActivity.class);    //TODO change, add actual path to this
                intent = new Intent(this, ViewExerciseSectionsActivity.class);
                break;
            case (R.id.buttonChat):
                intent = new Intent(this, GenerateRegisterCodeActivity.class);
                break;
            case (R.id.buttonPatient):
                intent = new Intent(this, ViewPatientsActivity.class);
                break;
            case (R.id.buttonAssessment):
                intent = new Intent(this, AssessmentActivity.class);
                break;
            case (R.id.buttonProfile):
                intent = new Intent(this, ProfileActivity.class);
                //intent = new Intent(this, ViewTherapistsActivity.class);
                break;
            case (R.id.buttonHelp):
                //intent = new Intent(this, HelpActivity.class);
                intent = new Intent(this, AddInfoPageActivity.class);
                break;
            case (R.id.buttonTherapists):
                intent = new Intent(this, ViewTherapistsActivity.class);
                break;
            default:
                return;
        }
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_def, menu);
        return true;
    }

}
