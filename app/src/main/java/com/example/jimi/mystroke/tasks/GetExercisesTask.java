package com.example.jimi.mystroke.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.models.Exercise;
import com.example.jimi.mystroke.models.Imagery;

import java.util.List;

/**
 * Created by jimi on 02/02/2018.
 */

public class GetExercisesTask extends AsyncTask<Void, Void, List<Exercise>>{
    AppDatabase appDatabase;
    AsyncResponse asyncResponse;
    public static final int var = 15;

    public GetExercisesTask(Context applicationContext, AsyncResponse asyncResponse) {
        appDatabase = AppDatabase.getDatabase(applicationContext);
        this.asyncResponse = asyncResponse;
    }

    @Override
    protected List<Exercise> doInBackground(Void... voids) {
        return appDatabase.exerciseDao().getAll(false);
    }

    @Override
    protected void onPostExecute(List<Exercise> result) {
        asyncResponse.respond(var, result);
    }
}
