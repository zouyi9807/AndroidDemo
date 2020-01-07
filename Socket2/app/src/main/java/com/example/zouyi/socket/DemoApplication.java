package com.example.zouyi.socket;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;

import android.app.Application;

public class DemoApplication extends Application {
	 @Override
	    public void onCreate() {
	        super.onCreate();
	        //��ʹ��SDK�����֮ǰ��ʼ��context��Ϣ������ApplicationContext   
	        SDKInitializer.initialize(this);
	        //��4.3.0�𣬰ٶȵ�ͼSDK���нӿھ�֧�ְٶ�����͹�������꣬�ô˷���������ʹ�õ���������.
	        //����BD09LL��GCJ02�������꣬Ĭ����BD09LL���ꡣ
	        SDKInitializer.setCoordType(CoordType.BD09LL);
	    }
}
