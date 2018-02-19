package com.example.jimi.mystroke.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.provider.ContactsContract;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.daos.ExerciseDao;
import com.example.jimi.mystroke.models.Exercise;
import com.example.jimi.mystroke.models.ExerciseSection;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by jimi on 17/12/2017.
**/

public class GetSectionsTask extends AsyncTask<Void, Void, List<ExerciseSection>> {
    AppDatabase aDb;
    AsyncResponse asyncResponse;
    List<String> results;

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
        return sections;
    }

    protected void onPostExecute(List<ExerciseSection> results) {
        asyncResponse.respond(results);
    }
}
