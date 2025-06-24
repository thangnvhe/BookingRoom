package com.thangnvhe.bookingroom.ui.auth;

import android.content.Intent;
import android.os.Bundle;

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

import android.content.SharedPreferences;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

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
        TextView nameTv = findViewById(R.id.p_full_name);
        TextView emailTv = findViewById(R.id.p_email);
        TextView phoneTv = findViewById(R.id.p_phone);
        TextView birth = findViewById(R.id.p_birth);
        TextView address = findViewById(R.id.p_address);
        TextView usernameTv = findViewById(R.id.user_name_tv);
        TextView emailRTv = findViewById(R.id.user_email_tv);

        UserDao userDao = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "app_database")
//                .allowMainThreadQueries() // Không nên dùng trong app thực, chỉ debug
                .build()
                .userDao();

        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        boolean isLoggedIn = prefs.getBoolean("is_logged_in", false);
        String username = prefs.getString("is_user_name", null);
        if(username!=null && isLoggedIn){
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

        findViewById(R.id.btn_back_to_main).setOnClickListener(v -> {
            startActivity(new Intent(ProfileActivity.this, MainActivity.class));
            finish(); // Đóng ProfileActivity để không quay lại bằng nút back
        });
    }
}