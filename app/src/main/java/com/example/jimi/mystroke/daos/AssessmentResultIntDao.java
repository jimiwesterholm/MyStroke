package com.example.jimi.mystroke.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.jimi.mystroke.models.AssessmentResultInt;

import java.util.List;

/**
 * Created by jimi on 24/03/2018.
 */
@Dao
public interface AssessmentResultIntDao {
    @Query("SELECT * FROM assessment_result_int WHERE toDelete =:toDelete")
    List<AssessmentResultInt> getAll(boolean toDelete);

    @Query("SELECT * FROM assessment_result_int WHERE id IN (:ids) AND toDelete =:toDelete")
    List<AssessmentResultInt> loadAllByIds(String[] ids, boolean toDelete);

    @Query("SELECT * FROM assessment_result_int WHERE created > (:created) AND toDelete =:toDelete")
    List<AssessmentResultInt> loadChanged(long created, boolean toDelete);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(AssessmentResultInt... assessmentResultInts);

    @Delete
    void delete(AssessmentResultInt assessmentResultInt);
}
