package com.example.robin;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import broadcast.BroadcastReceiverExample;

public class BroadCastActivity extends AppCompatActivity {
    BroadcastReceiverExample broadcastReceiverExample = new BroadcastReceiverExample(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broad_cast);
    }
    @Override
    protected void onStart(){
        super.onStart();

        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(broadcastReceiverExample,intentFilter);
    }
    @Override
    protected void onStop(){
        super.onStop();

        unregisterReceiver(broadcastReceiverExample);
    }
}
