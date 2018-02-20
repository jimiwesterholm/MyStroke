package com.example.jimi.mystroke.tasks;

import android.os.AsyncTask;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.models.PatientListExercise;

import java.util.List;

/**
 * Created by jimi on 19/02/2018.
 */

public class GetPatientListExercisesTask extends AsyncTask<Void, Void, List<PatientListExercise>> {
    private AsyncResponse asyncResponse;
    private int pID;
    private AppDatabase appDatabase;
    public static final int var = 6;

    public GetPatientListExercisesTask(AsyncResponse asyncResponse, int pID, AppDatabase appDatabase) {
        this.asyncResponse = asyncResponse;
        this.pID = pID;
        this.appDatabase = appDatabase;
    }

    @Override
    protected List<PatientListExercise> doInBackground(Void...foo) {
        return appDatabase.patientListExerciseDao().loadAllByPatientID(pID);
    }

    @Override
    protected void onPostExecute(List<PatientListExercise> patientListExercises) {
        asyncResponse.respond(var, patientListExercises);
    }
}
