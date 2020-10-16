package com.example.fantahelp.model.daos;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.example.fantahelp.model.entities.User;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM User")
    LiveData<List<User>> getAll();

    @Query("SELECT * FROM User WHERE id IN (:userIds)")
    LiveData<List<User>> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM User WHERE name LIKE :first AND " +
            "name LIKE :last LIMIT 1")
    User findByName(String first, String last);

    @Update
    void updateUser(User user);

    @Insert
    void insertAll(List<User> users);

    @Delete
    void delete(User user);

    @Insert
    long insertUser(User newUser);
}
