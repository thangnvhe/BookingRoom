package com.thangnvhe.bookingroom.data.db.daos;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.thangnvhe.bookingroom.data.db.entities.User;

@Dao
public interface UserDao {
    @Insert
    void insert(User user);

    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    User getUser(String email, String password);

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    User getUserByEmail(String email);
}
