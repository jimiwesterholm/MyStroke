package com.example.jimi.mystroke.tasks;

import android.os.AsyncTask;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.models.Exercise;

import java.util.List;


public class GetExercisesNotFromIdsTask extends AsyncTask<Void, Void, List<Exercise>> {
    private AsyncResponse asyncResponse;
    private AppDatabase appDatabase;
    private String[] exerciseIds;
    public static final int var = 29;

    public GetExercisesNotFromIdsTask(AsyncResponse asyncResponse, String[] exerciseIds, AppDatabase appDatabase) {
        this.asyncResponse = asyncResponse;
        this.appDatabase = appDatabase;
        this.exerciseIds = exerciseIds;
    }

    @Override
    protected List<Exercise> doInBackground(Void...foo) {
        return appDatabase.exerciseDao().getAllExcept(exerciseIds, false);
    }

    @Override
    protected void onPostExecute(List<Exercise> sections) {
        asyncResponse.respond(var, sections);
    }
}
