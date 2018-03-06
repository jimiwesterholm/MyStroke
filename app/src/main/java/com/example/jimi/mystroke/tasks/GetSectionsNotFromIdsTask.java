package com.example.jimi.mystroke.tasks;

import android.os.AsyncTask;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.activities.AddToListActivity;

import java.util.List;

/**
 * Created by jimi on 02/03/2018.
 */

public class GetSectionsNotFromIdsTask extends AsyncTask<Void, Void, List<String>> {
    private AsyncResponse asyncResponse;
    private AppDatabase appDatabase;
    private int[] exerciseIds;
    public static final int var = 12;

    public GetSectionsNotFromIdsTask (AsyncResponse asyncResponse, int[] exerciseIds, AppDatabase appDatabase) {
        this.asyncResponse = asyncResponse;
        this.appDatabase = appDatabase;
        this.exerciseIds = exerciseIds;
    }

    @Override
    protected List<String> doInBackground(Void...foo) {
        return appDatabase.exerciseDao().getSectionsNotOfIds(exerciseIds, false);
    }

    @Override
    protected void onPostExecute(List<String> sections) {
        asyncResponse.respond(var, sections);
    }
}
