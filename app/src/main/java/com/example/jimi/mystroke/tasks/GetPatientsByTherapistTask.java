package com.example.jimi.mystroke.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.models.Patient;

import java.util.List;

/**
 * Created by jimi on 02/02/2018.
 */

public class GetPatientsByTherapistTask extends AsyncTask<Void, Void, List<Patient>>{
    private AppDatabase appDatabase;
    private AsyncResponse asyncResponse;
    private String tId;
    public static final int var = 27;

    public GetPatientsByTherapistTask(Context applicationContext, AsyncResponse asyncResponse, String tId) {
        appDatabase = AppDatabase.getDatabase(applicationContext);
        this.asyncResponse = asyncResponse;
        this.tId = tId;
    }

    @Override
    protected List<Patient> doInBackground(Void... voids) {
        List<Patient> patients = appDatabase.patientDao().loadAllByTherapistId(tId, false);
        for (Patient patient : patients) {
            patient.setUser(appDatabase.userDao().findById(patient.getUserID(), false));
        }
        return patients;
    }

    @Override
    protected void onPostExecute(List<Patient> result) {
        asyncResponse.respond(var, result);
    }
}
