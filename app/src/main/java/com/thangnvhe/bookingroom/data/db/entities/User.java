package com.thangnvhe.bookingroom.data.db.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String fullName;
    public String email;
    public String username;
    public String password;
    public String phone;
    public String gender;
    public String dob;
    public String address;
    public String role; // Cột role để phân biệt user/seller

    public User(String fullName, String email, String username, String password, String phone, String gender, String dob, String address, String role) {
        this.fullName = fullName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.gender = gender;
        this.dob = dob;
        this.address = address;
        this.role = role;
    }
}