package com.example.zouyi.service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MyService.DownloadBinder downloadBinder;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            downloadBinder = (MyService.DownloadBinder) service;
            downloadBinder.startDownload();
            downloadBinder.getProgress();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button startService = findViewById(R.id.start_service);
        Button stopService = findViewById(R.id.stop_service);
        startService.setOnClickListener(this);
        stopService.setOnClickListener(this);
        Button bindService = findViewById(R.id.bind_service);
        Button unbindService = findViewById(R.id.Unbind_service);
        bindService.setOnClickListener(this);
        unbindService.setOnClickListener(this);
        Button startIntentService = findViewById(R.id.start_intent_service);
        startIntentService.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_service:
                //Intent intent = new Intent();
                //intent.setComponent(new ComponentName("com.android.settings", "com.android.settings.RunningServices"));
                //startActivity(intent);
                Intent startService = new Intent(this, MyService.class);
                startService(startService);
                break;
            case R.id.stop_service:
                Intent stopService = new Intent(this, MyService.class);
                stopService(stopService);
                break;
            case R.id.bind_service:
                Intent bindService = new Intent(this, MyService.class);
                bindService(bindService, serviceConnection, BIND_AUTO_CREATE);
                break;
            case R.id.Unbind_service:
                unbindService(serviceConnection);
                break;
            case R.id.start_intent_service:
                Log.d("MainActivity", "Thread id is " + Thread.currentThread().getId());
                Intent intent = new Intent(this, MyIntentService.class);
                startService(intent);
                break;
            default:
                break;
        }
    }
}
