package com.example.jimi.mystroke.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.daos.ExerciseDao;
import com.example.jimi.mystroke.models.ExerciseSection;
import com.example.jimi.mystroke.models.PatientListExercise;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jimi on 17/12/2017.
**/

public class GetSectionsTask extends AsyncTask<Void, Void, List<ExerciseSection>> {
    private AppDatabase aDb;
    private AsyncResponse asyncResponse;
    private List<String> results;
    private String patientId;
    public static final int var = 1;

    public GetSectionsTask(Context context, AsyncResponse asyncResponse, String patientId) {
        this.aDb = AppDatabase.getDatabase(context);
        this.asyncResponse = asyncResponse;
        this.patientId = patientId;
    }

    @Override
    protected List<ExerciseSection> doInBackground(Void...foo) {
        List<ExerciseSection> sections = new ArrayList<ExerciseSection>();
        ExerciseDao exerciseDao = aDb.exerciseDao();
        if(patientId != null) {
            List<String> list = aDb.patientListExerciseDao().getPatientExercises(patientId, false);
            List<String> sectionNames = exerciseDao.getSectionsOfIds(list.toArray(new String[0]), false);

            //Get alerts
            List<PatientListExercise> newExercises = aDb.patientListExerciseDao().loadByViewedAndPatientId(patientId, false, false);
            String[] ids = new String[newExercises.size()];
            for (int i = 0; i < newExercises.size(); i++) {
                ids[i] = newExercises.get(i).getEID();
            }
            for (int i = 0; i < sectionNames.size(); i++) {
                int a = exerciseDao.loadAllByIdsFromSection(ids, sectionNames.get(i), false).size();
                sections.add(new ExerciseSection(a, sectionNames.get(i)));
            }
        } else {
            List<String> sectionNames = exerciseDao.getSections(false);
            for (String sectionName : sectionNames) {
                sections.add(new ExerciseSection(0, sectionName));
            }
        }
        //TODO maybe use patient imagery list to store whether they've seen all yet - or search for new ones by created?
        sections.add(new ExerciseSection(0, "Imageries"));
        return sections;
    }

    protected void onPostExecute(List<ExerciseSection> results) {
        asyncResponse.respond(var, results);
    }
}
