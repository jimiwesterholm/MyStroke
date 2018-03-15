package com.example.jimi.mystroke.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.jimi.mystroke.models.Exercise;
import com.example.jimi.mystroke.models.ExerciseImage;

import java.util.List;

/**
 * Created by jimi on 14/12/2017.
 */
@Dao
public interface ExerciseImageDao {
    //TODO: Add patient id requirements for queries
    @Query("SELECT * FROM exercise_image WHERE toDelete =:toDelete")
    List<ExerciseImage> getAll(boolean toDelete);

    @Query("SELECT * FROM exercise_image WHERE exerciseImageId IN (:ids) AND toDelete =:toDelete")
    List<ExerciseImage> loadAllByIds(String[] ids, boolean toDelete);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(ExerciseImage... exerciseImages);

    @Delete
    void delete(ExerciseImage exerciseImage);

    @Query("SELECT * FROM exercise_image WHERE created > :created AND toDelete =:toDelete")
    List<ExerciseImage> loadChanged(long created, boolean toDelete);

     @Update
    void update(ExerciseImage exerciseImage);
}
