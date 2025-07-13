package com.thangnvhe.bookingroom.data.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.thangnvhe.bookingroom.data.db.entities.User;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insertUser(User user);  // Gộp tên insert cho rõ ràng

    @Query("SELECT * FROM users")
    List<User> getAllUsers();

    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    User getUserByUsername(String username);

    @Query("SELECT * FROM users WHERE username = :username AND password = :password LIMIT 1")
    User getUserByUsernameAndPassword(String username, String password);

    @Update
    void updateUser(User user);  // Tên rõ ràng hơn
}
