package com.example.jimi.mystroke.tasks;

import android.os.AsyncTask;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.models.Exercise;
import com.example.jimi.mystroke.models.ExerciseImage;

import java.util.List;

/**
 * Created by jimi on 19/02/2018.
 */

public class GetExerciseImagesTask extends AsyncTask<Void, Void, List<ExerciseImage>> {
    private AppDatabase appDatabase;
    private AsyncResponse asyncResponse;
    private String eId;
    public static final int var = 24;

    public GetExerciseImagesTask(AppDatabase appDatabase, String eId, AsyncResponse asyncResponse) {
        this.asyncResponse = asyncResponse;
        this.appDatabase = appDatabase;
        this.eId = eId;
    }

    @Override
    protected List<ExerciseImage> doInBackground(Void... voids) {
        return appDatabase.exerciseImageDao().loadAllByExerciseId(eId, false);
    }

    @Override
    protected void onPostExecute(List<ExerciseImage> results) {
        asyncResponse.respond(var, results);
    }
}
