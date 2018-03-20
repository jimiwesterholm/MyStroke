package com.example.jimi.mystroke.tasks;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.Globals;
import com.example.jimi.mystroke.models.DatabaseObject;
import com.example.jimi.mystroke.models.ExerciseImage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by jimi on 17/03/2018.
 */

public class UploadImagesTask extends AsyncTask<ExerciseImage, Void, Boolean> {
    private AppDatabase appDatabase;
    private AsyncResponse asyncResponse;
    public final static int var = 18;

    public UploadImagesTask(Context context, AsyncResponse asyncResponse) {
        appDatabase = AppDatabase.getDatabase(context);
        this.asyncResponse = asyncResponse;
    }

    @Override
    protected Boolean doInBackground(ExerciseImage... exerciseImages) {
        HttpURLConnection con = null;
        try {
            String urlString = Globals.getInstance().getFileDispUrl().concat(Globals.getInstance().getAddImageFD());
            URL url = new URL(urlString);
            con = (HttpURLConnection) url.openConnection();
            //con.connect();
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestMethod("POST");
            con.setDoOutput(true);

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(con.getOutputStream());
            for(ExerciseImage image : exerciseImages) {
                JSONObject jsonObject = image.toJSONWithBitmap();
                outputStreamWriter.write(jsonObject.toString());
            }
            outputStreamWriter.flush();
            outputStreamWriter.close();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return false;
        } finally {
            if(con != null) {
                con.disconnect();
            }
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean res) {
        asyncResponse.respond(var, res);
    }
}
