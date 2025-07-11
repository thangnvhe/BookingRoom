package com.thangnvhe.bookingroom.data.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.thangnvhe.bookingroom.data.db.entities.CartItem;

import java.util.List;

@Dao
public interface CartDao {
    @Insert
    void insert(CartItem cartItem);

    @Query("SELECT * FROM cart_items WHERE userId = :userId")
    List<CartItem> getCartItemsByUserId(int userId);

    @Query("DELETE FROM cart_items WHERE id = :id")
    void deleteCartItemById(int id);

    @Query("DELETE FROM cart_items WHERE userId = :userId")
    void clearCartByUserId(int userId);

    @Query("UPDATE cart_items SET quantity = :quantity, totalPrice = :totalPrice WHERE id = :id")
    void updateQuantityAndPrice(int id, int quantity, double totalPrice);

    // Thêm phương thức mới để kiểm tra phần tử tồn tại
    @Query("SELECT * FROM cart_items WHERE userId = :userId AND packageId = :packageId AND selectedTimeSlot = :selectedTimeSlot AND (facilityId = :facilityId OR (facilityId IS NULL AND :facilityId IS NULL)) LIMIT 1")
    CartItem findCartItem(int userId, int packageId, String selectedTimeSlot, Integer facilityId);
}