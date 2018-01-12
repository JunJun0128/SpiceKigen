package com.lifeistech.android.schoolseventeen.junjun.spicekigen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    Realm realm;
    SharedPreferences settingss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
            //Notification
            case R.id.menuitem1:
                Intent intent1 = new Intent(this, NotificationActivity.class);
                //showMessage("Hello! Item1");
                startActivity(intent1);
                return true;

            //背景
            case R.id.menuitem2:
                Intent intent2 = new Intent(this, DesignActivity.class);
                //showMessage("Hello! Item2");
                startActivity(intent2);
                return true;

//        case R.id.menuitem3;
//            Intent intent3 = new Intent(this, .class);
//            //showMessage("Hello! Item3");
//            return true;

        }
        return false;
    }

//    @Override public boolean onCreateOptionsMenu(Menu menu) {
//        //右上の：のメニュー
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    //http://appdevmem.blogspot.jp/2015/09/android-app-settings.html
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.menu_settings) {
//            framelayout.removeAllViews();
//            handler.removeCallbacks(this);
//            init_flag = false;
//        } else if (item.getItemId() == R.id.menu_settings){
//            Intent intent = new Intent(this, SettingsActivity.class);
//            startActivity(intent);
//        }
//        return super.onOptionsItemSelected(item);
//    }

    public void add(View v) {
        Intent intent = new Intent(this, MemoActivity.class);
        startActivity(intent);
    }

    //public void scan(View v) {}

    public void move(View v) {
        Intent intent = new Intent(this, listActivity.class);
        startActivity(intent);
        super.onResume();
    }
}


