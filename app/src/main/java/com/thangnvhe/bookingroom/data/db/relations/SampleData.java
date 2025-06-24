package com.thangnvhe.bookingroom.data.db.relations;

import com.thangnvhe.bookingroom.data.AppDatabase;
import com.thangnvhe.bookingroom.data.db.entities.PackageEntity;
import com.thangnvhe.bookingroom.data.db.entities.FacilityEntity;

public class SampleData {

    public static void insertSampleData(AppDatabase db) {
        String[] packageNames = {
                "Ghế đơn", "Ghế đôi", "Bàn 4 chỗ", "Bàn 6 chỗ", "Ghế VIP",
                "Bàn nhóm 12 người", "Bàn hội nghị", "Ghế Sofa", "Bàn góc", "Bàn kết nối"
        };

        String[] descriptions = {
                "Ghế ngồi thoải mái cho 1 người.",
                "Ghế đôi dành cho cặp đôi hoặc bạn bè.",
                "Bàn 4 chỗ cho làm việc nhóm nhỏ.",
                "Bàn 6 chỗ phù hợp họp nhóm vừa.",
                "Ghế cao cấp với đệm thoải mái.",
                "Bàn dài cho nhóm lớn (12 người).",
                "Bàn họp với màn hình trình chiếu.",
                "Ghế sofa thư giãn đọc sách.",
                "Bàn góc riêng tư làm việc tập trung.",
                "Bàn liên kết cho workshop hoặc nhóm mở."
        };

        String[] imageUrls = {
                "img_ghe_don",
                "img_ghe_doi",
                "img_ban_tu_cho",
                "img_ban_sau_cho",
                "img_ghe_vip",
                "img_ban_mot_hai_nguoi",
                "img_ban_hoi_ngh",
                "img_ghe_sofa",
                "img_ban_goc",
                "img_ban_ket_noi"
        };

        String[] types = {
                "seat", "seat", "table", "table", "seat",
                "table", "table", "seat", "table", "table"
        };

        int[] capacities = {1, 2, 4, 6, 1, 12, 8, 1, 2, 6};
        double[] prices = {50000, 90000, 120000, 180000, 80000, 240000, 200000, 60000, 100000, 150000};

        // Insert packages
        for (int i = 0; i < 10; i++) {
            PackageEntity pkg = new PackageEntity();
            pkg.name = packageNames[i];
            pkg.description = descriptions[i];
            pkg.imageUrl = imageUrls[i];
            pkg.type = types[i];
            pkg.capacity = capacities[i];
            pkg.price = prices[i];
            pkg.isActive = true;
            pkg.rating = 4.0f + (i % 3) * 0.3f; // Tạo rating ngẫu nhiên 4.0 – 4.6
            pkg.availableTimeSlots = "[\"08:00-10:00\", \"13:00-15:00\"]";

            long pkgId = db.packageDao().insertPackage(pkg);

            // Mỗi package gán 2-3 tiện ích
            if (i % 2 == 0) {
                db.facilityDao().insertFacility(createFacility(pkgId, "Internet tốc độ cao", "Kết nối mạnh", 10000));
                db.facilityDao().insertFacility(createFacility(pkgId, "Bảng trắng", "Viết trình bày", 5000));
            } else {
                db.facilityDao().insertFacility(createFacility(pkgId, "Mạng LAN", "Kết nối ổn định", 7000));
                db.facilityDao().insertFacility(createFacility(pkgId, "Tivi", "Trình chiếu không dây", 15000));
                db.facilityDao().insertFacility(createFacility(pkgId, "Ổ cắm đa năng", "Sạc laptop, điện thoại", 3000));
            }
        }
    }

    private static FacilityEntity createFacility(long packageId, String name, String desc, double extraPrice) {
        FacilityEntity f = new FacilityEntity();
        f.packageId = (int) packageId;
        f.name = name;
        f.description = desc;
        f.extraPrice = extraPrice;
        return f;
    }
}
