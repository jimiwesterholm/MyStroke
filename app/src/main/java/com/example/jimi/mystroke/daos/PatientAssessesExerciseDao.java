package com.example.jimi.mystroke.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.jimi.mystroke.models.PatientAssessesExercise;

import java.util.List;

/**
 * Created by jimi on 30/12/2017.
 */

@Dao
public interface PatientAssessesExerciseDao {
    @Query("SELECT * FROM patient_assessment")
    List<PatientAssessesExercise> getAll();

    @Query("SELECT * FROM patient_assessment WHERE patientAssessesExerciseID IN (:patientAssessesExerciseIds)")
    List<PatientAssessesExercise> loadAllByIds(int[] patientAssessesExerciseIds);

    @Insert
    void insertAll(PatientAssessesExercise... patientAssessesExercises);

    @Delete
    void delete(PatientAssessesExercise patientAssessesExercise);
}
