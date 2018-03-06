package com.example.jimi.mystroke.tasks;

import android.os.AsyncTask;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.models.ExerciseSection;
import com.example.jimi.mystroke.models.PatientListExercise;

import java.util.List;

/**
 * Created by jimi on 19/02/2018.
 */

public class GetSectionsFromIdsTask extends AsyncTask<Void, Void, List<String>> {
    private AsyncResponse asyncResponse;
    private AppDatabase appDatabase;
    private int[] exerciseIds;
    public static final int var = 8;

    public GetSectionsFromIdsTask (AsyncResponse asyncResponse, int[] exerciseIds, AppDatabase appDatabase) {
        this.asyncResponse = asyncResponse;
        this.appDatabase = appDatabase;
        this.exerciseIds = exerciseIds;
    }

    @Override
    protected List<String> doInBackground(Void...foo) {
        return appDatabase.exerciseDao().getSectionsOfIds(exerciseIds, false);
    }

    @Override
    protected void onPostExecute(List<String> sections) {
        asyncResponse.respond(var, sections);
    }
}
