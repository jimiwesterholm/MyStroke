package com.example.jimi.mystroke.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.jimi.mystroke.models.DatabaseObject;
import com.example.jimi.mystroke.models.PatientListExercise;

import java.util.List;

/**
 * Created by jimi on 30/12/2017.
 */

@Dao
public interface PatientListExerciseDao {
    @Query("SELECT * FROM patient_list_exercise")
    List<PatientListExercise> getAll();

    @Query("SELECT * FROM patient_list_exercise WHERE listExerciseID IN (:patientListExerciseIds)")
    List<PatientListExercise> loadAllByIds(int[] patientListExerciseIds);

    @Insert
    void insertAll(PatientListExercise... patientListExercises);

    @Delete
    void delete(PatientListExercise patientListExercise);

    @Query("SELECT * FROM patient_list_exercise WHERE created > :created")
    List<PatientListExercise> loadChanged(long created);
}
