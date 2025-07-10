package com.thangnvhe.bookingroom.ui.auth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.thangnvhe.bookingroom.R;
import com.thangnvhe.bookingroom.ui.MainActivity;

public class LoginActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "user_prefs";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String KEY_IS_USER_NAME = "is_user_name";
    private static final String KEY_USER_ROLE = "user_role"; // Thêm key cho vai trò

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText etUsername = findViewById(R.id.etUsername);
        EditText etPassword = findViewById(R.id.etPassword);
        Button btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            // Giả sử kiểm tra đăng nhập (thay bằng logic thật của bạn)
            if (username.equals("seller") && password.equals("123456")) { // Ví dụ: seller đăng nhập
                SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                prefs.edit()
                        .putBoolean(KEY_IS_LOGGED_IN, true)
                        .putString(KEY_IS_USER_NAME, username)
                        .putString(KEY_USER_ROLE, "seller") // Lưu vai trò seller
                        .apply();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            } else if (username.equals("user") && password.equals("123456")) { // Ví dụ: user đăng nhập
                SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                prefs.edit()
                        .putBoolean(KEY_IS_LOGGED_IN, true)
                        .putString(KEY_IS_USER_NAME, username)
                        .putString(KEY_USER_ROLE, "user") // Lưu vai trò user
                        .apply();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Sai tên đăng nhập hoặc mật khẩu!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}