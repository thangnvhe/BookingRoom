package com.thangnvhe.bookingroom.ui.packages;

import android.content.Context;

import com.thangnvhe.bookingroom.data.repositories.PackageRepository;

public class PackageViewModel {
    private PackageRepository repository;

    public PackageViewModel(Context context) {
        repository = new PackageRepository(context);
    }

    public void getPackages(PackageRepository.OnPackageResultListener listener) {
        repository.getPackages(listener);
    }
}
