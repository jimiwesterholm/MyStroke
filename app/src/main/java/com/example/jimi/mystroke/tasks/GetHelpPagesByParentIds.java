package com.example.jimi.mystroke.tasks;

import android.os.AsyncTask;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.models.HelpPage;

import java.util.List;

/**
 * Created by jimi on 24/03/2018.
 */

public class GetHelpPagesByParentIds extends AsyncTask<String, Void, List<HelpPage>>{
    private AppDatabase appDatabase;
    private AsyncResponse asyncResponse;
    public static final int var = 22;

    public GetHelpPagesByParentIds(AppDatabase appDatabase, AsyncResponse asyncResponse) {
        this.appDatabase = appDatabase;
        this.asyncResponse = asyncResponse;
    }

    @Override
    protected List<HelpPage> doInBackground(String... strings) {
        return appDatabase.helpPageDao().loadAllByParentIds(strings, false);
    }

    @Override
    protected void onPostExecute(List<HelpPage> result) {
        asyncResponse.respond(var, result);
    }
}
