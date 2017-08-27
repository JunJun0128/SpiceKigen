package com.lifeistech.android.schoolseventeen.junjun.spicekigen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class FontSizeActivity extends AppCompatActivity {
    RadioGroup radio;
    SharedPreferences settingss = getSharedPreferences("ShoumiKigen", MODE_PRIVATE);

//    EditText titleadd = (EditText)activity_memo.(titlewrite);
//    TextView dateadd = (TextView)activity_memo.(datewrite);
//    EditText contentadd = (TextView)activity_memo.(contentwrite);

//    TextView titlelist = (TextView)item.(titleitem);
//    TextView dayslist = (TextView)item.(dateitem);
//    TextView contentlist = (TextView)item.(contentitem);
//    TextView Daysleft = (TextView)item.(howmanyDaysleft);
//    TextView daysLeft = (TextView)item.(howmanydaysLeft);
//    TextView diffday = (TextView)item.(diff);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_font_size);

        radio = (RadioGroup) findViewById(R.id.radio);
        //radio.setOnCheckedChangeListener(this);
    }


    public void onCheckedChanged(RadioGroup group, int checkedId) {
        // 一回選択するたびにtoast型の通知が来る。
        if (0 == checkedId) {
            Toast.makeText(FontSizeActivity.this,
                    "Font10 is selected",
                    Toast.LENGTH_SHORT).show();
        } else if (2 == checkedId){
            Toast.makeText(FontSizeActivity.this,
                    //((RadioButton)findViewById(checkedId)).getText()
                    "Font20 is selected",
                    Toast.LENGTH_SHORT).show();
        } else if (1 == checkedId){
            Toast.makeText(FontSizeActivity.this,
                    //((RadioButton)findViewById(checkedId)).getText()
                    "Font15 is selected",
                    Toast.LENGTH_SHORT).show();
        }
    }


    public void save(View v) {
        //Preference内操作
        SharedPreferences.Editor editor = settingss.edit();



        // チェックされているラジオボタンの ID を取得
        int id = radio.getCheckedRadioButtonId();
        // チェックされているラジオボタンオブジェクトを取得
        RadioButton radioButton = (RadioButton) findViewById(id);

        //何も選択されてない時は自動的に15
        if (id == 0) {
//            titleadd.setTextSize(10);
//            dateadd.setTextSize(10);
//            contentadd.setTextSize(10);
            editor.putInt("key_font", 10);
//            titlelist.setTextSize(10);
//            dayslist.setTextSize(10);
//            contentlist.setTextSize(10);
//            daysLeft.setTextSize(10);
//            Daysleft.setTextSize(10);
//            diffday.setTextSize(10);

        } else if (id == 2) {
            editor.putInt("key_font", 20);

//            titleadd.setTextSize(20);
//            dateadd.setTextSize(20);
//            contentadd.setTextSize(20);
//            titlelist.setTextSize(20);
//            dayslist.setTextSize(20);
//            contentlist.setTextSize(20);
//            daysLeft.setTextSize(20);
//            Daysleft.setTextSize(20);
//            diffday.setTextSize(20);
        } else {
            editor.putInt("key_font", 15);

//            titleadd.setTextSize(15);
//            dateadd.setTextSize(15);
//            contentadd.setTextSize(15);
//            titlelist.setTextSize(15);
//            dayslist.setTextSize(15);
//            contentlist.setTextSize(15);
//            daysLeft.setTextSize(15);
//            Daysleft.setTextSize(15);
//            diffday.setTextSize(15);
        }

        //editor.commit();
        editor.apply();
        finish();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        super.onResume();
    }
}