package com.example.jimi.mystroke.tasks;

import android.os.AsyncTask;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.models.Exercise;

import java.util.List;

/**
 * Created by jimi on 19/02/2018.
 */

public class GetSectionExercisesExceptTask extends AsyncTask<Void, Void, List<Exercise>> {
    private AsyncResponse asyncResponse;
    private int[] IDs;
    private AppDatabase appDatabase;
    private String section;
    public static final int var = 7;

    public GetSectionExercisesExceptTask(AsyncResponse asyncResponse, int[] IDs, String section, AppDatabase appDatabase) {
        this.asyncResponse = asyncResponse;
        this.IDs = IDs;
        this.appDatabase = appDatabase;
        this.section = section;
    }

    @Override
    protected List<Exercise> doInBackground(Void... voids) {
        return appDatabase.exerciseDao().loadAllButIdsFromSection(IDs, section, false);
    }

    @Override
    protected void onPostExecute(List<Exercise> exercises) {
        asyncResponse.respond(var, exercises);
    }
}
