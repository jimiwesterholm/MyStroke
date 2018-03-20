package com.example.jimi.mystroke.tasks;

import android.os.AsyncTask;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.daos.ExerciseDao;
import com.example.jimi.mystroke.models.Exercise;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by jimi on 17/12/2017.
 */

public class GetExerciseByIdTask extends AsyncTask<Void, Void, Exercise> {
    private AppDatabase aDb;
    private AsyncResponse asyncResponse;
    private String id;
    public final static int var = 20;

    public GetExerciseByIdTask(AppDatabase aDb, AsyncResponse asyncResponse, String id) {
        this.aDb = aDb;
        this.asyncResponse = asyncResponse;
        this.id = id;
    }

    @Override
    protected Exercise doInBackground(Void... voids) {
        ExerciseDao exerciseDao = aDb.exerciseDao();
        return exerciseDao.loadById(id, false);
    }

    @Override
    protected void onPostExecute(Exercise result) {
        asyncResponse.respond(var, result);
    }
}
