package com.example.zouyi.tp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import android.widget.Toast;

public class MainActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    RabbitView rabbit;
    float x;
    float y;
    long beforeUpTime;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final FrameLayout frameLayout = (FrameLayout) findViewById(R.id.mylayout); // 获取帧布局管理器
        // 为小兔子添加触摸事件监听
        frameLayout.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {// 如果发生触摸
                    x = event.getX();
                    y = event.getY();
                    long beforeUpTime = event.getDownTime();// 计算触摸事件持续时间
                    rabbit = new RabbitView(MainActivity.this);
                    rabbit.bitmapX = event.getX();
                    rabbit.bitmapY = event.getY();
                    frameLayout.addView(rabbit);
                    rabbit.invalidate();
                    return true;
                }
                if (event.getActionMasked() == MotionEvent.ACTION_UP) {
                    rabbit.bitmapX = event.getX();
                    rabbit.bitmapY = event.getY();
                    rabbit.invalidate();
                    float finalx = event.getX();            //弹起的位置
                    float finaly = event.getY();
                    long uptime = event.getEventTime() - event.getDownTime();
                    rabbit.Vx = (finalx - x) / (uptime - beforeUpTime);
                    rabbit.Vy = (finaly - y) / (uptime - beforeUpTime);
                    System.out.println("间隔时间:" + (uptime - beforeUpTime) + " 间隔像素：" + (finalx - x) + " 初始速速x：" + rabbit.Vx + "  y:" + rabbit.Vy);
                    new Thread(rabbit).start();
                    return true;
                }
                rabbit.bitmapX = event.getX();
                rabbit.bitmapY = event.getY();
                rabbit.invalidate();
                return true;
            }
        });

    }


}