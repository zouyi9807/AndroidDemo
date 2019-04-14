package com.example.zouyi.tp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class RabbitView extends View {
    public float bitmapX;
    public float bitmapY;

    public RabbitView(Context context) {
        super(context);
        bitmapX = 750;
        bitmapY = 500;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.airplane);
        int height = bitmap.getHeight() / 2;
        int width = bitmap.getWidth() / 2;
        canvas.drawBitmap(bitmap, bitmapX - width, bitmapY - height, paint);
        if (bitmap.isRecycled()) {
            bitmap.recycle();
        }
    }
}
