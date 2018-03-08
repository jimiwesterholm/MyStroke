package com.example.jimi.mystroke.tasks;

import android.os.AsyncTask;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.Globals;
import com.example.jimi.mystroke.models.Exercise;
import com.example.jimi.mystroke.models.PatientListExercise;

import java.util.List;

/**
 * Created by jimi on 19/02/2018.
 */

public class DeletePatientListExerciseTask extends AsyncTask<PatientListExercise, Void, Boolean> {
    private AsyncResponse asyncResponse;
    private AppDatabase appDatabase;
    public static final int var = 13;

    public DeletePatientListExerciseTask(AsyncResponse asyncResponse, AppDatabase appDatabase) {
        this.asyncResponse = asyncResponse;
        this.appDatabase = appDatabase;
    }

    @Override
    protected Boolean doInBackground(PatientListExercise... listExercises) {
        long latestUp = Globals.getInstance().getLatestUpdate();
        for (PatientListExercise listExercise : listExercises) {
            if(listExercise.getCreated() > latestUp) {   //If the item is not yet in global database, delete
                appDatabase.patientListExerciseDao().delete(listExercise);
            } else {
                listExercise.setToDelete(true);
                appDatabase.patientListExerciseDao().update(listExercise);
            }
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        asyncResponse.respond(var, result);
    }
}
