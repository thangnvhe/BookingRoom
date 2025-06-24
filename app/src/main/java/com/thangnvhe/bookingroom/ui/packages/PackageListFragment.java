package com.thangnvhe.bookingroom.ui.packages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thangnvhe.bookingroom.R;
import com.thangnvhe.bookingroom.ui.adapter.PackageAdapter;

public class PackageListFragment extends Fragment {

    private PackageViewModel viewModel;
    private PackageAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_packages_list, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.recyclerViewPackages);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PackageAdapter(getContext());
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(PackageViewModel.class);
        viewModel.getAllPackages().observe(getViewLifecycleOwner(), adapter::setPackages);

        return root;
    }
}

