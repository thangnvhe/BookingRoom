package com.thangnvhe.bookingroom.data.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.thangnvhe.bookingroom.data.db.entities.Booking;

import java.util.List;

@Dao
public interface BookingDao {
    @Insert
    void insert(Booking booking);

    @Query("SELECT * FROM booking")
    List<Booking> getAll();

    @Query("DELETE FROM booking")
    void deleteAll();
}
