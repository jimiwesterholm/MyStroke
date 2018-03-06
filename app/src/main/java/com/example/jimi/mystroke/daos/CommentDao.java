package com.example.jimi.mystroke.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.jimi.mystroke.models.Comment;
import com.example.jimi.mystroke.models.DatabaseObject;

import java.util.List;

/**
 * Created by jimi on 30/12/2017.
 */

@Dao
public interface CommentDao {
    @Query("SELECT * FROM comment WHERE toDelete =:toDelete")
    List<Comment> getAll(boolean toDelete);

    @Query("SELECT * FROM comment WHERE toDelete =:toDelete ORDER BY created ASC")
    List<Comment> getAllOrdered(boolean toDelete);

    @Query("SELECT * FROM comment WHERE id IN (:commentIds) AND toDelete =:toDelete")
    List<Comment> loadAllByIds(int[] commentIds, boolean toDelete);

    //TODO maybe not needed?
    @Query("SELECT MAX(idcomment) FROM comment WHERE toDelete =:toDelete")
    int loadMaxId(boolean toDelete);

    @Insert
    void insertAll(Comment... comments);

    @Delete
    void delete(Comment comment);

    @Query("SELECT * FROM comment WHERE created > :created AND toDelete =:toDelete")
    List<Comment> loadChanged(long created, boolean toDelete);
}
