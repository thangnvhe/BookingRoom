package com.thangnvhe.bookingroom.ui.cart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thangnvhe.bookingroom.R;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private final List<CartDisplayItem> cartItems;
    private final OnDeleteClickListener onDeleteClickListener;
    private final OnQuantityChangeListener onQuantityChangeListener;

    public interface OnDeleteClickListener {
        void onDeleteClick(int cartItemId, int position);
    }

    public interface OnQuantityChangeListener {
        void onQuantityChanged(int cartItemId, int newQuantity, double newTotalPrice, int position);
    }

    public CartAdapter(List<CartDisplayItem> cartItems, OnDeleteClickListener deleteListener,
                       OnQuantityChangeListener quantityChangeListener) {
        this.cartItems = cartItems;
        this.onDeleteClickListener = deleteListener;
        this.onQuantityChangeListener = quantityChangeListener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartDisplayItem item = cartItems.get(position);
        holder.tvPackageName.setText("Gói: " + item.packageName);
        holder.tvFacilityName.setText("Tiện ích: " + item.facilityName);
        holder.tvTimeSlot.setText("Khung giờ: " + item.selectedTimeSlot);
        holder.tvQuantity.setText(String.valueOf(item.quantity));
        holder.tvTotalPrice.setText(String.format("Giá: %.0f VNĐ", item.totalPrice));

        // Tải ảnh từ imageUrl
        String imageName = item.imageUrl;
        int imageResId = holder.itemView.getContext().getResources().getIdentifier(
                imageName, "drawable", holder.itemView.getContext().getPackageName());
        if (imageResId != 0) {
            holder.ivPackageImage.setImageResource(imageResId);
        } else {
            holder.ivPackageImage.setImageResource(R.drawable.placeholder);
        }

        // Xử lý nút xóa
        holder.btnDelete.setOnClickListener(v -> {
            if (onDeleteClickListener != null) {
                onDeleteClickListener.onDeleteClick(item.cartItemId, holder.getAdapterPosition());
            }
        });

        // Xử lý nút tăng số lượng
        holder.btnIncrease.setOnClickListener(v -> {
            int newQuantity = item.quantity + 1;
            updateQuantity(item, newQuantity, holder, position);
        });

        // Xử lý nút giảm số lượng
        holder.btnDecrease.setOnClickListener(v -> {
            if (item.quantity > 1) { // Không cho giảm dưới 1
                int newQuantity = item.quantity - 1;
                updateQuantity(item, newQuantity, holder, position);
            }
        });
    }

    private void updateQuantity(CartDisplayItem item, int newQuantity, CartViewHolder holder, int position) {
        // Tính giá đơn vị (totalPrice / quantity hiện tại)
        double unitPrice = item.totalPrice / item.quantity;
        double newTotalPrice = unitPrice * newQuantity;

        // Cập nhật UI
        item.quantity = newQuantity;
        item.totalPrice = newTotalPrice;
        holder.tvQuantity.setText(String.valueOf(newQuantity));
        holder.tvTotalPrice.setText(String.format("Giá: %.0f VNĐ", newTotalPrice));

        // Thông báo thay đổi số lượng
        if (onQuantityChangeListener != null) {
            onQuantityChangeListener.onQuantityChanged(item.cartItemId, newQuantity, newTotalPrice, position);
        }
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public void removeItem(int position) {
        cartItems.remove(position);
        notifyItemRemoved(position);
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView tvPackageName, tvFacilityName, tvTimeSlot, tvQuantity, tvTotalPrice;
        ImageView ivPackageImage;
        Button btnDelete, btnIncrease, btnDecrease;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPackageName = itemView.findViewById(R.id.tvPackageName);
            tvFacilityName = itemView.findViewById(R.id.tvFacilityName);
            tvTimeSlot = itemView.findViewById(R.id.tvTimeSlot);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvTotalPrice = itemView.findViewById(R.id.tvTotalPrice);
            ivPackageImage = itemView.findViewById(R.id.ivPackageImage);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnIncrease = itemView.findViewById(R.id.btnIncrease);
            btnDecrease = itemView.findViewById(R.id.btnDecrease);
        }
    }
}