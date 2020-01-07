package com.example.zouyi.socket;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;


import android.Manifest;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

interface CallBack
{
	void call(String arg);
}
public class MainActivity extends Activity  implements Runnable,CallBack{

	Button bt_sendloc;        //发送定位信息
	Button bt_sendselectedpic;   //选择照片发送
	Button bt_sendphoto;         //拍照发送
	Button bt_sendtext;
	Button bt_openmap;
	EditText et_keyto;
	EditText et_text;
	TextView tv;         

	Handler handler=new Handler();
	
    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;
    private ImageView picture;          //显示选择的图片
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv=(TextView) findViewById(R.id.textView1);
        picture=(ImageView) findViewById(R.id.imageView1);
        et_keyto=(EditText) findViewById(R.id.editText2);
        et_text=(EditText) findViewById(R.id.editText1);
        bt_sendtext=(Button) findViewById(R.id.button4);
        bt_openmap=(Button) findViewById(R.id.button5);
        bt_openmap.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(MainActivity.this,MapActivity.class);
				MainActivity.this.startActivity(intent);
		        //new Thread(MainActivity.this).start();
			}});
        bt_sendtext.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// TODO Auto-generated method stub
				new Thread(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						String text=et_text.getText().toString();
						ArrayList<String> names=new ArrayList();
						names.add("flag");
						names.add("text");
						names.add("keyfrom");
						names.add("keyto");
						ArrayList<String> values=new ArrayList();
						values.add("2");
						values.add(et_text.getText().toString());
						values.add(Constant.ID);
						values.add(et_keyto.getText().toString());
						final String result=HttpTools.PostFormData("http://"+Constant.IpAddress+":30000/MessageDispatcher/PutMessage.jsp", names, values);
						handler.post(new Runnable(){

							@Override
							public void run() {
								// TODO Auto-generated method stub
								//Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
								if(result!=null)
								{
									tv.setText(result.trim());					
								}
								else
								{
									tv.setText("网络错误");
								}
								//System.out.println(result.trim());
							}});
					}}).start();
			}});
        bt_sendloc=(Button) findViewById(R.id.button1);
        bt_sendloc.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Intent intent2=new Intent(MainActivity.this,MapActivity.class);
				//MainActivity.this.startActivity(intent2);
		        new Thread(MainActivity.this).start();
			}});

        bt_sendselectedpic=(Button) findViewById(R.id.button2);

        bt_sendphoto=(Button) findViewById(R.id.button3);
        bt_sendphoto.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //创建File对象，用于存储拍照后的照片
                File outputImage = new File(getExternalCacheDir(),"output_image.jpg");
                try {
                    if (outputImage.exists()){
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                }catch (IOException e){
                    e.printStackTrace();
                }
                if (Build.VERSION.SDK_INT >= 24){
                    imageUri = FileProvider.getUriForFile(MainActivity.this,"com.example.cameraalbumtest.fileprovider",outputImage);
                }else {
                    imageUri = Uri.fromFile(outputImage);
                }
                //启动相机程序
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(intent,TAKE_PHOTO);
            }
        });
        bt_sendselectedpic.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
