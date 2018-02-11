package com.example.jimi.mystroke.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.jimi.mystroke.models.DatabaseObject;
import com.example.jimi.mystroke.models.Imagery;

import java.util.List;

/**
 * Created by jimi on 30/12/2017.
 */

@Dao
public interface ImageryDao {
    @Query("SELECT * FROM imagery")
    List<Imagery> getAll();

    @Query("SELECT * FROM imagery WHERE imageryID IN (:imageryIds)")
    List<Imagery> loadAllByIds(int[] imageryIds);

    @Insert
    void insertAll(Imagery... imageries);

    @Delete
    void delete(Imagery imagery);

    @Query("SELECT * FROM imagery WHERE created > :created")
    List<Imagery> loadChanged(long created);
}
