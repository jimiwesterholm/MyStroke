package com.example.jimi.mystroke.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.jimi.mystroke.models.ExerciseImage;

import java.util.List;

/**
 * Created by jimi on 14/12/2017.
 */
@Dao
public abstract class ExerciseImageDao {
    //TODO: Add patient id requirements for queries
    @Query("SELECT * FROM exercise_image WHERE toDelete =:toDelete")
    public abstract List<ExerciseImage> getAll(boolean toDelete);

    @Query("SELECT * FROM exercise_image WHERE toDelete =:toDelete")
    public abstract List<ExerciseImage> loadByToDelete(boolean toDelete);

    @Query("SELECT * FROM exercise_image WHERE id IN (:ids) AND toDelete =:toDelete")
    public abstract List<ExerciseImage> loadAllByIds(String[] ids, boolean toDelete);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertAll(ExerciseImage... exerciseImages);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long insert(ExerciseImage exerciseImage);

    @Delete
    public abstract void delete(ExerciseImage exerciseImage);

    @Query("SELECT * FROM exercise_image WHERE created > :created AND toDelete =:toDelete")
    public abstract List<ExerciseImage> loadChanged(long created, boolean toDelete);

     @Update
     public abstract void update(ExerciseImage exerciseImage);

     @Query("SELECT * FROM exercise_image WHERE id =:eId AND toDelete =:toDelete")
     public abstract List<ExerciseImage> loadAllByExerciseId(String eId, boolean toDelete);

    public void upsert(ExerciseImage exerciseImage) {
        if(insert(exerciseImage) == -1) {
            update(exerciseImage);
        }
    }

    public void upsertAll(ExerciseImage... exerciseImages) {
        for (ExerciseImage exerciseImage : exerciseImages) {
            upsert(exerciseImage);
        }
    }
}
