package com.thangnvhe.bookingroom.ui.auth;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
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
            String fullName = etFullName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            String dob = etDob.getText().toString().trim();
            String address = etAddress.getText().toString().trim();
            String gender = rbMale.isChecked() ? "Nam" : "Nữ";
            String role = "user"; // Mặc định vai trò là user

            if (fullName.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty() ||
                    phone.isEmpty() || dob.isEmpty() || address.isEmpty()) {
                Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            User user = new User(fullName, email, username, password, phone, gender, dob, address, role);
            userViewModel.registerUser(user);
            Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        });

        // Bắt sự kiện quay lại đăng nhập
        btnBtoLogin.setOnClickListener(v -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        });
    }
}