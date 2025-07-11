package com.thangnvhe.bookingroom.ui;


import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.thangnvhe.bookingroom.R;
import com.thangnvhe.bookingroom.data.AppDatabase;
import com.thangnvhe.bookingroom.data.db.entities.Booking;

import java.util.concurrent.Executors;

public class ActivityBooking extends AppCompatActivity {

    private TextView tvBillDetails, tvBillTotal;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking); // Giao diện XML: activity_booking.xml

        // Ánh xạ giao diện
        tvBillDetails = findViewById(R.id.tvBillDetails);
        tvBillTotal = findViewById(R.id.tvBillTotal);

        // Khởi tạo database
        db = AppDatabase.getInstance(this);

        // Nhận dữ liệu từ Intent
        int userId = getIntent().getIntExtra("userId", -1);
        int packageId = getIntent().getIntExtra("packageId", -1);
        String amenities = getIntent().getStringExtra("amenities");
        String billDetails = getIntent().getStringExtra("billDetails");
        String totalPrice = getIntent().getStringExtra("totalPrice");

        // Hiển thị dữ liệu hóa đơn lên giao diện
        tvBillDetails.setText(billDetails);
        tvBillTotal.setText("Tổng cộng: " + totalPrice + " VNĐ");

        // Tạo đối tượng Booking để lưu vào Room DB
        Booking booking = new Booking();
        booking.userId = userId;
        booking.packageId = packageId;
        booking.amenities = amenities;
        booking.timestamp = System.currentTimeMillis();
        booking.status = "confirmed";

        // Lưu vào DB trong background thread
        Executors.newSingleThreadExecutor().execute(() -> {
            db.bookingDao().insert(booking);
            runOnUiThread(() ->
                    Toast.makeText(this, "Thanh toán thành công! Hóa đơn đã được lưu.", Toast.LENGTH_LONG).show());
        });
    }
}
