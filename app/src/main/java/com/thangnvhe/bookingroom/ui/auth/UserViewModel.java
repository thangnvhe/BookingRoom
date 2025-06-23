package com.thangnvhe.bookingroom.ui.auth;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.thangnvhe.bookingroom.data.db.entities.User;
import com.thangnvhe.bookingroom.data.repositories.UserRepository;

public class UserViewModel extends ViewModel {
    private final MutableLiveData<String> registerResult = new MutableLiveData<>();
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
}

