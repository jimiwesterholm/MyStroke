package com.example.jimi.mystroke.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.jimi.mystroke.R;

public class ExerciseClickedOptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_clicked_options);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void onButtonClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case (R.id.cancelButton):
                setResult(Activity.RESULT_CANCELED, intent);
                finish();
                break;
            case (R.id.editExerciseButton):
                intent.putExtra("EXTRA_ACTION", "edit");
                setResult(Activity.RESULT_OK, intent);
                finish();
                break;
            case (R.id.viewExerciseButton):
                intent.putExtra("EXTRA_ACTION", "view");
                setResult(Activity.RESULT_OK, intent);
                finish();
                break;
            case (R.id.deleteButton):
                intent.putExtra("EXTRA_ACTION", "delete");
                setResult(Activity.RESULT_OK, intent);
                finish();
                break;
        }
    }

}
