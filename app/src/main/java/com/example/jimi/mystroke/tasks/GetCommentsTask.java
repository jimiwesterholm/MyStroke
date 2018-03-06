package com.example.jimi.mystroke.tasks;

import android.os.AsyncTask;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.activities.ChatActivity;
import com.example.jimi.mystroke.models.Comment;

import java.util.List;

/**
 * Created by jimi on 19/02/2018.
 */

public class GetCommentsTask extends AsyncTask<Void, Void, List<Comment>> {
    private AppDatabase appDatabase;
    private AsyncResponse asyncResponse;
    public static final int var = 2;

    public GetCommentsTask(AppDatabase appDatabase, AsyncResponse asyncResponse) {
        this.asyncResponse = asyncResponse;
        this.appDatabase = appDatabase;
    }

    @Override
    protected List<Comment> doInBackground(Void... voids) {
        return appDatabase.commentDao().getAllOrdered(false);
    }

    @Override
    protected void onPostExecute(List<Comment> results) {
        asyncResponse.respond(var, results);
    }
}
