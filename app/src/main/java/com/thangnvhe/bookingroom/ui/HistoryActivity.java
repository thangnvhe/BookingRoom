package com.thangnvhe.bookingroom.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thangnvhe.bookingroom.R;
import com.thangnvhe.bookingroom.data.AppDatabase;
import com.thangnvhe.bookingroom.data.db.dao.BookingDao;
import com.thangnvhe.bookingroom.data.db.entities.Booking;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HistoryActivity extends AppCompatActivity {
    private RecyclerView recyclerHistory;
    private HistoryAdapter adapter;
    private BookingDao bookingDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recyclerHistory = findViewById(R.id.recyclerHistory);
        recyclerHistory.setLayoutManager(new LinearLayoutManager(this));

        AppDatabase db = AppDatabase.getInstance(this);
        bookingDao = db.bookingDao();

        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String username = prefs.getString("is_user_name", null);
        if (username == null) {
            Toast.makeText(this, "Vui lòng đăng nhập", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        new Thread(() -> {
            int userId = db.userDao().getUserByUsername(username).id;
            List<Booking> bookings = bookingDao.getAll();

            List<BookingDisplayItem> displayItems = new ArrayList<>();
            for (Booking booking : bookings) {
                if (booking.userId == userId) {
                    String orderId = "Mã đơn: #" + booking.id;
                    String orderDate = "Ngày đặt: " + new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                            .format(new Date(booking.timestamp));
                    String totalPrice = "Tổng tiền: " + booking.totalPrice + "đ";
                    String amenities = booking.amenities;
                    String status = booking.status;

                    displayItems.add(new BookingDisplayItem(orderId, orderDate, totalPrice, amenities, status));
                }
            }

            runOnUiThread(() -> {
                adapter = new HistoryAdapter(displayItems);
                recyclerHistory.setAdapter(adapter);
            });
        }).start();
    }
}