package com.thangnvhe.bookingroom.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.thangnvhe.bookingroom.data.db.dao.BookingDao;
import com.thangnvhe.bookingroom.data.db.dao.CartDao;
import com.thangnvhe.bookingroom.data.db.dao.FacilityDao;
import com.thangnvhe.bookingroom.data.db.dao.MessageDao;
import com.thangnvhe.bookingroom.data.db.dao.PackageDao;
import com.thangnvhe.bookingroom.data.db.dao.UserDao;
import com.thangnvhe.bookingroom.data.db.entities.Booking;
import com.thangnvhe.bookingroom.data.db.entities.CartItem;
import com.thangnvhe.bookingroom.data.db.entities.FacilityEntity;
import com.thangnvhe.bookingroom.data.db.entities.MessageEntity;
import com.thangnvhe.bookingroom.data.db.entities.PackageEntity;
import com.thangnvhe.bookingroom.data.db.entities.User;
import com.thangnvhe.bookingroom.data.db.relations.SampleData;

import java.util.concurrent.Executors;

@Database(
        entities = {
                User.class,
                PackageEntity.class,
                FacilityEntity.class,
                MessageEntity.class,
                CartItem.class,
                Booking.class
        },
        version = 10, // Đảm bảo đây là version mới nhất bạn dùng
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;

    // DAO declarations
    public abstract UserDao userDao();
    public abstract PackageDao packageDao();
    public abstract FacilityDao facilityDao();
    public abstract MessageDao messageDao();
    public abstract CartDao cartDao();
    public abstract BookingDao bookingDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    "app_database"
                            )
                            .fallbackToDestructiveMigration()
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    Executors.newSingleThreadExecutor().execute(() -> {
                                        SampleData.insertSampleData(getInstance(context));
                                    });
                                }
                            })
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
