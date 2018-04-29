package com.example.jimi.mystroke.tasks;

import android.os.AsyncTask;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.models.RegisterCode;

public class FindRegisterCodeTask  extends AsyncTask<Void, Void, RegisterCode> {
    private AppDatabase appDatabase;
    private AsyncResponse asyncResponse;
    private String rcId;
    public static final int var = 28;

    public FindRegisterCodeTask(AppDatabase appDatabase, AsyncResponse asyncResponse, String rcId) {
        this.appDatabase = appDatabase;
        this.asyncResponse = asyncResponse;
        this.rcId = rcId;
    }

    @Override
    protected RegisterCode doInBackground(Void... voids) {
        return appDatabase.registerCodeDao().findById(rcId, false);
    }

    @Override
    protected void onPostExecute(RegisterCode result) {
        asyncResponse.respond(var, result);
    }
}
