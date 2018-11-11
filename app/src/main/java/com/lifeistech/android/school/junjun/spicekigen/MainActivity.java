package com.lifeistech.android.school.junjun.spicekigen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import io.realm.Realm;

import static com.lifeistech.android.school.junjun.spicekigen.R.layout.activity_main;

public class MainActivity extends AppCompatActivity {

    Realm realm;
    SharedPreferences settingss;
    SharedPreferences background;
    RelativeLayout main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_main);

        background = getSharedPreferences("DataSave", Context.MODE_PRIVATE);
        int BackgroundColor = background.getInt("background", 0);

        main=(RelativeLayout) findViewById(R.id.main);
        main.setBackgroundColor(BackgroundColor);
    }


    @Override public boolean onCreateOptionsMenu(Menu menu) {
        //main.xmlの内容を読み込む
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.optionsmenu, menu);
        return true;
    }

    //設定の項目。右上にSettingをクリックしたら設定画面に飛ぶ。その中身
    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menuitem1:
                Intent intent1 = new Intent(this, DesignActivity.class);
                startActivity(intent1);
                return true;
        }
        return false;
    }

    public void add(View v) {
        Intent intent = new Intent(this, MemoActivity.class);
        startActivity(intent);
    }

    //public void scan(View v) {}

    public void move(View v) {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
        super.onResume();
    }
}


