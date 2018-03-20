package com.example.jimi.mystroke.tasks;

import android.os.AsyncTask;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.models.Exercise;

/**
 * Created by jimi on 20/03/2018.
 */

public class UpsertExerciseTask extends AsyncTask<Exercise, Void, Void> {
    private AppDatabase appDatabase;
    private String eId;

    public UpsertExerciseTask(AppDatabase appDatabase, String eId) {
        this.appDatabase = appDatabase;
        this.eId = eId;
    }

    @Override
    protected Void doInBackground(Exercise... exercises) {
        for (Exercise exercise : exercises) {
            appDatabase.exerciseDao().upsert(exercise);
        }
        return null;
    }
}
