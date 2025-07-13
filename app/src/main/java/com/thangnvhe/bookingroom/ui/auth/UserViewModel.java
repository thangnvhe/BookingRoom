package com.thangnvhe.bookingroom.ui.auth;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.thangnvhe.bookingroom.data.db.entities.User;
import com.thangnvhe.bookingroom.data.repositories.UserRepository;

public class UserViewModel extends ViewModel {

    private UserRepository repository;
    private final MutableLiveData<String> loginResult = new MutableLiveData<>();
    private final MutableLiveData<String> registerResult = new MutableLiveData<>();
    private final MutableLiveData<User> userLiveData = new MutableLiveData<>();

    public void init(Context context) {
        if (repository == null) {
            repository = new UserRepository(context);
        }
    }

    // --- Đăng ký ---
    public void registerUser(User user) {
        new Thread(() -> {
            User existing = repository.getUserByUsername(user.username);
            if (existing != null) {
                registerResult.postValue("Tên đăng nhập đã tồn tại!");
            } else {
                repository.insertUser(user);
                registerResult.postValue("Đăng ký thành công!");
            }
        }).start();
    }

    public LiveData<String> getRegisterResult() {
        return registerResult;
    }

    // --- Đăng nhập ---
    public void loginUser(Context context, String username, String password) {
        new Thread(() -> {
            User user = repository.getUserByUsernameAndPassword(username, password);
            if (user != null) {
                SharedPreferences prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
                prefs.edit()
                        .putBoolean("is_logged_in", true)
                        .putString("is_user_name", username)
                        .putString("user_role", user.role) // lưu vai trò nếu cần
                        .apply();
                loginResult.postValue("success");
            } else {
                loginResult.postValue("Sai tên đăng nhập hoặc mật khẩu");
            }
        }).start();
    }

    public LiveData<String> getLoginResult() {
        return loginResult;
    }

    // --- Lấy thông tin user theo username ---
    public LiveData<User> getUserByUsername(String username) {
        userLiveData.setValue(null); // Reset trước khi fetch
        new Thread(() -> {
            User user = repository.getUserByUsername(username);
            userLiveData.postValue(user);
        }).start();
        return userLiveData;
    }
}
