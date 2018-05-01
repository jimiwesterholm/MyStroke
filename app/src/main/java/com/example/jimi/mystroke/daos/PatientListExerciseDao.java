package com.example.jimi.mystroke.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.jimi.mystroke.models.PatientListExercise;

import java.util.List;

/**
 * Created by jimi on 30/12/2017.
 */

@Dao
public interface PatientListExerciseDao {
    @Query("SELECT * FROM patient_list_exercise WHERE toDelete =:toDelete")
    List<PatientListExercise> getAll(boolean toDelete);

    @Query("SELECT * FROM patient_list_exercise WHERE toDelete =:toDelete")
    public abstract List<PatientListExercise> loadByToDelete(boolean toDelete);

    @Query("SELECT * FROM patient_list_exercise WHERE id IN (:patientListExerciseIds) AND toDelete =:toDelete")
    List<PatientListExercise> loadAllByIds(String[] patientListExerciseIds, boolean toDelete);

    @Query("SELECT * FROM patient_list_exercise WHERE pID =:pID AND toDelete =:toDelete")
    List<PatientListExercise> loadAllByPatientID(String pID, boolean toDelete);

    @Query("SELECT * FROM patient_list_exercise WHERE pID =:pID AND eID =:eID AND toDelete =:toDelete")
    List<PatientListExercise> loadAllByPatientAndExerciseIDs(String pID, String eID, boolean toDelete);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(PatientListExercise... patientListExercises);

    @Delete
    void delete(PatientListExercise patientListExercise);

    @Query("SELECT * FROM patient_list_exercise WHERE created > :created AND toDelete =:toDelete")
    List<PatientListExercise> loadChanged(long created, boolean toDelete);

    @Query("SELECT * FROM patient_list_exercise WHERE viewed =:viewed AND toDelete =:toDelete")
    List<PatientListExercise> loadByViewed(boolean viewed, boolean toDelete);

    @Query("SELECT eID FROM patient_list_exercise WHERE pID =:pID AND toDelete =:toDelete")
    List<String> getPatientExercises(String pID, boolean toDelete);

    @Update
    void update(PatientListExercise listExercise);

    @Query("SELECT * FROM patient_list_exercise WHERE viewed =:viewed AND pID =:patientId AND toDelete =:toDelete")
    List<PatientListExercise>loadByViewedAndPatientId(String patientId, boolean viewed, boolean toDelete);
}
