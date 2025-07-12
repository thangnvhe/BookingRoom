package com.thangnvhe.bookingroom.data.repositories;

import android.content.Context;

import com.thangnvhe.bookingroom.data.AppDatabase;
import com.thangnvhe.bookingroom.data.db.dao.UserDao;
import com.thangnvhe.bookingroom.data.db.entities.User;

import java.util.List;
import java.util.concurrent.Executors;

public class UserRepository {
    private final UserDao userDao;

    public UserRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        userDao = db.userDao();
    }

    public void insertUser(User user) {
        Executors.newSingleThreadExecutor().execute(() -> userDao.insertUser(user));
    }

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    public User getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    public User getUserByUsernameAndPassword(String username, String password) {
        return userDao.getUserByUsernameAndPassword(username, password);
    }
}
