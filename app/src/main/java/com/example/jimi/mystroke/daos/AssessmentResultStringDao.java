package com.example.jimi.mystroke.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.jimi.mystroke.models.AssessmentResultString;

import java.util.List;

/**
 * Created by jimi on 24/03/2018.
 */
@Dao
public interface AssessmentResultStringDao {
    @Query("SELECT * FROM assessment_result_string WHERE toDelete =:toDelete")
    List<AssessmentResultString> getAll(boolean toDelete);

    @Query("SELECT * FROM assessment_result_string WHERE id IN (:ids) AND toDelete =:toDelete")
    List<AssessmentResultString> loadAllByIds(String[] ids, boolean toDelete);

    @Query("SELECT * FROM assessment_result_string WHERE created > (:created) AND toDelete =:toDelete")
    List<AssessmentResultString> loadChanged(long created, boolean toDelete);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(AssessmentResultString... assessmentResultStrings);

    @Delete
    void delete(AssessmentResultString assessmentResultString);
}
