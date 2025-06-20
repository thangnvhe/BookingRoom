//package com.thangnvhe.bookingroom.data.db.entities;
//
//import androidx.room.Entity;
//import androidx.room.ForeignKey;
//import androidx.room.PrimaryKey;
//import com.thangnvhe.bookingroom.data.db.entities.Package;
//import com.thangnvhe.bookingroom.data.db.entities.User;
//@Entity(tableName = "bookings", foreignKeys = {
//        @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "userId"),
//        @ForeignKey(entity = Package.class, parentColumns = "id", childColumns = "packageId")
//})
//public class Booking {
//    @PrimaryKey(autoGenerate = true)
//    public int id;
//    public int userId;
//    public int packageId;
//    public String amenities;
//    public long timestamp;
//    public String status;
//}