package com.thangnvhe.bookingroom.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.thangnvhe.bookingroom.data.db.entities.Package;
import com.thangnvhe.bookingroom.R;

import java.util.List;

public class PackageAdapter extends RecyclerView.Adapter<PackageAdapter.PackageViewHolder> {
    private List<Package> packageList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Package pkg);
    }

    public PackageAdapter(List<Package> packageList, OnItemClickListener listener) {
        this.packageList = packageList;
        this.listener = listener;
    }

    public static class PackageViewHolder extends RecyclerView.ViewHolder {
        public TextView typeTextView, capacityTextView;

        public PackageViewHolder(View itemView) {
            super(itemView);
            typeTextView = itemView.findViewById(R.id.type_text_view);
            capacityTextView = itemView.findViewById(R.id.capacity_text_view);
        }
    }

    @Override
    public PackageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_package, parent, false);
        return new PackageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PackageViewHolder holder, int position) {
        Package pkg = packageList.get(position);
        holder.typeTextView.setText(pkg.type.equals("seat") ? "Ghế" : "Bàn");
        holder.capacityTextView.setText("Sức chứa: " + pkg.capacity);
        holder.itemView.setOnClickListener(v -> listener.onItemClick(pkg));
    }

    @Override
    public int getItemCount() {
        return packageList.size();
    }
}