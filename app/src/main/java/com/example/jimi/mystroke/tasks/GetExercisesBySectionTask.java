package com.example.jimi.mystroke.tasks;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.daos.ExerciseDao;
import com.example.jimi.mystroke.models.Exercise;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by jimi on 17/12/2017.
 */

public class GetExercisesBySectionTask implements Callable {
    AppDatabase aDb;
    String section;

    public GetExercisesBySectionTask(AppDatabase aDb, String section) {
        this.aDb = aDb;
        this.section = section;
    }

    @Override
    public List<Exercise> call() {
        ExerciseDao exerciseDao = aDb.exerciseDao();
        return exerciseDao.getBySection(section);
    }

}
