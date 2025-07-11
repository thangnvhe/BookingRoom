package com.thangnvhe.bookingroom.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.thangnvhe.bookingroom.data.db.entities.MessageEntity;

import java.util.List;

@Dao
public interface MessageDao {
    @Insert
    void insertMessage(MessageEntity message);

    @Query("SELECT * FROM messages WHERE userId = :userId ORDER BY timestamp ASC")
    LiveData<List<MessageEntity>> getMessagesByUserId(String userId);

    @Query("SELECT DISTINCT userId FROM messages")
    LiveData<List<String>> getAllUserIds();
}