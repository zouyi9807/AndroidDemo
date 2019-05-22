package com.example.zouyi.tp;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.view.View;

public class RabbitView extends View implements Runnable {
    Handler handler = new Handler();

    public float bitmapX; // 兔子显示位置的X坐标
    public float bitmapY; // 兔子显示位置的Y坐标

    RabbitView rabbit;
    public float Vx;    //X速度
    public float Vy;    //Y速度
    public double g = 0.001;    //重力加速度px/ms^2
    public int t = 10;

    public RabbitView(Context context) { // 重写构造方法
        super(context);
        bitmapX = 750; // 设置兔子的默认显示位置的X坐标
        bitmapY = 500; // 设置兔子的默认显示位置的Y坐标
        rabbit = this;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        @SuppressLint("DrawAllocation") Paint paint = new Paint(); // 创建并实例化Paint的对象
        @SuppressLint("DrawAllocation") Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),
                R.drawable.rabbit); // 根据图片生成位图对象
        canvas.drawBitmap(bitmap, bitmapX, bitmapY, paint); // 绘制小兔子
        if (bitmap.isRecycled()) { // 判断图片是否回收
            bitmap.recycle(); // 强制回收图片
        }
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        //noinspection InfiniteLoopStatement
        while (true) {
            float Vy1 = (float) (Vy + g * t);
            bitmapY = bitmapY + (Vy1 + Vy) / 2 * t;
            bitmapX = bitmapX + Vx * t;
            Vy = Vy1;
            handler.post(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    rabbit.invalidate();
                }
            });
            try {
                Thread.sleep(t);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
