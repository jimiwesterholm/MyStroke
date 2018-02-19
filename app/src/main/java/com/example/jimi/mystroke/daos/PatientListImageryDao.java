package com.example.jimi.mystroke.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.jimi.mystroke.models.PatientListImagery;

import java.util.List;

/**
 * Created by jimi on 30/12/2017.
 */

@Dao
public interface PatientListImageryDao {
    @Query("SELECT * FROM patient_list_imagery")
    List<PatientListImagery> getAll();

    @Query("SELECT * FROM patient_list_imagery WHERE listImageryID IN (:patientListImageryIds)")
    List<PatientListImagery> loadAllByIds(int[] patientListImageryIds);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(PatientListImagery... patientListImageries);

    @Delete
    void delete(PatientListImagery patientListImagery);

    @Query("SELECT * FROM patient_list_imagery WHERE created > :created")
    List<PatientListImagery> loadChanged(long created);
}
