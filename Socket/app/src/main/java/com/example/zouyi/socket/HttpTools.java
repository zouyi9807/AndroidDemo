package com.example.zouyi.socket;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

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

}
