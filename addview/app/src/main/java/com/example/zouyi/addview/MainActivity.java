package com.example.zouyi.addview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.list_view);
        final BaseAdapter adapter = new BaseAdapter() {
            private int count=8;
            @Override
            public int getCount() {
                return count;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                LinearLayout linearLayout = new LinearLayout( MainActivity.this);
                for (int i = 0; i < position; i++) {
                    Button button = new Button(MainActivity.this);
                    final int finalI = i+1;
                    button.setText("" + finalI);
                    final int finalP = position;
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(MainActivity.this,"你点击了第"+ finalP+"行"+"第"+finalI+"个按钮",Toast.LENGTH_SHORT).show();
                        }
                    });
                    linearLayout.addView(button);
                }
                return linearLayout;
            }
        };
        listView.setAdapter(adapter);
    }
}
