package com.example.zouyi.socket;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Runnable{

    Button button;
    Handler handler = new Handler();
    TextView textView;
    EditText editText;

    //EditText editText = findViewById(R.id.editText);
    final String fromUrl = "http://192.168.43.126:30000/MessageDispatcher/PutMessage.jsp";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);
        //editText = findViewById(R.id.editText_1);
        //final String f = fromUrl;
        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //Intent intent2=new Intent(MainActivity.this,MapActivity.class);
                //MainActivity.this.startActivity(intent2);
                new Thread(MainActivity.this).start();
            }});
    }

    /*@Override
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
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/

    @Override
    public void run() {
        // TODO Auto-generated method stub
        String longitude = "32.3329";
        String latitude = "119.4031";
        String keyfrom = "邹毅";
        String keyto = "Android";
        ArrayList<String> names = new ArrayList();
        names.add("longitude");
        names.add("latitude");
        names.add("keyfrom");
        names.add("keyto");
        ArrayList<String> values = new ArrayList();
        values.add(longitude);
        values.add(latitude);
        values.add(keyfrom);
        values.add(keyto);
        //String fromUrl = "http://192.168.43.1:30000/MessageDispatcher/PutMessage.jsp";
        //String fromUrl = editText.getText().toString().trim();
        final String result=HttpTools.PostFormData( fromUrl, names, values);
        handler.post(new Runnable(){

            @Override
            public void run() {
                // TODO Auto-generated method stub
                //Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
                if(result!=null)
                {
                    textView.setText(result.trim());
                }
                else
                {
                    textView.setText("网络异常");
                }
                System.out.println(result.trim());
            }});
    }
}
