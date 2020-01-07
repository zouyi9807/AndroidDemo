package com.example.zouyi.socket;

import java.util.ArrayList;

import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;

public class MainService extends AccessibilityService implements Runnable{

	Handler handler=new Handler();
	@Override
	public void onAccessibilityEvent(AccessibilityEvent event) {
		// TODO Auto-generated method stub

	}
	@SuppressLint("NewApi") @Override
	protected boolean onKeyEvent(KeyEvent event) {
		int key = event.getKeyCode();	
		switch(key){
		case KeyEvent.KEYCODE_VOLUME_DOWN:
			
			
			Intent downintent = new Intent("com.exmaple.broadcaster.KEYDOWN");
			downintent.putExtra("dtime", System.currentTimeMillis());
			//sendBroadcast(upintent);

			Log.i("callsaving", "启动发送位置线程");
	        new Thread(this).start();
			break;
		case KeyEvent.KEYCODE_VOLUME_UP:
			
	
			break;
		default:
				break;
		}
		return super.onKeyEvent(event);
	}
	@Override
	public void onInterrupt() {
		// TODO Auto-generated method stub

	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		String longitude="12.44444";
		String latitude="104.55555";
		ArrayList<String> names=new ArrayList();
		names.add("longitude");
		names.add("latitude");
		ArrayList<String> values=new ArrayList();
		values.add("12.44444");
		values.add("104.55555");
		final String result=HttpTools.PostFormData("http://192.168.1.108:30000/MessageDispatcher/PutMessage.jsp", names, values);
		handler.post(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				//Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
				//tv.setText(result.trim());
				System.out.println(result.trim());
			}});
	}
}