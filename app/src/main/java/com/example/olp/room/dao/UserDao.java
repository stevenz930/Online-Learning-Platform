package com.example.olp.room.dao;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import com.example.olp.room.model.User;

@Dao
public interface UserDao {
    @Insert
    void insert(User user); // insert

    @Update
    public void updateUser(User user); // update

    @Delete
    public void deleteUser(User user); // delete

    @Query("SELECT * FROM user_table")
    LiveData<List<User>> getAllUsers(); // search all

    @Query("SELECT * FROM user_table WHERE id = :userId")
    List<User> getAllById(int[] userId); // search all by id

    @Query("DELETE FROM user_table WHERE id = :userId")
    void deleteById(int userId); // delete by id

    @Query("DELETE FROM user_table")
    void deleteAll(); // delete all
}
