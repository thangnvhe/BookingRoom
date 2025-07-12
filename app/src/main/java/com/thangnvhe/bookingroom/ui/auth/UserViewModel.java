package com.thangnvhe.bookingroom.ui.auth;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.thangnvhe.bookingroom.data.AppDatabase;
import com.thangnvhe.bookingroom.data.db.entities.User;
import com.thangnvhe.bookingroom.data.db.dao.UserDao;

public class UserViewModel extends ViewModel {
    private UserDao userDao;
    private MutableLiveData<String> loginResult = new MutableLiveData<>();
    private MutableLiveData<User> userLiveData = new MutableLiveData<>();

    public void init(Context context) {
        userDao = AppDatabase.getInstance(context).userDao();
    }

    public LiveData<String> getLoginResult() {
        return loginResult;
    }

    public LiveData<User> getUserByUsername(String username) {
        userLiveData.setValue(null);
        new Thread(() -> {
            User user = userDao.getUserByUsername(username);
            userLiveData.postValue(user);
        }).start();
        return userLiveData;
    }

    public void loginUser(Context context, String username, String password) {
        new Thread(() -> {
            User user = userDao.getUserByUsername(username);
            if (user == null) {
                loginResult.postValue("Tài khoản không tồn tại");
            } else if (!user.password.equals(password)) {
                loginResult.postValue("Mật khẩu không đúng");
            } else {
                loginResult.postValue("success");
            }
        }).start();
    }

    public void registerUser(User user) {
        new Thread(() -> {
            userDao.insertUser(user);
        }).start();
    }
}