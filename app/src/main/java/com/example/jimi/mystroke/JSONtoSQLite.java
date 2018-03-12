package com.example.jimi.mystroke;

import android.content.Context;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by jimi on 30/11/2017.
 */

public class JSONtoSQLite {

    public static <T>boolean parseAndUploadJSON(String json, String className, Context context) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        jsonObject = (JSONObject) jsonObject.get(className);
        JSONArray columns = jsonObject.getJSONArray("columns");
        JSONArray records = jsonObject.getJSONArray("records");

        List<T> results = new ArrayList<T>();

        //TODO make sure this works with any class, switch to static methods in models? e.g. static T JSONToObject(JSONArray json)
        for (int i = 0; i < records.length(); i++) {
            JSONArray record = records.getJSONArray(i);
            switch (className) {
                //TODO Make these use the string resource
                case "user":
                    results.add((T) new User((String) record.get(0), (String) record.get(1), (String) record.get(2), (String) record.get(3), (int) record.get(4), (int) record.get(5), (String) record.get(6), (String) record.get(7), (String) record.get(8)));
                    break;
                case "exercise":
                    results.add((T) new Exercise((String) record.get(0), (String) record.get(1), (String) record.get(2), (String) record.get(3), (String) record.get(4)));
                    break;
                case "assessment":
                    results.add ((T) new Assessment((String) record.get(0), (int) record.get(1), (int) record.get(2), (String) record.get(3)));
                    break;
                case "comment":
                    results.add((T) new Comment((String) record.get(0) , (Date) record.get(1), (Time) record.get(2), (String) record.get(3), (String) record.get(4), (String) record.get(5), (int) record.get(6)));
                    break;
                case "imagery":
                    results.add((T) new Imagery((String) record.get(0), (String) record.get(1)));
                    break;
                case "patient":
                    results.add((T) new Patient((String) record.get(0), (String) record.get(1), (int) record.get(2), (int) record.get(3)));
                    break;
                case "therapist":
                    results.add((T) new Therapist((String) record.get(0), (String) record.get(3), (String) record.get(1), (int) record.get(4)));
                    break;
                case "patient_assessess_exercise":
                    results.add((T) new PatientAssessesExercise((String) record.get(0), (String) record.get(1), (String) record.get(2), (double) record.get(3), (Date) record.get(4), (Time) record.get(5)));
                    break;
                case "patient_list_exercise":
                    results.add((T) new PatientListExercise((String) record.get(0), (String) record.get(1), (String) record.get(2), (String) record.get(3)));
                    break;
                case "patient_list_imagery":
                    results.add((T) new PatientListImagery((String) record.get(0), (String) record.get(1), (String) record.get(2)));
                    break;
            }
        }


        AppDatabase aDb = AppDatabase.getDatabase(context);
        switch (className) {
            //TODO Make these use the string resource
            case "user":
                UserDao userDao = aDb.userDao();
                userDao.upsertAll(results.toArray(new User[records.length()]));
                break;
            case "exercise":
                ExerciseDao exerciseDao = aDb.exerciseDao();
                exerciseDao.insertAll(results.toArray(new Exercise[records.length()]));
                //List<Exercise> exercisessss = exerciseDao.getAll();
                break;
            case "assessment":
                AssessmentDao assessmentDao = aDb.assessmentDao();  //.insertAll(results.toArray(new [records.length()]));
                assessmentDao.insertAll(results.toArray(new Assessment[records.length()]));
                break;
            case "comment":
                CommentDao commentDao = aDb.commentDao();
                commentDao.insertAll(results.toArray(new Comment[records.length()]));
                break;
            case "imagery":
                ImageryDao imageryDao =aDb.imageryDao();
                imageryDao.insertAll(results.toArray(new Imagery[records.length()]));
                break;
            case "patient":
                PatientDao patientDao = aDb.patientDao();
                patientDao.insertAll(results.toArray(new Patient[records.length()]));
                break;
            case "therapist":
                TherapistDao therapistDao = aDb.therapistDao();
                therapistDao.insertAll(results.toArray(new Therapist[records.length()]));
                break;
            case "patient_assessess_exercise":
                PatientAssessesExerciseDao patientAssessesExerciseDao = aDb.patientAssessesExerciseDao();
                patientAssessesExerciseDao.insertAll(results.toArray(new PatientAssessesExercise[records.length()]));
                break;
            case "patient_list_exercise":
                PatientListExerciseDao patientListExerciseDao = aDb.patientListExerciseDao();
                patientListExerciseDao.insertAll(results.toArray(new PatientListExercise[records.length()]));
                break;
            case "patient_list_imagery":
                PatientListImageryDao patientListImageryDao = aDb.patientListImageryDao();
                patientListImageryDao.insertAll(results.toArray(new PatientListImagery[records.length()]));
                break;
        }

        return true;
    }
}
