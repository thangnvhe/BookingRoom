package com.thangnvhe.bookingroom.data.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.thangnvhe.bookingroom.data.AppDatabase;
import com.thangnvhe.bookingroom.data.db.dao.PackageDao;
import com.thangnvhe.bookingroom.data.db.relations.PackageWithFacilities;

import java.util.List;

public class PackageRepository {
    private final PackageDao packageDao;

    public PackageRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        this.packageDao = db.packageDao();
    }

    public LiveData<List<PackageWithFacilities>> getAllPackages() {
        return packageDao.getAllPackagesWithFacilities();
    }
}
