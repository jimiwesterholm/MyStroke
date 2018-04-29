package com.example.jimi.mystroke;

import android.content.Context;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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
            JSONArray recordJSON = records.getJSONArray(i);

            Object[] record = new Object[recordJSON.length()];
            for(int j=0; j<recordJSON.length(); j++) {
                try {
                    String temp = recordJSON.getString(j);
                    if(temp == "null") {
                        record[j] = null;
                    } else {
                        record[j] = recordJSON.get(j);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            switch (className) {
                //TODO Make these use the string resource?
                case "user":
                    results.add((T) new User((String) record[0], (String) record[1], (String) record[2], (String) record[3], (int) record[4], (int) record[5], (String) record[6], (String) record[7], (String) record[8]));
                    break;
                case "exercise":
                    results.add((T) new Exercise((String) record[0], (String) record[1], (String) record[2], (String) record[3], (String) record[4], (String) record[5]));
                    break;
                case "exercise_image":
                    results.add((T) new ExerciseImage((String) record[0], (String) record[1], (String) record[3], (int) record[2]));
                    break;
                case "assessment":
                    results.add ((T) new Assessment((String) record[0], (String) record[1]));
                    break;
                case "comment":
                    results.add((T) new Comment((String) record[0] , (Date) record[1], (Time) record[2], (String) record[3], (String) record[4], (String) record[5], (int) record[6]));
                    break;
                case "imagery":
                    results.add((T) new Imagery((String) record[0], (String) record[1]));
                    break;
                case "patient":
                    results.add((T) new Patient((String) record[0], (String) record[1], (String) record[2], (int) record[3]));
                    break;
                case "therapist":
                    results.add((T) new Therapist((String) record[0], (String) record[3], (String) record[1], (int) record[4]));
                    break;
                case "patient_assesses_exercise":
                    results.add((T) new PatientAssessesExercise((String) record[0], (String) record[1], (String) record[2], (int) record[3], (Date) record[4], (Time) record[5]));
                    break;
                case "therapist_assesses_exercise":
                    results.add((T) new TherapistAssessesExercise((String) record[0], (String) record[1], (String) record[2], (String) record[3], (Date) record[4], (Time) record[5]));
                    break;
                case "patient_list_exercise":
                    results.add((T) new PatientListExercise((String) record[0], (String) record[1], (String) record[2], (String) record[3]));
                    break;
                case "patient_list_imagery":
                    results.add((T) new PatientListImagery((String) record[0], (String) record[1], (String) record[2]));
                    break;
                case "help_page":
                    results.add((T) new HelpPage((String) record[0], (String) record[1], (String) record[2], (String) record[3]));
                    break;
                case "assessment_item":
                    results.add((T) new AssessmentItem((String) record[0], (String) record[1], (String) record[2], (String) record[3]));
                    break;
                case "assessment_result_double":
                    results.add((T) new AssessmentResultDouble((String) record[0], (String) record[1], (String) record[2], (double) record[3]));
                    break;
                case "assessment_result_int":
                    results.add((T) new AssessmentResultInt((String) record[0], (String) record[1], (String) record[2], (int) record[3]));
                    break;
                case "assessment_result_string":
                    results.add((T) new AssessmentResultString((String) record[0], (String) record[1], (String) record[2], (String) record[3]));
                    break;
                case "assessment_result_boolean":
                    results.add((T) new AssessmentResultBoolean((String) record[0], (String) record[1], (String) record[2], (boolean) record[3]));
                    break;
                case "register_code":
                    results.add((T) new RegisterCode((String) record[0], (int) record[1], (long) record[2], (String) record[3]));
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
                break;
            case "exercise_image":
                ExerciseImageDao exerciseImageDao = aDb.exerciseImageDao();
                exerciseImageDao.insertAll(results.toArray(new ExerciseImage[records.length()]));
                break;
            case "assessment":
                AssessmentDao assessmentDao = aDb.assessmentDao();
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
            case "patient_assesses_exercise":
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
            case "therapist_assesses_exercise":
                TherapistAssessesExerciseDao therapistAssessesExerciseDao = aDb.therapistAssessesExerciseDao();
                therapistAssessesExerciseDao.insertAll(results.toArray(new TherapistAssessesExercise[records.length()]));
                break;
            case "help_page":
                HelpPageDao helpPageDao = aDb.helpPageDao();
                helpPageDao.insertAll(results.toArray(new HelpPage[records.length()]));
                break;
            case "assessment_item":
                AssessmentItemDao assessmentItemDao = aDb.assessmentItemDao();
                assessmentItemDao.insertAll(results.toArray(new AssessmentItem[records.length()]));
                break;
            case "assessment_result_double":
                AssessmentResultDoubleDao assessmentResultDoubleDao = aDb.assessmentResultDoubleDao();
                assessmentResultDoubleDao.insertAll(results.toArray(new AssessmentResultDouble[records.length()]));
                break;
            case "assessment_result_int":
                AssessmentResultIntDao assessmentResultIntDao= aDb.assessmentResultIntDao();
                assessmentResultIntDao.insertAll(results.toArray(new AssessmentResultInt[records.length()]));
                break;
            case "assessment_result_string":
                AssessmentResultStringDao assessmentResultStringDao = aDb.assessmentResultStringDao();
                assessmentResultStringDao.insertAll(results.toArray(new AssessmentResultString[records.length()]));
                break;
            case "assessment_result_boolean":
                AssessmentResultBooleanDao assessmentResultBooleanDao = aDb.assessmentResultBooleanDao();
                assessmentResultBooleanDao.insertAll(results.toArray(new AssessmentResultBoolean[records.length()]));
                break;
            case "register_code":
                RegisterCodeDao registerCodeDao = aDb.registerCodeDao();
                registerCodeDao.insertAll(results.toArray(new RegisterCode[records.length()]));
                break;
        }

        return true;
    }
}
