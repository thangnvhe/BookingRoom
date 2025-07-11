package com.thangnvhe.bookingroom.ui.cart;

public class CartDisplayItem {
    public int cartItemId;
    public int packageId; // ✅ Thêm dòng này
    public String packageName;
    public String facilityName;
    public String selectedTimeSlot;
    public String imageUrl;
    public int quantity;
    public double totalPrice;

    public CartDisplayItem(int cartItemId, int packageId, String packageName, String facilityName,
                           String selectedTimeSlot, String imageUrl, int quantity, double totalPrice) {
        this.cartItemId = cartItemId;
        this.packageId = packageId; // ✅ Gán packageId
        this.packageName = packageName;
        this.facilityName = facilityName;
        this.selectedTimeSlot = selectedTimeSlot;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }
}
