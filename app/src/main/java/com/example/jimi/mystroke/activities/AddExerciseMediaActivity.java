package com.example.jimi.mystroke.activities;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.Globals;
import com.example.jimi.mystroke.ImageAdapter;
import com.example.jimi.mystroke.R;
import com.example.jimi.mystroke.models.Exercise;
import com.example.jimi.mystroke.models.ExerciseImage;
import com.example.jimi.mystroke.tasks.AsyncResponse;
import com.example.jimi.mystroke.tasks.GetExerciseByIdTask;
import com.example.jimi.mystroke.tasks.GetExerciseImagesTask;
import com.example.jimi.mystroke.tasks.RecordsToAppDatabaseTask;
import com.example.jimi.mystroke.tasks.UploadImagesTask;
import com.example.jimi.mystroke.tasks.UpsertExerciseTask;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.android.volley.Request.Method.GET;
import static com.android.volley.Request.Method.POST;

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
    private List<ExerciseImage> exerciseImageList;
    private ExerciseImage exerciseImage;
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

        eId = getIntent().getStringExtra("EXTRA_EXERCISE_ID");
        if(eId != null) {
            new GetExerciseByIdTask(AppDatabase.getDatabase(getApplicationContext()), this, eId).execute();
        } else {
            finish(); //TODO error msg? - doubt this'll ever happen tho
        }

        gridView = findViewById(R.id.imageGridView);
        ExerciseImage exerciseImage = new ExerciseImage(null, null, -1, null);
        exerciseImage.setBitmap(BitmapFactory.decodeResource(getApplicationContext().getResources(), android.R.drawable.ic_input_add));
        exerciseImageList.add(exerciseImage);
        itemsToGridView(exerciseImageList);

        youTubeText = findViewById(R.id.youTubeText);
        findViewById(R.id.youTubeButton).setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                String vidAddress = youTubeText.getText().toString();
                onYouTubeClick(vidAddress);
            }
        });
        youTubeInstruction = findViewById(R.id.youTubeInstructionText);
        thumbnailView = findViewById(R.id.thumbnailView);
    }


    protected void onFinishClick(View view) {
        //Validate presence of stuff?
        new RecordsToAppDatabaseTask(getString(R.string.exercise), AppDatabase.getDatabase(getApplicationContext())).execute(exercise);
        upload();
        startActivity(new Intent(this, MediaSubmittedActivity.class));
    }

    //This method is from https://stackoverflow.com/questions/574195/android-youtube-app-play-video-intent - by Roger Garzon Nieto
    protected void watchYoutubeVideo(){
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + exercise.getVideo()));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(Exercise.youTubeFromVidId(exercise.getVideo())));
        try {
            this.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            this.startActivity(webIntent);
        }
    }

    private void upload() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Globals.getInstance().getFileDispUrl().concat(Globals.getInstance().getAddImageFD());

        List<ExerciseImage> images = exerciseImageList;
        //Remove the add button
        images.remove(images.size()-1);
        for (ExerciseImage exerciseImage : images) {
            if (exerciseImage.getBitmap() == null) {
                continue;
            }
            try {
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (POST, url, exerciseImage.toJSONWithBitmap(), new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        });

                // Add the request to the RequestQueue.
                queue.add(jsonObjectRequest);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    protected void onYouTubeClick(String vidAddress) {
        if(true) {  //TODO validate address
            String vidId = null;
            if(vidAddress.length() == 11) {
                vidId = vidAddress;
            } else {
                vidId = Exercise.vidIdFromYouTube(vidAddress);
            }
            if(vidId != null) {
                exercise.setVideo(vidId);

                //Make views visible
                youTubeInstruction.setVisibility(View.VISIBLE);
                thumbnailView.setVisibility(View.VISIBLE);

                //Get thumbnail
                Glide.with(this).load("http://img.youtube.com/vi/"+vidId+"/mqdefault.jpg").into(thumbnailView);
                thumbnailView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        watchYoutubeVideo();
                    }
                });
            } else {
                //TODO not valid address msg
            }
        }
    }

    private void itemsToGridView(List<ExerciseImage> items) {
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
                            exerciseImage = new ExerciseImage(eId, null, exerciseImageList.size() - 1, img);
                            exerciseImage.setBitmap(image);
                            Intent intent = new Intent(getApplicationContext(), ImageConfirmActivity.class);
                            intent.putExtra("EXTRA_URI", img);
                            startActivityForResult(intent, 2);
                        } else {
                            //TODO error msg
                        }
                    } catch (Exception e) {
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
                            exerciseImageList.remove(pos);
                        } else {
                            exerciseImageList.get(pos).setAltText(alt);
                        }
                    } else {
                        exerciseImage.setAltText(alt);
                        exerciseImageList.add(exerciseImageList.size() - 1, exerciseImage);
                    }
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    if (data.getIntExtra("EXTRA_LIST_POS", -1) == -1) {
                        exerciseImage = null;
                    }
                }
                adapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void respond(int var, Object... objects) {
        switch (var) {
            case UploadImagesTask.var:
                if ((Boolean) objects[0]) {
                    new RecordsToAppDatabaseTask(getString(R.string.exercise_image), AppDatabase.getDatabase(getApplicationContext())).execute(exerciseImageList);
                    //TODO success activity
                } else {
                    //TODO smth went wrong msg
                }
                break;
            case GetExerciseByIdTask.var:
                exercise = (Exercise) objects[0];
                if (exercise.getVideo() != null) {
                    onYouTubeClick(exercise.getVideo());
                }
                new GetExerciseImagesTask(AppDatabase.getDatabase(getApplicationContext()), exercise.getId(), this).execute();
                break;
            case GetExerciseImagesTask.var:
                List<ExerciseImage> exerciseImages = (List<ExerciseImage>) objects[0];
                Globals globals = Globals.getInstance();
                if(exerciseImages.size() > 0) {
                    exerciseImageList = new ArrayList<ExerciseImage>(exerciseImages.size());
                    ExerciseImage addButton = new ExerciseImage(null, null, -1, null);
                    addButton.setBitmap(BitmapFactory.decodeResource(getApplicationContext().getResources(), android.R.drawable.ic_input_add));
                    exerciseImageList.add(exerciseImageList.size() - 1, addButton);
                    for (int i = 0; i < exerciseImages.size(); i++) {
                        ExerciseImage newImage = exerciseImages.get(i);
                        exerciseImageList.add(newImage.getPosition(), newImage);
                    }
                }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if(i == adapter.getCount() - 1) {
            findImage();
        } else {
            Intent intent = new Intent(getApplicationContext(), ImageConfirmActivity.class);
            intent.putExtra("EXTRA_URI", exerciseImageList.get(i).getAddress());
            intent.putExtra("EXTRA_ALT_TEXT", exerciseImageList.get(i).getAltText());
            intent.putExtra("EXTRA_LIST_POS", i);
            startActivityForResult(intent, 2);
        }
    }

}
