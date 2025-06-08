//package com.thangnvhe.bookingroom.ui.chat;
//
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Looper;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.thangnvhe.bookingroom.R;
//import com.thangnvhe.bookingroom.ui.adapter.ChatAdapter;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.PrintWriter;
//import java.net.Socket;
//import java.util.ArrayList;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//public class ChatActivity extends AppCompatActivity {
//    private RecyclerView recyclerView;
//    private ChatAdapter adapter;
//    private EditText messageEditText;
//    private Button sendButton;
//    private ExecutorService executorService = Executors.newSingleThreadExecutor();
//    private Handler mainHandler = new Handler(Looper.getMainLooper());
//    private Socket socket;
//    private PrintWriter out;
//    private BufferedReader in;
//    private boolean isConnected = false;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_chat);
//
//        recyclerView = findViewById(R.id.recycler_view);
//        messageEditText = findViewById(R.id.message_edit_text);
//        sendButton = findViewById(R.id.send_button);
//
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        adapter = new ChatAdapter(new ArrayList<>());
//        recyclerView.setAdapter(adapter);
//
//        connectToServer();
//
//        sendButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String message = messageEditText.getText().toString();
//                if (!message.isEmpty() && isConnected) {
//                    sendMessage(message);
//                    messageEditText.setText("");
//                } else {
//                    Toast.makeText(ChatActivity.this, "Không thể gửi", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
//
//    private void connectToServer() {
//        executorService.execute(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    socket = new Socket("YOUR_SERVER_IP", 12345);
//                    out = new PrintWriter(socket.getOutputStream(), true);
//                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                    isConnected = true;
//
//                    mainHandler.post(() -> Toast.makeText(ChatActivity.this, "Đã kết nối", Toast.LENGTH_SHORT).show());
//
//                    String message;
//                    while ((message = in.readLine()) != null) {
//                        final String finalMessage = message;
//                        mainHandler.post(() -> {
//                            adapter.addMessage("Người bán: " + finalMessage);
//                            recyclerView.scrollToPosition(adapter.getItemCount() - 1);
//                        });
//                    }
//                } catch (IOException e) {
//                    mainHandler.post(() -> Toast.makeText(ChatActivity.this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show());
//                }
//            }
//        });
//    }
//
//    private void sendMessage(String message) {
//        executorService.execute(() -> {
//            if (out != null) {
//                out.println(message);
//                mainHandler.post(() -> {
//                    adapter.addMessage("Bạn: " + message);
//                    recyclerView.scrollToPosition(adapter.getItemCount() - 1);
//                });
//            }
//        });
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        try {
//            if (in != null) in.close();
//            if (out != null) out.close();
//            if (socket != null) socket.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        executorService.shutdown();
//    }
//}