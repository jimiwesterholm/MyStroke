package com.example.jimi.mystroke.tasks;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.daos.ExerciseDao;
import com.example.jimi.mystroke.models.Exercise;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by jimi on 17/12/2017.
 */

public class GetExerciseByIdTask implements Callable {
    AppDatabase aDb;
    int id;

    public GetExerciseByIdTask(AppDatabase aDb, int id) {
        this.aDb = aDb;
        this.id = id;
    }

    @Override
    public Exercise call() {
        ExerciseDao exerciseDao = aDb.exerciseDao();
        return exerciseDao.loadById(id);
    }

}
