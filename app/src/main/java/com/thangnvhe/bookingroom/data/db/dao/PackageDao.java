package com.thangnvhe.bookingroom.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.thangnvhe.bookingroom.data.db.entities.PackageEntity;
import com.thangnvhe.bookingroom.data.db.relations.PackageWithFacilities;

import java.util.List;

@Dao
public interface PackageDao {

    @Transaction
    @Query("SELECT * FROM packages")
    LiveData<List<PackageWithFacilities>> getAllPackagesWithFacilities();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertPackage(PackageEntity packageEntity);
}
