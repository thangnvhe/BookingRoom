package com.thangnvhe.bookingroom.ui.packages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thangnvhe.bookingroom.R;
import com.thangnvhe.bookingroom.data.db.entities.Package;
import com.thangnvhe.bookingroom.data.repositories.PackageRepository;
import com.thangnvhe.bookingroom.ui.adapter.PackageAdapter;
import com.thangnvhe.bookingroom.ui.auth.LoginActivity;

import java.util.List;

public class PackageListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PackageAdapter adapter;
    private PackageViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_list);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        viewModel = new PackageViewModel(this);

        viewModel.getPackages(new PackageRepository.OnPackageResultListener() {
            @Override
            public void onSuccess(List<Package> packages) {
                runOnUiThread(() -> {
                    TextView emptyTextView = findViewById(R.id.emptyTextView);

                    if (packages == null || packages.isEmpty()) {
                        emptyTextView.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    } else {
                        emptyTextView.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);

                        adapter = new PackageAdapter(packages, pkg -> {
                            Intent intent = new Intent(PackageListActivity.this, PackageDetailActivity.class);
                            intent.putExtra("PACKAGE_ID", pkg.id);
                            startActivity(intent);
                        });
                        recyclerView.setAdapter(adapter);
                    }
                });
            }
        });
    }
}
