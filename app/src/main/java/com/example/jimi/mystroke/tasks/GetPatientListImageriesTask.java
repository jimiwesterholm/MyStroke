package com.example.jimi.mystroke.tasks;

import android.os.AsyncTask;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.models.Imagery;
import com.example.jimi.mystroke.models.PatientListImagery;

import java.util.List;

/**
 * Created by jimi on 02/03/2018.
 */

public class GetPatientListImageriesTask extends AsyncTask<Void, Void, List<PatientListImagery>> {
    private AsyncResponse asyncResponse;
    private String pID;
    private AppDatabase appDatabase;
    public static final int var = 11;

    public GetPatientListImageriesTask(AsyncResponse asyncResponse, String pID, AppDatabase appDatabase) {
        this.asyncResponse = asyncResponse;
        this.pID = pID;
        this.appDatabase = appDatabase;
    }

    @Override
    protected List<PatientListImagery> doInBackground(Void... voids) {
        List<PatientListImagery> patientList = appDatabase.patientListImageryDao().loadAllByPatientId(pID, false);
        int[] ids = new int[patientList.size()];
        for (int i = 0; i < patientList.size(); i++) {
            Imagery imagery = appDatabase.imageryDao().loadByImageryId(patientList.get(i).getIID(), false);
            patientList.get(i).setImagery(imagery);
        }
        return patientList;
    }

     @Override
    protected void onPostExecute(List<PatientListImagery> results) {
        asyncResponse.respond(var, results);
     }
}
