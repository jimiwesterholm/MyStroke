package com.example.jimi.mystroke.daos; /**
 * Created by jimi on 25/11/2017.
 */
import android.arch.persistence.room.*;

import com.example.jimi.mystroke.models.Therapist;

import java.util.List;

@Dao
public interface TherapistDao {
    @Query("SELECT * FROM therapist WHERE toDelete =:false")
    List<Therapist> getAll();

    @Query("SELECT * FROM therapist WHERE id IN (:therapistIds) AND toDelete =:false")
    List<Therapist> loadAllByIds(int[] therapistIds);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Therapist... therapists);

    @Delete
    void delete(Therapist therapist);

    @Query("SELECT * FROM therapist WHERE created > :created AND toDelete =:false")
    List<Therapist> loadChanged(long created);
}
