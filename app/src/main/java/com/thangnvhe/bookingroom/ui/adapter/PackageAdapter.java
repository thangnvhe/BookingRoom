package com.thangnvhe.bookingroom.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thangnvhe.bookingroom.R;
import com.thangnvhe.bookingroom.data.db.relations.PackageWithFacilities;

import java.util.ArrayList;
import java.util.List;

public class PackageAdapter extends RecyclerView.Adapter<PackageAdapter.PackageViewHolder> {

    private List<PackageWithFacilities> packages = new ArrayList<>();
    private final Context context;

    // Interface xử lý click
    public interface OnItemClickListener {
        void onItemClick(PackageWithFacilities item);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public PackageAdapter(Context context) {
        this.context = context;
    }

    public void setPackages(List<PackageWithFacilities> packages) {
        this.packages = packages;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PackageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_package, parent, false);
        return new PackageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PackageViewHolder holder, int position) {
        PackageWithFacilities item = packages.get(position);
        holder.name.setText(item.packageEntity.name);
        holder.capacity.setText("Chỗ: " + item.packageEntity.capacity);
        holder.price.setText(item.packageEntity.price + " VNĐ / Đêm");

        // Load ảnh từ drawable theo imageUrl
        int imageResId = context.getResources().getIdentifier(
                item.packageEntity.imageUrl,
                "drawable",
                context.getPackageName()
        );

        if (imageResId != 0) {
            holder.image.setImageResource(imageResId);
        } else {
            holder.image.setImageResource(R.drawable.placeholder);
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(item);
            }
        });
    }


    @Override
    public int getItemCount() {
        return packages.size();
    }

    static class PackageViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name, capacity, price;

        public PackageViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.packageImage);
            name = itemView.findViewById(R.id.packageTitle);
            capacity = itemView.findViewById(R.id.packageCapacity);
            price = itemView.findViewById(R.id.packagePrice);
        }
    }
}
