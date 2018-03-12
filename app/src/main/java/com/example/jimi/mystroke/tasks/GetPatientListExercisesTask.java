package com.example.jimi.mystroke.tasks;

import android.os.AsyncTask;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.models.Exercise;
import com.example.jimi.mystroke.models.PatientListExercise;

import java.util.List;

/**
 * Created by jimi on 19/02/2018.
 */

public class GetPatientListExercisesTask extends AsyncTask<Void, Void, List<PatientListExercise>> {
    private AsyncResponse asyncResponse;
    private String pID;
    private AppDatabase appDatabase;
    public static final int var = 6;

    public GetPatientListExercisesTask(AsyncResponse asyncResponse, String pID, AppDatabase appDatabase) {
        this.asyncResponse = asyncResponse;
        this.pID = pID;
        this.appDatabase = appDatabase;
    }

    @Override
    protected List<PatientListExercise> doInBackground(Void...foo) {
        List<PatientListExercise> patientListExercises  = appDatabase.patientListExerciseDao().loadAllByPatientID(pID, false);
        int[] exerciseIDs = new int[patientListExercises.size()];
        for (int i = 0; i < patientListExercises.size(); i++) {
            Exercise exercise = appDatabase.exerciseDao().loadByExerciseId(patientListExercises.get(i).getEID(), false);
            patientListExercises.get(i).setExercise(exercise);
        }

        return patientListExercises;
    }

    @Override
    protected void onPostExecute(List<PatientListExercise> patientListExercises) {
        asyncResponse.respond(var, patientListExercises);
    }
}
