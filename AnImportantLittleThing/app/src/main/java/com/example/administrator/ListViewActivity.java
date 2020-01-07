package com.example.administrator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ListViewActivity extends Activity {
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Intent intent = getIntent();
        String i = String.valueOf(intent.getIntExtra("name", -1));
        tv = (TextView) findViewById(R.id.textview);
        tv.setText("这是第 " + i + " 条消息");
    }
}
