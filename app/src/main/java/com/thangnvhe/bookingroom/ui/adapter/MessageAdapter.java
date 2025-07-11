package com.thangnvhe.bookingroom.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thangnvhe.bookingroom.R;
import com.thangnvhe.bookingroom.data.db.entities.MessageEntity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private final List<MessageEntity> messages = new ArrayList<>();
    private final Context context;

    public MessageAdapter(Context context) {
        this.context = context;
    }

    public void setMessages(List<MessageEntity> messages) {
        this.messages.clear();
        this.messages.addAll(messages);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == 1) {
            view = LayoutInflater.from(context).inflate(R.layout.item_message_user, parent, false);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.item_message_seller, parent, false);
        }
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        MessageEntity message = messages.get(position);
        holder.content.setText(message.content);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        holder.timestamp.setText(sdf.format(new Date(message.timestamp)));
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public int getItemViewType(int position) {
        return messages.get(position).sender.equals("user") ? 1 : 0;
    }

    static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView content, timestamp;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.messageContent);
            timestamp = itemView.findViewById(R.id.messageTimestamp);
        }
    }
}