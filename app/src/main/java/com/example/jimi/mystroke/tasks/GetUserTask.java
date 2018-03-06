package com.example.jimi.mystroke.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.models.User;

import java.util.List;

/**
 * Created by jimi on 02/02/2018.
 */

public class GetUserTask extends AsyncTask<Void, Void, User>{
    private AppDatabase appDatabase;
    private AsyncResponse asyncResponse;
    private int id;
    public static final int var = 10;

    public GetUserTask(Context applicationContext, AsyncResponse asyncResponse, int id) {
        appDatabase = AppDatabase.getDatabase(applicationContext);
        this.asyncResponse = asyncResponse;
        this.id = id;
    }

    @Override
    protected User doInBackground(Void... voids) {
        return appDatabase.userDao().findById(id, false);
    }

    @Override
    protected void onPostExecute(User result) {
        asyncResponse.respond(var, result);
    }
}
