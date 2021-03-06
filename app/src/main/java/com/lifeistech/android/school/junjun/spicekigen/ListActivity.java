package com.lifeistech.android.school.junjun.spicekigen;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

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

//import java.util.Scanner;


public class ListActivity extends AppCompatActivity {
    private final int EDIT_INTENT = 200; //適当な数字/合言葉的な
    ListView list;
    FoodAdapter foodAdapter;
    List<Food> foodList;

    Realm realm;
    SharedPreferences background;
    RelativeLayout activityLayout;

    //Scanner scanner = new Scanner(System.in);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        background = getSharedPreferences("DataSave", Context.MODE_PRIVATE);
        int BackgroundColor = background.getInt("background", 0);
        activityLayout = (RelativeLayout) findViewById(R.id.activity_list);
        activityLayout.setBackgroundColor(BackgroundColor);

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        foodList = loadFoodlist(realm);
        foodAdapter = new FoodAdapter(this, R.layout.item, foodList);
        readFile();

        list = (ListView) findViewById(R.id.list);
        list.setAdapter(foodAdapter);

        //itemのクリック・ロングクリックで実装されるメソッドを実行する。
        // 詳細はしたのmethodに書いてある。
        // ここもメソッドである以上、この中には本来メソドをかけないので省略している。
        initOnClickFunction();

        //alarmbroadcastrecieverを起動したい
        //Foodのdeadlineが2日前なら、通知をする
        //メソッドを書いて、下にそのメソッドの中身を書く
        //
        //それぞれのfoodについて、
        // if(deadline < 3){
        // calendar.add(Calendar.SECOND, 0);
        // scheduleNotification((title +" Will Expire : " + date) , calendar);
        // }
        //の処理を行う
    }


    private void initOnClickFunction() {
        // if we click some item on list....?
        // set onClick
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, View view, final int i, long l) {
                AlertDialog.Builder editAlertDialog = createEditAlertDialog(i);
                editAlertDialog.create().show();
            }
        });

        // set onLongClick
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(final AdapterView<?> adapterView, View view,
                                           final int position, long l) {
                AlertDialog.Builder deleteAlertDialog = createDeleteAlertDialog(position);
                deleteAlertDialog.create().show();
                return true;
            }
        });
    }

    //右上onoptionsmenuの中身
    @Override public boolean onCreateOptionsMenu(Menu menu) {
        //main.xmlの内容を読み込む
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.optionsmenu, menu);
        return true;
    }

    //onoptionsをクリックしたら出てくるやつ。
    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menuitem1:
                Intent intent1 = new Intent(this, DesignActivity.class);
                startActivity(intent1);
                return true;
        }
        return false;
    }

    //delete if we click the items
    private AlertDialog.Builder createDeleteAlertDialog(final int itemPosition) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ListActivity.this);
        alertDialog.setMessage("delete this item?")
                //削除しますを押した時の操作を１クラスとして書いている。
                       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position) {
                        Log.d("List", "position=" + position);
                        Log.d("List", "size=" + foodAdapter.getCount());


                        final Food delete = foodAdapter.getItem(itemPosition);
                        foodAdapter.remove(delete);
                        //一致してるのを検索しなきゃいけないけど、その一致した奴or奴らのまとまりを選択する
                        final RealmResults<Food> results = realm.where(Food.class).equalTo("foodid", delete.getFoodid()).findAll();

                        //Realmにも
                        realm.executeTransaction(new Realm.Transaction(){
                            @Override
                            public void execute(Realm realm) {
                                //"0"は上の検索でヒットした奴(1個しかないから)
                                //results.deleteFromRealm(0);
                                results.deleteLastFromRealm();
                            }
                        });
                        list.setAdapter(foodAdapter);

                        foodAdapter.notifyDataSetChanged();
                    }
                })

                //cancel
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(ListActivity.this, ListActivity.class);
                    }
                });
        return alertDialog;
    }

    //edit to tap the item
    //引数 int iを受け渡してる
    private AlertDialog.Builder createEditAlertDialog(final int itemPosition) {
        //final＝書き換え禁止
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ListActivity.this);

        alertDialog
                .setMessage("edit this item?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    //編集機能 一旦削除する
                    public void onClick(DialogInterface dialogInterface, int position) {

                        final Food edit = (Food) foodAdapter.getItem(itemPosition);

                        foodAdapter.remove(edit);
                        foodList.remove(edit);
                        list.setAdapter(foodAdapter);
                        foodAdapter.notifyDataSetChanged();

                        //IDID 完全に消す処理 再登録した時にまた現れる　Realmにも
                        final RealmResults<Food> results = realm.where(Food.class).equalTo("foodid", edit.getFoodid()).findAll();
                        realm.executeTransaction(new Realm.Transaction(){
                            @Override
                            public void execute(Realm realm) {
                                //results.deleteFromRealm(0);
                                results.deleteLastFromRealm();
                            }
                        });
                        list.setAdapter(foodAdapter);

                        Intent intentEdit = new Intent(ListActivity.this, MemoActivity.class);
                        intentEdit.putExtra("id_key", edit);

                        //intenteditには、editの内容を残しつつ、list(realmからも)からは削除
                        startActivity(intentEdit);
                        startActivityForResult(intentEdit, EDIT_INTENT);

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(ListActivity.this, ListActivity.class);
                    }
                });
        return alertDialog;
    }

    //add button
    public void add(View v) {
        //intentcode = 0;
        Intent intent = new Intent(ListActivity.this, MemoActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    //scan button
    //public void scan (View v){
        //スキャナー起動。　
        //読む処理って自動的にやってくれるの？
    //}

    public List<Food> loadFoodlist(Realm realm) {
        //Realmでは無い方の普通のlistでは...
        List<Food> list = new ArrayList<>();

        // Realmの読み込み(クエリ)
        // 引数として渡された、 realm から、データを引き出す。
        RealmQuery<Food> query = realm.where(Food.class);

        // Execute the query:
        RealmResults<Food> result1 = query.findAll();

        //RealmResults <Food> result1 = realm.where(Food.class).findAll();
        //新しい(毎日変わるやつ)differenceはdifferenceっていうlong型変数  でソート
        result1 = result1.sort("deadline");

        //Realmでは無い方の普通のlistでは...
        for (int foood = 0; foood < result1.size(); foood++) {
            Food value = new Food();

            String deadlinestring = value.toString();
            Log.d("ss", deadlinestring);

            value.setTitle(result1.get(foood).getTitle());
            value.setDate(result1.get(foood).getDate());
            value.setContent(result1.get(foood).getContent());
            value.setDeadline(result1.get(foood).getDeadline());

            list.add(value);
        }

        return list;
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

    //これはgoogleによると端末のホームボタン押す時の操作。
    @Override
    public void onUserLeaveHint() {
        try {
            FileOutputStream fos = openFileOutput("lFood", MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(foodList);
            oos.close();
            fos.close();
        } catch (Exception e) {
        }
    }

    public boolean readFile() {
        try {
            FileInputStream fis = openFileInput("lFood");
            ObjectInputStream ois = new ObjectInputStream(fis);
            foodList = (ArrayList<Food>) ois.readObject();
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

    //多分これってactivity起動するけどscreenにはでないやつmaybe?
    private void scheduleNotification(String content, Calendar calendar){
        Intent notificationIntent = new Intent(this, AlarmBroadcastReceiver.class);
        notificationIntent.putExtra(AlarmBroadcastReceiver.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(AlarmBroadcastReceiver.NOTIFICATION_CONTENT, content);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

}

