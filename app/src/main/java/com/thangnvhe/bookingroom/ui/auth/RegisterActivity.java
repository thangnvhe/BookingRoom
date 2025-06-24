package com.thangnvhe.bookingroom.ui.auth;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.view.View;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.thangnvhe.bookingroom.R;
import com.thangnvhe.bookingroom.data.db.entities.User;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {

    private EditText etFullName, etEmail, etUsername, etPassword, etPhone, etDob, etAddress;
    private RadioGroup rgGender;
    private Button btnRegister;
    private UserViewModel userViewModel;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        // View binding
        etFullName = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.etEmail);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etPhone = findViewById(R.id.etPhone);
        etDob = findViewById(R.id.etDob);
        etAddress = findViewById(R.id.etAddress);
        rgGender = findViewById(R.id.rgGender);
        btnRegister = findViewById(R.id.btnRegister);

        // Date picker cho ngày sinh
        etDob.setOnClickListener(v -> showDatePicker());

        // Init ViewModel
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.init(getApplicationContext());

        // Observe LiveData kết quả đăng ký
        userViewModel.getRegisterResult().observe(this, result -> {
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
            if (result.contains("thành công")) {
                // Nếu đăng ký thành công → chuyển sang LoginActivity
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Đóng RegisterActivity
            }
        });

        btnRegister.setOnClickListener(v -> {
            String fullName = etFullName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            String gender = getSelectedGender();
            String dob = etDob.getText().toString().trim();
            String address = etAddress.getText().toString().trim();

            if (fullName.isEmpty()) {
                etFullName.setError("Vui lòng nhập họ tên");
                etFullName.requestFocus();
                return;
            }
            if (email.isEmpty()) {
                etEmail.setError("Vui lòng nhập email");
                etEmail.requestFocus();
                return;
            }
            if (username.isEmpty()) {
                etUsername.setError("Vui lòng nhập tên đăng nhập");
                etUsername.requestFocus();
                return;
            }
            if (password.isEmpty()) {
                etPassword.setError("Vui lòng nhập mật khẩu");
                etPassword.requestFocus();
                return;
            }
            if (phone.isEmpty()) {
                etPhone.setError("Vui lòng nhập số điện thoại");
                etPhone.requestFocus();
                return;
            }
            if (gender.isEmpty()) {
                Toast.makeText(this, "Vui lòng chọn giới tính", Toast.LENGTH_SHORT).show();
                return;
            }
            if (dob.isEmpty()) {
                etDob.setError("Vui lòng chọn ngày sinh");
                etDob.requestFocus();
                return;
            }
            if (address.isEmpty()) {
                etAddress.setError("Vui lòng nhập địa chỉ");
                etAddress.requestFocus();
                return;
            }

            // Tạo User và gửi ViewModel
            User user = new User(fullName, email, username, password, phone, gender, dob, address);
            userViewModel.registerUser(user);
        });


        Button btnBackToLogin = findViewById(R.id.btnBtoLogin);
        btnBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // kết thúc RegisterActivity nếu không muốn quay lại bằng nút back
            }
        });

    }

    private String getSelectedGender() {
        int selectedId = rgGender.getCheckedRadioButtonId();
        if (selectedId != -1) {
            RadioButton radioButton = findViewById(selectedId);
            return radioButton.getText().toString();
        }
        return "";
    }

    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                RegisterActivity.this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String selectedDate = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear);
                    etDob.setText(selectedDate);
                },
                year, month, day
        );

        datePickerDialog.show();
    }
}
