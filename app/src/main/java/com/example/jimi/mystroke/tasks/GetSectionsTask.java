package com.example.jimi.mystroke.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.daos.ExerciseDao;
import com.example.jimi.mystroke.models.ExerciseSection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jimi on 17/12/2017.
**/

public class GetSectionsTask extends AsyncTask<Void, Void, List<ExerciseSection>> {
    private AppDatabase aDb;
    private AsyncResponse asyncResponse;
    private List<String> results;
    public static final int var = 1;

    public GetSectionsTask(Context context, AsyncResponse asyncResponse) {
        this.aDb = AppDatabase.getDatabase(context);
        this.asyncResponse = asyncResponse;
    }

    @Override
    protected List<ExerciseSection> doInBackground(Void...foo) {
        ExerciseDao exerciseDao = aDb.exerciseDao();
        List<String> sectionNames = exerciseDao.getSections();
        List<ExerciseSection> sections = new ArrayList<ExerciseSection>();
        for (String sectionName : sectionNames) {
            sections.add(new ExerciseSection(exerciseDao.getBySectionAndViewed(sectionName, false).size(), sectionName));
        }
        sections.add(new ExerciseSection(aDb.imageryDao().getAll().size(), "Imageries"));
        return sections;
    }

    protected void onPostExecute(List<ExerciseSection> results) {
        asyncResponse.respond(var, results);
    }
}
