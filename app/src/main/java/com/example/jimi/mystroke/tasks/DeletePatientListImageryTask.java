package com.example.jimi.mystroke.tasks;

import android.os.AsyncTask;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.Globals;
import com.example.jimi.mystroke.models.Imagery;
import com.example.jimi.mystroke.models.PatientListImagery;

import java.util.List;

/**
 * Created by jimi on 02/03/2018.
 */

public class DeletePatientListImageryTask extends AsyncTask<PatientListImagery, Void, Boolean> {
    private AsyncResponse asyncResponse;
    private AppDatabase appDatabase;
    public static final int var = 12;

    public DeletePatientListImageryTask(AsyncResponse asyncResponse, AppDatabase appDatabase) {
        this.asyncResponse = asyncResponse;
        this.appDatabase = appDatabase;
    }

    @Override
    protected Boolean doInBackground(PatientListImagery... listImageries) {
        long latestUp = Globals.getInstance().getLatestUpdate();
        for (PatientListImagery listImagery : listImageries) {
            if(listImagery.getCreated() > latestUp) {   //If the item is not yet in global database, delete
                appDatabase.patientListImageryDao().delete(listImagery);
            } else {
                listImagery.setToDelete(true);
                appDatabase.patientListImageryDao().update(listImagery);
            }
        }
        return true;
    }

     @Override
    protected void onPostExecute(Boolean results) {
        asyncResponse.respond(var, results);
     }
}
