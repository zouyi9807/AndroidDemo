package com.example.zouyi.appweiget;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

    Button bt=null;
    Button stop=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt=(Button) findViewById(R.id.button1);
        bt.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent2=new Intent(MainActivity.this,MyService.class);
                //设置action来区分服务将要执行哪种动作
                intent2.setAction("ACTION_START");
                MainActivity.this.startService(intent2);
            }});
        stop=(Button) findViewById(R.id.button2);
        stop.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent2=new Intent(MainActivity.this,MyService.class);
                intent2.setAction("ACTION_STOP");
                MainActivity.this.startService(intent2);
            }});


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


}
