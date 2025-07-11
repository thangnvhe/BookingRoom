package com.thangnvhe.bookingroom.data.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.thangnvhe.bookingroom.data.db.entities.FacilityEntity;

@Dao
public interface FacilityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFacility(FacilityEntity facility);

    @Query("SELECT id FROM facilities WHERE name = :name LIMIT 1")
    int getFacilityIdByName(String name);

    @Query("SELECT name FROM facilities WHERE id = :id LIMIT 1")
    String getFacilityNameById(int id);

    @Query("SELECT * FROM facilities WHERE id = :id LIMIT 1")
    FacilityEntity getFacilityById(int id);
}