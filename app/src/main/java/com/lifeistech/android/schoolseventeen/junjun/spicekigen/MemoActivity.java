package com.lifeistech.android.schoolseventeen.junjun.spicekigen;

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
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;


public class MemoActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    EditText titleEditText;
    TextView dateTextView;
    EditText contentEditText;
    List<Food> FoodList;
    long deadlineMillis;
    int currentTimeInt;
    Realm realm;

    //カレンダーで使う、deadlineまでの日数と名前
    long alarmtimeinterval;
    int alarmtimeintervalint;

    RelativeLayout memo;
    //背景のpreference
    SharedPreferences background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);

        background = getSharedPreferences("DataSave", Context.MODE_PRIVATE);
        int BackgroundColor = background.getInt("background", 0);

        memo = (RelativeLayout) findViewById(R.id.memo);
        memo.setBackgroundColor(BackgroundColor);

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        //それぞれのEditTextの機能
        titleEditText = (EditText) findViewById(R.id.titlewrite);
        titleEditText.setInputType(InputType.TYPE_CLASS_TEXT);
        dateTextView = (TextView) findViewById(R.id.datewrite);
        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datepick();
            }
        });
        contentEditText = (EditText) findViewById(R.id.contentwrite);
        titleEditText.setInputType(InputType.TYPE_CLASS_TEXT);
        //TODO listの定義 反応なし?
        FoodList = new RealmList<Food>();
        readFile();
    }

    //なぜかmonthOfYearだけ0から始まるので、+1しているのだが、他はしなくていい。
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        dateTextView.setText(String.valueOf(year) + "/ " + String.valueOf(monthOfYear + 1) + "/ " + String.valueOf(dayOfMonth));

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, monthOfYear, dayOfMonth);
        deadlineMillis = calendar.getTimeInMillis();

        //TODO ここから7行はdiffではなくてalarmのみ？
        long currentTimeMillis = System.currentTimeMillis();
        currentTimeMillis = (int)currentTimeMillis;
        long tillexactday = deadlineMillis - currentTimeMillis;
        tillexactday = tillexactday / 1000;
        tillexactday = tillexactday / 60;
        tillexactday = tillexactday / 60;
        tillexactday = tillexactday / 24;
        alarmtimeinterval = tillexactday + 1;
        alarmtimeintervalint = (int)alarmtimeinterval;
    }

    //realmlistのFoodに登録
    public boolean readFile() {
        try {
            FileInputStream fis = openFileInput("lFood");
            ObjectInputStream ois = new ObjectInputStream(fis);
            FoodList = (RealmList<Food>) ois.readObject();
            ois.close();
            fis.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean datepick() {
        DialogFragment newFragment = new DatePickFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
        return true;
    }

    public void save(View v) {
        realm.beginTransaction();
        Food model = realm.createObject(Food.class);
        Random random = new Random();

        // TODO id の実装を変える　randomだとやばそう カウント形式？
        int foodid = currentTimeInt;
        String title = titleEditText.getText().toString();
        String date = dateTextView.getText().toString();
        String content = contentEditText.getText().toString();
        Long deadline = deadlineMillis;

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

        //alarm
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.DAY_OF_MONTH, alarmtimeintervalint);
        calendar.add(Calendar.DAY_OF_MONTH, alarmtimeintervalint - 1);
        calendar.add(Calendar.DAY_OF_MONTH, alarmtimeintervalint - 2);
        calendar.add(Calendar.DAY_OF_MONTH, alarmtimeintervalint - 3);
        //TODO ３日前も上と同じようにcalendarに登録する
        scheduleNotification(title + "expires" + date, calendar);
    }

    public void showLog() {
        //検索用のクエリ作成
        RealmQuery<Food> query = realm.where(Food.class);
        //インスタンス生成し、その中にすべてのデータを入れる 今回なら全てのデータ
        RealmResults<Food> results = query.findAll();
        //すべての値をログに出力
        for (Food test : results) {
            System.out.println(test.getFoodid());
            System.out.println(test.getTitle());
            System.out.println(test.getDate());
            System.out.println(test.getContent());
            System.out.println(test.getDeadline());
        }
    }

    private void scheduleNotification(String content, Calendar calendar){
        Intent notificationIntent = new Intent(this, AlarmBroadcastReceiver.class);
        notificationIntent.putExtra(AlarmBroadcastReceiver.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(AlarmBroadcastReceiver.NOTIFICATION_CONTENT, content);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }
}

