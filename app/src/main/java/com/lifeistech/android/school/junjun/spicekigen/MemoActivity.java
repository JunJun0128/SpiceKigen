package com.lifeistech.android.school.junjun.spicekigen;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;


public class MemoActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    EditText titleEditText;
    TextView dateTextView;
    EditText contentEditText;

    List<Food> foodList;
    long deadlineMillis;
    int currentTimeInt;
    Realm realm;
    Food edit;

    //カレンダーで使う、deadlineまでの日数と名前
    long alarmtimeinterval;
    long alarmtimeintervalminusone;
    long alarmtimeintervalminustwo;
    int alarmtimeintervalint;
    int alarmtimeintervalminusoneint;
    int alarmtimeintervalminustwoint;
    String alarmtimeintervalstring;
    String alarmtimeintervalminusonestring;
    String alarmtimeintervalminustwostring;

    RelativeLayout memo;
    Button savebutton;
    //背景のpreference
    SharedPreferences background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);

        //それぞれのEditTextの機能
        titleEditText = (EditText) findViewById(R.id.titlewrite);
        titleEditText.setInputType(InputType.TYPE_CLASS_TEXT);
        dateTextView = (TextView) findViewById(R.id.datewrite);

        if (dateTextView == null) Log.d("Memo", "null");
        Log.d("Memo", dateTextView.toString());
        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datepick();
            }
        });
        contentEditText = (EditText) findViewById(R.id.contentwrite);
        titleEditText.setInputType(InputType.TYPE_CLASS_TEXT);

        //listからの編集機能から渡ってきたdata
        Intent intentEdit = getIntent();
        edit = (Food) intentEdit.getSerializableExtra("id_key");
        if (edit != null) {
            titleEditText.setText(String.valueOf(edit.title));
            dateTextView.setText(String.valueOf(edit.date));
            contentEditText.setText(String.valueOf(edit.content));
            //millisをここで設定したら、日付のところをクリックしなくてもdiffの値が変にならない。
            //IDID dateの変換の処理をpublicメソッドとして書くorそのまま書いちゃう。
            // 変換対象の日付文字列
            String dateInString = edit.date;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            Date dateInDate = new Date();
            // Date型変換
            try {
                dateInDate = sdf.parse(dateInString);
            } catch (ParseException e) {
            }

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateInDate);
            deadlineMillis = calendar.getTimeInMillis();

            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);  // １月: 0 １２月: 11 な点に注意
            int date = calendar.get(Calendar.DATE);
            Log.d("Check", "year: " + year);
            Log.d("Check", "month: " + month);
            Log.d("Check", "date: " + date);

            //↓ 上のstring.valueof(edit.date)と本質的に同じことをしている
            //dateTextView.setText(year + "/" + (month + 1) + "/" + date);

            //ここから7行alarmのみ
            long currentTimeMillis = System.currentTimeMillis();
            currentTimeMillis = (int) currentTimeMillis;
            long tillexactday = deadlineMillis - currentTimeMillis;
            tillexactday = tillexactday / 1000;
            tillexactday = tillexactday / 60;
            tillexactday = tillexactday / 60;
            tillexactday = tillexactday / 24;
            alarmtimeinterval = tillexactday + 1;
            alarmtimeintervalint = (int) alarmtimeinterval;
        }

        //画面の設定
        background = getSharedPreferences("DataSave", Context.MODE_PRIVATE);
        int BackgroundColor = background.getInt("background", 0);
        memo = (RelativeLayout) findViewById(R.id.memo);
        memo.setBackgroundColor(BackgroundColor);

        //MemoActivityとRealmの連携
        Realm.init(this);
        realm = Realm.getDefaultInstance();
        savebutton = (Button)findViewById(R.id.button);
        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveitem();
            }
        });
    }

    //なぜかmonthOfYearだけ0から始まるので、+1しているのだが、他はしなくていい。


    public boolean saveitem (){

            realm.beginTransaction();
            Food model = realm.createObject(Food.class);
            Random random = new Random();

            int foodid = currentTimeInt;
            String title = titleEditText.getText().toString();
            String date = dateTextView.getText().toString();
            String content = contentEditText.getText().toString();
            Long deadline = deadlineMillis;

            Toast.makeText(MemoActivity.this, "alarm made", Toast.LENGTH_SHORT).show();

            //set alarm before deleting all the title/date data
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());

            if (alarmtimeintervalint == 0) {
                //当日に届く通知です
                calendar.add(Calendar.DAY_OF_MONTH, alarmtimeintervalint);
                scheduleNotification(title + "expires today", calendar);
            } else if (alarmtimeintervalint == 1) {
                //１日前に届く通知です
                calendar.add(Calendar.DAY_OF_MONTH, alarmtimeintervalminusoneint);
                scheduleNotification(title + "expires tomorrow", calendar);
            } else if (alarmtimeintervalint == 1) {
                //２日前に届く通知です
                calendar.add(Calendar.DAY_OF_MONTH, alarmtimeintervalminustwoint);
                scheduleNotification(title + "expires two days later" + date, calendar);
            }

            //書き込みたいデータをインスタンスに入れる
            model.setFoodid(foodid);
            model.setTitle(title);
            model.setDate(date);
            model.setContent(content);
            model.setDeadline(deadline);

            //データ保存
            realm.commitTransaction();
            showLog();
            Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MemoActivity.this, ListActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        return false;
    }

    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        //datetextviewをこの形式でセット
        dateTextView.setText(String.valueOf(year) + "/ " + String.valueOf(monthOfYear + 1) + "/ " + String.valueOf(dayOfMonth));

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, monthOfYear, dayOfMonth);
        Log.d("Check", year + "/" + monthOfYear + "/" + dayOfMonth);

        deadlineMillis = calendar.getTimeInMillis();

        //ここから7行alarmのみ
        long currentTimeMillis = System.currentTimeMillis();
        currentTimeMillis = (int)currentTimeMillis;
        long tillexactday = deadlineMillis - currentTimeMillis;
        tillexactday = tillexactday / 1000;
        tillexactday = tillexactday / 60;
        tillexactday = tillexactday / 60;
        tillexactday = tillexactday / 24;
        alarmtimeinterval = tillexactday + 1;
        alarmtimeintervalint = (int)alarmtimeinterval;
        alarmtimeintervalstring = String.valueOf(alarmtimeintervalint);
        //上で計算終わり。


        //1日前の計算
        long tillexactdayminusone = deadlineMillis - currentTimeMillis - 1000*60*60*24;
        tillexactdayminusone = tillexactdayminusone / 1000;
        tillexactdayminusone = tillexactdayminusone / 60;
        tillexactdayminusone = tillexactdayminusone / 60;
        tillexactdayminusone = tillexactdayminusone / 24;
        alarmtimeintervalminusone = tillexactdayminusone;
        alarmtimeintervalminusoneint = (int)alarmtimeintervalminusone;
        alarmtimeintervalminusonestring = String.valueOf(alarmtimeintervalminusoneint);


        //2日前の計算
        long tillexactdayminustwo = deadlineMillis - currentTimeMillis - 1000*60*60*24;
        tillexactdayminustwo = tillexactdayminustwo / 1000;
        tillexactdayminustwo = tillexactdayminustwo / 60;
        tillexactdayminustwo = tillexactdayminustwo / 60;
        tillexactdayminustwo = tillexactdayminustwo / 24;
        alarmtimeintervalminustwo = tillexactdayminustwo;
        alarmtimeintervalminustwoint = (int)alarmtimeintervalminustwo;
        alarmtimeintervalminustwostring = String.valueOf(alarmtimeintervalminustwoint);
        }


    public boolean datepick() {
        DialogFragment newFragment = new DatePickFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
        return true;
    }

    public void showLog() {

        //検索用のクエリ作成
        RealmQuery<Food> query = realm.where(Food.class);

        //インスタンス生成し、その中にすべてのデータを入れる 今回なら全てのデータ
        RealmResults<Food> results = query.findAll();

        //すべての値をlogに出力(println)
        for (Food test : results) {
            System.out.println(test.getFoodid());
            System.out.println(test.getTitle());
            System.out.println(test.getDate());
            System.out.println(test.getContent());
            System.out.println(test.getDeadline());
        }
    }


    //設定の項目。右上にSettingをクリックしたら設定画面に飛ぶ。その中身
    public boolean onCreateOptionsMenu(Menu menu) {
        //main.xmlの内容を読み込む
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.optionsmenu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menuitem1:
                Intent intent1 = new Intent(this, DesignActivity.class);
                startActivity(intent1);
                return true;
        }
        return false;
    }


    //多分これってactivity起動して、screenにはでないけど、向こうのrecieverに認識してもらう。
    private void scheduleNotification(String content, Calendar calendar){

        // AlarmReceiver が受け取ることになる Intent をここで作る
        Intent notificationIntent = new Intent(this, AlarmBroadcastReceiver.class);
        notificationIntent.putExtra(AlarmBroadcastReceiver.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(AlarmBroadcastReceiver.NOTIFICATION_CONTENT, content);

        // PendingIntent に作った Intent を入れる

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Manager に PendingIntent に登録する
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }
}