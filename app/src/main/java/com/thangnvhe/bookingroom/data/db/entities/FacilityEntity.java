package com.thangnvhe.bookingroom.data.db.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "facilities",
        foreignKeys = @ForeignKey(
                entity = PackageEntity.class,
                parentColumns = "id",
                childColumns = "packageId",
                onDelete = ForeignKey.CASCADE),
        indices = {@Index("packageId")})
public class FacilityEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public int packageId;          // Liên kết đến gói

    @NonNull
    public String name;            // Tên tiện ích: "TV", "Internet", "Bảng trắng"

    public String description;     // Mô tả tiện ích (nếu cần)

    public double extraPrice;      // Phụ thu nếu chọn tiện ích này (nếu có)
}
