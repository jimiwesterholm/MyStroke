package com.example.jimi.mystroke.tasks;

import android.os.AsyncTask;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.models.Imagery;
import com.example.jimi.mystroke.models.PatientListImagery;

import java.util.List;

/**
 * Created by jimi on 02/03/2018.
 */

public class GetPatientListImageriesTask extends AsyncTask<Void, Void, List<Imagery>> {
    private AsyncResponse asyncResponse;
    private int pID;
    private AppDatabase appDatabase;
    public static final int var = 11;

    public GetPatientListImageriesTask(AsyncResponse asyncResponse, int pID, AppDatabase appDatabase) {
        this.asyncResponse = asyncResponse;
        this.pID = pID;
        this.appDatabase = appDatabase;
    }

    @Override
    protected List<Imagery> doInBackground(Void... voids) {
        List<PatientListImagery> patientList = appDatabase.patientListImageryDao().loadAllByPatientId(pID, false);
        int[] ids = new int[patientList.size()];
        for (int i = 0; i < patientList.size(); i++) {
            ids[i] = patientList.get(i).getIID();
        }
        return appDatabase.imageryDao().loadAllByIds(ids, false);
    }

     @Override
    protected void onPostExecute(List<Imagery> results) {
        asyncResponse.respond(var, results);
     }
}
