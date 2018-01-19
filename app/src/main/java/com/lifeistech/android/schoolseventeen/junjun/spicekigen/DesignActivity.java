package com.lifeistech.android.schoolseventeen.junjun.spicekigen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;


public class DesignActivity extends AppCompatActivity {
    SharedPreferences background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design);

        background = getSharedPreferences("DataSave", Context.MODE_PRIVATE);
        int BackgroundColor = background.getInt("background", 0);
        RelativeLayout design = (RelativeLayout) findViewById(R.id.activity_design);
        design.setBackgroundColor(BackgroundColor);
    }

    //imagebuttonのOnClick idを押すと、背景色および文字色が変わること。
    public void bgwhite (View v){
        SharedPreferences.Editor editor = background.edit();
        editor.putInt("background", Color.parseColor("#ffffff"));
        editor.apply();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        super.onResume();
    }

    public void bglightblue (View v){
        SharedPreferences.Editor editor = background.edit();
        editor.putInt("background", Color.parseColor("#99D8C2"));
        editor.apply();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        super.onResume();
    }

    public void bgyellow (View v) {
        SharedPreferences.Editor editor = background.edit();
        editor.putInt("background", Color.parseColor("#FED475"));
        editor.apply();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        super.onResume();
    }

    public void bgpink (View v){
        SharedPreferences.Editor editor = background.edit();
        editor.putInt("background", Color.parseColor("#C990BD"));
        editor.apply();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        super.onResume();
    }

//    if ("background" == LightBlue){
////        activity_main.setBackgroundColor(LightBlue);
////        activity_memo.setBackgroundColor(LightBlue);
////        activity_list.setBackgroundColor(LightBlue);
//    }else if ("background" == Yellow){
////        activity_main.setBackgroundColor(Yellow);
////        activity_memo.setBackgroundColor(Yellow);
////        activity_list.setBackgroundColor(Yellow);
//    }else if ("background" == Pink){
////        activity_main.setBackgroundColor(Pink);
////        activity_memo.setBackgroundColor(Pink);
////        activity_list.setBackgroundColor(Pink);
//    }else{
//        activity_main.setBackgroundColor(White);
//        activity_memo.setBackgroundColor(White);
//        activity_list.setBackgroundColor(White);
//    }

}
