package com.example.zouyi.appweiget;

import java.io.IOException;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Handler;
import android.os.IBinder;
import android.text.format.Time;
import android.util.Log;
import android.widget.Toast;

public class MyService extends Service implements Runnable{
	boolean flag;
	MediaPlayer mp;
	Handler handler=new Handler();
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	public void onCreate()
	{

		mp= MediaPlayer.create(this,R.raw.enter);

		try {
			mp.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Toast.makeText(this, "服务创建完成", Toast.LENGTH_SHORT).show();
	}
	public void onDestroy()
	{
		Toast.makeText(this, "服务销毁", Toast.LENGTH_SHORT).show();
	}
	@Override
	public int onStartCommand(Intent intent,int flags,int startid)
	{
		Toast.makeText(this, "服务被调用", Toast.LENGTH_SHORT).show();
		//获取intent中的action来区分要做什么事情
		String action=intent.getAction();
		if(action.equals("ACTION_START"))
		{
			Toast.makeText(this, "ACTION_START", Toast.LENGTH_SHORT).show();
			flag=true;
			new Thread(this).start();
		}
		else if(action.equals("ACTION_STOP"))
		{
			Toast.makeText(this, "ACTION_STOP", Toast.LENGTH_SHORT).show();

			//mp.stop();
			flag=false;
		}
		return 0;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub

		while(flag)
		{
			try {
				//mp.prepare();
				mp.start();

				Thread.sleep(10000);
				//Toast.makeText(MyService.this, "试图启动播放器", Toast.LENGTH_SHORT);
				handler.post(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						Toast.makeText(MyService.this, "试图启动播放器", Toast.LENGTH_LONG).show();
					}});

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		handler.post(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Toast.makeText(MyService.this, "线程结束", Toast.LENGTH_LONG).show();
			}});

	}

}
