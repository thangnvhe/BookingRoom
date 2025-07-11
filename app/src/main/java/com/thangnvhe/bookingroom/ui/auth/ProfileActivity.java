package com.thangnvhe.bookingroom.ui.auth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import com.thangnvhe.bookingroom.R;
import com.thangnvhe.bookingroom.data.AppDatabase;
import com.thangnvhe.bookingroom.data.db.dao.UserDao;
import com.thangnvhe.bookingroom.data.db.entities.User;
import com.thangnvhe.bookingroom.ui.MainActivity;

public class ProfileActivity extends AppCompatActivity {

    private TextView nameTv, emailTv, phoneTv, birth, address, usernameTv, emailRTv;
    private UserDao userDao;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        nameTv = findViewById(R.id.p_full_name);
        emailTv = findViewById(R.id.p_email);
        phoneTv = findViewById(R.id.p_phone);
        birth = findViewById(R.id.p_birth);
        address = findViewById(R.id.p_address);
        usernameTv = findViewById(R.id.user_name_tv);
        emailRTv = findViewById(R.id.user_email_tv);

        // Khởi tạo database
        userDao = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "app_database")
                .build()
                .userDao();

        // Lấy user name từ SharedPreferences
        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        boolean isLoggedIn = prefs.getBoolean("is_logged_in", false);
        username = prefs.getString("is_user_name", null);

        if (username != null && isLoggedIn) {
            loadUserInfo();
        }

        findViewById(R.id.btn_back_to_main).setOnClickListener(v -> {
            startActivity(new Intent(ProfileActivity.this, MainActivity.class));
            finish();
        });

        // Gắn click mở EditProfileActivity
        findViewById(R.id.btn_edit_profile).setOnClickListener(v -> {
            startActivity(new Intent(ProfileActivity.this, EditProfileActivity.class));
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        // Load lại thông tin mỗi khi quay lại màn hình
        loadUserInfo();
    }

    private void loadUserInfo() {
        new Thread(() -> {
            User user = userDao.getUserByUsername(username);
            runOnUiThread(() -> {
                if (user != null) {
                    nameTv.setText(user.fullName);
                    emailTv.setText(user.email);
                    phoneTv.setText(user.phone);
                    birth.setText(user.dob);
                    address.setText(user.address);
                    usernameTv.setText(user.username);
                    emailRTv.setText(user.email);
                }
            });
        }).start();
    }
}
