package com.thangnvhe.bookingroom.data.db.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "booking",
        foreignKeys = {
                @ForeignKey(
                        entity = User.class,
                        parentColumns = "id",
                        childColumns = "userId",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = PackageEntity.class,
                        parentColumns = "id",
                        childColumns = "packageId",
                        onDelete = ForeignKey.CASCADE
                )
        }
)
public class Booking {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public int userId;
    public int packageId;
    public String amenities;
    public long timestamp; // Unix timestamp
    public String status;  // "confirmed", "cancelled"
    public double totalPrice; // Thêm trường totalPrice
}