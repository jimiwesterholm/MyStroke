package com.example.jimi.mystroke.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.activities.ViewImageriesActivity;
import com.example.jimi.mystroke.models.Imagery;

import java.util.List;

/**
 * Created by jimi on 02/02/2018.
 */

public class GetImageriesTask extends AsyncTask<Void, Void, List<Imagery>>{
    AppDatabase appDatabase;
    AsyncResponse asyncResponse;
    public static final int var = 3;

    public GetImageriesTask(Context applicationContext, AsyncResponse asyncResponse) {
        appDatabase = AppDatabase.getDatabase(applicationContext);
        this.asyncResponse = asyncResponse;
    }

    @Override
    protected List<Imagery> doInBackground(Void... voids) {
        return appDatabase.imageryDao().getAll();
    }

    @Override
    protected void onPostExecute(List<Imagery> result) {
        asyncResponse.respond(var, result);
    }
}
