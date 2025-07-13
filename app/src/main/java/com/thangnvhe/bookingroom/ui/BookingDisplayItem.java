package com.thangnvhe.bookingroom.ui;

public class BookingDisplayItem {
    public String orderId;
    public String orderDate;
    public String totalPrice;
    public String amenities;
    public String status;

    public BookingDisplayItem(String orderId, String orderDate, String totalPrice, String amenities, String status) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.amenities = amenities;
        this.status = status;
    }
}
