package com.example.jimi.mystroke.activities;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.ImageAdapter;
import com.example.jimi.mystroke.R;
import com.example.jimi.mystroke.models.Exercise;
import com.example.jimi.mystroke.models.ExerciseImage;
import com.example.jimi.mystroke.tasks.AsyncResponse;
import com.example.jimi.mystroke.tasks.GetExerciseByIdTask;
import com.example.jimi.mystroke.tasks.RecordsToAppDatabaseTask;
import com.example.jimi.mystroke.tasks.UploadImagesTask;

import java.util.ArrayList;
import java.util.List;

/** TODO - check links, make work - will need some server side nonsense as well
 * VIDEO:
 *  https://www.simplifiedcoding.net/android-upload-video-to-server-using-php/
 *  https://stackoverflow.com/questions/10991515/how-to-play-an-mp4-video-from-server-in-android
 *  https://developer.android.com/guide/topics/media/media-formats.html
 *  https://code.tutsplus.com/tutorials/streaming-video-in-android-apps--cms-19888
 *
 *  remember to check that file types can be played back (webm? good for android 4.0+
 *
 *
 *  THUMBNAILS (for YT)    https://stackoverflow.com/questions/7324759/how-to-display-thumbnail-of-youtube-videos-in-android
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

public class AddExerciseMediaActivity extends AppCompatActivity implements AsyncResponse, GridView.OnItemClickListener {
    private Exercise exercise;
    private String eId;
    private GridView gridView;
    private ImageAdapter adapter;
    private List<Bitmap> list;
    private List<ExerciseImage> exerciseImageList;
    private List<String> uriList;
    private EditText youTubeText;
    private TextView youTubeInstruction;
    private ImageView thumbnailView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise_media);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        exerciseImageList = new ArrayList<ExerciseImage>();
        uriList = new ArrayList<String>();
        list = new ArrayList<Bitmap>();

        //TODO: get exercise
        eId = getIntent().getStringExtra("EXTRA_EXERCISE_ID");
        new GetExerciseByIdTask(AppDatabase.getDatabase(getApplicationContext()), this, eId).execute();
        gridView = findViewById(R.id.imageGridView);
        list.add(BitmapFactory.decodeResource(getApplicationContext().getResources(), android.R.drawable.ic_input_add));
        itemsToGridView(list);

        youTubeText = findViewById(R.id.youTubeText);
        ((Button)findViewById(R.id.youTubeButton)).setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                onYouTubeClick();
            }
        });
        youTubeInstruction = findViewById(R.id.youTubeInstructionText);
        thumbnailView = findViewById(R.id.thumbnailView);
    }


    protected void onFinishClick(View view) {
        //Validate presence of stuff?
        upload();
    }

    //This method is from https://stackoverflow.com/questions/574195/android-youtube-app-play-video-intent - by Roger Garzon Nieto
    protected void watchYoutubeVideo(Context context, String id){
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + id));
        try {
            context.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            context.startActivity(webIntent);
        }
    }

    protected void onYouTubeClick() {
        if(true) {  //TODO validation
            String vidAddress = youTubeText.getText().toString();
            if(vidAddress.contains("?v=")) {
                String vidId = vidAddress.substring(vidAddress.indexOf("?v=") + 3);

                //Set views visible
                youTubeInstruction.setVisibility(View.VISIBLE);
                thumbnailView.setVisibility(View.VISIBLE);

                //Get thumbnail
                Glide.with(this).load("http://img.youtube.com/vi/"+vidId+"/mqdefault.jpg").into(thumbnailView);
            } else {
                //TODO not valid msg
            }
        }
    }

    private void upload() {
        new UploadImagesTask(getApplicationContext(), this).execute(exerciseImageList.toArray(new ExerciseImage[0]));
        //TODO indicate loading happening
    }

    private void itemsToGridView(List<Bitmap> items) {
        adapter = new ImageAdapter(this, items);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);
        adapter.notifyDataSetChanged();
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
        switch (requestCode) {
            case 1:
                if(resultCode == Activity.RESULT_OK) {
                    try {
                        Uri selectedImage = data.getData();
                        String img = selectedImage.toString();
                        Bitmap image = BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage));
                        if (image != null) {
                            Intent intent = new Intent(getApplicationContext(), ImageConfirmActivity.class);
                            intent.putExtra("EXTRA_URI", img);
                            //intent.putExtra("EXTRA_LIST_POS", -1);
                            list.add(0, image);
                            uriList.add(0, img);
                            startActivityForResult(intent, 2);
                        } else {
                            //TODO error message
                        }
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        return;
                    }
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    //Smth?
                }
                break;
            case 2:
                if(resultCode == Activity.RESULT_OK) {
                    String alt = data.getStringExtra("EXTRA_ALT_TEXT");
                    int pos = data.getIntExtra("EXTRA_LIST_POS", -1);
                    if(pos >= 0) {
                        if(data.getBooleanExtra("EXTRA_TO_DELETE", false)) {
                            list.remove(pos);
                            uriList.remove(pos);
                        } else {
                            exerciseImageList.get(pos).setAltText(alt);
                        }
                    } else {
                        exerciseImageList.add(new ExerciseImage(eId, alt, 0, list.get(0)));
                    }
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    if (data.getIntExtra("EXTRA_LIST_POS", -1) == -1) {
                        list.remove(0);
                        uriList.remove(0);
                    }
                }
                adapter.notifyDataSetChanged();
                break;
        }
        //TODO FOR BLUE COLOUR: https://stackoverflow.com/questions/920306/sending-data-back-to-the-main-activity-in-android !?!!!! SHIT YES


    }

    @Override
    public void respond(int var, Object... objects) {
        switch (var) {
            case UploadImagesTask.var:
                if((Boolean) objects[0]) {
                    new RecordsToAppDatabaseTask(getString(R.string.exercise_image), AppDatabase.getDatabase(getApplicationContext())).execute(exerciseImageList);
                    //TODO success activity
                } else {
                    //TODO smth went wrong msg
                }
                break;
            case GetExerciseByIdTask.var:
                exercise = (Exercise) objects[0];
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if(i == adapter.getCount() - 1) {
            findImage();
        } else {
            Intent intent = new Intent(getApplicationContext(), ImageConfirmActivity.class);
            intent.putExtra("EXTRA_URI", uriList.get(i));
            intent.putExtra("EXTRA_ALT_TEXT", exerciseImageList.get(i).getAltText());
            intent.putExtra("EXTRA_LIST_POS", i);
            startActivityForResult(intent, 2);
        }
    }
}
