package com.example.jimi.mystroke.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.jimi.mystroke.models.Assessment;
import com.example.jimi.mystroke.models.DatabaseObject;

import java.util.List;

@Dao
public interface AssessmentDao {
    @Query("SELECT * FROM assessment WHERE toDelete =:toDelete")
    List<Assessment> getAll(boolean toDelete);

    @Query("SELECT * FROM assessment WHERE id IN (:assessmentIds) AND toDelete =:toDelete")
    List<Assessment> loadAllByIds(String[] assessmentIds, boolean toDelete);

    @Query("SELECT * FROM assessment WHERE created > (:created) AND toDelete =:toDelete")
    List<Assessment> loadChanged(long created, boolean toDelete);

    @Query("SELECT * FROM assessment WHERE toDelete =:toDelete")
    public abstract List<Assessment> loadByToDelete(boolean toDelete);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Assessment... assessments);

    @Delete
    void delete(Assessment assessment);
}
