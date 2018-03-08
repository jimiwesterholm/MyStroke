package com.example.jimi.mystroke.daos; /**
 * Created by jimi on 25/11/2017.
 */
import android.arch.persistence.room.*;

import com.example.jimi.mystroke.models.DatabaseObject;
import com.example.jimi.mystroke.models.Patient;

import java.util.List;

@Dao
public interface PatientDao {
    @Query("SELECT * FROM patient WHERE toDelete =:toDelete")
    List<Patient> getAll(boolean toDelete);

    @Query("SELECT * FROM patient WHERE SQLiteId IN (:patientIds) AND toDelete =:toDelete")
    List<Patient> loadAllByIds(int[] patientIds, boolean toDelete);

    @Query("SELECT * FROM patient WHERE SQLiteId =:patientId AND toDelete =:toDelete")
    Patient loadById(int patientId, boolean toDelete);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Patient... patients);

    @Delete
    void delete(Patient patient);

    @Query("SELECT * FROM patient WHERE created > :created AND toDelete =:toDelete")
    List<Patient> loadChanged(long created, boolean toDelete);
}
