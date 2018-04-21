package com.example.jimi.mystroke.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.activities.ViewTherapistsActivity;
import com.example.jimi.mystroke.models.Therapist;

import java.util.List;

public class GetTherapistsWithUsersTask extends AsyncTask<Void, Void, List<Therapist>> {
    private AppDatabase appDatabase;
    private AsyncResponse asyncResponse;
    public static final int var = 25;

    public GetTherapistsWithUsersTask(AsyncResponse asyncResponse, Context applicationContext) {
        appDatabase = AppDatabase.getDatabase(applicationContext);
        this.asyncResponse = asyncResponse;
    }

    @Override
    protected List<Therapist> doInBackground(Void... voids) {
        List<Therapist> therapists = appDatabase.therapistDao().getAll(false);
        for (Therapist therapist : therapists) {
            therapist.setUser(appDatabase.userDao().findById(therapist.getUserID(), false));
        }
        return therapists;
    }

    @Override
    protected void onPostExecute(List<Therapist> result) {
        asyncResponse.respond(var, result);
    }
}
