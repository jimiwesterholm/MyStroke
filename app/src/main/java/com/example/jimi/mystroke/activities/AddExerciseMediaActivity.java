package com.example.jimi.mystroke.activities;

import android.app.Activity;
import android.content.ContentProvider;
import android.content.ContentResolver;
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
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.jimi.mystroke.ImageAdapter;
import com.example.jimi.mystroke.R;
import com.example.jimi.mystroke.models.Exercise;
import com.example.jimi.mystroke.tasks.AsyncResponse;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class AddExerciseMediaActivity extends AppCompatActivity implements AsyncResponse, GridView.OnItemClickListener {
    private Exercise exercise;
    private GridView gridView;
    private ImageAdapter adapter;
    private List<Bitmap> list;
    private List<String> uriList;

    /** TODO - check links, make work - will need some server side nonsense as well
     * VIDEO:
     *  https://www.simplifiedcoding.net/android-upload-video-to-server-using-php/
     *  https://stackoverflow.com/questions/10991515/how-to-play-an-mp4-video-from-server-in-android
     *  https://developer.android.com/guide/topics/media/media-formats.html
     *  https://code.tutsplus.com/tutorials/streaming-video-in-android-apps--cms-19888
     *
     *  remember to check that file types can be played back (webm? good for android 4.0+
     *
     *  IMAGE:
     *  saving:
     *  https://stackoverflow.com/questions/7593737/how-to-display-bitmap-from-internal-storage
     *  https://stackoverflow.com/questions/17674634/saving-and-reading-bitmaps-images-from-internal-memory-in-android/35827955
     *
     *  loading:
     *
     *  AUDIO(move eventually):
     *
     *
     * save state:
     * https://stackoverflow.com/questions/151777/saving-android-activity-state-using-save-instance-state
     **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise_media);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //TODO: get exercise
        String id = getIntent().getStringExtra("EXTRA_EXERCISE_ID");

        gridView = findViewById(R.id.imageGridView);
        list = new ArrayList<Bitmap>();
        list.add(BitmapFactory.decodeResource(getApplicationContext().getResources(), android.R.drawable.ic_input_add));
        itemsToGridView(list);
    }

    private void upload() {

    }

    private void itemsToGridView(List<Bitmap> items) {
        adapter = new ImageAdapter(this, items);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);
    }

    private void findImage() {
        Intent findImage = new Intent(Intent.ACTION_GET_CONTENT);
        findImage.setType("image/*");
        startActivityForResult(findImage, 1);
        return;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != 1 || resultCode != Activity.RESULT_OK) {
            //something went wrong
            return;
        }

        //image_name_tv.setText(filePath);

        try {
            Uri selectedImage = data.getData();
            String img = selectedImage.toString();

            Intent intent = new Intent(getApplicationContext(), ImageConfirmActivity.class);
            intent.putExtra("EXTRA_URI", img);

            Bitmap image = BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage));
            if(image != null) {
                list.add(0, image);
                //adapter.notifyDataSetChanged();
            }

            startActivity(intent);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return;
        }


    }

    @Override
    public void respond(int var, Object... objects) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if(i == adapter.getCount() - 1) {
            findImage();
        } else {
            //TODO Delete option? Larger size? both?
        }
    }
}
