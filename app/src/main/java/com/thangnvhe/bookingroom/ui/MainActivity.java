package com.thangnvhe.bookingroom.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.thangnvhe.bookingroom.R;
import com.thangnvhe.bookingroom.ui.auth.LoginActivity;
import com.thangnvhe.bookingroom.ui.auth.ProfileActivity;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "user_prefs";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Xử lý insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Gắn Toolbar nếu có
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Kiểm tra đăng nhập
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean isLoggedIn = prefs.getBoolean(KEY_IS_LOGGED_IN, false);

        if (!isLoggedIn) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish(); // Không cho quay lại MainActivity khi chưa login
        }
    }

    // Gắn menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    // Xử lý click menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_logout) {
            // Đăng xuất
            SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            prefs.edit().putBoolean(KEY_IS_LOGGED_IN, false).apply();

            Toast.makeText(this, "Đã đăng xuất!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
            return true;

        } else if (id == R.id.menu_settings) {
            Toast.makeText(this, "Chức năng cài đặt sẽ được thêm sau!", Toast.LENGTH_SHORT).show();
            return true;

        } else if (id == R.id.menu_cart) {
            //startActivity(new Intent(MainActivity.this, CartActivity.class));
            return true;

        } else if (id == R.id.menu_profile) {
            startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
