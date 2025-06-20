package com.thangnvhe.bookingroom.data.db.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "packages")
public class Package {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String type; // "seat" or "table"
    public int capacity; // 1-6 for seat, 4/6/12 for table
}
