package com.example.jimi.mystroke.tasks;

import android.os.AsyncTask;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.models.Exercise;
import com.example.jimi.mystroke.models.PatientListExercise;

import java.util.List;

/**
 * Created by jimi on 19/02/2018.
 */

public class GetPatientListExerciseTask extends AsyncTask<Void, Void, List<PatientListExercise>> {
    private AsyncResponse asyncResponse;
    private String pID;
    private String eID;
    private AppDatabase appDatabase;
    public static final int var = 6;

    public GetPatientListExerciseTask(AsyncResponse asyncResponse, String pID, String eID, AppDatabase appDatabase) {
        this.asyncResponse = asyncResponse;
        this.pID = pID;
        this.appDatabase = appDatabase;
        this.eID = eID;
    }

    @Override
    protected List<PatientListExercise> doInBackground(Void...foo) {
        List<PatientListExercise> patientListExercises  = appDatabase.patientListExerciseDao().loadAllByPatientAndExerciseIDs(pID, eID, false);
        return patientListExercises;
    }

    @Override
    protected void onPostExecute(List<PatientListExercise> patientListExercises) {
        asyncResponse.respond(var, patientListExercises);
    }
}
