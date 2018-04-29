package com.example.jimi.mystroke.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.jimi.mystroke.models.HelpPage;

import java.util.List;

/**
 * Created by jimi on 24/03/2018.
 */
@Dao
public interface HelpPageDao {
    @Query("SELECT * FROM help_page WHERE toDelete =:toDelete")
    List<HelpPage> getAll(boolean toDelete);

    @Query("SELECT * FROM help_page WHERE toDelete =:toDelete")
    public abstract List<HelpPage> loadByToDelete(boolean toDelete);

    @Query("SELECT * FROM help_page WHERE id IN (:ids) AND toDelete =:toDelete")
    List<HelpPage> loadAllByIds(String[] ids, boolean toDelete);

    @Query("SELECT * FROM help_page WHERE created > (:created) AND toDelete =:toDelete")
    List<HelpPage> loadChanged(long created, boolean toDelete);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(HelpPage... helpPages);

    @Delete
    void delete(HelpPage helpPage);

    @Query("SELECT * FROM help_page WHERE parentId IN (:ids) AND toDelete =:toDelete")
    List<HelpPage> loadAllByParentIds(String[] ids, boolean toDelete);
}
