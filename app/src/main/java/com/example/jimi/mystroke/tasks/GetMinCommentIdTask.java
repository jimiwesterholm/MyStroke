package com.example.jimi.mystroke.tasks;

import android.os.AsyncTask;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.activities.ChatActivity;
import com.example.jimi.mystroke.models.Comment;

import java.lang.ref.WeakReference;

/**
 * Created by jimi on 19/02/2018.
 */

public class GetMinCommentIdTask extends AsyncTask<Void, Void, Integer> {
    private AppDatabase appDatabase;
    private AsyncResponse asyncResponse;
    public static final int var = 4;

    public GetMinCommentIdTask(AppDatabase appDatabase, AsyncResponse asyncResponse) {
        this.appDatabase = appDatabase;
        this.asyncResponse = asyncResponse;
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        return appDatabase.commentDao().loadMinId();
    }

    @Override
    protected void onPostExecute(Integer result) {
        if (result==null) result = 0;
        asyncResponse.respond(var, result);
    }
}
