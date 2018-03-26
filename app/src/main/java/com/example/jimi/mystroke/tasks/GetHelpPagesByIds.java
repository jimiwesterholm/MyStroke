package com.example.jimi.mystroke.tasks;

import android.os.AsyncTask;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.models.HelpPage;

import java.util.List;

/**
 * Created by jimi on 24/03/2018.
 */

public class GetHelpPagesByIds extends AsyncTask<String, Void, List<HelpPage>>{
    private AppDatabase appDatabase;
    private AsyncResponse asyncResponse;
    public static final int var = 21;

    public GetHelpPagesByIds(AppDatabase appDatabase, AsyncResponse asyncResponse) {
        this.appDatabase = appDatabase;
        this.asyncResponse = asyncResponse;
    }

    @Override
    protected List<HelpPage> doInBackground(String... strings) {
        if(strings.length == 0) {
            return appDatabase.helpPageDao().getAll(false);
        } else {
            return appDatabase.helpPageDao().loadAllByIds(strings, false);
        }
    }

    @Override
    protected void onPostExecute(List<HelpPage> result) {
        asyncResponse.respond(var, result);
    }
}
