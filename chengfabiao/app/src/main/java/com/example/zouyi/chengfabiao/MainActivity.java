package com.example.zouyi.chengfabiao;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText editText1;
    EditText editText2;
    TextView textView;
    Button jia;
    Button jian;
    Button cheng;
    Button chu;
    double a = 0,b=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jia = findViewById(R.id.jia);
        jia.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                editText1= findViewById (R.id.cs);
                a = Double.parseDouble(editText1.getText().toString());
                editText2 = findViewById (R.id.bc);
                b = Double.parseDouble(editText2.getText().toString());

                textView = findViewById(R.id.jg);
                String c = String.valueOf(a+b);
                textView.setText(c);
                editText1.setText("");
                editText2.setText("");
            }
        });
        jian = findViewById(R.id.jian);
        jian.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                editText1= findViewById (R.id.cs);
                a = Double.parseDouble(editText1.getText().toString());
                editText2 = findViewById (R.id.bc);
                b = Double.parseDouble(editText2.getText().toString());

                textView = findViewById(R.id.jg);
                String c = String.valueOf(a-b);
                textView.setText(c);
                editText1.setText("");
                editText2.setText("");
            }
        });
        cheng = findViewById(R.id.cheng);
        cheng.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                editText1= findViewById (R.id.cs);
                a = Double.parseDouble(editText1.getText().toString());
                editText2 = findViewById (R.id.bc);
                b = Double.parseDouble(editText2.getText().toString());

                textView = findViewById(R.id.jg);
                String c = String.valueOf(a*b);
                textView.setText(c);
                editText1.setText("");
                editText2.setText("");
            }
        });
        chu = findViewById(R.id.chu);
        chu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                editText1= findViewById (R.id.cs);
                a = Double.parseDouble(editText1.getText().toString());
                editText2 = findViewById (R.id.bc);
                b = Double.parseDouble(editText2.getText().toString());

                textView = findViewById(R.id.jg);
                String c = String.valueOf(a/b);
                textView.setText(c);
                editText1.setText("");
                editText2.setText("");
            }
        });
    }
}
