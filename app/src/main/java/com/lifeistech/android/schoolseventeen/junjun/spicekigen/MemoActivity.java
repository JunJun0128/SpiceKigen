package com.lifeistech.android.schoolseventeen.junjun.spicekigen;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
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

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;


public class MemoActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    EditText titleEditText;
    TextView dateTextView;
    EditText contentEditText;
    SharedPreferences settingss;
    String mtitle;
    String mdate;
    String mcontent;
    String mdeadline;
    long mdiffday;
    List<Card> foodList;
    //String subject[];
    List<String> readList;
    String helper[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);
        realm = Realm.getDefaultInstance();

        //定義とそれぞれの入力画面の機能

        //食品名を入力
        titleEditText = (EditText) findViewById(R.id.titlewrite);
        titleEditText.setInputType(InputType.TYPE_CLASS_TEXT);

        //日付入力
        dateTextView = (TextView) findViewById(R.id.datewrite);
        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datepick();
            }
        });

        //内容入力
        contentEditText = (EditText) findViewById(R.id.contentwrite);
        titleEditText.setInputType(InputType.TYPE_CLASS_TEXT);
        foodList = new ArrayList<Card>();
        readFile();

        SharedPreferences settingss = getSharedPreferences("ShoumiKigen", MODE_PRIVATE);
        int fontsize = settingss.getInt("keyfont", 15);

        // TODO　settings if ってなってた
        if (fontsize == 10) {
            titleEditText.setTextSize(fontsize);
            dateTextView.setTextSize(fontsize);
            contentEditText.setTextSize(fontsize);
        }else if(fontsize == 20) {
            titleEditText.setTextSize(fontsize);
            dateTextView.setTextSize(fontsize);
            contentEditText.setTextSize(fontsize);
        }else if(fontsize == 15) {
            titleEditText.setTextSize(fontsize);
            dateTextView.setTextSize(fontsize);
            contentEditText.setTextSize(fontsize);

            //各項目用のSharedPrefrencesについて定義

            //ArrayListについて定義
            foodList = new ArrayList<>();
            //readFile();

            readList = new ArrayList<String>();
            //readHelperList = new ArrayList<String>();
            //readSubjectFile();
            //readHelperFile();
            //firststarting();
        }
    }

    //@Override　（いらない）
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        dateTextView.setText(String.valueOf(year) + "/ " + String.valueOf(monthOfYear + 1) + "/ " + String.valueOf(dayOfMonth));
        mdeadline = String.valueOf(year) + "/ " + String.valueOf(monthOfYear + 1) + "/ " + String.valueOf(dayOfMonth);

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, monthOfYear, dayOfMonth);
        long deadlineMillis = calendar.getTimeInMillis();
        long currentTimeMillis = System.currentTimeMillis();
        long diff = deadlineMillis - currentTimeMillis;
        diff = diff / 1000;
        diff = diff / 60;
        diff = diff / 60;
        diff = diff / 24;
        mdiffday = diff;
    }

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

    public boolean readSubjectFile() {
        try {
            FileInputStream fis = openFileInput("savesubject");
            ObjectInputStream ois = new ObjectInputStream(fis);
            readList = (List<String>) ois.readObject();
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

//helperいらない
//科目はいらない

    public void save(View v) {
        String titleText = titleEditText.getText().toString();
        String dateText = dateTextView.getText().toString();
        String contentText = contentEditText.getText().toString();
        String mtitle = String.valueOf(titleEditText.getText());
        String mdate = String.valueOf(dateTextView.getText());
        String mcontent = String.valueOf(contentEditText.getText());
        Card addCard = new Card(mtitle, mdate, mcontent, mdiffday);

     //216行がnull pointer exception
        foodList.add(addCard);
        if (titleText.isEmpty() && dateText.isEmpty() && contentText.isEmpty()) {
            Toast.makeText(this, R.string.msg_destruction, Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            FileOutputStream fos = openFileOutput("lCard", MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(foodList);
            oos.close();
            fos.close();
        } catch (Exception e) {

        }
        Intent intent = new Intent(MemoActivity.this, listActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

        //各野菜保存のPrefは使わずRealm
        realm.beginTransaction();
        //インスタンスを生成
        Food model = realm.createObject(Food.class);

        //書き込みたいデータをインスタンスに入れる
        model.setMtitle(titleEditText.getText().toString());
        model.setMdate(dateTextView.getText().toString());
        model.setMcontent(contentEditText.getText().toString());

        //トランザクション終了 (データを書き込む)
        realm.commitTransaction();

        // データを挿入する
        realm.executeTransaction(new Realm.Transaction(){
            @Override
            public void execute(Realm realm){
                TestDB u = realm.createObject(TestDB.class);
                u.setMtitle("Salt");
                u.setMdate(dd/mm/yy=01/01/18);
                u.setMcontent("Memo)
            }
        });
    }

    public boolean datepick() {
        DialogFragment newFragment = new DatePickFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
        return true;
    }

    public void showLog(View v){
        //検索用のクエリ作成
        RealmQuery<Food> query = realm.where(Food.class);

//        query.equalTo("name", "test");
//        query.or().equalTo("id", 2);
//        query.or().equalTo("id", 3);

        //インスタンス生成し、その中にすべてのデータを入れる 今回なら全てのデータ
        RealmResults<Food> results = query.findAll();

        //すべての値をログに出力
        for (Food test:results){
            System.out.println(test.getMtitle());
            System.out.println(test.getMdate());
            System.out.println(test.getMcontent());
        }
    }
}
}