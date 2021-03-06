package com.example.jimi.mystroke.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.jimi.mystroke.models.Imagery;

import java.util.List;

/**
 * Created by jimi on 30/12/2017.
 */

@Dao
public interface ImageryDao {
    @Query("SELECT * FROM imagery WHERE toDelete =:toDelete")
    List<Imagery> getAll(boolean toDelete);

    @Query("SELECT * FROM imagery WHERE toDelete =:toDelete")
    public abstract List<Imagery> loadByToDelete(boolean toDelete);

    @Query("SELECT * FROM imagery WHERE id IN (:imageryIds) AND toDelete =:toDelete")
    List<Imagery> loadAllByIds(String[] imageryIds, boolean toDelete);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Imagery... imageries);

    @Delete
    void delete(Imagery imagery);

    @Query("SELECT * FROM imagery WHERE created > :created AND toDelete =:toDelete")
    List<Imagery> loadChanged(long created, boolean toDelete);

    @Query("SELECT * FROM imagery WHERE id =:iId AND toDelete =:toDelete")
    Imagery loadByImageryId(String iId, boolean toDelete);

    @Update
    void update(Imagery imagery);
}
