package com.example.jimi.mystroke.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.jimi.mystroke.models.Assessment;
import com.example.jimi.mystroke.models.DatabaseObject;

import java.util.List;

/**
 * Created by jimi on 14/12/2017.
 */
@Dao
public interface AssessmentDao {
    @Query("SELECT * FROM assessment")
    List<Assessment> getAll();

    @Query("SELECT * FROM assessment WHERE idassessment IN (:assessmentIds)")
    List<Assessment> loadAllByIds(String[] assessmentIds);

    @Query("SELECT * FROM assessment WHERE created > (:created)")
    List<Assessment> loadChanged(long created);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Assessment... assessments);

    @Delete
    void delete(Assessment assessment);
}
