package com.thangnvhe.bookingroom.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.thangnvhe.bookingroom.R;
import java.util.ArrayList;
import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserViewHolder> {

    private final List<String> userIds = new ArrayList<>();
    private final OnUserClickListener listener;

    public interface OnUserClickListener {
        void onUserClick(String userId);
    }

    public UserListAdapter(OnUserClickListener listener) {
        this.listener = listener;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds.clear();
        this.userIds.addAll(userIds);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        String userId = userIds.get(position);
        holder.userIdText.setText(userId);
        holder.itemView.setOnClickListener(v -> listener.onUserClick(userId));
    }

    @Override
    public int getItemCount() {
        return userIds.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView userIdText;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            userIdText = itemView.findViewById(R.id.userIdText);
        }
    }
}