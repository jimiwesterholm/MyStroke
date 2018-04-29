package com.example.jimi.mystroke.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.jimi.mystroke.models.RegisterCode;

import java.util.List;

@Dao
public abstract class RegisterCodeDao {
    @Query("SELECT * FROM registerCode WHERE toDelete =:toDelete")
    public abstract List<RegisterCode> getAll(boolean toDelete);

    @Query("SELECT * FROM registerCode WHERE toDelete =:toDelete")
    public abstract List<RegisterCode> loadByToDelete(boolean toDelete);

    @Query("SELECT * FROM registerCode WHERE id IN (:registerCodeIds) AND toDelete =:toDelete")
    public abstract List<RegisterCode> loadAllByIds(String[] registerCodeIds, boolean toDelete);

    @Query("SELECT * FROM registerCode WHERE id = :idregisterCode AND toDelete =:toDelete LIMIT 1")
    public abstract RegisterCode findById(String idregisterCode, boolean toDelete);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long[] insertAll(RegisterCode... registerCodes);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long insert(RegisterCode registerCode);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    public abstract void update(RegisterCode registerCode);

    @Delete
    public abstract void delete(RegisterCode registerCode);

    @Query("SELECT * FROM registerCode WHERE created > :created AND toDelete =:toDelete")
    public abstract List<RegisterCode> loadChanged(long created, boolean toDelete);

    public void upsert(RegisterCode registerCode) {
        if(insert(registerCode) == -1) {
            update(registerCode);
        }
    }

    public void upsertAll(RegisterCode[] registerCodes) {
        long[] ignored = insertAll(registerCodes);
        for (int i = 0; i < ignored.length; i++) {
            if (ignored[i] == -1) update(registerCodes[i]);
        }
    }
}