//                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
//                }else {
//                    openAlbum();
//                }
            	openAlbum();
            }
        });

		Intent intent=new Intent(MainActivity.this,GetMessagesService.class);
		//设置action来区分服务将要执行哪种动作
		intent.setAction("ACTION_START");                
		MainActivity.this.startService(intent);
		
        Intent itnIn=getIntent();
        Bundle extras = itnIn.getExtras();
        String action = itnIn.getAction();
        if (Intent.ACTION_SEND.equals(action)) {
            if (extras.containsKey(Intent.EXTRA_STREAM)) {
                try {
                    // Get resource path from intent
                    Uri uri = (Uri) extras.getParcelable(Intent.EXTRA_STREAM);

                    // 返回路径
                    String path = getRealPathFromURI(this, uri);
                    diaplayImage(path);
                } catch (Exception e) {
                    Log.e(this.getClass().getName(), e.toString());
                }
            }
        }
        
    }
    public String getRealPathFromURI(Activity act, Uri contentUri) {

    	// can post image
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = act.managedQuery(contentUri, proj, // Which columns to return
                null, // WHERE clause; which rows to return (all rows)
                null, // WHERE clause selection arguments (none)
                null); // Order-by clause (ascending by name)
        if (cursor==null) {
            String path = contentUri.getPath();
            return path;
        }

        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    	
    private void openAlbum(){
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_PHOTO);//打开相册
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch (requestCode){
//            case 1:
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                    openAlbum();
//                }else {
//                    Toast.makeText(this,"You denied the permission",Toast.LENGTH_SHORT).show();
//                }
//                break;
//                default:
//                    break;
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK){
                    try {
                        //将拍摄的照片显示出来
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        picture.setImageBitmap(bitmap);
                    }catch (FileNotFoundException e){
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK){
                    //判断手机系统版本号
                    if (Build.VERSION.SDK_INT >= 19){
                        //4.4及以上系统使用这个方法处理图片
                        handleImageOnKitKat(data);
                    }else {
                        //4.4以下系统使用这个放出处理图片
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
                default:
                    break;
        }
    }
    
    @TargetApi(19)
    private void handleImageOnKitKat(Intent data){
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this,uri)){
            //如果是document类型的Uri,则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id = docId.split(":")[1];//解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
                imagePath = getImagePath(contentUri,null);
            }
        }else if ("content".equalsIgnoreCase(uri.getScheme())){
            //如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri,null);
        }else if ("file".equalsIgnoreCase(uri.getScheme())){
            //如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        diaplayImage(imagePath);//根据图片路径显示图片
    }
    
    private void handleImageBeforeKitKat(Intent data){
        Uri uri = data.getData();
        String imagePath = getImagePath(uri,null);
        diaplayImage(imagePath);
    }
    
    private String getImagePath(Uri uri,String selection){
        String path = null;
        //通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri,null,selection,null,null);
        if (cursor != null){
            if (cursor.moveToFirst()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
    
    private void diaplayImage(String imagePath){
        if (imagePath != null){
    		BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 4;
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath,options);

            //Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            picture.setImageBitmap(bitmap);
        }else {
            Toast.makeText(this,"failed to get iamge",Toast.LENGTH_SHORT).show();
        }
        //发送图片
        Map<String, String> paramsmap=new HashMap<String, String>();
		paramsmap.put("keyto", et_keyto.getText().toString());
		paramsmap.put("keyfrom", Constant.ID);
		File file=new File(imagePath);
		Map<String, File> filesmap=new HashMap<String, File>();
		StringTokenizer st=new StringTokenizer(imagePath,"/");
		String filename=null;
		while(st.hasMoreTokens())
		{
			filename=st.nextToken();
			if(!st.hasMoreTokens())
			{
				break;
			}
		}
		filesmap.put(filename, file);
		CopyOfUploadFiles.upFiles(paramsmap,filesmap,this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
        	Intent intent = new Intent(
					android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS);
			startActivity(intent);
			Toast.makeText(this, "找到按键监听服务，开启即可", Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

	@Override
	public void run() {
		// TODO Auto-generated method stub
		String longitude="12.44444";
		String latitude="104.55555";
		ArrayList<String> names=new ArrayList();
		names.add("longitude");
		names.add("latitude");
		names.add("keyfrom");
		names.add("keyto");
		ArrayList<String> values=new ArrayList();
		values.add(longitude);
		values.add(latitude);
		values.add(Constant.ID);
		values.add(et_keyto.getText().toString());
		final String result=HttpTools.PostFormData("http://192.168.43.126:30000/MessageDispatcher/PutMessage.jsp", names, values);
		handler.post(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				//Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
				if(result!=null)
				{
					tv.setText(result.trim());					
				}
				else
				{
					tv.setText("网络错误");
				}
				//System.out.println(result.trim());
			}});
	}


	@Override
	public void call(final String arg) {
		// TODO Auto-generated method stub
		handler.post(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				//Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
				if(arg!=null)
				{
					tv.setText(arg.trim());					
				}
				else
				{
					tv.setText("网络错误");
				}
				//System.out.println(result.trim());
			}});
	}
}
