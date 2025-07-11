package com.thangnvhe.bookingroom.data.db.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "cart_items",
        foreignKeys = {
                @ForeignKey(entity = User.class,
                        parentColumns = "id",
                        childColumns = "userId",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = PackageEntity.class,
                        parentColumns = "id",
                        childColumns = "packageId",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = FacilityEntity.class,
                        parentColumns = "id",
                        childColumns = "facilityId",
                        onDelete = ForeignKey.CASCADE)
        })
public class CartItem {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public int userId; // Liên kết với người dùng
    public int packageId; // Liên kết với gói dịch vụ
    public String selectedTimeSlot; // Khung giờ đã chọn
    public Integer facilityId; // Liên kết với tiện ích được chọn, cho phép null
    public int quantity; // Số lượng, mặc định là 1
    public String imageUrl; // Ảnh của gói dịch vụ
    public double totalPrice; // Tổng giá (giá gói + giá tiện ích × số lượng)

    public class CartDisplayItem {
        public String packageName;
        public String facilityName;
        public String selectedTimeSlot;
        public int quantity;
        public String imageUrl; // Thêm imageUrl
        public double totalPrice; // Thêm totalPrice

        public CartDisplayItem(String packageName, String facilityName, String selectedTimeSlot,
                               int quantity, String imageUrl, double totalPrice) {
            this.packageName = packageName;
            this.facilityName = facilityName;
            this.selectedTimeSlot = selectedTimeSlot;
            this.quantity = quantity;
            this.imageUrl = imageUrl;
            this.totalPrice = totalPrice;
        }
    }

    public CartItem(int userId, int packageId, String selectedTimeSlot, Integer facilityId,
                    int quantity, String imageUrl, double totalPrice) {
        this.userId = userId;
        this.packageId = packageId;
        this.selectedTimeSlot = selectedTimeSlot;
        this.facilityId = facilityId;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
        this.totalPrice = totalPrice;
    }
}