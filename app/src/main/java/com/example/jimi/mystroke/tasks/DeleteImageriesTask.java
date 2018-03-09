package com.example.jimi.mystroke.tasks;

import android.os.AsyncTask;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.Globals;
import com.example.jimi.mystroke.models.Imagery;
import com.example.jimi.mystroke.models.PatientListImagery;

/**
 * Created by jimi on 02/03/2018.
 */

public class DeleteImageriesTask extends AsyncTask<Imagery, Void, Boolean> {
    private AsyncResponse asyncResponse;
    private AppDatabase appDatabase;
    public static final int var = 12;

    public DeleteImageriesTask(AsyncResponse asyncResponse, AppDatabase appDatabase) {
        this.asyncResponse = asyncResponse;
        this.appDatabase = appDatabase;
    }

    @Override
    protected Boolean doInBackground(Imagery... imageries) {
        long latestUp = Globals.getInstance().getLatestUpdate();
        for (Imagery imagery : imageries) {
            if(imagery.getCreated() > latestUp) {   //If the item is not yet in global database, delete
                appDatabase.imageryDao().delete(imagery);
            } else {
                imagery.setToDelete(true);
                appDatabase.imageryDao().update(imagery);
            }
        }
        return true;
    }

     @Override
    protected void onPostExecute(Boolean results) {
        asyncResponse.respond(var, results);
     }
}
