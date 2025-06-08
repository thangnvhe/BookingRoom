package com.thangnvhe.bookingroom.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.thangnvhe.bookingroom.data.db.daos.BookingDao;
import com.thangnvhe.bookingroom.data.db.daos.CartItemDao;
import com.thangnvhe.bookingroom.data.db.daos.PackageDao;
import com.thangnvhe.bookingroom.data.db.daos.UserDao;
import com.thangnvhe.bookingroom.data.db.entities.User;
import com.thangnvhe.bookingroom.data.db.entities.Package;

@Database(entities = {User.class, Package.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract PackageDao packageDao();
//    public abstract CartItemDao cartItemDao();
//    public abstract BookingDao bookingDao();
}
