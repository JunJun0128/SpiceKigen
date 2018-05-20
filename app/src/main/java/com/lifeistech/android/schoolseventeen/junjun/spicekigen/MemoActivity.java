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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;

import static com.lifeistech.android.schoolseventeen.junjun.spicekigen.R.id.diff;


public class MemoActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    EditText titleEditText;
    TextView dateTextView;
    EditText contentEditText;
    SharedPreferences settingss;
    List<Food> FoodList;
    Realm realm;
    //カレンダーで使う、deadlineまでの日数
    long tillexactday;

    //これarrayだったりしない？
    String mtitle;
    String mdate;
    String mcontent;
    long mexactdeadline;

    //long mdiffday;
    //下のdiffdayのusageが消していいなら上消す

    //List<Card> foodList;

    //⬇︎いらなくね？
    List<String> readList;
    SharedPreferences background;
    RelativeLayout memo;

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
        //反応なし?どちらもw
        foodList = new ArrayList<Card>();
        FoodList = new RealmList<Food>();
        readFile();

        //sharedprefとrealmどっちもあるよね？
        SharedPreferences settingss = getSharedPreferences("ShoumiKigen", MODE_PRIVATE);

        //各項目用のSharedPrefrencesについて定義しよう

        //ArrayListについて定義
        //
        foodList = new ArrayList<>();
        readFile();
    }

    //@Overrideはいらない
    //なぜかmonthOfYearだけ0から始まるので、+1しているのだが、他はしなくていい。
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        dateTextView.setText(String.valueOf(year) + "/ " + String.valueOf(monthOfYear + 1) + "/ " + String.valueOf(dayOfMonth));
        mdeadline = String.valueOf(year) + "/ " + String.valueOf(monthOfYear + 1) + "/ " + String.valueOf(dayOfMonth);

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, monthOfYear, dayOfMonth);
        long deadlineMillis = calendar.getTimeInMillis();

        //ここから7行いらなくね？
//        long currentTimeMillis = System.currentTimeMillis();
//        long diff = deadlineMillis - currentTimeMillis;
//        diff = diff / 1000;
//        diff = diff / 60;
//        diff = diff / 60;
//        diff = diff / 24;
//        mdiffday = diff;

        //書き方 同じ内容にしたいだけです
        exactdeadline = deadlineMillis;
    }

    //ArrayListのCardに登録
    public boolean readFile() {
        try {
            FileInputStream fis = openFileInput("lCard");
            ObjectInputStream ois = new ObjectInputStream(fis);
            foodList = (ArrayList<Card>) ois.readObject();
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

//    反応していないよね?字色的に。subject,...ということは教科?
//    public boolean readSubjectFile() {
//        try {
//            FileInputStream fis = openFileInput("savesubject");
//            ObjectInputStream ois = new ObjectInputStream(fis);
//            readList = (List<String>) ois.readObject();
//            ois.close();
//            fis.close();
//            return true;
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            return false;
//        } catch (StreamCorruptedException e) {
//            e.printStackTrace();
//            return false;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return false;
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }

    public void save(View v) {
        //ここの内容がほぼarrayの方になってるよ

        String titleText = titleEditText.getText().toString();
        String dateText = dateTextView.getText().toString();
        String contentText = contentEditText.getText().toString();
        Long exactdeadline;

        //ArrayListに保存 → 消すよね ⬇︎

//        String mtitle = String.valueOf(titleEditText.getText());
//        String mdate = String.valueOf(dateTextView.getText());
//        String mcontent = String.valueOf(contentEditText.getText());
//        Card addCard = new Card(mtitle, mdate, mcontent, mdiffday);

//        foodList.add(addCard);
//        if (titleText.isEmpty() && dateText.isEmpty() && contentText.isEmpty()) {
//            Toast.makeText(this, R.string.msg_destruction, Toast.LENGTH_SHORT).show();
//            return;
//        }

        try {
            FileOutputStream fos = openFileOutput("lCard", MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            //array⬇︎
            oos.writeObject(foodList);
            oos.close();
            fos.close();
        } catch (Exception e) {
        }
        Intent intent = new Intent(MemoActivity.this, listActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);


        //以下、Realm 残す
        realm.beginTransaction();
        //インスタンスを生成
        Food model = realm.createObject(Food.class);
        Random random = new Random();
        model.setFoodid(random.nextInt(10000));

        //書き込みたいデータをインスタンスに入れる
        model.setFoodid(random.nextInt(10000));
        model.setMtitle(titleText);
        model.setMdate(dateText);
        model.setMcontent(contentText);
        model.setMexactdeadline(exactdeadline);

        //トランザクション終了 (データを書き込む)
        realm.commitTransaction();
        showLog();

        //各foodについてのalarm

        // 時間をセットする
        Calendar calendar = Calendar.getInstance();
        // Calendarを使って現在の時間をミリ秒で取得
        calendar.setTimeInMillis(System.currentTimeMillis());
        // 5秒後に設定

        calendar.add(Calendar.DAY_OF_MONTH, tillexactday);
        scheduleNotification(mtitle + "expired", calendar);

        }


    private void scheduleNotification(String content, Calendar calendar){
        Intent notificationIntent = new Intent(this, AlarmBroadcastReceiver.class);
        notificationIntent.putExtra(AlarmBroadcastReceiver.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(AlarmBroadcastReceiver.NOTIFICATION_CONTENT, content);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }


    public boolean datepick() {
        DialogFragment newFragment = new DatePickFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
        return true;
    }

//I did Shori/sending to Realm
    public void showLog() {
        //検索用のクエリ作成
        RealmQuery<Food> query = realm.where(Food.class);

        //インスタンス生成し、その中にすべてのデータを入れる 今回なら全てのデータ
        RealmResults<Food> results = query.findAll();

        //すべての値をログに出力
        for (Food test : results) {
            System.out.println(test.getMtitle());
            System.out.println(test.getMdate());
            System.out.println(test.getMcontent());
            System.out.println(test.getMexactdeadline());
        }
    }
}

