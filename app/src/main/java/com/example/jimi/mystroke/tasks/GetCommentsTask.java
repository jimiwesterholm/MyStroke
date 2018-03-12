package com.example.jimi.mystroke.tasks;

import android.os.AsyncTask;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.models.Comment;

import java.util.List;

/**
 * Created by jimi on 19/02/2018.
 */

public class GetCommentsTask extends AsyncTask<Void, Void, List<Comment>> {
    private AppDatabase appDatabase;
    private AsyncResponse asyncResponse;
    private String pId;
    public static final int var = 2;

    public GetCommentsTask(AppDatabase appDatabase, String pId, AsyncResponse asyncResponse) {
        this.asyncResponse = asyncResponse;
        this.appDatabase = appDatabase;
        this.pId = pId;
    }

    @Override
    protected List<Comment> doInBackground(Void... voids) {
        return appDatabase.commentDao().getByPatientOrdered(pId, false);
    }

    @Override
    protected void onPostExecute(List<Comment> results) {
        asyncResponse.respond(var, results);
    }
}
