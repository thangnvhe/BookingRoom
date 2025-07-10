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
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.thangnvhe.bookingroom.R;
import com.thangnvhe.bookingroom.ui.auth.LoginActivity;
import com.thangnvhe.bookingroom.ui.auth.ProfileActivity;
import com.thangnvhe.bookingroom.ui.chat.ChatActivity;
import com.thangnvhe.bookingroom.ui.chat.SellerChatListActivity;
import com.thangnvhe.bookingroom.ui.packages.PackagesListActivity;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "user_prefs";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String KEY_IS_USER_NAME = "is_user_name";
    private static final String KEY_USER_ROLE = "user_role";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean isLoggedIn = prefs.getBoolean(KEY_IS_LOGGED_IN, false);
        String isUserName = prefs.getString(KEY_IS_USER_NAME, null);
        String userRole = prefs.getString(KEY_USER_ROLE, "user"); // Mặc định là user

        if (!isLoggedIn || isUserName == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }

        CardView cardHome = findViewById(R.id.cardHome);
        cardHome.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, PackagesListActivity.class);
            startActivity(intent);
        });

        CardView cardChat = findViewById(R.id.cardChat);
        cardChat.setOnClickListener(v -> {
            Intent intent;
            if ("seller".equals(userRole)) {
                intent = new Intent(MainActivity.this, SellerChatListActivity.class);
            } else {
                intent = new Intent(MainActivity.this, ChatActivity.class);
            }
            startActivity(intent);
        });

        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_logout) {
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
            return true;
        } else if (id == R.id.menu_profile) {
            startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}