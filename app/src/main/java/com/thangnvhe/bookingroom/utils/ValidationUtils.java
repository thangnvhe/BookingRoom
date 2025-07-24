package com.thangnvhe.bookingroom.utils;

import android.util.Patterns;
import java.util.regex.Pattern;

public class ValidationUtils {

    // Validate tên đầy đủ
    public static ValidationResult validateFullName(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) {
            return new ValidationResult(false, "Họ tên không được để trống");
        }
        
        if (fullName.trim().length() < 2) {
            return new ValidationResult(false, "Họ tên phải có ít nhất 2 ký tự");
        }
        
        if (fullName.trim().length() > 50) {
            return new ValidationResult(false, "Họ tên không được vượt quá 50 ký tự");
        }
        
        // Kiểm tra chỉ chứa chữ cái và khoảng trắng
        if (!Pattern.matches("^[a-zA-ZàáạảãâầấậẩẫăằắặẳẵèéẹẻẽêềếệểễìíịỉĩòóọỏõôồốộổỗơờớợởỡùúụủũưừứựửữỳýỵỷỹđĐ\\s]+$", fullName.trim())) {
            return new ValidationResult(false, "Họ tên chỉ được chứa chữ cái và khoảng trắng");
        }
        
        return new ValidationResult(true, "");
    }

    // Validate email
    public static ValidationResult validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return new ValidationResult(false, "Email không được để trống");
        }
        
        if (!Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()) {
            return new ValidationResult(false, "Email không hợp lệ");
        }
        
        return new ValidationResult(true, "");
    }

    // Validate username
    public static ValidationResult validateUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            return new ValidationResult(false, "Tên đăng nhập không được để trống");
        }
        
        if (username.trim().length() < 3) {
            return new ValidationResult(false, "Tên đăng nhập phải có ít nhất 3 ký tự");
        }
        
        if (username.trim().length() > 20) {
            return new ValidationResult(false, "Tên đăng nhập không được vượt quá 20 ký tự");
        }
        
        // Chỉ cho phép chữ cái, số và dấu gạch dưới
        if (!Pattern.matches("^[a-zA-Z0-9_]+$", username.trim())) {
            return new ValidationResult(false, "Tên đăng nhập chỉ được chứa chữ cái, số và dấu gạch dưới");
        }
        
        return new ValidationResult(true, "");
    }

    // Validate mật khẩu
    public static ValidationResult validatePassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            return new ValidationResult(false, "Mật khẩu không được để trống");
        }
        
        if (password.length() < 6) {
            return new ValidationResult(false, "Mật khẩu phải có ít nhất 6 ký tự");
        }
        
        if (password.length() > 50) {
            return new ValidationResult(false, "Mật khẩu không được vượt quá 50 ký tự");
        }
        
        // Kiểm tra có ít nhất 1 chữ cái và 1 số
        boolean hasLetter = Pattern.compile("[a-zA-Z]").matcher(password).find();
        boolean hasDigit = Pattern.compile("[0-9]").matcher(password).find();
        
        if (!hasLetter || !hasDigit) {
            return new ValidationResult(false, "Mật khẩu phải chứa ít nhất 1 chữ cái và 1 số");
        }
        
        return new ValidationResult(true, "");
    }

    // Validate số điện thoại
    public static ValidationResult validatePhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return new ValidationResult(false, "Số điện thoại không được để trống");
        }
        
        // Loại bỏ khoảng trắng và dấu gạch ngang
        String cleanPhone = phone.replaceAll("[\\s-]", "");
        
        // Kiểm tra định dạng số điện thoại Việt Nam
        if (!Pattern.matches("^(\\+84|84|0)(3|5|7|8|9)[0-9]{8}$", cleanPhone)) {
            return new ValidationResult(false, "Số điện thoại không hợp lệ. Vui lòng nhập số điện thoại Việt Nam");
        }
        
        return new ValidationResult(true, "");
    }

    // Validate ngày sinh
    public static ValidationResult validateDateOfBirth(String dob) {
        if (dob == null || dob.trim().isEmpty()) {
            return new ValidationResult(false, "Ngày sinh không được để trống");
        }
        
        // Kiểm tra định dạng dd/MM/yyyy
        if (!Pattern.matches("^\\d{2}/\\d{2}/\\d{4}$", dob.trim())) {
            return new ValidationResult(false, "Ngày sinh phải có định dạng dd/MM/yyyy");
        }
        
        try {
            String[] parts = dob.trim().split("/");
            int day = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int year = Integer.parseInt(parts[2]);
            
            // Kiểm tra tháng hợp lệ
            if (month < 1 || month > 12) {
                return new ValidationResult(false, "Tháng không hợp lệ");
            }
            
            // Kiểm tra ngày hợp lệ
            if (day < 1 || day > 31) {
                return new ValidationResult(false, "Ngày không hợp lệ");
            }
            
            // Kiểm tra năm hợp lệ (từ 1900 đến năm hiện tại - 13)
            int currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
            if (year < 1900 || year > (currentYear - 13)) {
                return new ValidationResult(false, "Năm sinh phải từ 1900 đến " + (currentYear - 13) + " (ít nhất 13 tuổi)");
            }
            
            // Kiểm tra ngày tháng có hợp lệ không (tháng 2, 30/31)
            if ((month == 4 || month == 6 || month == 9 || month == 11) && day > 30) {
                return new ValidationResult(false, "Ngày không hợp lệ cho tháng " + month);
            }
            
            if (month == 2) {
                boolean isLeapYear = (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
                if ((isLeapYear && day > 29) || (!isLeapYear && day > 28)) {
                    return new ValidationResult(false, "Ngày không hợp lệ cho tháng 2");
                }
            }
            
        } catch (NumberFormatException e) {
            return new ValidationResult(false, "Ngày sinh không hợp lệ");
        }
        
        return new ValidationResult(true, "");
    }

    // Validate địa chỉ
    public static ValidationResult validateAddress(String address) {
        if (address == null || address.trim().isEmpty()) {
            return new ValidationResult(false, "Địa chỉ không được để trống");
        }
        
        if (address.trim().length() < 5) {
            return new ValidationResult(false, "Địa chỉ phải có ít nhất 5 ký tự");
        }
        
        if (address.trim().length() > 200) {
            return new ValidationResult(false, "Địa chỉ không được vượt quá 200 ký tự");
        }
        
        return new ValidationResult(true, "");
    }

    // Class để lưu kết quả validation
    public static class ValidationResult {
        private final boolean isValid;
        private final String errorMessage;

        public ValidationResult(boolean isValid, String errorMessage) {
            this.isValid = isValid;
            this.errorMessage = errorMessage;
        }

        public boolean isValid() {
            return isValid;
        }

        public String getErrorMessage() {
            return errorMessage;
        }
    }
}
