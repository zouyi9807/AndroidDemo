package com.example.zouyi.tp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.mylayout);
        final RabbitView rabbitView = new RabbitView(MainActivity.this);
        rabbitView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                rabbitView.bitmapX = event.getX();
                rabbitView.bitmapY = event.getY();
                rabbitView.invalidate();
                return true;
            }
        });
        frameLayout.addView(rabbitView);
    }
}
