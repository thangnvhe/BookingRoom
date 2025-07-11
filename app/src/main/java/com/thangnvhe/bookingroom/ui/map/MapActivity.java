package com.thangnvhe.bookingroom.ui.map;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MapActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Tọa độ và tiêu đề của Trường Đại học FPT Hà Nội
        String latitude = "21.0124167";
        String longitude = "105.5252892";
        String label = "Trường Đại học FPT Hà Nội";

        // Tạo URI để mở trong Google Maps app
        Uri gmmIntentUri = Uri.parse("geo:" + latitude + "," + longitude + "?q=" + latitude + "," + longitude + "(" + Uri.encode(label) + ")");

        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");

        // Mở Google Maps nếu có, ngược lại dùng trình duyệt
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        } else {
            // Fallback mở link Google Maps trên trình duyệt
            Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.google.com/maps/place/21.0124167,105.5252892"));
            startActivity(browserIntent);
        }

        finish(); // Đóng activity sau khi chuyển hướng
    }
}
