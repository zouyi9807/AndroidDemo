package com.example.zouyi.tp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

public class MainActivity extends Activity {

    float x1 = 0, x2 = 0, y1 = 0, y2 = 0;
    private static final String TAG = "MainActivity";

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        final int width = displayMetrics.widthPixels;
        final int height = displayMetrics.heightPixels;

        FrameLayout frameLayout = findViewById(R.id.mylayout);
        final RabbitView rabbitView = new RabbitView(MainActivity.this);
        final RabbitView rabbitView1 = new RabbitView(MainActivity.this);
        rabbitView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                rabbitView.bitmapX = event.getX();
                rabbitView.bitmapY = event.getY();
                rabbitView.invalidate();
                rabbitView1.bitmapX = width - event.getX();
                rabbitView1.bitmapY = height - event.getY();
                rabbitView1.invalidate();
                return true;
            }
        });
        frameLayout.addView(rabbitView);
        frameLayout.addView(rabbitView1);
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        //继承了Activity的onTouchEvent方法，直接监听点击事件
//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            //当手指按下的时候
//            x1 = event.getX();
//            y1 = event.getY();
//        }
//        if (event.getAction() == MotionEvent.ACTION_UP) {
//            //当手指离开的时候
//            x2 = event.getX();
//            y2 = event.getY();
//            if (y1 - y2 > 200) {
//                Toast.makeText(MainActivity.this, "向上滑了200", Toast.LENGTH_LONG).show();
//                Log.e(TAG, "111 ");
//            } else if (y2 - y1 > 200) {
//                Toast.makeText(MainActivity.this, "向下滑了200", Toast.LENGTH_LONG).show();
//            }
//        }
//        return super.onTouchEvent(event);
//    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //当手指按下的时候
            x1 = event.getX();
            y1 = event.getY();
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            //当手指离开的时候
            x2 = event.getX();
            y2 = event.getY();
            if (y1 - y2 > 0) {
                Toast.makeText(MainActivity.this, "向上滑了" + (y1 - y2) + "px", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "111 ");
            } else if (y2 - y1 > 0) {
                Toast.makeText(MainActivity.this, "向下滑了" + (y2 - y1) + "px", Toast.LENGTH_SHORT).show();
            } else if (x1 - x2 > 0) {
                Toast.makeText(MainActivity.this, "向左滑了" + (x1 - x2) + "px", Toast.LENGTH_SHORT).show();
            } else if (x2 - x1 > 0) {
                Toast.makeText(MainActivity.this, "向右滑了" + (x2 - x1) + "px", Toast.LENGTH_SHORT).show();
            }
        }
        return super.dispatchTouchEvent(event);
    }
}
