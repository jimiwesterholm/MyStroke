package com.example.jimi.mystroke.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.jimi.mystroke.models.Exercise;

import java.util.List;

/**
 * Created by jimi on 14/12/2017.
 */
@Dao
public abstract class ExerciseDao {
    @Query("SELECT * FROM exercise WHERE toDelete =:toDelete")
    public abstract List<Exercise> getAll(boolean toDelete);

    @Query("SELECT * FROM exercise WHERE toDelete =:toDelete")
    public abstract List<Exercise> loadByToDelete(boolean toDelete);

    @Query("SELECT DISTINCT section FROM exercise WHERE toDelete =:toDelete")
    public abstract List<String> getSections(boolean toDelete);

    @Query("SELECT DISTINCT section FROM exercise WHERE id IN (:ids) AND toDelete =:toDelete")
    public abstract List<String> getSectionsOfIds(String[] ids, boolean toDelete);

    @Query("SELECT DISTINCT section FROM exercise WHERE id NOT IN (:ids) AND toDelete =:toDelete")
    public abstract List<String> getSectionsNotOfIds(String[] ids, boolean toDelete);

    @Query("SELECT * FROM exercise WHERE section = :section AND toDelete =:toDelete")
    public abstract List<Exercise> getBySection(String section, boolean toDelete);

    @Query("SELECT * FROM exercise WHERE id IN (:exerciseIds) AND toDelete =:toDelete")
    public abstract List<Exercise> loadAllByIds(String[] exerciseIds, boolean toDelete);

    @Query("SELECT * FROM exercise WHERE id NOT IN (:exerciseIds) AND toDelete =:toDelete")
    public abstract List<Exercise> loadAllButIds(String[] exerciseIds, boolean toDelete);

    @Query("SELECT * FROM exercise WHERE id IN (:exerciseIds) AND section =:section AND toDelete =:toDelete")
    public abstract List<Exercise> loadAllByIdsFromSection(String[] exerciseIds, String section, boolean toDelete);

    @Query("SELECT * FROM exercise WHERE id NOT IN (:exerciseIds) AND section =:section AND toDelete =:toDelete")
    public abstract List<Exercise> loadAllButIdsFromSection(String[] exerciseIds, String section, boolean toDelete);

    @Query("SELECT * FROM exercise WHERE id = :exerciseId AND toDelete =:toDelete LIMIT 1")
    public abstract Exercise loadById(String exerciseId, boolean toDelete);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertAll(Exercise... exercises);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long insert(Exercise exercise);

    @Delete
    public abstract void delete(Exercise exercise);

    @Query("SELECT * FROM exercise WHERE created > :created AND toDelete =:toDelete")
    public abstract List<Exercise> loadChanged(long created, boolean toDelete);

    @Query("SELECT * FROM exercise WHERE id =:exerciseId AND toDelete =:toDelete LIMIT 1")
    public abstract Exercise loadByExerciseId(String exerciseId, boolean toDelete);

     @Update
     public abstract void update(Exercise exercise);

    public void upsert(Exercise exercise) {
        if(insert(exercise) == -1) {
            update(exercise);
        }
    }

    public void upsertAll(Exercise... exercises) {
        for (Exercise exercise : exercises) {
            upsert(exercise);
        }
    }

    @Query("SELECT * FROM exercise WHERE id NOT IN (:ids) AND toDelete =:toDelete")
    public abstract List<Exercise> getAllExcept(String[] ids, boolean toDelete);
}
