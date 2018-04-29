package com.example.jimi.mystroke.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.jimi.mystroke.models.AssessmentResultBoolean;

import java.util.List;

/**
 * Created by jimi on 24/03/2018.
 */
@Dao
public interface AssessmentResultBooleanDao {
    @Query("SELECT * FROM assessment_result_boolean WHERE toDelete =:toDelete")
    List<AssessmentResultBoolean> getAll(boolean toDelete);

    @Query("SELECT * FROM assessment_result_boolean  WHERE id IN (:ids) AND toDelete =:toDelete")
    List<AssessmentResultBoolean> loadAllByIds(String[] ids, boolean toDelete);

    @Query("SELECT * FROM assessment_result_boolean WHERE created > (:created) AND toDelete =:toDelete")
    List<AssessmentResultBoolean> loadChanged(long created, boolean toDelete);

    @Query("SELECT * FROM assessment_result_boolean WHERE toDelete =:toDelete")
    public abstract List<AssessmentResultBoolean> loadByToDelete(boolean toDelete);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(AssessmentResultBoolean... assessmentResultBooleans);

    @Delete
    void delete(AssessmentResultBoolean assessmentResultBoolean);
}
