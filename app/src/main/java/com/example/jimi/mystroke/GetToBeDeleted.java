package com.example.jimi.mystroke;

import android.content.Context;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.Globals;
import com.example.jimi.mystroke.models.DatabaseObject;

import java.util.List;

public class GetToBeDeleted {
    private Context context;

    public GetToBeDeleted(Context context) {
        this.context = context;
    }

    public List<? extends DatabaseObject> getToBeDeleted(String className) {
        long latestUpdate = Globals.getInstance().getLatestUpdate();
        switch (className) {
            case "user":
                return AppDatabase.getDatabase(context).userDao().loadByToDelete(true);
            case "exercise":
                return AppDatabase.getDatabase(context).exerciseDao().loadByToDelete(true);
            case "assessment":
                return AppDatabase.getDatabase(context).assessmentDao().loadByToDelete(true);
            case "comment":
                return AppDatabase.getDatabase(context).commentDao().loadByToDelete(true);
            case "imagery":
                return AppDatabase.getDatabase(context).imageryDao().loadByToDelete(true);
            case "patient":
                return AppDatabase.getDatabase(context).patientDao().loadByToDelete(true);
            case "therapist":
                return AppDatabase.getDatabase(context).therapistDao().loadByToDelete(true);
            case "patient_assesses_exercise":
                return AppDatabase.getDatabase(context).patientAssessesExerciseDao().loadByToDelete(true);
            case "patient_list_exercise":
                return AppDatabase.getDatabase(context).patientListExerciseDao().loadByToDelete(true);
            case "patient_list_imagery":
                return AppDatabase.getDatabase(context).patientListImageryDao().loadByToDelete(true);
            case "exercise_image":
                return AppDatabase.getDatabase(context).exerciseImageDao().loadByToDelete(true);
            case "therapist_assesses_exercise":
                return AppDatabase.getDatabase(context).therapistAssessesExerciseDao().loadByToDelete(true);
            case "help_page":
                return AppDatabase.getDatabase(context).helpPageDao().loadByToDelete(true);
            case "assessment_item":
                return AppDatabase.getDatabase(context).assessmentItemDao().loadByToDelete(true);
            case "assessment_result_double":
                return AppDatabase.getDatabase(context).assessmentResultDoubleDao().loadByToDelete(true);
            case "assessment_result_int":
                return AppDatabase.getDatabase(context).assessmentResultIntDao().loadByToDelete(true);
            case "assessment_result_string":
                return AppDatabase.getDatabase(context).assessmentResultStringDao().loadByToDelete(true);
            case "assessment_result_boolean":
                return AppDatabase.getDatabase(context).assessmentResultBooleanDao().loadByToDelete(true);
            case "register_code":
                return AppDatabase.getDatabase(context).registerCodeDao().loadByToDelete(true);
        }
        return null;
    }
}
