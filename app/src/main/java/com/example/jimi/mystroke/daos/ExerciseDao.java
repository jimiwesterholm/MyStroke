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
    @Query("SELECT * FROM exercise")
    List<Exercise> getAll();

    @Query("SELECT DISTINCT section FROM exercise")
    List<String> getSections();

    @Query("SELECT DISTINCT section FROM exercise WHERE id IN (:ids)")
    List<String> getSectionsOfIds(int[] ids);

    @Query("SELECT DISTINCT section FROM exercise WHERE id NOT IN (:ids)")
    List<String> getSectionsNotOfIds(int[] ids);

    @Query("SELECT * FROM exercise WHERE section = :section AND viewed =:viewed")
    List<Exercise> getBySectionAndViewed(String section, boolean viewed);

    @Query("SELECT * FROM exercise WHERE section = :section")
    List<Exercise> getBySection(String section);

    @Query("SELECT * FROM exercise WHERE id IN (:exerciseIds)")
    List<Exercise> loadAllByIds(int[] exerciseIds);

    @Query("SELECT * FROM exercise WHERE id NOT IN (:exerciseIds)")
    List<Exercise> loadAllButIds(int[] exerciseIds);

    @Query("SELECT * FROM exercise WHERE id IN (:exerciseIds) AND section =:section")
    List<Exercise> loadAllByIdsFromSection(int[] exerciseIds, String section);

    @Query("SELECT * FROM exercise WHERE id NOT IN (:exerciseIds) AND section =:section")
    List<Exercise> loadAllButIdsFromSection(int[] exerciseIds, String section);

    @Query("SELECT * FROM exercise WHERE id = :exerciseId LIMIT 1")
    Exercise loadById(int exerciseId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Exercise... exercises);

    @Delete
    void delete(Exercise exercise);

    @Query("SELECT * FROM exercise WHERE created > :created")
    List<Exercise> loadChanged(long created);
}
