package com.thangnvhe.bookingroom.data.repositories;

import android.content.Context;

import com.thangnvhe.bookingroom.data.AppDatabase;
import com.thangnvhe.bookingroom.data.db.dao.CartDao;
import com.thangnvhe.bookingroom.data.db.entities.CartItem;

import java.util.List;
import java.util.concurrent.Executors;

public class CartRepository {
    private final CartDao cartDao;

    public CartRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        cartDao = db.cartDao();
    }

    public void insert(CartItem cartItem) {
        Executors.newSingleThreadExecutor().execute(() -> cartDao.insert(cartItem));
    }

    public List<CartItem> getCartItemsByUserId(int userId) {
        return cartDao.getCartItemsByUserId(userId);
    }

    public void deleteCartItemById(int id) {
        Executors.newSingleThreadExecutor().execute(() -> cartDao.deleteCartItemById(id));
    }

    public void clearCartByUserId(int userId) {
        Executors.newSingleThreadExecutor().execute(() -> cartDao.clearCartByUserId(userId));
    }

    public void updateQuantityAndPrice(int id, int quantity, double totalPrice) {
        Executors.newSingleThreadExecutor().execute(() -> cartDao.updateQuantityAndPrice(id, quantity, totalPrice));
    }

    // Thêm phương thức mới
    public CartItem findCartItem(int userId, int packageId, String selectedTimeSlot, Integer facilityId) {
        return cartDao.findCartItem(userId, packageId, selectedTimeSlot, facilityId);
    }
}