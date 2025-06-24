package com.thangnvhe.bookingroom.ui.auth;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.thangnvhe.bookingroom.data.db.entities.User;
import com.thangnvhe.bookingroom.data.repositories.UserRepository;

public class UserViewModel extends ViewModel {
    private final MutableLiveData<String> registerResult = new MutableLiveData<>();
    private final MutableLiveData<String> loginResult = new MutableLiveData<>();
    private UserRepository repository;

    public LiveData<String> getRegisterResult() {
        return registerResult;
    }

    public void init(Context context) {
        repository = new UserRepository(context);
    }

    public void registerUser(User user) {
        new Thread(() -> {
            repository.insert(user);
            registerResult.postValue("Đăng ký thành công (Room)!");
        }).start();
    }
    public LiveData<String> getLoginResult() {
        return loginResult;
    }

    public void loginUser(Context context, String username, String password) {
        new Thread(() -> {
            User user = repository.getUserByUsernameAndPassword(username, password);
            if (user != null) {
                // Lưu trạng thái đăng nhập
                SharedPreferences prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
                prefs.edit().putBoolean("is_logged_in", true).putString("is_user_name",username).apply();
                loginResult.postValue("success");
            } else {
                loginResult.postValue("Sai tên đăng nhập hoặc mật khẩu");
            }
        }).start();
    }
}

