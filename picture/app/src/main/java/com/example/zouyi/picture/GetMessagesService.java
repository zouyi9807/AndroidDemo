package com.example.zouyi.picture;

import java.io.IOException;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

interface ShowMessage {
    void showText(String keyFrom, String Text);

    void showImage(String keyFrom, String imageName, String path);
}

public class GetMessagesService extends Service implements Runnable, ShowMessage {

    final int NOTIFYID = 0x123;            //通知的ID

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate");
    }

    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startid) {
        Log.e(TAG, "onStartCommand");
        new Thread(this).start();
        return 0;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        while (true) {
            HttpTools.GetMessage("http://" + Constant.IpAddress + ":30000/MessageDispatcher/servlet/GetMessageServlet", Constant.ID, this);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @Override
    public void showText(String keyFrom, String Text) {
        // TODO Auto-generated method stub

    }

    @Override
    public void showImage(String keyFrom, String imageName, String path) {
        // TODO Auto-generated method stub
        //获取通知管理器，用于发送通知
        final NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification.Builder notification = new Notification.Builder(this); // 创建一个Notification对象
        // 设置打开该通知，该通知自动消失
        notification.setAutoCancel(true);
        // 设置通知的图标
        notification.setSmallIcon(R.drawable.ic_launcher_background);
        // 设置通知内容的标题
        notification.setContentTitle(keyFrom + "给您发来了图片");
        // 设置通知内容
        notification.setContentText(imageName);
        //设置使用系统默认的声音、默认震动
        notification.setDefaults(Notification.DEFAULT_SOUND
                | Notification.DEFAULT_VIBRATE);
        //设置发送时间
        notification.setWhen(System.currentTimeMillis());
        // 创建一个启动其他Activity的Intent
        Intent intent = new Intent(GetMessagesService.this
                , ShowMessagesActivity.class);
        intent.setAction("ShowImage");
        intent.putExtra("path", path);
        intent.putExtra("imagename", imageName);
        PendingIntent pi = PendingIntent.getActivity(
                GetMessagesService.this, 0, intent, 0);
        //设置通知栏点击跳转
        notification.setContentIntent(pi);
        //发送通知
        notificationManager.notify(NOTIFYID, notification.build());
    }
}
