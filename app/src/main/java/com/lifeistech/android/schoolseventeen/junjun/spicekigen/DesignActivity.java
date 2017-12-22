package com.lifeistech.android.schoolseventeen.junjun.spicekigen;

import android.content.Context;
import android.content.SharedPreferences;
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
        editor.putInt("background", R.color.White);
        editor.apply();
    }

    public void bglightblue (View v){
        SharedPreferences.Editor editor = background.edit();
        editor.putInt("background", R.color.LightBlue);
        editor.apply();
    }

    public void bgyellow (View v) {
        SharedPreferences.Editor editor = background.edit();
        editor.putInt("background", R.color.Yellow);
        editor.apply();
    }

    public void bgpink (View v){
        SharedPreferences.Editor editor = background.edit();
        editor.putInt("background", R.color.Pink);
        editor.apply();
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
