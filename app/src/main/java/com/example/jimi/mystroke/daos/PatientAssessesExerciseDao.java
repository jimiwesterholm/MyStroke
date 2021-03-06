package com.example.jimi.mystroke.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.jimi.mystroke.models.PatientAssessesExercise;

import java.util.List;

/**
 * Created by jimi on 30/12/2017.
 */

@Dao
public interface PatientAssessesExerciseDao {
    @Query("SELECT * FROM patient_assesses_exercise WHERE toDelete =:toDelete")
    List<PatientAssessesExercise> getAll(boolean toDelete);

    @Query("SELECT * FROM patient_assesses_exercise WHERE toDelete =:toDelete")
    public abstract List<PatientAssessesExercise> loadByToDelete(boolean toDelete);

    @Query("SELECT * FROM patient_assesses_exercise WHERE id IN (:patientAssessesExerciseIds) AND toDelete =:toDelete")
    List<PatientAssessesExercise> loadAllByIds(String[] patientAssessesExerciseIds, boolean toDelete);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(PatientAssessesExercise... patientAssessesExercises);

    @Delete
    void delete(PatientAssessesExercise patientAssessesExercise);

    @Query("SELECT * FROM patient_assesses_exercise WHERE created > :created AND toDelete =:toDelete")
    List<PatientAssessesExercise> loadChanged(long created, boolean toDelete);
}
