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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.jimi.mystroke.R;

import java.io.FileNotFoundException;

public class ImageConfirmActivity extends Activity {
    private int pos;
    private EditText altText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_image_confirm);
        setContentView(R.layout.content_image_confirm);
        /*
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        altText = findViewById(R.id.img_alt_text_form);
        String uriString = getIntent().getStringExtra("EXTRA_URI");
        pos = getIntent().getIntExtra("EXTRA_LIST_POS", -1);
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
        Button delBut = findViewById(R.id.delete_image_button);
        if(pos >= 0) {
            altText.setText(getIntent().getStringExtra("EXTRA_ALT_TEXT"));
            delBut.setVisibility(View.VISIBLE);
        } else {
            delBut.setVisibility(View.GONE);
        }
    }

    protected void onCancelClick(View view) {
        Intent intent = new Intent();
        setResult(Activity.RESULT_CANCELED, intent);
        finish();
    }

    protected void onConfirmClick(View view) {
        Intent intent = new Intent();
        intent.putExtra("EXTRA_ALT_TEXT", altText.getText().toString());
        intent.putExtra("EXTRA_LIST_POS", pos);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    protected void onDeleteClick(View view) {
        Intent intent = new Intent();
        intent.putExtra("EXTRA_TO_DELETE", true);
        intent.putExtra("EXTRA_LIST_POS", pos);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

}