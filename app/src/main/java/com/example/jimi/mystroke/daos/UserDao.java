package com.example.jimi.mystroke.daos; /**
 * Created by jimi on 25/11/2017.
 */
import android.arch.persistence.room.*;

import com.example.jimi.mystroke.models.DatabaseObject;
import com.example.jimi.mystroke.models.User;

import java.util.List;

@Dao
public abstract class UserDao {
    @Query("SELECT * FROM user WHERE toDelete =:false")
    public abstract List<User> getAll();

    @Query("SELECT * FROM user WHERE id IN (:userIds) AND toDelete =:false")
    public abstract List<User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE id = :iduser AND toDelete =:false LIMIT 1")
    public abstract User findById(int iduser);

    @Query("SELECT * FROM user WHERE firstName LIKE :first AND "
            + "lastName LIKE :last AND toDelete =:false LIMIT 1")
    public abstract User findByName(String first, String last);

    @Query("SELECT * FROM user WHERE email = :email AND toDelete =:false LIMIT 1")
    public abstract User findByEmail(String email);

    @Query("SELECT * FROM user WHERE username = :username AND toDelete =:false LIMIT 1")
    public abstract User findByUsername(String username);

    //https://stackoverflow.com/questions/45677230/android-room-persistence-library-upsert
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long[] insertAll(User... users);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long insert(User user);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    public abstract void update(User user);

    @Delete
    public abstract void delete(User user);

    @Query("SELECT * FROM user WHERE created > :created AND toDelete =:false")
    public abstract List<User> loadChanged(long created);

    public void upsert(User user) {
        if(insert(user) == -1) {
            update(user);
        }
    }

    public void upsertAll(User[] users) {
        long[] ignored = insertAll(users);
        for (int i = 0; i < ignored.length; i++) {
            if (ignored[i] == -1) update(users[i]);
        }
    }
}
