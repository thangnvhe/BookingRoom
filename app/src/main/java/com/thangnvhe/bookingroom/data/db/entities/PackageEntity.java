package com.thangnvhe.bookingroom.data.db.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "packages")
public class PackageEntity implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    public String name;              // Tên gói: "Bàn 6 chỗ", "Ghế đơn"

    public String description;       // Mô tả chi tiết gói

    public String imageUrl;          // Hình ảnh gói

    public double price;             // Giá cơ bản

    @NonNull
    public String type;              // "seat" hoặc "table"

    public int capacity;             // Số chỗ ngồi

    public boolean isActive;         // Gói này còn hoạt động không?

    public float rating;             // Đánh giá trung bình (1.0 - 5.0)

    public String availableTimeSlots; // JSON dạng chuỗi, ví dụ: "[\"08:00-10:00\", \"13:00-15:00\"]"
}

