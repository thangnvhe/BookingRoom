package com.thangnvhe.bookingroom.data.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.thangnvhe.bookingroom.data.db.entities.FacilityEntity;

@Dao
public interface FacilityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFacility(FacilityEntity facility);
}

