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
    @Query("SELECT * FROM exercise WHERE toDelete =:false")
    List<Exercise> getAll();

    @Query("SELECT DISTINCT section FROM exercise WHERE toDelete =:false")
    List<String> getSections();

    @Query("SELECT DISTINCT section FROM exercise WHERE id IN (:ids) AND toDelete =:false")
    List<String> getSectionsOfIds(int[] ids);

    @Query("SELECT DISTINCT section FROM exercise WHERE id NOT IN (:ids) AND toDelete =:false")
    List<String> getSectionsNotOfIds(int[] ids);

    @Query("SELECT * FROM exercise WHERE section = :section AND viewed =:viewed AND toDelete =:false")
    List<Exercise> getBySectionAndViewed(String section, boolean viewed);

    @Query("SELECT * FROM exercise WHERE section = :section AND toDelete =:false")
    List<Exercise> getBySection(String section);

    @Query("SELECT * FROM exercise WHERE id IN (:exerciseIds) AND toDelete =:false")
    List<Exercise> loadAllByIds(int[] exerciseIds);

    @Query("SELECT * FROM exercise WHERE id NOT IN (:exerciseIds) AND toDelete =:false")
    List<Exercise> loadAllButIds(int[] exerciseIds);

    @Query("SELECT * FROM exercise WHERE id IN (:exerciseIds) AND section =:section AND toDelete =:false")
    List<Exercise> loadAllByIdsFromSection(int[] exerciseIds, String section);

    @Query("SELECT * FROM exercise WHERE id NOT IN (:exerciseIds) AND section =:section AND toDelete =:false")
    List<Exercise> loadAllButIdsFromSection(int[] exerciseIds, String section);

    @Query("SELECT * FROM exercise WHERE id = :exerciseId AND toDelete =:false LIMIT 1")
    Exercise loadById(int exerciseId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Exercise... exercises);

    @Delete
    void delete(Exercise exercise);

    @Query("SELECT * FROM exercise WHERE created > :created AND toDelete =:false")
    List<Exercise> loadChanged(long created);
}
