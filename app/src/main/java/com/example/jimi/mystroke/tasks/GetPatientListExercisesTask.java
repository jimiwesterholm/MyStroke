package com.example.jimi.mystroke.tasks;

import android.os.AsyncTask;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.models.Exercise;
import com.example.jimi.mystroke.models.PatientListExercise;

import java.util.List;

/**
 * Created by jimi on 19/02/2018.
 */

public class GetPatientListExercisesTask extends AsyncTask<Void, Void, List<Exercise>> {
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
    protected List<Exercise> doInBackground(Void...foo) {
        List<PatientListExercise> patientListExercises  = appDatabase.patientListExerciseDao().loadAllByPatientID(pID);
        int[] exerciseIDs = new int[patientListExercises.size()];
        for (int i = 0; i < patientListExercises.size(); i++) {
            exerciseIDs[i] = patientListExercises.get(i).getId();
        }
        return appDatabase.exerciseDao().loadAllByIds(exerciseIDs);
    }

    @Override
    protected void onPostExecute(List<Exercise> patientListExercises) {
        asyncResponse.respond(var, patientListExercises);
    }
}
