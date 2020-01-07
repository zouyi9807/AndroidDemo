package com.example.zouyi.socket;

import android.app.Activity;
//import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class ShowMessageActivity extends Activity implements ShowMessage{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show);
		ImageView im =(ImageView)findViewById(R.id.imageView1);
		TextView tv=(TextView) findViewById(R.id.textView1);
		Intent in =getIntent();
		System.out.println("Action:"+in.getAction());
		if(in.getAction().equals("ShowImage")) {          //显示图片
			Bundle data=in.getExtras();
			String path=data.getString("path");
			String name=data.getString("imagename");
			if (path != null){
				System.out.println(path+name);
	    		BitmapFactory.Options options = new BitmapFactory.Options();
	            options.inSampleSize = 2;
	            Bitmap bitmap = BitmapFactory.decodeFile(path+name,options);
	            //Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
	            im.setImageBitmap(bitmap);
	        }else {
	            Toast.makeText(this,"failed to get iamge",Toast.LENGTH_SHORT).show();
	        }
		}
		else if(in.getAction().equals("ShowText"))      //显示文本消息
		{
			Bundle data=in.getExtras();
			String text=data.getString("Text");
			tv.setText(text);
		}		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_message, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void showText(String keyFrom, String Text) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showImage(String keyFrom, String imageName, String path) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showPosition(String keyFrom, String latlon) {

	}
}
