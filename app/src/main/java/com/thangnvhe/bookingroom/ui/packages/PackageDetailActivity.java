package com.thangnvhe.bookingroom.ui.packages;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.flexbox.FlexboxLayout;
import com.thangnvhe.bookingroom.R;
import com.thangnvhe.bookingroom.data.AppDatabase;
import com.thangnvhe.bookingroom.data.db.entities.CartItem;
import com.thangnvhe.bookingroom.data.db.entities.FacilityEntity;
import com.thangnvhe.bookingroom.data.db.entities.PackageEntity;
import com.thangnvhe.bookingroom.data.db.entities.User;
import com.thangnvhe.bookingroom.data.repositories.UserRepository;
import com.thangnvhe.bookingroom.ui.CartViewModel;
import com.thangnvhe.bookingroom.ui.auth.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class PackageDetailActivity extends AppCompatActivity {

    private PackageEntity pkg;
    private TextView[] selectedChip = {null};
    private CartViewModel cartViewModel;
    private UserRepository userRepository;
    private static final String PREFS_NAME = "user_prefs";
    private static final String KEY_IS_USER_NAME = "is_user_name";
    
    // Thêm biến để lưu giá gốc và TextView giá
    private double basePrice = 0.0;
    private TextView priceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_package_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = findViewById(R.id.topBar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_left);
        }

        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        userRepository = new UserRepository(this);

        pkg = (PackageEntity) getIntent().getSerializableExtra("package");

        TextView name = findViewById(R.id.productTitle);
        TextView capacity = findViewById(R.id.colorValue);
        priceTextView = findViewById(R.id.totalPrice); // Lưu reference để cập nhật sau
        TextView type = findViewById(R.id.brandValue);
        TextView description = findViewById(R.id.productDescription);
        TextView rating = findViewById(R.id.ratingText);
        TextView availableTime = findViewById(R.id.soldText);
        ImageView image = findViewById(R.id.mainProductImage);
        Button toggleFacilityBtn = findViewById(R.id.toggleFacilityBtn);
        Spinner facilitySpinner = findViewById(R.id.facilitySpinner);
        Button addToCartButton = findViewById(R.id.addToCartButton);

        // Luôn hiển thị spinner tiện ích
        facilitySpinner.setVisibility(View.VISIBLE);
        toggleFacilityBtn.setVisibility(View.GONE);

        ArrayList<String> facilities = getIntent().getStringArrayListExtra("facility_names");
        if (facilities == null || facilities.isEmpty()) {
            facilities = new ArrayList<>();
            facilities.add("Không có tiện ích đi kèm");
        }

        // Đảm bảo có option "Không có tiện ích đi kèm" ở đầu danh sách
        if (!facilities.get(0).contains("không chọn tiện ích") && !facilities.get(0).equals("Không có tiện ích đi kèm")) {
            facilities.add(0, "Không có tiện ích đi kèm");
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, facilities);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        facilitySpinner.setAdapter(spinnerAdapter);

        // Thêm listener để cập nhật giá khi chọn tiện ích
        facilitySpinner.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, android.view.View view, int position, long id) {
                updatePriceWithFacility();
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {
                // Không làm gì
            }
        });

        // Loại bỏ listener cũ của toggleFacilityBtn vì không cần nữa

        name.setText(pkg.name);
        capacity.setText(pkg.capacity + " người");
        basePrice = pkg.price; // Lưu giá gốc
        priceTextView.setText(String.format("%.0f VNĐ", basePrice)); // Hiển thị giá gốc
        type.setText(pkg.type);
        description.setText(pkg.description);
        rating.setText(pkg.rating + " Ratings");

        // Cập nhật giá ngay khi load trang (với tiện ích mặc định)
        updatePriceWithFacility();

        String imageName = pkg.imageUrl;
        int imageResId = getResources().getIdentifier(imageName, "drawable", getPackageName());
        if (imageResId != 0) {
            image.setImageResource(imageResId);
        } else {
            image.setImageResource(R.drawable.placeholder);
        }

        FlexboxLayout timeSlotContainer = findViewById(R.id.timeSlotContainer);
        TextView[] selectedChip = {null}; // Để lưu chip được chọn
        try {
            JSONArray timeArray = new JSONArray(pkg.availableTimeSlots);
            for (int i = 0; i < timeArray.length(); i++) {
                String timeSlot = timeArray.getString(i);
                TextView chip = new TextView(this);
                chip.setText(timeSlot);
                chip.setTextSize(14);
                chip.setTextColor(getResources().getColor(android.R.color.white));
                chip.setBackgroundResource(R.drawable.bg_time_slot);
                chip.setPadding(32, 16, 32, 16);

                ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(16, 8, 16, 8);
                chip.setLayoutParams(params);

                chip.setOnClickListener(v -> {
                    // Reset chip trước đó
                    if (selectedChip[0] != null) {
                        selectedChip[0].setBackgroundResource(R.drawable.bg_time_slot);
                        selectedChip[0].setTextColor(getResources().getColor(android.R.color.white));
                    }
                    // Set chip hiện tại
                    chip.setBackgroundResource(R.drawable.bg_time_slot_selected);
                    chip.setTextColor(getResources().getColor(android.R.color.black));
                    selectedChip[0] = chip;
                    this.selectedChip[0] = chip; // Cập nhật biến instance
                });

                timeSlotContainer.addView(chip);
                
                // Chọn slot đầu tiên mặc định
                if (i == 0) {
                    chip.setBackgroundResource(R.drawable.bg_time_slot_selected);
                    chip.setTextColor(getResources().getColor(android.R.color.black));
                    selectedChip[0] = chip;
                    this.selectedChip[0] = chip; // Cập nhật biến instance
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            // availableTime.setText("Không có thời gian khả dụng");
        }

        addToCartButton.setOnClickListener(v -> {
            SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            String username = prefs.getString(KEY_IS_USER_NAME, null);

            if (username == null) {
                Toast.makeText(this, "Vui lòng đăng nhập để thêm vào giỏ hàng!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, LoginActivity.class));
                return;
            }

            new Thread(() -> {
                try {
                    User user = userRepository.getUserByUsername(username);
                    if (user == null) {
                        runOnUiThread(() -> Toast.makeText(this, "Lỗi: Không tìm thấy người dùng!", Toast.LENGTH_SHORT).show());
                        return;
                    }

                    String selectedTime = selectedChip[0] != null ? selectedChip[0].getText().toString() : null;
                    if (selectedTime == null) {
                        runOnUiThread(() -> Toast.makeText(this, "Vui lòng chọn khung giờ!", Toast.LENGTH_SHORT).show());
                        return;
                    }

                    String selectedFacility = facilitySpinner.getSelectedItem() != null ? facilitySpinner.getSelectedItem().toString() : null;
                    Integer facilityId = null;
                    double facilityPrice = 0.0;
                    if (selectedFacility != null && 
                        !selectedFacility.equals("Không có tiện ích đi kèm") && 
                        !selectedFacility.equals("không chọn tiện ích (0)")) {
                        try {
                            String idStr = selectedFacility.substring(selectedFacility.lastIndexOf("(") + 1, selectedFacility.lastIndexOf(")"));
                            facilityId = Integer.parseInt(idStr);
                            // Lấy giá tiện ích từ database
                            FacilityEntity facility = AppDatabase.getInstance(this).facilityDao()
                                    .getFacilityById(facilityId);
                            if (facility != null) {
                                facilityPrice = facility.extraPrice;
                            }
                        } catch (Exception e) {
                            runOnUiThread(() -> Toast.makeText(this, "Lỗi: Không thể lấy ID tiện ích!", Toast.LENGTH_SHORT).show());
                            return;
                        }
                    }

                    // Tính tổng giá: (giá gói + giá tiện ích) × quantity
                    int quantity = 1; // Mặc định số lượng là 1
                    double totalPrice = (pkg.price + facilityPrice) * quantity;

                    // Lấy imageUrl từ PackageEntity
                    String imageUrl = pkg.imageUrl;

                    CartItem cartItem = new CartItem(user.id, pkg.id, selectedTime, facilityId, quantity, imageUrl, totalPrice);
                    cartViewModel.insert(cartItem);
                    runOnUiThread(() -> Toast.makeText(this, "Đã thêm vào giỏ hàng!", Toast.LENGTH_SHORT).show());
                } catch (Exception e) {
                    runOnUiThread(() -> Toast.makeText(this, "Lỗi khi thêm vào giỏ hàng: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                }
            }).start();
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Cập nhật giá hiển thị khi người dùng chọn tiện ích
     */
    private void updatePriceWithFacility() {
        new Thread(() -> {
            try {
                Spinner facilitySpinner = findViewById(R.id.facilitySpinner);
                String selectedFacility = facilitySpinner.getSelectedItem() != null ? 
                    facilitySpinner.getSelectedItem().toString() : null;
                
                double facilityPrice = 0.0;
                
                if (selectedFacility != null && 
                    !selectedFacility.equals("Không có tiện ích đi kèm") && 
                    !selectedFacility.equals("không chọn tiện ích (0)")) {
                    
                    try {
                        // Extract facility ID from string like "Tên tiện ích (123)"
                        String idStr = selectedFacility.substring(
                            selectedFacility.lastIndexOf("(") + 1, 
                            selectedFacility.lastIndexOf(")")
                        );
                        Integer facilityId = Integer.parseInt(idStr);
                        
                        // Lấy giá tiện ích từ database
                        FacilityEntity facility = AppDatabase.getInstance(this).facilityDao()
                                .getFacilityById(facilityId);
                        if (facility != null) {
                            facilityPrice = facility.extraPrice;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        facilityPrice = 0.0;
                    }
                }
                
                // Tính tổng giá và cập nhật UI
                double totalPrice = basePrice + facilityPrice;
                final double finalTotalPrice = totalPrice;
                final double finalFacilityPrice = facilityPrice;
                
                runOnUiThread(() -> {
                    if (finalFacilityPrice > 0) {
                        priceTextView.setText(String.format("%.0f VNĐ (%.0f + %.0f)", 
                            finalTotalPrice, basePrice, finalFacilityPrice));
                    } else {
                        priceTextView.setText(String.format("%.0f VNĐ", finalTotalPrice));
                    }
                });
                
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> {
                    priceTextView.setText(String.format("%.0f VNĐ", basePrice));
                });
            }
        }).start();
    }
}