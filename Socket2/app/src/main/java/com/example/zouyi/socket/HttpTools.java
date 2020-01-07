package com.example.zouyi.socket;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

public class HttpTools {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//FormDataPOST("http://192.168.1.108:30000/MessageDispatcher/");
	}
	public static String testPostFormData(String fromurl,ArrayList<String> names,ArrayList<String> values)
	{
    	URL url=null;
    	HttpURLConnection conn=null;
    	InputStream in=null;
    	byte[] data =new byte[1024];
    	int len=0;
    	ByteArrayOutputStream os;
    	try
        {
    	    url=new URL(fromurl);
    	    conn=(HttpURLConnection)url.openConnection();
    	    conn.setRequestMethod("POST");
    	    conn.setDoOutput(true);
    	    conn.setUseCaches(false);
    	    PrintWriter pw=new PrintWriter(conn.getOutputStream());
    	    String content="";
    	    content = content+names.get(0) +"="+ URLEncoder.encode(values.get(0), "UTF_8");
    	    for(int i=1;i<names.size();i++)
    	    {
    	    	content =content+ "&"+names.get(i)+"="+ URLEncoder.encode(values.get(i), "UTF_8");    	        	    	
    	    }
    	    pw.print(content);
    	    pw.flush();
    	    pw.close();
    	    //conn.setDoInput(true);
    	    in=conn.getInputStream();
    	    os=new ByteArrayOutputStream();
    	    while ((len=in.read(data))!=-1)
    	    {
    	    	os.write(data,0,len);
    	    }
    	    in.close();
    	    return new String(os.toByteArray());
    	}catch (Exception e)
    	{
    		e.printStackTrace();
    	}
    	return null;
    }
	public static String PostFormData(String fromurl,ArrayList<String> names,ArrayList<String> values)
	{
    	URL url=null;
    	HttpURLConnection conn=null;
    	InputStream in=null;
    	byte[] data =new byte[1024];
    	int len=0;
    	ByteArrayOutputStream os;
    	try
        {
    	    url=new URL(fromurl);
    	    conn=(HttpURLConnection)url.openConnection();
    	    conn.setRequestMethod("POST");
    	    conn.setDoOutput(true);
    	    conn.setUseCaches(false);
    	    PrintWriter pw=new PrintWriter(conn.getOutputStream());
    	    String content="";
    	    content = content+names.get(0) +"="+ URLEncoder.encode(values.get(0), "UTF_8");
    	    for(int i=1;i<names.size();i++)
    	    {
    	    	content =content+ "&"+names.get(i)+"="+ URLEncoder.encode(values.get(i), "UTF_8");    	        	    	
    	    }
    	    pw.print(content);
    	    pw.flush();
    	    pw.close();
    	    //conn.setDoInput(true);
    	    in=conn.getInputStream();
    	    os=new ByteArrayOutputStream();
    	    while ((len=in.read(data))!=-1)
    	    {
    	    	os.write(data,0,len);
    	    }
    	    in.close();
    	    return new String(os.toByteArray());
    	}catch (Exception e)
    	{
    		e.printStackTrace();
    	}
    	return null;
    }	
	public static String GetMessage(String posturl,String keyto,ShowMessage sev)
	{
    	URL url=null;
    	HttpURLConnection conn=null;
    	InputStream in=null;
    	byte[] data =new byte[1024];
    	int len=0;
    	ByteArrayOutputStream os;
    	try
        {
    	    url=new URL(posturl);
    	    conn=(HttpURLConnection)url.openConnection();
    	    conn.setRequestMethod("POST");
    	    conn.setDoOutput(true);
    	    conn.setUseCaches(false);
    	    PrintWriter pw=new PrintWriter(conn.getOutputStream());
    	    String content="";
    	    content = "keyto" +"="+ URLEncoder.encode(keyto, "UTF_8");
    	    pw.print(content);
    	    pw.flush();
    	    pw.close();
    	    //conn.setDoInput(true);
    	    in=conn.getInputStream();
    	    //读取整数Flag
            byte [] buffer=new byte[1];
            //char [] buffer=new char[1];
            in.read(buffer);
            int i=buffer[0];
            switch(i)
            {    
                case 1:    //message类型是文件
                	System.out.println("接收到Flag："+1);
                	//读取文件名
                    String filename = null;
                    String keyFrom=null;
                    //BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    //InputStreamReader reader=new InputStreamReader(in);
                    //封装为字符流读取的方式                   
                    DataInputStream reader=new DataInputStream(in);
                    //char [] buffer1=new char[13];
                    //keyFrom=reader.readLine();
                    //keyFrom=new String(keyFrom.getBytes("UTF-16"),"UTF-8");
                    //filename=reader.readLine();  
                    keyFrom=reader.readUTF();
                    filename=reader.readUTF(); 
                    System.out.println("接收到文件名："+filename); 

                    //读取并保存文件                   
            	    os=new ByteArrayOutputStream();
            	    while ((len=in.read(data))!=-1)
            	    {
            	    	os.write(data,0,len);
            	    }
            	    System.out.println("接收到文件大小："+os.size());
            	    in.close();
            	    writeFileToSDCard(os.toByteArray(),"/CallSaving/",filename,false,false);
            	    sev.showImage(keyFrom, filename, "/storage/emulated/0/CallSaving/");
            	    break;
                case 2:   //普通文本消息
                	DataInputStream reader1=new DataInputStream(in);
                    //char [] buffer1=new char[13];
                    //keyFrom=reader.readLine();
                    //keyFrom=new String(keyFrom.getBytes("UTF-16"),"UTF-8");
                    //filename=reader.readLine();  
                    String keyFrom1=reader1.readUTF();
                    String messageText=reader1.readUTF(); 
                	sev.showText(keyFrom1, messageText);               	
                	break;
                case 3:    //定位消息
                	DataInputStream reader2=new DataInputStream(in);
                    //char [] buffer1=new char[13];
                    //keyFrom=reader.readLine();
                    //keyFrom=new String(keyFrom.getBytes("UTF-16"),"UTF-8");
                    //filename=reader.readLine();  
                    String keyFrom2=reader2.readUTF();
                    String latlon=reader2.readUTF(); 
                	sev.showPosition(keyFrom2, latlon);               	
                	break;
            }            
            
    	}catch (Exception e)
    	{
    		e.printStackTrace();
    	}
    	return null;
    }
	public synchronized static void writeFileToSDCard(@NonNull final byte[] buffer, @Nullable final String folder,
            @Nullable final String fileName, final boolean append, final boolean autoLine) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				boolean sdCardExist = Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
				String folderPath = "";
				if (sdCardExist) {
					//TextUtils为android自带的帮助类
					if (TextUtils.isEmpty(folder)) {
						//如果folder为空，则直接保存在sd卡的根目录
						folderPath = Environment.getExternalStorageDirectory()
						+ File.separator;
					} else {
						folderPath = Environment.getExternalStorageDirectory()
						+ File.separator + folder + File.separator;
					}
				} else {
					return;
				}
				
				File fileDir = new File(folderPath);
				if (!fileDir.exists()) {
					if (!fileDir.mkdirs()) {
						return;
					}
				}
				File file;
				//判断文件名是否为空
				if (TextUtils.isEmpty(fileName)) {
				file = new File(folderPath + "app_log.txt");
				} else {
				file = new File(folderPath + fileName);
				}
				RandomAccessFile raf = null;
				FileOutputStream out = null;
				try {
				if (append) {
				//如果为追加则在原来的基础上继续写文件
				raf = new RandomAccessFile(file, "rw");
				raf.seek(file.length());
				raf.write(buffer);
				if (autoLine) {
				raf.write("\n".getBytes());
				}
				} else {
				//重写文件，覆盖掉原来的数据
				out = new FileOutputStream(file);
				out.write(buffer);
				out.flush();
				}
				} catch (IOException e) {
				e.printStackTrace();
				} finally {
				try {
				if (raf != null) {
				raf.close();
				}
				if (out != null) {
				out.close();
				}
				} catch (IOException e) {
				e.printStackTrace();
				}
				}
			}
		}).start();
	}

}
