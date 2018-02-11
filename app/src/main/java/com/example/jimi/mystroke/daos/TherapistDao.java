package com.example.jimi.mystroke.daos; /**
 * Created by jimi on 25/11/2017.
 */
import android.arch.persistence.room.*;

import com.example.jimi.mystroke.models.Therapist;

import java.util.List;

@Dao
public interface TherapistDao {
    @Query("SELECT * FROM therapist")
    List<Therapist> getAll();

    @Query("SELECT * FROM therapist WHERE idtherapist IN (:therapistIds)")
    List<Therapist> loadAllByIds(int[] therapistIds);

    @Insert
    void insertAll(Therapist... therapists);

    @Delete
    void delete(Therapist therapist);

    @Query("SELECT * FROM therapist WHERE created > :created")
    List<Therapist> loadChanged(long created);
}
