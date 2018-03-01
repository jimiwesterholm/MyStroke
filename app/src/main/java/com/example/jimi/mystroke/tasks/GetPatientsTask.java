package com.example.jimi.mystroke.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.models.Imagery;
import com.example.jimi.mystroke.models.Patient;

import java.util.List;

/**
 * Created by jimi on 02/02/2018.
 */

public class GetPatientsTask extends AsyncTask<Void, Void, List<Patient>>{
    private AppDatabase appDatabase;
    private AsyncResponse asyncResponse;
    public static final int var = 9;

    public GetPatientsTask(Context applicationContext, AsyncResponse asyncResponse) {
        appDatabase = AppDatabase.getDatabase(applicationContext);
        this.asyncResponse = asyncResponse;
    }

    @Override
    protected List<Patient> doInBackground(Void... voids) {
        List<Patient> patients = appDatabase.patientDao().getAll();
        for (Patient patient : patients) {
            patient.setUser(appDatabase.userDao().findById(patient.getPid()));
        }
        return patients;
    }

    @Override
    protected void onPostExecute(List<Patient> result) {
        asyncResponse.respond(var, result);
    }
}
