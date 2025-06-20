package com.thangnvhe.bookingroom.data.repositories;

import android.content.Context;

import androidx.room.Room;

import com.thangnvhe.bookingroom.data.db.AppDatabase;
import com.thangnvhe.bookingroom.data.db.entities.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserRepository {
    private AppDatabase db;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    public UserRepository(Context context) {
        db = Room.databaseBuilder(context.getApplicationContext(),
                AppDatabase.class, "lab_booking_db").build();
    }

    public void register(User user, OnResultListener listener) {
        executorService.execute(() -> {
            try {
                // Kiểm tra email đã tồn tại
                User existingUser = db.userDao().getUserByEmail(user.email);
                if (existingUser != null) {
                    listener.onError("Email đã tồn tại");
                    return;
                }
                db.userDao().insert(user);
                listener.onSuccess();
            } catch (Exception e) {
                listener.onError("Lỗi đăng ký: " + e.getMessage());
            }
        });
    }

    public void login(String email, String password, OnResultListener listener) {
        executorService.execute(() -> {
            try {
                User user = db.userDao().getUser(email, password);
                if (user != null) {
                    listener.onSuccess(user.id);
                } else {
                    listener.onError("Email hoặc mật khẩu sai");
                }
            } catch (Exception e) {
                listener.onError("Lỗi đăng nhập: " + e.getMessage());
            }
        });
    }

    public interface OnResultListener {
        void onSuccess();
        void onSuccess(int userId);
        void onError(String message);
    }
}
