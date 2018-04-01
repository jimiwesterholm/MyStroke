package com.example.jimi.mystroke.daos; /**
 * Created by jimi on 25/11/2017.
 */
import android.arch.persistence.room.*;

import com.example.jimi.mystroke.models.Therapist;

import java.util.List;

@Dao
public interface TherapistDao {
    @Query("SELECT * FROM therapist WHERE toDelete =:toDelete")
    List<Therapist> getAll(boolean toDelete);

    @Query("SELECT * FROM therapist WHERE id IN (:therapistIds) AND toDelete =:toDelete")
    List<Therapist> loadAllByIds(String[] therapistIds, boolean toDelete);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Therapist... therapists);

    @Delete
    void delete(Therapist therapist);

    @Query("SELECT * FROM therapist WHERE created > :created AND toDelete =:toDelete")
    List<Therapist> loadChanged(long created, boolean toDelete);

    @Query("SELECT * FROM therapist WHERE id =:tId AND toDelete =:toDelete LIMIT 1")
    Therapist loadById(String tId, boolean toDelete);

    @Query("SELECT * FROM therapist WHERE  user_iduser=:id AND toDelete =:toDelete LIMIT 1")
    Therapist loadByUserId(String id, boolean toDelete);
}
