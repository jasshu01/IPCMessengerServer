package com.example.ipcmessengerserver;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class MessengerServerService extends Service {
    public MessengerServerService() {
    }


    static final int FROM_CLIENT_TO_SERVER = 1;
    static final int FROM_SERVER_TO_CLIENT = 2;

    Messenger messenger;

    private class IncomingHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {

            Log.d("MymessengerClient", "Server : handling Message");

            switch (msg.what) {
                case FROM_CLIENT_TO_SERVER:
//                    Log.d("MymessengerClient", "" + msg.getData());
                    Log.d("MymessengerClient", "" + msg.getData().getString("FROM_CLIENT_TO_SERVER"));

                    try {
                        msg.replyTo.send(Message.obtain(null, FROM_SERVER_TO_CLIENT, 3, 3));
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }


                default:
                    super.handleMessage(msg);
            }

        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        messenger = new Messenger(new IncomingHandler());
        Log.d("MymessengerClient", "server Binded");
        return messenger.getBinder();
    }
}