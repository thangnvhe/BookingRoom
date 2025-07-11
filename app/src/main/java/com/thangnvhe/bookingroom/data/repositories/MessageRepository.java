package com.thangnvhe.bookingroom.data.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.thangnvhe.bookingroom.data.AppDatabase;
import com.thangnvhe.bookingroom.data.db.dao.MessageDao;
import com.thangnvhe.bookingroom.data.db.entities.MessageEntity;

import java.util.List;

public class MessageRepository {
    private final MessageDao messageDao;

    public MessageRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        messageDao = db.messageDao();
    }

    public void insertMessage(MessageEntity message) {
        new Thread(() -> messageDao.insertMessage(message)).start();
    }

    public LiveData<List<MessageEntity>> getMessagesByUserId(String userId) {
        return messageDao.getMessagesByUserId(userId);
    }

    public LiveData<List<String>> getAllUserIds() {
        return messageDao.getAllUserIds();
    }
}