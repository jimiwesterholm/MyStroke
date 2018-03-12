package com.example.jimi.mystroke.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.jimi.mystroke.models.DatabaseObject;
import com.example.jimi.mystroke.models.Exercise;

import java.util.List;

/**
 * Created by jimi on 14/12/2017.
 */
@Dao
public interface ExerciseDao {
    //TODO: Add patient id requirements for queries
    @Query("SELECT * FROM exercise WHERE toDelete =:toDelete")
    List<Exercise> getAll(boolean toDelete);

    @Query("SELECT DISTINCT section FROM exercise WHERE toDelete =:toDelete")
    List<String> getSections(boolean toDelete);

    @Query("SELECT DISTINCT section FROM exercise WHERE idexercise IN (:ids) AND toDelete =:toDelete")
    List<String> getSectionsOfIds(String[] ids, boolean toDelete);

    @Query("SELECT DISTINCT section FROM exercise WHERE idexercise NOT IN (:ids) AND toDelete =:toDelete")
    List<String> getSectionsNotOfIds(String[] ids, boolean toDelete);

    @Query("SELECT * FROM exercise WHERE section = :section AND toDelete =:toDelete")
    List<Exercise> getBySection(String section, boolean toDelete);

    @Query("SELECT * FROM exercise WHERE idexercise IN (:exerciseIds) AND toDelete =:toDelete")
    List<Exercise> loadAllByIds(String[] exerciseIds, boolean toDelete);

    @Query("SELECT * FROM exercise WHERE idexercise NOT IN (:exerciseIds) AND toDelete =:toDelete")
    List<Exercise> loadAllButIds(String[] exerciseIds, boolean toDelete);

    @Query("SELECT * FROM exercise WHERE idexercise IN (:exerciseIds) AND section =:section AND toDelete =:toDelete")
    List<Exercise> loadAllByIdsFromSection(String[] exerciseIds, String section, boolean toDelete);

    @Query("SELECT * FROM exercise WHERE idexercise NOT IN (:exerciseIds) AND section =:section AND toDelete =:toDelete")
    List<Exercise> loadAllButIdsFromSection(String[] exerciseIds, String section, boolean toDelete);

    @Query("SELECT * FROM exercise WHERE idexercise = :exerciseId AND toDelete =:toDelete LIMIT 1")
    Exercise loadById(String exerciseId, boolean toDelete);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Exercise... exercises);

    @Delete
    void delete(Exercise exercise);

    @Query("SELECT * FROM exercise WHERE created > :created AND toDelete =:toDelete")
    List<Exercise> loadChanged(long created, boolean toDelete);

    @Query("SELECT * FROM exercise WHERE idexercise =:exerciseId AND toDelete =:toDelete LIMIT 1")
    Exercise loadByExerciseId(String exerciseId, boolean toDelete);

     @Update
    void update(Exercise exercise);
}
