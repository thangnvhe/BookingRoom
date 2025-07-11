package com.thangnvhe.bookingroom.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.thangnvhe.bookingroom.R;
import com.thangnvhe.bookingroom.data.db.entities.CartItem;
import com.thangnvhe.bookingroom.ui.auth.LoginActivity;
import com.thangnvhe.bookingroom.ui.auth.ProfileActivity;
import com.thangnvhe.bookingroom.ui.cart.CartActivity;
import com.thangnvhe.bookingroom.ui.packages.PackagesListActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "user_prefs";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String KEY_IS_USER_NAME = "is_user_name";

    private CartViewModel cartViewModel;

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

        // Khởi tạo Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Khởi tạo CartViewModel
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);

        // Kiểm tra trạng thái đăng nhập
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean isLoggedIn = prefs.getBoolean(KEY_IS_LOGGED_IN, false);
        String username = prefs.getString(KEY_IS_USER_NAME, null);

        if (!isLoggedIn || username == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
            return;
        }

        // Kiểm tra giỏ hàng
        checkCartItems(username);

        // Xử lý sự kiện click
        LinearLayout lnHome = findViewById(R.id.ln_home);
        lnHome.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, PackagesListActivity.class));
        });

        CardView cardCart = findViewById(R.id.cardCart);
        cardCart.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, CartActivity.class));
        });

        CardView cardHistory = findViewById(R.id.cardHistory);
        cardHistory.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
            startActivity(intent);
        });


        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    private void checkCartItems(String username) {
        new Thread(() -> {
            try {
                // Lấy userId từ username
                int userId = cartViewModel.getUserIdByUsername(username);
                if (userId == -1) { // Giả sử getUserIdByUsername trả về -1 nếu không tìm thấy
                    runOnUiThread(() -> Toast.makeText(this, "Lỗi: Không tìm thấy người dùng!", Toast.LENGTH_SHORT).show());
                    return;
                }

                // Lấy danh sách CartItem
                List<CartItem> cartItems = cartViewModel.getCartItemsByUserId(userId);
                if (!cartItems.isEmpty()) {
                    // Nếu có sản phẩm trong giỏ hàng, hiển thị AlertDialog
                    runOnUiThread(() -> showCartNotificationDialog());
                }
            } catch (Exception e) {
                runOnUiThread(() -> Toast.makeText(this, "Lỗi khi kiểm tra giỏ hàng: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    private void showCartNotificationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Thông báo giỏ hàng")
                .setMessage("Có sản phẩm trong giỏ hàng, bạn vui lòng vào giỏ hàng để kiểm tra và mua sắm.")
                .setPositiveButton("Vào giỏ hàng", (dialog, which) -> {
                    startActivity(new Intent(MainActivity.this, CartActivity.class));
                    dialog.dismiss();
                })
                .setNegativeButton("Đóng", (dialog, which) -> dialog.dismiss())
                .setCancelable(true)
                .show();
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
            startActivity(new Intent(MainActivity.this, CartActivity.class));
            return true;

        } else if (id == R.id.menu_profile) {
            startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}