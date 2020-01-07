package com.example.zouyi.simplecursoradaptertest;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends Activity {
    Cursor cursor;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = this.getSharedPreferences("share", MODE_PRIVATE);
        boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);
        editor = sharedPreferences.edit();
        if (isFirstRun) {
            DBUtil.createTable();
            DBUtil.insertdata();
            cursor = DBUtil.getCursor();
            editor.putBoolean("isFirstRun", false);
            editor.apply();
        } else {
            cursor = DBUtil.getCursor();
        }
        // 最后一个参数flags是一个标识，标识当数据改变调用onContentChanged()的时候，是
        // 否通知ContentProvider数据的改变，如果无需监听ContentProvider的改变，则可以传0。
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                R.layout.item, cursor, new String[]{"name", "age"},
                new int[]{R.id.name, R.id.age}, 0);

        ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(adapter);
    }

}