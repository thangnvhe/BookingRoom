package com.thangnvhe.bookingroom.ui.packages;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.thangnvhe.bookingroom.data.db.relations.PackageWithFacilities;
import com.thangnvhe.bookingroom.data.repositories.PackageRepository;

import java.util.List;

public class PackageViewModel extends AndroidViewModel {
    private final PackageRepository repository;
    private final LiveData<List<PackageWithFacilities>> allPackages;

    public PackageViewModel(@NonNull Application application) {
        super(application);
        repository = new PackageRepository(application);
        allPackages = repository.getAllPackages();
    }

    public LiveData<List<PackageWithFacilities>> getAllPackages() {
        return allPackages;
    }
}
