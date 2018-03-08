package com.example.jimi.mystroke.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.models.Imagery;
import com.example.jimi.mystroke.models.Patient;

import java.util.List;

/**
 * Created by jimi on 02/03/2018.
 */

public class GetPatientTask extends AsyncTask<Void, Void, Patient>{
    private AppDatabase appDatabase;
    private AsyncResponse asyncResponse;
    private int pId;
    public static final int var = 12;

    public GetPatientTask(AsyncResponse asyncResponse, int pId, Context applicationContext) {
        appDatabase = AppDatabase.getDatabase(applicationContext);
        this.asyncResponse = asyncResponse;
        this.pId = pId;
    }

    @Override
    protected Patient doInBackground(Void... voids) {
        return appDatabase.patientDao().loadById(pId, false);
    }

    @Override
    protected void onPostExecute(Patient result) {
        asyncResponse.respond(var, result);
    }
}
