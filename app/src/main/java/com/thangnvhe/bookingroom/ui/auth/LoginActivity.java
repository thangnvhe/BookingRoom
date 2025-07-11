package com.thangnvhe.bookingroom.ui.auth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.thangnvhe.bookingroom.R;
import com.thangnvhe.bookingroom.ui.MainActivity;

public class LoginActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "user_prefs";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String KEY_IS_USER_NAME = "is_user_name";
    private static final String KEY_USER_ROLE = "user_role"; // Thêm key cho vai trò

    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText etUsername = findViewById(R.id.etUsername);
        EditText etPassword = findViewById(R.id.etPassword);
        Button btnLogin = findViewById(R.id.btnLogin);

        // ViewModel
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.init(getApplicationContext());
        // Lắng nghe kết quả đăng nhập
        userViewModel.getLoginResult().observe(this, result -> {
            if (result != null) {
                if (result.equals("success")) {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
                }
            }
        });

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

            } else {
                userViewModel.loginUser(this,username, password);

            }
        });
        // Bắt sự kiện khi đăng ký
        TextView textViewRegister = findViewById(R.id.textViewRegister);
        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}