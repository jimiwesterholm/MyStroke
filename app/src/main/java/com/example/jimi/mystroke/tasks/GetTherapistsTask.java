package com.example.jimi.mystroke.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.models.Therapist;

import java.util.List;

/**
 * Created by jimi on 02/03/2018.
 */

public class GetTherapistsTask extends AsyncTask<Void, Void, List<Therapist>>{
    private AppDatabase appDatabase;
    private AsyncResponse asyncResponse;
    public static final int var = 25;

    public GetTherapistsTask(AsyncResponse asyncResponse, Context applicationContext) {
        appDatabase = AppDatabase.getDatabase(applicationContext);
        this.asyncResponse = asyncResponse;
    }

    @Override
    protected List<Therapist> doInBackground(Void... voids) {
        return appDatabase.therapistDao().getAll(false);
    }

    @Override
    protected void onPostExecute(List<Therapist> result) {
        asyncResponse.respond(var, result);
    }
}
