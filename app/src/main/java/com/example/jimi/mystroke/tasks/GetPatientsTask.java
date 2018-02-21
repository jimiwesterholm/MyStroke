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
    AppDatabase appDatabase;
    AsyncResponse asyncResponse;
    public static final int var = 9;

    public GetPatientsTask(Context applicationContext, AsyncResponse asyncResponse) {
        appDatabase = AppDatabase.getDatabase(applicationContext);
        this.asyncResponse = asyncResponse;
    }

    @Override
    protected List<Patient> doInBackground(Void... voids) {
        return appDatabase.patientDao().getAll();
    }

    @Override
    protected void onPostExecute(List<Patient> result) {
        asyncResponse.respond(var, result);
    }
}
