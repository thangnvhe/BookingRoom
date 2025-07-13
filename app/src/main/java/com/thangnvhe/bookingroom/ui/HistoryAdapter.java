
package com.thangnvhe.bookingroom.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thangnvhe.bookingroom.R;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private final List<BookingDisplayItem> historyItems;

    public HistoryAdapter(List<BookingDisplayItem> historyItems) {
        this.historyItems = historyItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BookingDisplayItem item = historyItems.get(position);
        holder.tvOrderId.setText(item.orderId);
        holder.tvAmenities.setText("Tiện ích: " + item.amenities);
        holder.tvOrderDate.setText(item.orderDate);
        holder.tvOrderTotal.setText(item.totalPrice);
        holder.tvStatus.setText("Trạng thái: " + item.status);
    }

    @Override
    public int getItemCount() {
        return historyItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderId, tvPackage, tvAmenities, tvOrderDate, tvOrderTotal, tvStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderId = itemView.findViewById(R.id.tvOrderId);
            tvPackage = itemView.findViewById(R.id.tvPackage);
            tvAmenities = itemView.findViewById(R.id.tvAmenities);
            tvOrderDate = itemView.findViewById(R.id.tvOrderDate);
            tvOrderTotal = itemView.findViewById(R.id.tvOrderTotal);
            tvStatus = itemView.findViewById(R.id.tvStatus);
        }
    }
} 