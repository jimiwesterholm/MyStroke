package com.example.jimi.mystroke; /**
 * Created by jimi on 25/11/2017.
 */
import android.arch.persistence.room.*;
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

import java.sql.Date;
import java.sql.Time;

@Database(entities = {User.class, Patient.class, Therapist.class, Exercise.class, Assessment.class, Comment.class, Imagery.class, PatientListExercise.class, PatientListImagery.class, PatientAssessesExercise.class},
        version = 14)
@TypeConverters(PersistenceTypeConverters.class)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase APPDB;

    public abstract ExerciseDao exerciseDao();
    public abstract AssessmentDao assessmentDao();
    public abstract UserDao userDao();
    public abstract PatientDao patientDao();
    public abstract TherapistDao therapistDao();
    public abstract CommentDao commentDao();
    public abstract ImageryDao imageryDao();
    public abstract PatientAssessesExerciseDao patientAssessesExerciseDao();
    public abstract PatientListImageryDao patientListImageryDao();
    public abstract PatientListExerciseDao patientListExerciseDao();

    public static AppDatabase getDatabase(Context context) {
        if (APPDB == null) {
            APPDB = Room.databaseBuilder(context, AppDatabase.class, "database").fallbackToDestructiveMigration().build();
        }
        return APPDB;
    }

    /*//Type converters
    @TypeConverter
    public long dateToLong(Date date) {
        return date.getTime();
    }

    @TypeConverter
    public long timeToLong(Time time) {
        return time.getTime();
    }*/

    public static void destroyInstance() {
        APPDB = null;
    }
}

