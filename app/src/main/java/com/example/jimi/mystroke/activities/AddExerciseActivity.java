package com.example.jimi.mystroke.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.jimi.mystroke.R;

public class AddExerciseActivity extends AppCompatActivity {

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

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
