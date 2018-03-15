package com.example.jimi.mystroke.activities;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.example.jimi.mystroke.R;

import java.io.FileNotFoundException;

public class ImageConfirmActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_confirm);/*
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        String uriString = getIntent().getStringExtra("EXTRA_EXERCISE_ID");
        if(uriString != null) {
            Bitmap image = null;
            try {
                image = BitmapFactory.decodeStream(getContentResolver().openInputStream(Uri.parse(uriString)));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return; //?
            }
            if(image != null) {
                ImageView imageView = findViewById(R.id.image_confirm_view);
                imageView.setImageBitmap(image);
            }
        }
    }

    protected void onCancelClick(View view) {

    }

    protected void onConfirmClick(View view) {

    }

}
