package com.example.jimi.mystroke;

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

public class GetChangedRecords {
    private Context context;
    public GetChangedRecords(Context context) {
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
                return AppDatabase.getDatabase(context).assessmentDao().loadChanged(latestUpdate, false);
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
            case "exercise_image":
                return AppDatabase.getDatabase(context).exerciseImageDao().loadChanged(latestUpdate, false);
            case "therapist_assesses_exercise":
                return AppDatabase.getDatabase(context).therapistAssessesExerciseDao().loadChanged(latestUpdate, false);
            case "help_page":
                return AppDatabase.getDatabase(context).helpPageDao().loadChanged(latestUpdate, false);
            case "assessment_item":
                return AppDatabase.getDatabase(context).assessmentItemDao().loadChanged(latestUpdate, false);
            case "assessment_result_double":
                return AppDatabase.getDatabase(context).assessmentResultDoubleDao().loadChanged(latestUpdate, false);
            case "assessment_result_int":
                return AppDatabase.getDatabase(context).assessmentResultIntDao().loadChanged(latestUpdate, false);
            case "assessment_result_string":
                return AppDatabase.getDatabase(context).assessmentResultStringDao().loadChanged(latestUpdate, false);
            case "assessment_result_boolean":
                return AppDatabase.getDatabase(context).assessmentResultBooleanDao().loadChanged(latestUpdate, false);
            case "register_code":
                return AppDatabase.getDatabase(context).registerCodeDao().loadChanged(latestUpdate, false);
        }
        return null;
    }
}
