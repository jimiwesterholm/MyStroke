package com.example.jimi.mystroke.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.daos.AssessmentDao;
import com.example.jimi.mystroke.daos.CommentDao;
import com.example.jimi.mystroke.daos.ExerciseDao;
import com.example.jimi.mystroke.daos.ImageryDao;
import com.example.jimi.mystroke.daos.PatientAssessesExerciseDao;
import com.example.jimi.mystroke.daos.PatientDao;
import com.example.jimi.mystroke.daos.PatientListExerciseDao;
import com.example.jimi.mystroke.daos.PatientListImageryDao;
import com.example.jimi.mystroke.daos.TherapistDao;
import com.example.jimi.mystroke.daos.UserDao;
import com.example.jimi.mystroke.models.Assessment;
import com.example.jimi.mystroke.models.Comment;
import com.example.jimi.mystroke.models.Exercise;
import com.example.jimi.mystroke.models.Imagery;
import com.example.jimi.mystroke.models.Patient;
import com.example.jimi.mystroke.models.PatientAssessesExercise;
import com.example.jimi.mystroke.models.PatientListExercise;
import com.example.jimi.mystroke.models.PatientListImagery;
import com.example.jimi.mystroke.models.Therapist;
import com.example.jimi.mystroke.models.User;

import java.util.Arrays;

/**
 * Created by jimi on 25/01/2018.
 */

public class RecordsToAppDatabaseTask extends AsyncTask<Object, Integer, Boolean> {
    private String className;
    private AppDatabase aDb ;

    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }

    public RecordsToAppDatabaseTask(String className, AppDatabase aDb) {
        this.className = className;
        this.aDb = aDb;
    }

    @Override
    protected Boolean doInBackground(Object[] objects) {
        switch (className) {
            case "user":
                UserDao userDao = aDb.userDao();
                userDao.insertAll(Arrays.copyOf(objects, objects.length, User[].class));
                break;
            case "exercise":
                ExerciseDao exerciseDao = aDb.exerciseDao();
                exerciseDao.insertAll(Arrays.copyOf(objects, objects.length, Exercise[].class));
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
            case "patient_assessess_exercise":
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
        }

        return true;
    }
}
