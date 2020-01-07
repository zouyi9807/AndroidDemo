package com.example.zouyi.socket;

import java.io.IOException;
import java.util.StringTokenizer;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;
interface ShowMessage
{
	void showText(String keyFrom, String Text);
	void showImage(String keyFrom, String imageName, String path);
	void showPosition(String keyFrom, String latlon);
}
public class GetMessagesService extends Service implements Runnable,ShowMessage{

    int NOTIFYID = 0x123;            //通知的ID
	Thread thread=null;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	public void onCreate()
	{
		
	}
	public void onDestroy()
	{	
		
	}
	@Override
	public int onStartCommand(Intent intent,int flags,int startid)
	{
		if(thread==null||!thread.isAlive())
		{
			thread=new Thread(this);
			thread.start();	
	        Toast.makeText(this,"新开了获取消息线程",Toast.LENGTH_LONG).show();	
		}	
		return 0;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true)
		{
			HttpTools.GetMessage("http://"+Constant.IpAddress+":30000/MessageDispatcher/servlet/GetMessageServlet", Constant.ID,this);
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
		 //获取通知管理器，用于发送通知
        final NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification.Builder notification = new Notification.Builder(this); // 创建一个Notification对象
        // 设置打开该通知，该通知自动消失
        notification.setAutoCancel(true);
        // 设置通知的图标
        notification.setSmallIcon(R.drawable.ic_launcher_background);
        // 设置通知内容的标题
        notification.setContentTitle(keyFrom+"给您发来了消息");
        // 设置通知内容
        notification.setContentText(Text);
        //设置使用系统默认的声音、默认震动
        notification.setDefaults(Notification.DEFAULT_SOUND
                | Notification.DEFAULT_VIBRATE);
        //设置发送时间
        notification.setWhen(System.currentTimeMillis());
        // 创建一个启动其他Activity的Intent
        Intent intent = new Intent(GetMessagesService.this
                , ShowMessageActivity.class);
        intent.setAction("ShowText");
        intent.putExtra("Text", Text);
        PendingIntent pi = PendingIntent.getActivity(
        		GetMessagesService.this, 0, intent,  PendingIntent.FLAG_UPDATE_CURRENT);
        //设置通知栏点击跳转
        notification.setContentIntent(pi);
        //发送通知
        NOTIFYID++;
        notificationManager.notify(NOTIFYID, notification.build());
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
        notification.setContentTitle(keyFrom+"给您发来了图片");
        // 设置通知内容
        notification.setContentText(imageName);
        //设置使用系统默认的声音、默认震动
        notification.setDefaults(Notification.DEFAULT_SOUND
                | Notification.DEFAULT_VIBRATE);
        //设置发送时间
        notification.setWhen(System.currentTimeMillis());
        // 创建一个启动其他Activity的Intent
        Intent intent = new Intent(GetMessagesService.this
                , ShowMessageActivity.class);
        intent.setAction("ShowImage");
        intent.putExtra("path", path);
        intent.putExtra("imagename", imageName);
        PendingIntent pi = PendingIntent.getActivity(
        		GetMessagesService.this, 0, intent,  PendingIntent.FLAG_UPDATE_CURRENT);
        //设置通知栏点击跳转
        notification.setContentIntent(pi);
        //发送通知
        NOTIFYID++;
        notificationManager.notify(NOTIFYID, notification.build());
	}
	@Override
	public void showPosition(String keyFrom, String latlon) {
		// TODO Auto-generated method stub
		StringTokenizer st=new StringTokenizer(latlon,",");
		String latitude=st.nextToken();
		String longitude=st.nextToken();
		//获取通知管理器，用于发送通知
        final NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification.Builder notification = new Notification.Builder(this); // 创建一个Notification对象
        // 设置打开该通知，该通知自动消失
        notification.setAutoCancel(true);
        // 设置通知的图标
        notification.setSmallIcon(R.drawable.ic_launcher_background);
        // 设置通知内容的标题
        notification.setContentTitle(keyFrom+"给您发来了位置信息");
        // 设置通知内容
        notification.setContentText(latlon);
        //设置使用系统默认的声音、默认震动
        notification.setDefaults(Notification.DEFAULT_SOUND
                | Notification.DEFAULT_VIBRATE);
        //设置发送时间
        notification.setWhen(System.currentTimeMillis());
        // 创建一个启动其他Activity的Intent
        Intent intent = new Intent(GetMessagesService.this
                , MapActivity.class);
        intent.setAction("ShowPosition");
        intent.putExtra("latitude", latitude);
        intent.putExtra("longitude", longitude);
        PendingIntent pi = PendingIntent.getActivity(
        		GetMessagesService.this, 0, intent,  PendingIntent.FLAG_UPDATE_CURRENT);
        //设置通知栏点击跳转
        notification.setContentIntent(pi);
        //发送通知
        NOTIFYID++;
        notificationManager.notify(NOTIFYID, notification.build());
	}
}
