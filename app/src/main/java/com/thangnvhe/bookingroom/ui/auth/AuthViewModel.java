package com.thangnvhe.bookingroom.ui.auth;

import android.content.Context;

import com.thangnvhe.bookingroom.data.db.entities.User;
import com.thangnvhe.bookingroom.data.repositories.UserRepository;
import com.thangnvhe.bookingroom.util.SessionManager;
import com.thangnvhe.bookingroom.util.ValidationUtils;

public class AuthViewModel {
    private UserRepository repository;
    private SessionManager sessionManager;

    public AuthViewModel(Context context) {
        repository = new UserRepository(context);
        sessionManager = new SessionManager(context);
    }

    public void register(String email, String password, UserRepository.OnResultListener listener) {
        if (!ValidationUtils.isValidEmail(email)) {
            listener.onError("Email không hợp lệ");
            return;
        }
        if (!ValidationUtils.isValidPassword(password)) {
            listener.onError("Mật khẩu phải dài ít nhất 6 ký tự");
            return;
        }
        repository.register(new User(email, password), new UserRepository.OnResultListener() {
            @Override
            public void onSuccess() {
                listener.onSuccess();
            }

            @Override
            public void onSuccess(int userId) {
                // Không dùng
            }

            @Override
            public void onError(String message) {
                listener.onError(message);
            }
        });
    }

    public void login(String email, String password, UserRepository.OnResultListener listener) {
        if (!ValidationUtils.isValidEmail(email)) {
            listener.onError("Email không hợp lệ");
            return;
        }
        if (!ValidationUtils.isValidPassword(password)) {
            listener.onError("Mật khẩu phải dài ít nhất 6 ký tự");
            return;
        }
        repository.login(email, password, new UserRepository.OnResultListener() {
            @Override
            public void onSuccess() {
                // Không dùng
            }

            @Override
            public void onSuccess(int userId) {
                sessionManager.login(userId);
                listener.onSuccess(userId);
            }

            @Override
            public void onError(String message) {
                listener.onError(message);
            }
        });
    }

    public void logout() {
        sessionManager.logout();
    }

    public boolean isLoggedIn() {
        return sessionManager.isLoggedIn();
    }

    public int getUserId() {
        return sessionManager.getUserId();
    }
}
