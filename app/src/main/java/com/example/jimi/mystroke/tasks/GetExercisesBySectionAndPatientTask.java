package com.example.jimi.mystroke.tasks;

import android.os.AsyncTask;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.activities.ViewExercisesActivity;
import com.example.jimi.mystroke.daos.ExerciseDao;
import com.example.jimi.mystroke.daos.PatientListExerciseDao;
import com.example.jimi.mystroke.models.Exercise;
import com.example.jimi.mystroke.models.PatientListExercise;

import java.util.List;

/**
 * Created by jimi on 28/03/2018.
 */

public class GetExercisesBySectionAndPatientTask extends AsyncTask<Void, Void, List<Exercise>> {
    private AppDatabase aDb;
    private AsyncResponse asyncResponse;
    private String section;
    private String pID;
    public static final int var = 23;

    public GetExercisesBySectionAndPatientTask(AppDatabase aDb, AsyncResponse asyncResponse, String section, String pID) {
        this.aDb = aDb;
        this.asyncResponse = asyncResponse;
        this.section = section;
        this.pID = pID;
    }

    @Override
    public List<Exercise> doInBackground(Void...foo) {
        PatientListExerciseDao patientListExerciseDao = aDb.patientListExerciseDao();
        List<PatientListExercise> patientListExercises = patientListExerciseDao.loadAllByPatientID(pID, false);
        String[] ids = new String[patientListExercises.size()];
        for (int i=0; i<patientListExercises.size(); i++) {
            ids[i] = patientListExercises.get(i).getEID();
        }
        ExerciseDao exerciseDao = aDb.exerciseDao();
        return exerciseDao.loadAllByIdsFromSection(ids, section, false);
    }

    @Override
    protected void onPostExecute(List<Exercise> exercises) {
        asyncResponse.respond(var, exercises);
    }
}
