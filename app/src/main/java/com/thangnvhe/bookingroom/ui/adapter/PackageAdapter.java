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
        holder.capacity.setText("Chỗ: " + String.valueOf(item.packageEntity.capacity));
        holder.price.setText(String.valueOf(item.packageEntity.price) + " VNĐ / Đêm");

        // Tạm thời ảnh là cố định (bạn có thể chỉnh theo dữ liệu sau)
        holder.image.setImageResource(R.drawable.ic_nu);
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
