package com.example.zouyi.socket;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import android.util.Log;

public class CopyOfUploadFiles {

	/**
	 * @param args
	 */
	 private static final int TIME_OUT = 8 * 1000;                          //超时时间
	    private static final String CHARSET = "UTF-8";                         //编码格式
	    private static final String PREFIX = "--";                            //前缀
	    private static final String BOUNDARY = UUID.randomUUID().toString();  //边界标识 随机生成
	    private static final String CONTENT_TYPE = "multipart/form-data";     //内容类型
	    private static final String LINE_END = "\r\n";                        //换行
	/**
	     * post请求方法
	     * */
	    public static void upFiles(final Map<String, String> strParams, final Map<String, File> fileParams,final CallBack activity) {
            final StringBuilder response = new StringBuilder();
	        new Thread(new Runnable() {
	            @Override
	            public void run() {
	                HttpURLConnection conn = null;
	                try {
	                    URL url = new URL("http://"+Constant.IpAddress+":30000/MessageDispatcher/servlet/UploadServlet");
	                    conn = (HttpURLConnection) url.openConnection();
	                    conn.setRequestMethod("POST");
	                    conn.setReadTimeout(TIME_OUT);
	                    conn.setConnectTimeout(TIME_OUT);
	                    conn.setDoOutput(true);
	                    conn.setDoInput(true);
	                    conn.setUseCaches(false);//Post 请求不能使用缓存   
	                    //设置请求头参数
	                    conn.setRequestProperty("Connection", "Keep-Alive");
	                    conn.setRequestProperty("Charset", "UTF-8");
	                    conn.setRequestProperty("Content-Type", CONTENT_TYPE+";boundary=" + BOUNDARY);
	                    /**
	                     * 请求体
	                     */
	                    //上传参数
	                    OutputStream dos = new DataOutputStream(conn.getOutputStream());
	                    //getStrParams()为一个
	                    dos.write( getStrParams(strParams).toString().getBytes("UTF-8"));
	                    dos.flush();

	                    //文件上传
	                    StringBuilder fileSb = new StringBuilder();
	                    for (Map.Entry<String, File> fileEntry: fileParams.entrySet()){
	                        fileSb.append(PREFIX)
	                                .append(BOUNDARY)
	                                .append(LINE_END)
	                                /**
	                                 * 这里重点注意： name里面的值为服务端需要的key 只有这个key 才可以得到对应的文件
	                                 * filename是文件的名字，包含后缀名的 比如:abc.png
	                                 */
	                                .append("Content-Disposition: form-data; name=\"file\"; filename=\""
	                                        + fileEntry.getKey() + "\"" + LINE_END)
	                                .append("Content-Type: image/jpg" + LINE_END) //此处的ContentType不同于 请求头 中Content-Type
	                                .append("Content-Transfer-Encoding: 8bit" + LINE_END)
	                                .append(LINE_END);// 参数头设置完以后需要两个换行，然后才是参数内容
	                        dos.write(fileSb.toString().getBytes());
	                        dos.flush();
	                        InputStream is = new FileInputStream(fileEntry.getValue());
	                        byte[] buffer = new byte[1024];
	                        int len = 0;
	                        while ((len = is.read(buffer)) != -1){
	                            dos.write(buffer,0,len);
	                        }
	                        is.close();
	                        dos.write(LINE_END.getBytes());
	                    }
	                    //请求结束标志
	                    String end=PREFIX + BOUNDARY + PREFIX + LINE_END;
	                    dos.write(end.getBytes());
	                    dos.flush();
	                    dos.close();
	                    //Log.e(TAG, "postResponseCode() = "+conn.getResponseCode() );
	                    //读取服务器返回信息
	                    if (conn.getResponseCode() == 200) {
	                        InputStream in = conn.getInputStream();
	                        //byte [] buffer=new byte[4];
//	                        in.read(buffer);
//	                        int i=buffer[0];
//	                        switch(i)
//	                        {
//	                            
//	                        }
	                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
	                        String line = null;
	                        //response = new StringBuilder();
	                        while ((line = reader.readLine()) != null) {
	                            response.append(line);
	                        }
	                        //Log.e(TAG, "run: " + response);
	                        System.out.println("取得返回值"+response);
	                        activity.call(response.toString());
	                    }
	                    else
	                    {
	                        System.out.println("返回码不是200");
	                    }
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }finally {
	                    if (conn!=null){
	                        conn.disconnect();
	                    }
	                }
	            }
	        }).start();
	    }

	    /**
	     * 对post参数进行编码处理
	     * */
	    private static StringBuilder getStrParams(Map<String,String> strParams){
	        StringBuilder strSb = new StringBuilder();
	        for (Map.Entry<String, String> entry : strParams.entrySet() ){
	            strSb.append(PREFIX)
	                    .append(BOUNDARY)
	                    .append(LINE_END)
	                    .append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"" + LINE_END)
	                    .append("Content-Type: text/plain; Charset=" + CHARSET + LINE_END)
	                    .append("Content-Transfer-Encoding: 8bit" + LINE_END)
	                    .append(LINE_END)// 参数头设置完以后需要两个换行，然后才是参数内容
	                    .append(entry.getValue())
	                    .append(LINE_END);
	        }
	        System.out.println(strSb);
	        return strSb;
	        
	    }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Map<String, String> paramsmap=new HashMap<String, String>();
		paramsmap.put("longitude", "114.55555");
		File file=new File("‪C:/Users/MAIBENBEN/Desktop/b3203ed73e4dd72d.jpg");

	}

}
