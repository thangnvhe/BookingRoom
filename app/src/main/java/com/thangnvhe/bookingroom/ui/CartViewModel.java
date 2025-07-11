package com.thangnvhe.bookingroom.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.thangnvhe.bookingroom.data.db.entities.CartItem;
import com.thangnvhe.bookingroom.data.repositories.CartRepository;
import com.thangnvhe.bookingroom.data.repositories.UserRepository;

import java.util.List;

public class CartViewModel extends AndroidViewModel {
    private final CartRepository repository;

    public CartViewModel(@NonNull Application application) {
        super(application);
        repository = new CartRepository(application);
    }

    public void insert(CartItem cartItem) {
        // Kiểm tra xem phần tử đã tồn tại chưa
        CartItem existingItem = repository.findCartItem(
                cartItem.userId,
                cartItem.packageId,
                cartItem.selectedTimeSlot,
                cartItem.facilityId
        );

        if (existingItem != null) {
            // Nếu đã tồn tại, tăng quantity và cập nhật totalPrice
            int newQuantity = existingItem.quantity + 1;
            double newTotalPrice = cartItem.totalPrice / cartItem.quantity * newQuantity; // Tính lại totalPrice dựa trên giá đơn vị
            repository.updateQuantityAndPrice(existingItem.id, newQuantity, newTotalPrice);
        } else {
            // Nếu chưa tồn tại, thêm mới
            repository.insert(cartItem);
        }
    }

    public List<CartItem> getCartItemsByUserId(int userId) {
        return repository.getCartItemsByUserId(userId);
    }

    public int getUserIdByUsername(String username) {
        UserRepository userRepo = new UserRepository(getApplication());
        return userRepo.getUserIdByUsername(username);
    }

    public void clearCartByUserId(int userId) {
        repository.clearCartByUserId(userId);
    }

    public void updateQuantityAndPrice(int id, int quantity, double totalPrice) {
        repository.updateQuantityAndPrice(id, quantity, totalPrice);
    }
}