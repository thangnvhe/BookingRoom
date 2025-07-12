package com.thangnvhe.bookingroom.ui.auth;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.thangnvhe.bookingroom.R;
import com.thangnvhe.bookingroom.data.AppDatabase;
import com.thangnvhe.bookingroom.data.db.dao.UserDao;
import com.thangnvhe.bookingroom.data.db.entities.User;

public class EditProfileActivity extends AppCompatActivity {

    private EditText etFullName, etEmail, etPhone, etAddress, etPassword;
    private Button btnSave;
    private UserDao userDao;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        etFullName = findViewById(R.id.et_full_name);
        etEmail = findViewById(R.id.et_email);
        etPhone = findViewById(R.id.et_phone);
        etAddress = findViewById(R.id.et_address);
        etPassword = findViewById(R.id.et_password);
        btnSave = findViewById(R.id.btn_save_profile);

        userDao = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "app_database")
                .build()
                .userDao();

        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        username = prefs.getString("is_user_name", null);

        if (username != null) {
            loadUserData(username);
        }

        btnSave.setOnClickListener(v -> {
            if (validateInputs()) {
                saveProfileChanges();
            }
        });
    }

    private void loadUserData(String username) {
        new Thread(() -> {
            User user = userDao.getUserByUsername(username);
            if (user != null) {
                runOnUiThread(() -> {
                    etFullName.setText(user.fullName);
                    etEmail.setText(user.email);
                    etPhone.setText(user.phone);
                    etAddress.setText(user.address);
                    etPassword.setText(user.password);
                });
            }
        }).start();
    }

    private boolean validateInputs() {
        String fullName = etFullName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (fullName.isEmpty()) {
            etFullName.setError("Full name is required");
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Invalid email format");
            return false;
        }

        if (!phone.matches("\\d{9,11}")) {
            etPhone.setError("Phone must be 9 to 11 digits");
            return false;
        }

        if (address.isEmpty()) {
            etAddress.setError("Address is required");
            return false;
        }

        if (password.length() < 6) {
            etPassword.setError("Password must be at least 6 characters");
            return false;
        }

        return true;
    }

    private void saveProfileChanges() {
        String fullName = etFullName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        new Thread(() -> {
            User user = userDao.getUserByUsername(username);
            if (user != null) {
                user.fullName = fullName;
                user.email = email;
                user.phone = phone;
                user.address = address;
                user.password = password;

                userDao.updateUser(user); // Sửa từ update thành updateUser

                runOnUiThread(() -> {
                    Toast.makeText(this, "Profile updated successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                });
            }
        }).start();
    }
}