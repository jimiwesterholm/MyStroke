package com.example.jimi.mystroke.daos; /**
 * Created by jimi on 25/11/2017.
 */
import android.arch.persistence.room.*;

import com.example.jimi.mystroke.models.DatabaseObject;
import com.example.jimi.mystroke.models.Patient;

import java.util.List;

@Dao
public interface PatientDao {
    @Query("SELECT * FROM patient WHERE toDelete =:false")
    List<Patient> getAll();

    @Query("SELECT * FROM patient WHERE id IN (:patientIds) AND toDelete =:false")
    List<Patient> loadAllByIds(int[] patientIds);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Patient... patients);

    @Delete
    void delete(Patient patient);

    @Query("SELECT * FROM patient WHERE created > :created AND toDelete =:false")
    List<Patient> loadChanged(long created);
}
