package com.example.jimi.mystroke.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.example.jimi.mystroke.R;

public class AddExerciseActivity extends AppCompatActivity {
    private Spinner sectionSpinner;
    private Spinner assessmentSpinner;
    private Button addButton;
    private Button mediaButton;

    /** TODO - check links, make work - will need some server side nonsense as well
     *  https://www.simplifiedcoding.net/android-upload-video-to-server-using-php/
     *  https://stackoverflow.com/questions/10991515/how-to-play-an-mp4-video-from-server-in-android
     *  https://developer.android.com/guide/topics/media/media-formats.html
     *  https://code.tutsplus.com/tutorials/streaming-video-in-android-apps--cms-19888
     *
     *  remember to check that file types can be played back (webm? good for android 4.0+
     **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        View view = findViewById(R.id.include_edit_add);
        sectionSpinner = view.findViewById(R.id.section_spinner).findViewById(R.id.spinner);
        assessmentSpinner = view.findViewById(R.id.assessment_spinner).findViewById(R.id.spinner);
        addButton = view.findViewById(R.id.add_button);
        mediaButton = view.findViewById(R.id.media_button);
    }

}
