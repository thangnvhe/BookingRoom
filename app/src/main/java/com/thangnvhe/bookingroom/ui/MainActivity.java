package com.thangnvhe.bookingroom.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.thangnvhe.bookingroom.R;
import com.thangnvhe.bookingroom.ui.auth.AuthViewModel;
import com.thangnvhe.bookingroom.ui.auth.LoginActivity;
import com.thangnvhe.bookingroom.ui.packages.PackageListActivity;

public class MainActivity extends AppCompatActivity {
    private AuthViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new AuthViewModel(this);

        // Kiểm tra đăng nhập
        if (!viewModel.isLoggedIn()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        // Khởi tạo màn hình chính (ví dụ: điều hướng đến PackageListActivity)
        startActivity(new Intent(this, PackageListActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            viewModel.logout();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}