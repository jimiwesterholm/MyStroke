package com.example.jimi.mystroke.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.provider.ContactsContract;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.daos.ExerciseDao;
import com.example.jimi.mystroke.models.Exercise;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by jimi on 17/12/2017.
**/

public class GetSectionsTask extends AsyncTask<Void, Void, List<String>> {
    AppDatabase aDb;
    AsyncResponse asyncResponse;
    List<String> results;

    public GetSectionsTask(Context context, AsyncResponse asyncResponse) {
        this.aDb = AppDatabase.getDatabase(context);
        this.asyncResponse = asyncResponse;
    }

    @Override
    protected List<String> doInBackground(Void...foo) {
        ExerciseDao exerciseDao = aDb.exerciseDao();
        return exerciseDao.getSections();
    }

    protected void onPostExecute(List<String> results) {
        asyncResponse.respond(results);
    }
}
