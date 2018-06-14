package com.lifeistech.android.schoolseventeen.junjun.spicekigen;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
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
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class listActivity extends AppCompatActivity {
    ListView list;
    //これは<FOOD>を扱うから、realmの方！
    foodAdapter mFoodAdapter;
    List<Food> readFoodList;
    long mexactdeadline;

    Realm realm;
    SharedPreferences background;
    RelativeLayout activity_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        background = getSharedPreferences("DataSave", Context.MODE_PRIVATE);
        int BackgroundColor = background.getInt("background", 0);

        activity_list=(RelativeLayout) findViewById(R.id.activity_list);
        activity_list.setBackgroundColor(BackgroundColor);

        //Realmの宣言
        Realm.init(this);
        realm = Realm.getDefaultInstance();
        mFoodAdapter = new foodAdapter(this, R.layout.item);

        // Realmの読み込み(クエリ)
        RealmQuery<Food> query = realm.where(Food.class);
        // Execute the query:
        RealmResults<Food> result1 = query.findAll();

        //RealmResults <Food> result1 = realm.where(Food.class).findAll();
        //新しい(毎日変わるやつ)differenceはdifferenceっていうlong型変数  でソート
        result1 = result1.sort("deadline");

        //何個のfooodでも同じようにmfoodadapterに追加できる。
        for (int foood = 0; foood < result1.size(); foood ++){
            Food value = new Food();
            value.setTitle(result1.get(foood).getTitle());
            value.setDate(result1.get(foood).getDate());
            value.setContent(result1.get(foood).getContent());
            value.setDeadline(result1.get(foood).getDeadline());
            mFoodAdapter.add(value);
        }

        readFoodList = new ArrayList<>();
        readFile();

        list = (ListView)findViewById(R.id.list);
        list.setAdapter(mFoodAdapter);

        //ボタン
        ImageButton fab = (ImageButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                save();
            }
        });

        //TODO クリックしたらチェックボックスが各foodに現れる　選んでOKか何かを押して消す。
//        ImageButton DeleteButton = (ImageButton)findViewById(R.id.deletebutton);
//        DeleteButton.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v, final int i, long l) {
//                //TODO 削除処理
//
//                //TODO 各listのitemごとにチェックボタンを生えさせる
//
//                AlertDialog.Builder alertDialog = new AlertDialog.Builder(listActivity.this);
//                alertDialog.setMessage("delete?")
//
//                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                            @Override
//                            //TODO クリックしたら項目削除
//                            public void onClick(DialogInterface dialogInterface, int position) {
//                                Food delete = (Food)mFoodAdapter.getItem(i);
//                                mFoodAdapter.remove(delete);
//                                //readFoodList.remove(i);
//                                list.setAdapter(mFoodAdapter);
//                            }
//                        })
//                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//
//                            }
//                        });
//                alertDialog.create().show();
//
//                //realmからもobject消す。または、realmobjectのfoodを消したのでそれを更新/永久削除
//                //画面上でショートログ通知
//                Snackbar.make(v, "Deleted", Snackbar.LENGTH_SHORT)
//                        .setAction("Action", null).show();
//            }
//        });

//        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(final AdapterView<?> adapterView, View view, final int i, long l) {
//                // TODO 編集機能
//
//                Intent intentEdit = new Intent(listActivity.this, MemoActivity.class);
//
//                // TODO 始めは登録していた時の情報が入ったままクリックしたら今まで同様に変更できる。
//
//                // TODO Save ボタン　→ 上書き
//
//
//            }
//        });

    }

    @Override
    public void onUserLeaveHint() {
        try {
            FileOutputStream fos = openFileOutput("lFood", MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(readFoodList);
            oos.close();
            fos.close();
        } catch (Exception e) {
        }
    }

    public boolean readFile() {
        try {
            FileInputStream fis = openFileInput("lFood");
            ObjectInputStream ois = new ObjectInputStream(fis);
            readFoodList = (ArrayList<Food>) ois.readObject();
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

    public boolean save() {
        try {
            FileOutputStream fos = openFileOutput("lFood", MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(readFoodList);
            oos.close();
            fos.close();
        } catch (Exception e) {

        }

        Intent intent = new Intent(listActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

        return true;
    }
}
