package com.example.jimi.mystroke.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.models.Patient;
import com.example.jimi.mystroke.models.Therapist;

/**
 * Created by jimi on 02/03/2018.
 */

public class GetTherapistTask extends AsyncTask<Void, Void, Therapist>{
    private AppDatabase appDatabase;
    private AsyncResponse asyncResponse;
    private String tId;
    public static final int var = 12;

    public GetTherapistTask(AsyncResponse asyncResponse, String tId, Context applicationContext) {
        appDatabase = AppDatabase.getDatabase(applicationContext);
        this.asyncResponse = asyncResponse;
        this.tId = tId;
    }

    @Override
    protected Therapist doInBackground(Void... voids) {
        return appDatabase.therapistDao().loadById(tId, false);
    }

    @Override
    protected void onPostExecute(Therapist result) {
        asyncResponse.respond(var, result);
    }
}
