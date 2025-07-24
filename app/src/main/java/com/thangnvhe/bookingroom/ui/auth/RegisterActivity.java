package com.thangnvhe.bookingroom.ui.auth;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import com.thangnvhe.bookingroom.R;
import com.thangnvhe.bookingroom.data.db.entities.User;
import com.thangnvhe.bookingroom.utils.ValidationUtils;
import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {

    private UserViewModel userViewModel;
    private EditText etFullName, etEmail, etUsername, etPassword, etPhone, etDob, etAddress;
    private RadioGroup rgGender;
    private RadioButton rbMale, rbFemale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ánh xạ view
        etFullName = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.etEmail);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etPhone = findViewById(R.id.etPhone);
        etDob = findViewById(R.id.etDob);
        etAddress = findViewById(R.id.etAddress);
        rgGender = findViewById(R.id.rgGender);
        rbMale = findViewById(R.id.rbMale);
        rbFemale = findViewById(R.id.rbFemale);
        Button btnRegister = findViewById(R.id.btnRegister);
        Button btnBtoLogin = findViewById(R.id.btnBtoLogin);

        // ViewModel
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.init(getApplicationContext());

        // Thiết lập validation real-time
        setupRealTimeValidation();

        // DatePicker cho ngày sinh
        etDob.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    RegisterActivity.this,
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        String dob = String.format("%02d/%02d/%d", selectedDay, selectedMonth + 1, selectedYear);
                        etDob.setText(dob);
                    },
                    year, month, day);
            datePickerDialog.show();
        });

        // Bắt sự kiện đăng ký
        btnRegister.setOnClickListener(v -> {
            if (validateAllFields()) {
                registerUser();
            }
        });

        // Bắt sự kiện quay lại đăng nhập
        btnBtoLogin.setOnClickListener(v -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        });
    }

    // Thiết lập validation real-time
    private void setupRealTimeValidation() {
        // Full name validation
        etFullName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                ValidationUtils.ValidationResult result = ValidationUtils.validateFullName(s.toString());
                if (!result.isValid()) {
                    etFullName.setError(result.getErrorMessage());
                } else {
                    etFullName.setError(null);
                }
            }
        });

        // Email validation
        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                ValidationUtils.ValidationResult result = ValidationUtils.validateEmail(s.toString());
                if (!result.isValid() && !s.toString().trim().isEmpty()) {
                    etEmail.setError(result.getErrorMessage());
                } else {
                    etEmail.setError(null);
                }
            }
        });

        // Username validation
        etUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                ValidationUtils.ValidationResult result = ValidationUtils.validateUsername(s.toString());
                if (!result.isValid() && !s.toString().trim().isEmpty()) {
                    etUsername.setError(result.getErrorMessage());
                } else {
                    etUsername.setError(null);
                }
            }
        });

        // Password validation
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                ValidationUtils.ValidationResult result = ValidationUtils.validatePassword(s.toString());
                if (!result.isValid() && !s.toString().trim().isEmpty()) {
                    etPassword.setError(result.getErrorMessage());
                } else {
                    etPassword.setError(null);
                }
            }
        });

        // Phone validation
        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                ValidationUtils.ValidationResult result = ValidationUtils.validatePhone(s.toString());
                if (!result.isValid() && !s.toString().trim().isEmpty()) {
                    etPhone.setError(result.getErrorMessage());
                } else {
                    etPhone.setError(null);
                }
            }
        });

        // Address validation
        etAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                ValidationUtils.ValidationResult result = ValidationUtils.validateAddress(s.toString());
                if (!result.isValid() && !s.toString().trim().isEmpty()) {
                    etAddress.setError(result.getErrorMessage());
                } else {
                    etAddress.setError(null);
                }
            }
        });
    }

    // Phương thức validate tất cả các trường
    private boolean validateAllFields() {
        // Reset error trước khi validate
        clearErrors();
        
        boolean isValid = true;
        
        // Validate họ tên
        ValidationUtils.ValidationResult fullNameResult = ValidationUtils.validateFullName(
                etFullName.getText().toString());
        if (!fullNameResult.isValid()) {
            etFullName.setError(fullNameResult.getErrorMessage());
            isValid = false;
        }
        
        // Validate email
        ValidationUtils.ValidationResult emailResult = ValidationUtils.validateEmail(
                etEmail.getText().toString());
        if (!emailResult.isValid()) {
            etEmail.setError(emailResult.getErrorMessage());
            isValid = false;
        }
        
        // Validate username
        ValidationUtils.ValidationResult usernameResult = ValidationUtils.validateUsername(
                etUsername.getText().toString());
        if (!usernameResult.isValid()) {
            etUsername.setError(usernameResult.getErrorMessage());
            isValid = false;
        }
        
        // Validate password
        ValidationUtils.ValidationResult passwordResult = ValidationUtils.validatePassword(
                etPassword.getText().toString());
        if (!passwordResult.isValid()) {
            etPassword.setError(passwordResult.getErrorMessage());
            isValid = false;
        }
        
        // Validate phone
        ValidationUtils.ValidationResult phoneResult = ValidationUtils.validatePhone(
                etPhone.getText().toString());
        if (!phoneResult.isValid()) {
            etPhone.setError(phoneResult.getErrorMessage());
            isValid = false;
        }
        
        // Validate date of birth
        ValidationUtils.ValidationResult dobResult = ValidationUtils.validateDateOfBirth(
                etDob.getText().toString());
        if (!dobResult.isValid()) {
            etDob.setError(dobResult.getErrorMessage());
            isValid = false;
        }
        
        // Validate address
        ValidationUtils.ValidationResult addressResult = ValidationUtils.validateAddress(
                etAddress.getText().toString());
        if (!addressResult.isValid()) {
            etAddress.setError(addressResult.getErrorMessage());
            isValid = false;
        }
        
        if (!isValid) {
            Toast.makeText(this, "Vui lòng kiểm tra lại thông tin đã nhập", Toast.LENGTH_SHORT).show();
        }
        
        return isValid;
    }
    
    // Phương thức xóa lỗi
    private void clearErrors() {
        etFullName.setError(null);
        etEmail.setError(null);
        etUsername.setError(null);
        etPassword.setError(null);
        etPhone.setError(null);
        etDob.setError(null);
        etAddress.setError(null);
    }
    
    // Phương thức đăng ký người dùng
    private void registerUser() {
        String fullName = etFullName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String dob = etDob.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String gender = rbMale.isChecked() ? "Nam" : "Nữ";
        String role = "user"; // Mặc định vai trò là user

        User user = new User(fullName, email, username, password, phone, gender, dob, address, role);
        userViewModel.registerUser(user);
        Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        finish();
    }
}