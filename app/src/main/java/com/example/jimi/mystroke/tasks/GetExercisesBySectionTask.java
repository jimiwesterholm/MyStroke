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

public class GetExercisesBySectionTask extends AsyncTask<Void, Void, List<Exercise>> {
    AppDatabase aDb;
    AsyncResponse asyncResponse;
    String section;

    public GetExercisesBySectionTask(AppDatabase aDb, AsyncResponse asyncResponse, String section) {
        this.aDb = aDb;
        this.asyncResponse = asyncResponse;
        this.section = section;
    }

    @Override
    public List<Exercise> doInBackground(Void...foo) {
        ExerciseDao exerciseDao = aDb.exerciseDao();
        return exerciseDao.getBySection(section);
    }

}
