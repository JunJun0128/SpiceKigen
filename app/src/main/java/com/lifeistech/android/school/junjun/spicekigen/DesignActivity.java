package com.lifeistech.android.school.junjun.spicekigen;

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

    public void bgapricot (View v){
        SharedPreferences.Editor editor = background.edit();
        editor.putInt("background", Color.parseColor("#F48161"));
        editor.apply();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        super.onResume();
    }

    public void bgorange (View v){
        SharedPreferences.Editor editor = background.edit();
        editor.putInt("background", Color.parseColor("#FFA060"));
        editor.apply();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        super.onResume();
    }

    public void bgcarrot (View v){
        SharedPreferences.Editor editor = background.edit();
        editor.putInt("background", Color.parseColor("#FFBD77"));
        editor.apply();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        super.onResume();
    }


    public void bgegg (View v) {
        SharedPreferences.Editor editor = background.edit();
        editor.putInt("background", Color.parseColor("#FED475"));
        editor.apply();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        super.onResume();
    }

    public void bgpineapple (View v){
        SharedPreferences.Editor editor = background.edit();
        editor.putInt("background", Color.parseColor("#F4EB61"));
        editor.apply();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        super.onResume();
    }

    public void bglemon (View v){
        SharedPreferences.Editor editor = background.edit();
        editor.putInt("background", Color.parseColor("#eef49c"));
        editor.apply();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        super.onResume();
    }

    public void bglime (View v){
        SharedPreferences.Editor editor = background.edit();
        editor.putInt("background", Color.parseColor("#D7F49C"));
        editor.apply();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        super.onResume();
    }

    public void bggrass (View v){
        SharedPreferences.Editor editor = background.edit();
        editor.putInt("background", Color.parseColor("#A7F484"));
        editor.apply();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        super.onResume();
    }

    //変える
    public void bgcabbage (View v){
        SharedPreferences.Editor editor = background.edit();
        editor.putInt("background", Color.parseColor("#5BFF92"));
        editor.apply();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        super.onResume();
    }

    public void bgcucumber (View v){
        SharedPreferences.Editor editor = background.edit();
        editor.putInt("background", Color.parseColor("#2D7F4E"));
        editor.apply();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        super.onResume();
    }

    public void bgseashore (View v){
        SharedPreferences.Editor editor = background.edit();
        editor.putInt("background", Color.parseColor("#84F4DC"));
        editor.apply();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        super.onResume();
    }

    public void bgsky (View v){
        SharedPreferences.Editor editor = background.edit();
        editor.putInt("background", Color.parseColor("#84EBF4"));
        editor.apply();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        super.onResume();
    }

    public void bglightblue (View v){
        SharedPreferences.Editor editor = background.edit();
        editor.putInt("background", Color.parseColor("#84B6F4"));
        editor.apply();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        super.onResume();
    }

    public void bgcobaltblue (View v){
        SharedPreferences.Editor editor = background.edit();
        editor.putInt("background", Color.parseColor("#388DF4"));
        editor.apply();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        super.onResume();
    }

    public void bglavender (View v){
        SharedPreferences.Editor editor = background.edit();
        editor.putInt("background", Color.parseColor("#ADB9FF"));
        editor.apply();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        super.onResume();
    }

    public void bggrape (View v){
        SharedPreferences.Editor editor = background.edit();
        editor.putInt("background", Color.parseColor("#564592"));
        editor.apply();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        super.onResume();
    }

    public void bgviolet (View v){
        SharedPreferences.Editor editor = background.edit();
        editor.putInt("background", Color.parseColor("#724CF9"));
        editor.apply();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        super.onResume();
    }

    public void bgorchid (View v){
        SharedPreferences.Editor editor = background.edit();
        editor.putInt("background", Color.parseColor("#CA7DF9"));
        editor.apply();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        super.onResume();
    }

    public void bgplum (View v){
        SharedPreferences.Editor editor = background.edit();
        editor.putInt("background", Color.parseColor("#683257"));
        editor.apply();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        super.onResume();
    }

    //変える
    public void bgpink (View v){
        SharedPreferences.Editor editor = background.edit();
        editor.putInt("background", Color.parseColor("#C990BD"));
        editor.apply();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        super.onResume();
    }

    public void bgsakura (View v){
        SharedPreferences.Editor editor = background.edit();
        editor.putInt("background", Color.parseColor("#F7b4e1"));
        editor.apply();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        super.onResume();
    }

    public void bgcoral (View v){
        SharedPreferences.Editor editor = background.edit();
        editor.putInt("background", Color.parseColor("#F78896"));
        editor.apply();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        super.onResume();
    }

    public void bgpeach (View v){
        SharedPreferences.Editor editor = background.edit();
        editor.putInt("background", Color.parseColor("#FF7575"));
        editor.apply();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        super.onResume();
    }

    public void bgtomato (View v){
        SharedPreferences.Editor editor = background.edit();
        editor.putInt("background", Color.parseColor("#C45F5C"));
        editor.apply();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        super.onResume();
    }

}
