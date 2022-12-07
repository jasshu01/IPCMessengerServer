package com.example.ipcmessengerserver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    static final int FROM_CLIENT_TO_SERVER = 1;
    static final int FROM_SERVER_TO_CLIENT = 2;

//    Messenger ServerMessenger = new Messenger(new ServerHandler());
//
//    class ServerHandler extends Handler {
//        @Override
//        public void handleMessage(@NonNull Message msg) {
//            super.handleMessage(msg);
//            Log.d("MymessengerClient", "Client : handling Message");
//
//            switch (msg.what) {
//                case FROM_CLIENT_TO_SERVER:
//
//                    TextView textView = findViewById(R.id.textView);
//                    String str = msg.getData().getString("FROM_CLIENT_TO_SERVER");
//                    textView.setText(str);
//                    Log.d("MymessengerClient", "" + str);
//
//
//                default:
//                    super.handleMessage(msg);
//            }
//
//
//        }
//    }

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d("MymessengerClient", "Server : Connected to " + componentName.getPackageName());
            Messenger ClientMessenger = new Messenger(iBinder);


            Button sendBtn = findViewById(R.id.sendBtn);
            EditText sendMessage = findViewById(R.id.sendMessage);
            sendBtn.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View view) {
                    Log.d("MymessengerClient", "Server : clicked");
                    Message message = Message.obtain(null, FROM_SERVER_TO_CLIENT);
                    Bundle bundle = new Bundle();
                    String str = String.valueOf(sendMessage.getText());
                    sendMessage.setText("");
                    bundle.putString("FROM_SERVER_TO_CLIENT", "Hey Client! " + str);
                    message.setData(bundle);
                    try {

                        ClientMessenger.send(message);

                        Toast.makeText(MainActivity.this, "Sent", Toast.LENGTH_SHORT).show();

                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            });

//
//            Button sendBtn = findViewById(R.id.sendBtn);
//            EditText sendMessage = findViewById(R.id.sendMessage);
//            sendBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    Message message = Message.obtain(null, FROM_CLIENT_TO_SERVER);
//
//                    Bundle data = new Bundle();
//                    String msg = String.valueOf(sendMessage.getText());
//                    sendMessage.setText("");
//                    data.putString("FROM_CLIENT_TO_SERVER", "Hey Server ! " + msg);
//                    message.setData(data);
//                    message.replyTo = ServerMessenger;
//                    try {
//                        messenger.send(message);
//
//                        Toast.makeText(MainActivity.this, "Message Sent", Toast.LENGTH_SHORT).show();
//                    } catch (RemoteException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Intent intent = new Intent("com.jasshugarg.MessengerServer");
        intent.setComponent(new ComponentName("com.example.ipcmessengerclient1", "com.example.ipcmessengerclient1.MessengerClientService"));
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);


    }
}