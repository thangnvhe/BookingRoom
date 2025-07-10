package com.thangnvhe.bookingroom.ui.chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.thangnvhe.bookingroom.R;
import com.thangnvhe.bookingroom.ui.adapter.UserListAdapter;

public class SellerChatListActivity extends AppCompatActivity {

    private ChatViewModel viewModel;
    private UserListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_seller_chat_list);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_left);
        }

        RecyclerView recyclerView = findViewById(R.id.recyclerViewUsers);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserListAdapter(userId -> {
            Intent intent = new Intent(SellerChatListActivity.this, SellerChatActivity.class);
            intent.putExtra("userId", userId);
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(ChatViewModel.class);
        viewModel.getAllUserIds().observe(this, userIds -> {
            adapter.setUserIds(userIds);
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}