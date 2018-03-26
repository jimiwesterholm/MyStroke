package com.example.jimi.mystroke.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.jimi.mystroke.models.PatientAssessesExercise;
import com.example.jimi.mystroke.models.TherapistAssessesExercise;

import java.util.List;

/**
 * Created by jimi on 30/12/2017.
 */

@Dao
public interface TherapistAssessesExerciseDao {
    @Query("SELECT * FROM therapist_assesses_exercise WHERE toDelete =:toDelete")
    List<TherapistAssessesExercise> getAll(boolean toDelete);

    @Query("SELECT * FROM therapist_assesses_exercise WHERE id IN (:patientAssessesExerciseIds) AND toDelete =:toDelete")
    List<TherapistAssessesExercise> loadAllByIds(String[] patientAssessesExerciseIds, boolean toDelete);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(TherapistAssessesExercise... therapistAssessesExercises);

    @Delete
    void delete(TherapistAssessesExercise therapistAssessesExercise);

    @Query("SELECT * FROM therapist_assesses_exercise WHERE created > :created AND toDelete =:toDelete")
    List<TherapistAssessesExercise> loadChanged(long created, boolean toDelete);
}
