package com.example.jimi.mystroke.tasks;

import android.os.AsyncTask;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.Globals;
import com.example.jimi.mystroke.models.Exercise;
import com.example.jimi.mystroke.models.PatientListExercise;

/**
 * Created by jimi on 19/02/2018.
 */

public class DeleteExercisesTask extends AsyncTask<Exercise, Void, Boolean> {
    private AsyncResponse asyncResponse;
    private AppDatabase appDatabase;
    public static final int var = 13;

    public DeleteExercisesTask(AsyncResponse asyncResponse, AppDatabase appDatabase) {
        this.asyncResponse = asyncResponse;
        this.appDatabase = appDatabase;
    }

    @Override
    protected Boolean doInBackground(Exercise... exercises) {
        long latestUp = Globals.getInstance().getLatestUpdate();
        for (Exercise exercise : exercises) {
            if(exercise.getCreated() > latestUp) {   //If the item is not yet in global database, delete
                appDatabase.exerciseDao().delete(exercise);
            } else {
                exercise.setToDelete(true);
                appDatabase.exerciseDao().update(exercise);
            }
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        asyncResponse.respond(var, result);
    }
}
