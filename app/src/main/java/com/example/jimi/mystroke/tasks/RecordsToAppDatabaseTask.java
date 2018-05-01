package com.example.jimi.mystroke.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.Globals;
import com.example.jimi.mystroke.daos.AssessmentDao;
import com.example.jimi.mystroke.daos.AssessmentItemDao;
import com.example.jimi.mystroke.daos.AssessmentResultBooleanDao;
import com.example.jimi.mystroke.daos.AssessmentResultDoubleDao;
import com.example.jimi.mystroke.daos.AssessmentResultIntDao;
import com.example.jimi.mystroke.daos.AssessmentResultStringDao;
import com.example.jimi.mystroke.daos.CommentDao;
import com.example.jimi.mystroke.daos.ExerciseDao;
import com.example.jimi.mystroke.daos.ExerciseImageDao;
import com.example.jimi.mystroke.daos.HelpPageDao;
import com.example.jimi.mystroke.daos.ImageryDao;
import com.example.jimi.mystroke.daos.PatientAssessesExerciseDao;
import com.example.jimi.mystroke.daos.PatientDao;
import com.example.jimi.mystroke.daos.PatientListExerciseDao;
import com.example.jimi.mystroke.daos.PatientListImageryDao;
import com.example.jimi.mystroke.daos.RegisterCodeDao;
import com.example.jimi.mystroke.daos.TherapistAssessesExerciseDao;
import com.example.jimi.mystroke.daos.TherapistDao;
import com.example.jimi.mystroke.daos.UserDao;
import com.example.jimi.mystroke.models.Assessment;
import com.example.jimi.mystroke.models.AssessmentItem;
import com.example.jimi.mystroke.models.AssessmentResultBoolean;
import com.example.jimi.mystroke.models.AssessmentResultDouble;
import com.example.jimi.mystroke.models.AssessmentResultInt;
import com.example.jimi.mystroke.models.AssessmentResultString;
import com.example.jimi.mystroke.models.Comment;
import com.example.jimi.mystroke.models.Exercise;
import com.example.jimi.mystroke.models.ExerciseImage;
import com.example.jimi.mystroke.models.HelpPage;
import com.example.jimi.mystroke.models.Imagery;
import com.example.jimi.mystroke.models.Patient;
import com.example.jimi.mystroke.models.PatientAssessesExercise;
import com.example.jimi.mystroke.models.PatientListExercise;
import com.example.jimi.mystroke.models.PatientListImagery;
import com.example.jimi.mystroke.models.RegisterCode;
import com.example.jimi.mystroke.models.Therapist;
import com.example.jimi.mystroke.models.TherapistAssessesExercise;
import com.example.jimi.mystroke.models.User;

import java.util.Arrays;

/**
 * Created by jimi on 25/01/2018.
 */

public class RecordsToAppDatabaseTask extends AsyncTask<Object, Integer, Boolean> {
    private String className;
    private AppDatabase aDb;
    private Context context;

    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }

    public RecordsToAppDatabaseTask(String className, Context context) {
        this.className = className;
        this.aDb = AppDatabase.getDatabase(context);
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(Object[] objects) {
        switch (className) {
            case "user":
                UserDao userDao = aDb.userDao();
                userDao.upsertAll(Arrays.copyOf(objects, objects.length, User[].class));
                break;
            case "exercise":
                ExerciseDao exerciseDao = aDb.exerciseDao();
                exerciseDao.upsertAll(Arrays.copyOf(objects, objects.length, Exercise[].class));
                break;
            case "exercise_image":
                ExerciseImageDao exerciseImageDao = aDb.exerciseImageDao();
                exerciseImageDao.upsertAll(Arrays.copyOf(objects, objects.length, ExerciseImage[].class));
                break;
            case "assessment":
                AssessmentDao assessmentDao = aDb.assessmentDao();
                assessmentDao.insertAll(Arrays.copyOf(objects, objects.length, Assessment[].class));
                break;
            case "comment":
                CommentDao commentDao = aDb.commentDao();
                commentDao.insertAll(Arrays.copyOf(objects, objects.length, Comment[].class));
                break;
            case "imagery":
                ImageryDao imageryDao =aDb.imageryDao();
                imageryDao.insertAll(Arrays.copyOf(objects, objects.length, Imagery[].class));
                break;
            case "patient":
                PatientDao patientDao = aDb.patientDao();
                patientDao.insertAll(Arrays.copyOf(objects, objects.length, Patient[].class));
                break;
            case "therapist":
                TherapistDao therapistDao = aDb.therapistDao();
                therapistDao.insertAll(Arrays.copyOf(objects, objects.length, Therapist[].class));
                break;
            case "patient_assesses_exercise":
                PatientAssessesExerciseDao patientAssessesExerciseDao = aDb.patientAssessesExerciseDao();
                patientAssessesExerciseDao.insertAll(Arrays.copyOf(objects, objects.length, PatientAssessesExercise[].class));
                break;
            case "patient_list_exercise":
                PatientListExerciseDao patientListExerciseDao = aDb.patientListExerciseDao();
                patientListExerciseDao.insertAll(Arrays.copyOf(objects, objects.length, PatientListExercise[].class));
                break;
            case "patient_list_imagery":
                PatientListImageryDao patientListImageryDao = aDb.patientListImageryDao();
                patientListImageryDao.insertAll(Arrays.copyOf(objects, objects.length, PatientListImagery[].class));
                break;
            case "therapist_assesses_exercise":
                TherapistAssessesExerciseDao therapistAssessesExerciseDao = aDb.therapistAssessesExerciseDao();
                therapistAssessesExerciseDao.insertAll(Arrays.copyOf(objects, objects.length, TherapistAssessesExercise[].class));
                break;
            case "help_page":
                HelpPageDao helpPageDao = aDb.helpPageDao();
                helpPageDao.insertAll(Arrays.copyOf(objects, objects.length, HelpPage[].class));
                break;
            case "assessment_item":
                AssessmentItemDao assessmentItemDao = aDb.assessmentItemDao();
                assessmentItemDao.insertAll(Arrays.copyOf(objects, objects.length, AssessmentItem[].class));
                break;
            case "assessment_result_double":
                AssessmentResultDoubleDao assessmentResultDoubleDao = aDb.assessmentResultDoubleDao();
                assessmentResultDoubleDao.insertAll(Arrays.copyOf(objects, objects.length, AssessmentResultDouble[].class));
                break;
            case "assessment_result_int":
                AssessmentResultIntDao assessmentResultIntDao= aDb.assessmentResultIntDao();
                assessmentResultIntDao.insertAll(Arrays.copyOf(objects, objects.length, AssessmentResultInt[].class));
                break;
            case "assessment_result_string":
                AssessmentResultStringDao assessmentResultStringDao = aDb.assessmentResultStringDao();
                assessmentResultStringDao.insertAll(Arrays.copyOf(objects, objects.length, AssessmentResultString[].class));
                break;
            case "assessment_result_boolean":
                AssessmentResultBooleanDao assessmentResultBooleanDao = aDb.assessmentResultBooleanDao();
                assessmentResultBooleanDao.insertAll(Arrays.copyOf(objects, objects.length, AssessmentResultBoolean[].class));
                break;
            case "register_code":
                RegisterCodeDao registerCodeDao = aDb.registerCodeDao();
                registerCodeDao.insertAll(Arrays.copyOf(objects, objects.length, RegisterCode[].class));
                break;
                default:
                    Log.e("ERROR", "Class name not found!");
        }
        new SyncDatabaseTask(context).run();
        return true;
    }
}
