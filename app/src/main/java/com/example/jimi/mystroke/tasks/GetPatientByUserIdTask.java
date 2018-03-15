package com.example.jimi.mystroke.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.models.Patient;

/**
 * Created by jimi on 02/03/2018.
 */

public class GetPatientByUserIdTask extends AsyncTask<Void, Void, Patient>{
    private AppDatabase appDatabase;
    private AsyncResponse asyncResponse;
    private String uId;
    public static final int var = 12;

    public GetPatientByUserIdTask(AsyncResponse asyncResponse, String uId, Context applicationContext) {
        appDatabase = AppDatabase.getDatabase(applicationContext);
        this.asyncResponse = asyncResponse;
        this.uId = uId;
    }

    @Override
    protected Patient doInBackground(Void... voids) {
        return appDatabase.patientDao().loadByUserId(uId, false);
    }

    @Override
    protected void onPostExecute(Patient result) {
        asyncResponse.respond(var, result);
    }
}
