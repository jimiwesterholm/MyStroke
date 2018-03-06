package com.example.jimi.mystroke.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.jimi.mystroke.models.DatabaseObject;
import com.example.jimi.mystroke.models.PatientAssessesExercise;

import java.util.List;

/**
 * Created by jimi on 30/12/2017.
 */

@Dao
public interface PatientAssessesExerciseDao {
    @Query("SELECT * FROM patient_assessment WHERE toDelete =:false")
    List<PatientAssessesExercise> getAll();

    @Query("SELECT * FROM patient_assessment WHERE id IN (:patientAssessesExerciseIds) AND toDelete =:false")
    List<PatientAssessesExercise> loadAllByIds(int[] patientAssessesExerciseIds);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(PatientAssessesExercise... patientAssessesExercises);

    @Delete
    void delete(PatientAssessesExercise patientAssessesExercise);

    @Query("SELECT * FROM patient_assessment WHERE created > :created AND toDelete =:false")
    List<PatientAssessesExercise> loadChanged(long created);
}
