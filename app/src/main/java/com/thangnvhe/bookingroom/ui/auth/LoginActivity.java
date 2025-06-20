package com.thangnvhe.bookingroom.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.thangnvhe.bookingroom.R;
import com.thangnvhe.bookingroom.data.repositories.UserRepository;
import com.thangnvhe.bookingroom.ui.packages.PackageListActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private TextView registerTextView;
    private AuthViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.email_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        loginButton = findViewById(R.id.login_button);
        registerTextView = findViewById(R.id.register_text_view);
        viewModel = new AuthViewModel(this);

        // Kiểm tra đã đăng nhập
        if (viewModel.isLoggedIn()) {
            startActivity(new Intent(this, PackageListActivity.class));
            finish();
            return;
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                viewModel.login(email, password, new UserRepository.OnResultListener() {
                    @Override
                    public void onSuccess() {
                        // Không dùng
                    }

                    @Override
                    public void onSuccess(int userId) {
                        runOnUiThread(() -> {
                            Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, PackageListActivity.class));
                            finish();
                        });
                    }

                    @Override
                    public void onError(String message) {
                        runOnUiThread(() -> {
                            Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                        });
                    }
                });
            }
        });

        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });
    }
}
