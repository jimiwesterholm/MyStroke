package com.example.jimi.mystroke.tasks;

import android.content.Context;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.Globals;
import com.example.jimi.mystroke.models.Assessment;
import com.example.jimi.mystroke.models.Comment;
import com.example.jimi.mystroke.models.DatabaseObject;
import com.example.jimi.mystroke.models.Exercise;
import com.example.jimi.mystroke.models.Imagery;
import com.example.jimi.mystroke.models.Patient;
import com.example.jimi.mystroke.models.PatientAssessesExercise;
import com.example.jimi.mystroke.models.PatientListExercise;
import com.example.jimi.mystroke.models.PatientListImagery;
import com.example.jimi.mystroke.models.Therapist;
import com.example.jimi.mystroke.models.User;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

/**
 * Created by jimi on 04/02/2018.
 */

public class GetChangedRecords {
    private Context context;
    GetChangedRecords(Context context) {
        this.context = context;
    }

    public List<? extends DatabaseObject> getChanged(String className) {
        long latestUpdate = Globals.getInstance().getLatestUpdate();
        switch (className) {
            case "user":
                return AppDatabase.getDatabase(context).userDao().loadChanged(latestUpdate, false);
            case "exercise":
                return AppDatabase.getDatabase(context).exerciseDao().loadChanged(latestUpdate, false);
            case "assessment":
                return AppDatabase.getDatabase(context).assessmentDao().loadChanged(latestUpdate);
            case "comment":
                return AppDatabase.getDatabase(context).commentDao().loadChanged(latestUpdate, false);
            case "imagery":
                return AppDatabase.getDatabase(context).imageryDao().loadChanged(latestUpdate, false);
            case "patient":
                return AppDatabase.getDatabase(context).patientDao().loadChanged(latestUpdate, false);
            case "therapist":
                return AppDatabase.getDatabase(context).therapistDao().loadChanged(latestUpdate, false);
            case "patient_assesses_exercise":
                return AppDatabase.getDatabase(context).patientAssessesExerciseDao().loadChanged(latestUpdate, false);
            case "patient_list_exercise":
                return AppDatabase.getDatabase(context).patientListExerciseDao().loadChanged(latestUpdate, false);
            case "patient_list_imagery":
                return AppDatabase.getDatabase(context).patientListImageryDao().loadChanged(latestUpdate, false);
        }
        return null;
    }
}
