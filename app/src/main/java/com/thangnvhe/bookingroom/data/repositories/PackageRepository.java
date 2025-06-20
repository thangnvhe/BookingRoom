package com.thangnvhe.bookingroom.data.repositories;

import android.content.Context;

import androidx.room.Room;

import com.thangnvhe.bookingroom.data.db.AppDatabase;
import com.thangnvhe.bookingroom.data.db.daos.PackageDao;
import com.thangnvhe.bookingroom.data.db.entities.Package;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PackageRepository {
    private PackageDao packageDao;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    public PackageRepository(Context context) {
        AppDatabase db = Room.databaseBuilder(context.getApplicationContext(),
                AppDatabase.class, "lab_booking_db").build();
        packageDao = db.packageDao();
    }

    public void getPackages(OnPackageResultListener listener) {
        executorService.execute(() -> {
            List<Package> packages = packageDao.getAll();
            listener.onSuccess(packages);
        });
    }

    public interface OnPackageResultListener {
        void onSuccess(List<Package> packages);
    }
}