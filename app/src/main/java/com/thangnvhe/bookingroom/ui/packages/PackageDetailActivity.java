package com.thangnvhe.bookingroom.ui.packages;
import static android.content.Intent.getIntent;

import android.annotation.SuppressLint;
import android.content.Intent;
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

import com.google.android.flexbox.FlexboxLayout;
import com.thangnvhe.bookingroom.R;
import com.thangnvhe.bookingroom.data.db.entities.PackageEntity;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class PackageDetailActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
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

// Hiển thị nút back
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_left); // icon trái
        }

        PackageEntity pkg = (PackageEntity) getIntent().getSerializableExtra("package");

        TextView name = findViewById(R.id.productTitle);
        TextView capacity = findViewById(R.id.colorValue); // đã đổi nhãn thành "Sức chứa"
        TextView price = findViewById(R.id.totalPrice);
        TextView type = findViewById(R.id.brandValue);
        TextView description = findViewById(R.id.productDescription);
        TextView rating = findViewById(R.id.ratingText);
        TextView availableTime = findViewById(R.id.soldText);
        ImageView image = findViewById(R.id.mainProductImage);

        Button toggleFacilityBtn = findViewById(R.id.toggleFacilityBtn);
        Spinner facilitySpinner = findViewById(R.id.facilitySpinner);
        toggleFacilityBtn.setVisibility(View.GONE); // Ẩn ban đầu
        facilitySpinner.setVisibility(View.GONE);


// Nhận danh sách tiện ích từ Intent
        ArrayList<String> facilities = getIntent().getStringArrayListExtra("facility_names");

// Nếu null hoặc rỗng, hiển thị thông báo
        if (facilities == null || facilities.isEmpty()) {
            facilities = new ArrayList<>();
            facilities.add("Không có tiện ích kèm theo");
        }

// Gắn adapter cho Spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                facilities
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        facilitySpinner.setAdapter(spinnerAdapter);

// Bắt sự kiện toggle
        toggleFacilityBtn.setOnClickListener(v -> {
            if (facilitySpinner.getVisibility() == View.GONE) {
                facilitySpinner.setVisibility(View.VISIBLE);
                toggleFacilityBtn.setText("−"); // Hiện dấu trừ
            } else {
                facilitySpinner.setVisibility(View.GONE);
                toggleFacilityBtn.setText("+"); // Trở lại dấu cộng
            }
        });



        name.setText(pkg.name);
        capacity.setText(pkg.capacity + " người");
        price.setText(pkg.price + " VNĐ");
        type.setText(pkg.type);
        description.setText(pkg.description);
        rating.setText(pkg.rating + " Ratings");
//        availableTime.setText(pkg.availableTimeSlots);

        String imageName = pkg.imageUrl;
        int imageResId = getResources().getIdentifier(imageName, "drawable", getPackageName());

        if (imageResId != 0) {
            image.setImageResource(imageResId);
        } else {
            image.setImageResource(R.drawable.placeholder); // fallback
        }

        Glide.with(this)
                .load(imageResId)
                .placeholder(R.drawable.placeholder)
                .into(image);
        FlexboxLayout timeSlotContainer = findViewById(R.id.timeSlotContainer);

        try {
            JSONArray timeArray = new JSONArray(pkg.availableTimeSlots); // JSON dạng ["08:00-10:00", "15:00-17:00"]
            final TextView[] selectedChip = {null};
            for (int i = 0; i < timeArray.length(); i++) {
                String timeSlot = timeArray.getString(i);

                TextView chip = new TextView(this);
                chip.setText(timeSlot);
                chip.setTextSize(14);
                chip.setTextColor(getResources().getColor(android.R.color.black));
                chip.setBackgroundResource(R.drawable.bg_time_slot);
                chip.setPadding(32, 16, 32, 16);

                // Margin cho mỗi khung giờ
                ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(16, 8, 16, 8);
                chip.setLayoutParams(params);

                chip.setOnClickListener(v -> {
                    // Bỏ highlight chip trước nếu có
                    if (selectedChip[0] != null) {
                        selectedChip[0].setBackgroundResource(R.drawable.bg_time_slot);
                    }

                    // Highlight chip mới
                    chip.setBackgroundResource(R.drawable.bg_time_slot_selected);
                    selectedChip[0] = chip;

                    // 👉 Hiện button "+" chọn tiện ích
                    toggleFacilityBtn.setVisibility(View.VISIBLE);
                });


                timeSlotContainer.addView(chip);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            availableTime.setText("Không có thời gian khả dụng");
        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Quay lại PackagesListActivity
            finish(); // hoặc dùng onBackPressed()
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}