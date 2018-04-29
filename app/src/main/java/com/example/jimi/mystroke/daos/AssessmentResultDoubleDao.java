package com.example.jimi.mystroke.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.jimi.mystroke.models.AssessmentResultDouble;

import java.util.List;

/**
 * Created by jimi on 24/03/2018.
 */
@Dao
public interface AssessmentResultDoubleDao {
    @Query("SELECT * FROM assessment_result_double WHERE toDelete =:toDelete")
    List<AssessmentResultDouble> getAll(boolean toDelete);

    @Query("SELECT * FROM assessment_result_double  WHERE id IN (:ids) AND toDelete =:toDelete")
    List<AssessmentResultDouble> loadAllByIds(String[] ids, boolean toDelete);

    @Query("SELECT * FROM assessment_result_double  WHERE created > (:created) AND toDelete =:toDelete")
    List<AssessmentResultDouble> loadChanged(long created, boolean toDelete);

    @Query("SELECT * FROM assessment_result_double WHERE toDelete =:toDelete")
    public abstract List<AssessmentResultDouble> loadByToDelete(boolean toDelete);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(AssessmentResultDouble... assessmentResultDoubles);

    @Delete
    void delete(AssessmentResultDouble assessmentResultDouble);
}
