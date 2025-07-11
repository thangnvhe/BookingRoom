package com.thangnvhe.bookingroom.data.db.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "messages")
public class MessageEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String sender;
    public String content;
    public long timestamp;
    public String userId;

    public MessageEntity(String sender, String content, long timestamp, String userId) {
        this.sender = sender;
        this.content = content;
        this.timestamp = timestamp;
        this.userId = userId;
    }
}