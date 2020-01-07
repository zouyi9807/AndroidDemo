package com.example.administrator;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton fab;
    boolean fabOpened = false;
    TextView tv;
    private List<Item> itemList = new ArrayList<>();

    //private String[] data = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = findViewById(R.id.fab);
        tv = findViewById(R.id.tv);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!fabOpened) {
                    openMenu(v);
                } else {
                    closeMenu(v);
                }
            }
        });
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeMenu(fab);
            }
        });

        initItems();
        ItemAdapter adapter = new ItemAdapter(MainActivity.this, R.layout.list_item, itemList);
        ListView listView = findViewById(R.id.listview);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ListViewActivity.class);
                intent.putExtra("name", position);
                startActivity(intent);
            }
        });
    }

    /*
    private void setListeners(){
        OnClick onClick = new OnClick();
    }
    private class OnClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent = null;
            switch ( v.getId()){
                case R.id.:
                    intent = new Intent(MainActivity.this,ListViewActivity.class);
                    break;
            }
            startActivity(intent);
        }
    }
    */
    public void openMenu(View v) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, "rotation", 0, -155, -135);
        animator.setDuration(500);
        animator.start();
        tv.setVisibility(View.VISIBLE);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 0.7f);
        alphaAnimation.setDuration(500);
        alphaAnimation.setFillAfter(true);
        tv.startAnimation(alphaAnimation);
        fabOpened = true;

    }

    public void closeMenu(View v) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, "rotation", -135, 20, 0);
        animator.setDuration(500);
        animator.start();
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.7f, 0);
        alphaAnimation.setDuration(500);
        tv.startAnimation(alphaAnimation);
        tv.setVisibility(View.GONE);
        fabOpened = false;
    }

    private void initItems() {
        for (int i = 0; i < 20; i++) {
            Item item1 = new Item("qwe", R.drawable.cn);
            itemList.add(item1);
        }
    }
}
