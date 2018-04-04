package com.example.jimi.mystroke.tasks;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.Globals;
import com.example.jimi.mystroke.models.ExerciseImage;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by jimi on 17/03/2018.
 * TODO is this needed?
 */

public class DownloadImagesTask extends AsyncTask<String, Void, List<Bitmap>> {
    private AppDatabase appDatabase;
    private AsyncResponse asyncResponse;
    public final static int var = 19;

    public DownloadImagesTask(Context context, AsyncResponse asyncResponse) {
        appDatabase = AppDatabase.getDatabase(context);
        this.asyncResponse = asyncResponse;
    }

    @Override
    protected List<Bitmap> doInBackground(String... ids) {
        /*HttpURLConnection con = null;
        List<Bitmap> bitmaps = new ArrayList<Bitmap>();
        try {
            String urlString = Globals.getInstance().getFileDispUrl().concat(Globals.getInstance().getLoadImageFD());
            for (String id : ids) {
                String fullText = "";
                urlString += "?i=" + id;
                URL url = new URL(urlString);
                con = (HttpURLConnection) url.openConnection();
                InputStream is = new BufferedInputStream(con.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line = null;
                do {
                    line = br.readLine();
                    fullText += line;
                } while (line != null);
                JSONObject jsonObject = new JSONObject(fullText);
                jsonObject = (JSONObject) jsonObject.get("success");
                byte[] bytes = Base64.decode(fullText, Base64.DEFAULT);
                bitmaps.add(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return null;
        } finally {
            if(con != null) {
                con.disconnect();
            }
        }
        if(!bitmaps.isEmpty()) {
            return bitmaps;
        } else {
            return null;
        }*/
        return null;
    }

    @Override
    protected void onPostExecute(List<Bitmap> bitmaps) {
        asyncResponse.respond(var, bitmaps);
    }
}
