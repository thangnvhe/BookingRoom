package com.thangnvhe.bookingroom.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.thangnvhe.bookingroom.R;
import com.thangnvhe.bookingroom.data.db.entities.CartItem;
import com.thangnvhe.bookingroom.ui.auth.LoginActivity;
import com.thangnvhe.bookingroom.ui.auth.ProfileActivity;
import com.thangnvhe.bookingroom.ui.cart.CartActivity;
import com.thangnvhe.bookingroom.ui.chat.ChatActivity;
import com.thangnvhe.bookingroom.ui.chat.SellerChatListActivity;
import com.thangnvhe.bookingroom.ui.map.MapActivity;
import com.thangnvhe.bookingroom.ui.packages.PackagesListActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "user_prefs";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String KEY_IS_USER_NAME = "is_user_name";
    private static final String KEY_USER_ROLE = "user_role";

    private CartViewModel cartViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        String username = prefs.getString(KEY_IS_USER_NAME, null);
        String userRole = prefs.getString(KEY_USER_ROLE, "user");

        if (!isLoggedIn || username == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
            return;
        }

        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        checkCartItems(username);

        // Sự kiện click các mục trong trang chính
        findViewById(R.id.ln_home).setOnClickListener(v ->
                startActivity(new Intent(this, PackagesListActivity.class)));

        findViewById(R.id.cardCart).setOnClickListener(v ->
                startActivity(new Intent(this, CartActivity.class)));

        findViewById(R.id.cardHistory).setOnClickListener(v ->
                startActivity(new Intent(this, HistoryActivity.class)));

        findViewById(R.id.cardChat).setOnClickListener(v -> {
            Intent intent = "seller".equals(userRole)
                    ? new Intent(this, SellerChatListActivity.class)
                    : new Intent(this, ChatActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.cardMap).setOnClickListener(v ->
                startActivity(new Intent(this, MapActivity.class)));

        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    private void checkCartItems(String username) {
        new Thread(() -> {
            try {
                int userId = cartViewModel.getUserIdByUsername(username);
                if (userId == -1) {
                    runOnUiThread(() ->
                            Toast.makeText(this, "Không tìm thấy người dùng!", Toast.LENGTH_SHORT).show());
                    return;
                }

                List<CartItem> cartItems = cartViewModel.getCartItemsByUserId(userId);
                if (!cartItems.isEmpty()) {
                    runOnUiThread(this::showCartNotificationDialog);
                }
            } catch (Exception e) {
                runOnUiThread(() ->
                        Toast.makeText(this, "Lỗi khi kiểm tra giỏ hàng: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    private void showCartNotificationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Thông báo giỏ hàng")
                .setMessage("Bạn có sản phẩm trong giỏ hàng, kiểm tra ngay?")
                .setPositiveButton("Vào giỏ hàng", (dialog, which) -> {
                    startActivity(new Intent(MainActivity.this, CartActivity.class));
                    dialog.dismiss();
                })
                .setNegativeButton("Đóng", (dialog, which) -> dialog.dismiss())
                .setCancelable(true)
                .show();
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
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return true;
        } else if (id == R.id.menu_settings) {
            Toast.makeText(this, "Chức năng cài đặt sẽ sớm ra mắt!", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.menu_cart) {
            startActivity(new Intent(this, CartActivity.class));
            return true;
        } else if (id == R.id.menu_profile) {
            startActivity(new Intent(this, ProfileActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
