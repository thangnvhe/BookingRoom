# Validation cho phần Đăng Ký - BookingRoom App

## Tổng quan
Đã bổ sung hệ thống validation toàn diện cho form đăng ký người dùng nhằm đảm bảo tính chính xác và bảo mật của dữ liệu.

## Các tính năng Validation đã thêm

### 1. ValidationUtils Class
- **Vị trí**: `app/src/main/java/com/thangnvhe/bookingroom/utils/ValidationUtils.java`
- **Chức năng**: Chứa tất cả các phương thức validation cho từng trường dữ liệu

### 2. Validation cho từng trường

#### **Họ tên (Full Name)**
- ✅ Không được để trống
- ✅ Tối thiểu 2 ký tự
- ✅ Tối đa 50 ký tự
- ✅ Chỉ chứa chữ cái tiếng Việt và khoảng trắng

#### **Email**
- ✅ Không được để trống
- ✅ Định dạng email hợp lệ (sử dụng Android Patterns)

#### **Tên đăng nhập (Username)**
- ✅ Không được để trống
- ✅ Tối thiểu 3 ký tự
- ✅ Tối đa 20 ký tự
- ✅ Chỉ chứa chữ cái, số và dấu gạch dưới

#### **Mật khẩu (Password)**
- ✅ Không được để trống
- ✅ Tối thiểu 6 ký tự
- ✅ Tối đa 50 ký tự
- ✅ Phải chứa ít nhất 1 chữ cái và 1 số

#### **Số điện thoại (Phone)**
- ✅ Không được để trống
- ✅ Định dạng số điện thoại Việt Nam
- ✅ Hỗ trợ các đầu số: 03, 05, 07, 08, 09
- ✅ Có thể bắt đầu với +84, 84 hoặc 0

#### **Ngày sinh (Date of Birth)**
- ✅ Không được để trống
- ✅ Định dạng dd/MM/yyyy
- ✅ Kiểm tra tính hợp lệ của ngày tháng
- ✅ Kiểm tra năm nhuận cho tháng 2
- ✅ Người dùng phải ít nhất 13 tuổi

#### **Địa chỉ (Address)**
- ✅ Không được để trống
- ✅ Tối thiểu 5 ký tự
- ✅ Tối đa 200 ký tự

### 3. Hai phiên bản RegisterActivity

#### **RegisterActivity (Phiên bản cập nhật)**
- **Vị trí**: `app/src/main/java/com/thangnvhe/bookingroom/ui/auth/RegisterActivity.java`
- **Layout**: `activity_register.xml`
- **Đặc điểm**: 
  - Sử dụng EditText thông thường
  - Validation real-time khi người dùng nhập
  - Hiển thị lỗi trực tiếp trên EditText

#### **RegisterActivityImproved (Phiên bản cải tiến)**
- **Vị trí**: `app/src/main/java/com/thangnvhe/bookingroom/ui/auth/RegisterActivityImproved.java`
- **Layout**: `activity_register_improved.xml`
- **Đặc điểm**:
  - Sử dụng Material Design TextInputLayout
  - Hiển thị lỗi đẹp hơn với animation
  - Có nút show/hide password
  - Giao diện chuyên nghiệp hơn
  - Có ScrollView để hỗ trợ các màn hình nhỏ

### 4. Validation Real-time
- ✅ Kiểm tra ngay khi người dùng nhập (TextWatcher)
- ✅ Hiển thị lỗi ngay lập tức
- ✅ Xóa lỗi khi người dùng sửa đúng
- ✅ Chỉ hiển thị lỗi khi trường không trống (UX tốt hơn)

### 5. Validation khi Submit
- ✅ Kiểm tra tất cả trường trước khi đăng ký
- ✅ Hiển thị toast thông báo nếu có lỗi
- ✅ Chỉ cho phép đăng ký khi tất cả dữ liệu hợp lệ

## Cách sử dụng

### Để sử dụng phiên bản cải tiến:

1. **Cập nhật AndroidManifest.xml** để sử dụng RegisterActivityImproved:
```xml
<activity
    android:name=".ui.auth.RegisterActivityImproved"
    android:exported="false" />
```

2. **Thay đổi Intent** trong các activity khác để chuyển đến RegisterActivityImproved:
```java
startActivity(new Intent(this, RegisterActivityImproved.class));
```

### Customize Validation Rules:

Bạn có thể dễ dàng chỉnh sửa các rules validation trong `ValidationUtils.java`:

```java
// Ví dụ: Thay đổi độ dài tối thiểu của password
if (password.length() < 8) { // Thay đổi từ 6 thành 8
    return new ValidationResult(false, "Mật khẩu phải có ít nhất 8 ký tự");
}
```

## Lợi ích

1. **Bảo mật**: Đảm bảo dữ liệu đầu vào hợp lệ
2. **Trải nghiệm người dùng**: Phản hồi ngay lập tức khi nhập sai
3. **Maintainability**: Code dễ bảo trì và mở rong
4. **Consistency**: Validation rule nhất quán trong toàn bộ app
5. **Localizable**: Dễ dàng đa ngôn ngữ cho các thông báo lỗi

## Ghi chú
- ValidationUtils có thể được tái sử dụng cho các form khác trong app
- Layout improved hỗ trợ theme dark/light mode tự động
- Tất cả validation đều support tiếng Việt
