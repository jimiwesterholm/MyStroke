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
    @Query("SELECT * FROM comment")
    List<Comment> getAll();

    @Query("SELECT * FROM comment WHERE idcomment IN (:commentIds)")
    List<Comment> loadAllByIds(int[] commentIds);

    @Insert
    void insertAll(Comment... comments);

    @Delete
    void delete(Comment comment);

    @Query("SELECT * FROM comment WHERE created > :created")
    List<Comment> loadChanged(long created);
}
