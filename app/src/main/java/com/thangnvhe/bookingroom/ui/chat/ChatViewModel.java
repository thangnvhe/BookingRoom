package com.thangnvhe.bookingroom.ui.chat;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.thangnvhe.bookingroom.data.db.entities.MessageEntity;
import com.thangnvhe.bookingroom.data.repositories.MessageRepository;

import java.util.List;

public class ChatViewModel extends AndroidViewModel {
    private final MessageRepository repository;
    private final String userId;

    public ChatViewModel(@NonNull Application application) {
        super(application);
        SharedPreferences prefs = application.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        userId = prefs.getString("is_user_name", "");
        repository = new MessageRepository(application);
    }

    public LiveData<List<MessageEntity>> getMessages() {
        return repository.getMessagesByUserId(userId);
    }

    public LiveData<List<MessageEntity>> getMessagesForUser(String userId) {
        return repository.getMessagesByUserId(userId);
    }

    public LiveData<List<String>> getAllUserIds() {
        return repository.getAllUserIds();
    }

    public void sendMessage(String content) {
        MessageEntity message = new MessageEntity("user", content, System.currentTimeMillis(), userId);
        repository.insertMessage(message);

        new Thread(() -> {
            try {
                Thread.sleep(2000);
                MessageEntity reply = new MessageEntity("seller", "Cảm ơn bạn đã liên hệ! Chúng tôi sẽ phản hồi sớm.", System.currentTimeMillis(), userId);
                repository.insertMessage(reply);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void sendSellerMessage(String content, String userId) {
        MessageEntity message = new MessageEntity("seller", content, System.currentTimeMillis(), userId);
        repository.insertMessage(message);
    }
}