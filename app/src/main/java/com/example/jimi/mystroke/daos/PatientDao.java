package com.example.jimi.mystroke.daos; /**
 * Created by jimi on 25/11/2017.
 */
import android.arch.persistence.room.*;

import com.example.jimi.mystroke.models.Patient;

import java.util.List;

@Dao
public interface PatientDao {
    @Query("SELECT * FROM patient")
    List<Patient> getAll();

    @Query("SELECT * FROM patient WHERE idpatient IN (:patientIds)")
    List<Patient> loadAllByIds(int[] patientIds);

    @Insert
    void insertAll(Patient... patients);

    @Delete
    void delete(Patient patient);
}
