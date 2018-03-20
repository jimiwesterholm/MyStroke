package com.example.jimi.mystroke.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.jimi.mystroke.models.PatientListImagery;

import java.util.List;

/**
 * Created by jimi on 30/12/2017.
 */

@Dao
public interface PatientListImageryDao {
    @Query("SELECT * FROM patient_list_imagery WHERE toDelete =:toDelete")
    List<PatientListImagery> getAll(boolean toDelete);

    @Query("SELECT * FROM patient_list_imagery WHERE id IN (:patientListImageryIds) AND toDelete =:toDelete")
    List<PatientListImagery> loadAllByIds(String[] patientListImageryIds, boolean toDelete);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(PatientListImagery... patientListImageries);

    @Delete
    void delete(PatientListImagery patientListImagery);

    @Update
    void update(PatientListImagery patientListImagery);

    @Query("SELECT * FROM patient_list_imagery WHERE created > :created AND toDelete =:toDelete")
    List<PatientListImagery> loadChanged(long created, boolean toDelete);

    @Query("SELECT * FROM patient_list_imagery WHERE pID =:pID AND toDelete =:toDelete")
    List<PatientListImagery> loadAllByPatientId(String pID, boolean toDelete);
}
