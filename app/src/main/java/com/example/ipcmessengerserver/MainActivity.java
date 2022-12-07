package com.example.ipcmessengerserver;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    static final int FROM_CLIENT_TO_SERVER = 1;
    static final int FROM_SERVER_TO_CLIENT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView textView = findViewById(R.id.textView);


        Messenger messenger = MessengerServerService.messenger;

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message message = Message.obtain(null, FROM_SERVER_TO_CLIENT);
                Bundle bundle = new Bundle();
                bundle.putString("FROM_SERVER_TO_CLIENT", "Hey Client!");
                message.setData(bundle);
                try {
                    messenger.send(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}