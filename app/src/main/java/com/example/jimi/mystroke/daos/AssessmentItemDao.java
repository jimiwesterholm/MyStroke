package com.example.jimi.mystroke.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.jimi.mystroke.models.AssessmentItem;

import java.util.List;

/**
 * Created by jimi on 24/03/2018.
 */
@Dao
public interface AssessmentItemDao {
    @Query("SELECT * FROM assessment_item WHERE toDelete =:toDelete")
    List<AssessmentItem> getAll(boolean toDelete);

    @Query("SELECT * FROM assessment_item WHERE id IN (:ids) AND toDelete =:toDelete")
    List<AssessmentItem> loadAllByIds(String[] ids, boolean toDelete);

    @Query("SELECT * FROM assessment_item  WHERE created > (:created) AND toDelete =:toDelete")
    List<AssessmentItem> loadChanged(long created, boolean toDelete);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(AssessmentItem... assessmentItems);

    @Delete
    void delete(AssessmentItem assessmentItem);
}
