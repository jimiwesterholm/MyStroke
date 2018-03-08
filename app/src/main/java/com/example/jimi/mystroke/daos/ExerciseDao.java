package com.example.jimi.mystroke.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

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

    @Query("SELECT DISTINCT section FROM exercise WHERE SQLiteId IN (:ids) AND toDelete =:toDelete")
    List<String> getSectionsOfIds(int[] ids, boolean toDelete);

    @Query("SELECT DISTINCT section FROM exercise WHERE SQLiteId NOT IN (:ids) AND toDelete =:toDelete")
    List<String> getSectionsNotOfIds(int[] ids, boolean toDelete);

    @Query("SELECT * FROM exercise WHERE section = :section AND viewed =:viewed AND toDelete =:toDelete")
    List<Exercise> getBySectionAndViewed(String section, boolean viewed, boolean toDelete);

    @Query("SELECT * FROM exercise WHERE section = :section AND toDelete =:toDelete")
    List<Exercise> getBySection(String section, boolean toDelete);

    @Query("SELECT * FROM exercise WHERE SQLiteId IN (:exerciseIds) AND toDelete =:toDelete")
    List<Exercise> loadAllByIds(int[] exerciseIds, boolean toDelete);

    @Query("SELECT * FROM exercise WHERE SQLiteId NOT IN (:exerciseIds) AND toDelete =:toDelete")
    List<Exercise> loadAllButIds(int[] exerciseIds, boolean toDelete);

    @Query("SELECT * FROM exercise WHERE SQLiteId IN (:exerciseIds) AND section =:section AND toDelete =:toDelete")
    List<Exercise> loadAllByIdsFromSection(int[] exerciseIds, String section, boolean toDelete);

    @Query("SELECT * FROM exercise WHERE SQLiteId NOT IN (:exerciseIds) AND section =:section AND toDelete =:toDelete")
    List<Exercise> loadAllButIdsFromSection(int[] exerciseIds, String section, boolean toDelete);

    @Query("SELECT * FROM exercise WHERE SQLiteId = :exerciseId AND toDelete =:toDelete LIMIT 1")
    Exercise loadById(int exerciseId, boolean toDelete);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Exercise... exercises);

    @Delete
    void delete(Exercise exercise);

    @Query("SELECT * FROM exercise WHERE created > :created AND toDelete =:toDelete")
    List<Exercise> loadChanged(long created, boolean toDelete);

    @Query("SELECT * FROM exercise WHERE idexercise IN (:exerciseIDs) AND toDelete =:toDelete")
    List<Exercise> loadAllByExerciseIds(int[] exerciseIDs, boolean toDelete);

    @Query("SELECT * FROM exercise WHERE idexercise =:exerciseId AND toDelete =:toDelete LIMIT 1")
    Exercise loadByExerciseId(int exerciseId, boolean toDelete);
}
