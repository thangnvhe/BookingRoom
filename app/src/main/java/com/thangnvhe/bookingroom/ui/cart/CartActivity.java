package com.thangnvhe.bookingroom.ui.cart;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;
import com.thangnvhe.bookingroom.ui.MainActivity; // hoặc đúng package của bạn


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thangnvhe.bookingroom.R;
import com.thangnvhe.bookingroom.data.AppDatabase;
import com.thangnvhe.bookingroom.data.db.dao.FacilityDao;
import com.thangnvhe.bookingroom.data.db.dao.PackageDao;
import com.thangnvhe.bookingroom.data.db.entities.CartItem;
import com.thangnvhe.bookingroom.data.db.entities.User;
import com.thangnvhe.bookingroom.data.repositories.CartRepository;
import com.thangnvhe.bookingroom.data.repositories.UserRepository;
import com.thangnvhe.bookingroom.ui.auth.LoginActivity;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    private RecyclerView recyclerCart;
    private TextView tvTotalCartPrice;
    private CartRepository cartRepo;
    private UserRepository userRepo;
    private PackageDao packageDao;
    private FacilityDao facilityDao;
    private int userId;
    private CartAdapter adapter;
    private final List<CartDisplayItem> displayItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerCart = findViewById(R.id.recyclerCart);
        tvTotalCartPrice = findViewById(R.id.tvTotalCartPrice);
        recyclerCart.setLayoutManager(new LinearLayoutManager(this));

        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String username = prefs.getString("is_user_name", null);

        if (username == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        cartRepo = new CartRepository(this);
        userRepo = new UserRepository(this);
        AppDatabase db = AppDatabase.getInstance(this);
        packageDao = db.packageDao();
        facilityDao = db.facilityDao();

        new Thread(() -> {
            User user = userRepo.getUserByUsername(username);
            if (user == null) {
                runOnUiThread(() -> {
                    startActivity(new Intent(CartActivity.this, LoginActivity.class));
                    finish();
                });
                return;
            }

            userId = user.id;
            loadCartItems();
        }).start();
    }

    private void loadCartItems() {
        List<CartItem> cartItems = cartRepo.getCartItemsByUserId(userId);
        displayItems.clear();
        double totalCartPrice = 0.0;

        for (CartItem item : cartItems) {
            String packageName = packageDao.getPackageNameById(item.packageId);
            String facilityName = item.facilityId != null
                    ? facilityDao.getFacilityNameById(item.facilityId)
                    : "Không có";

            displayItems.add(new CartDisplayItem(
                    item.id,
                    item.packageId, // ✅ thêm dòng này
                    packageName,
                    facilityName,
                    item.selectedTimeSlot,
                    item.imageUrl,
                    item.quantity,
                    item.totalPrice
            ));

            totalCartPrice += item.totalPrice;
        }

        double finalTotalCartPrice = totalCartPrice;
        runOnUiThread(() -> {
            adapter = new CartAdapter(displayItems,
                    (cartItemId, position) -> {
                        new Thread(() -> {
                            cartRepo.deleteCartItemById(cartItemId);
                            runOnUiThread(() -> {
                                adapter.removeItem(position);
                                updateTotalCartPrice();
                            });
                        }).start();
                    },
                    (cartItemId, newQuantity, newTotalPrice, position) -> {
                        new Thread(() -> {
                            cartRepo.updateQuantityAndPrice(cartItemId, newQuantity, newTotalPrice);
                            runOnUiThread(() -> {
                                adapter.notifyItemChanged(position);
                                updateTotalCartPrice();
                            });
                        }).start();
                    });

            recyclerCart.setAdapter(adapter);
            tvTotalCartPrice.setText(String.format("Tổng giá: %.0f VNĐ", finalTotalCartPrice));

            // ✅ Xử lý nút "Thanh toán"
            Button btnCheckout = findViewById(R.id.btnCheckout);
            btnCheckout.setOnClickListener(v -> {
                if (displayItems.isEmpty()) return;

                StringBuilder billDetails = new StringBuilder();
                StringBuilder amenities = new StringBuilder();
                int selectedPackageId = -1;
                double totalPrice = 0.0;

                for (CartDisplayItem item : displayItems) {
                    billDetails.append(String.format("- %s (%s) x%d: %.0f VNĐ\n",
                            item.packageName, item.facilityName, item.quantity, item.totalPrice));
                    amenities.append(item.facilityName).append(", ");
                    selectedPackageId = item.packageId;
                    totalPrice += item.totalPrice;
                }

                if (amenities.length() > 2) {
                    amenities.setLength(amenities.length() - 2); // Xóa dấu ", " cuối
                }

                // ✅ Khai báo final để dùng trong lambda
                final int finalPackageId = selectedPackageId;
                final String finalTotalPrice = String.format("%.0f", totalPrice);
                final String finalAmenities = amenities.toString();
                final String finalBillDetails = billDetails.toString();

                // ✅ Xóa giỏ hàng sau khi thanh toán và chuyển sang ActivityBooking
                new Thread(() -> {
                    cartRepo.clearCartByUserId(userId);
                    runOnUiThread(() -> {
                        Intent intent = new Intent(CartActivity.this, com.thangnvhe.bookingroom.ui.ActivityBooking.class);
                        intent.putExtra("userId", userId);
                        intent.putExtra("packageId", finalPackageId);
                        intent.putExtra("totalPrice", finalTotalPrice);
                        intent.putExtra("amenities", finalAmenities);
                        intent.putExtra("billDetails", finalBillDetails);
                        startActivity(intent);
                    });
                }).start();
            });
        });


    }

    private void updateTotalCartPrice() {
        double newTotalCartPrice = 0.0;
        for (CartDisplayItem item : displayItems) {
            newTotalCartPrice += item.totalPrice;
        }
        tvTotalCartPrice.setText(String.format("Tổng giá: %.0f VNĐ", newTotalCartPrice));
    }
}