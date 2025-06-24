package com.thangnvhe.bookingroom.data.db.relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.thangnvhe.bookingroom.data.db.entities.FacilityEntity;
import com.thangnvhe.bookingroom.data.db.entities.PackageEntity;

import java.util.List;

// Tạo trong thư mục: com.yourapp.model hoặc com.yourapp.data.model
public class PackageWithFacilities {
    @Embedded
    public PackageEntity packageEntity;

    @Relation(
            parentColumn = "id",
            entityColumn = "packageId"
    )
    public List<FacilityEntity> facilities;
}

