package com.thangnvhe.bookingroom.data.db.daos;

import androidx.room.Dao;
import androidx.room.Query;
import com.thangnvhe.bookingroom.data.db.entities.Package;
import java.util.List;

@Dao
public interface PackageDao {
    @Query("SELECT * FROM packages")
    List<Package> getAll();
}