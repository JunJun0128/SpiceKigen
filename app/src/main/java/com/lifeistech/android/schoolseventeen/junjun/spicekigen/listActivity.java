package com.lifeistech.android.schoolseventeen.junjun.spicekigen;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
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

public class ListActivity extends AppCompatActivity {
    ListView list;
    FoodAdapter FoodAdapter;
    List<Food> foodList;

    Realm realm;
    SharedPreferences backgroundPref;
    int backgroundColor;
    RelativeLayout activityLayout;

//    /**
//     * ATTENTION: This was auto-generated to implement the App Indexing API.
//     * See https://g.co/AppIndexing/AndroidStudio for more information.
//     */
//    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        backgroundPref = getSharedPreferences("DataSave", Context.MODE_PRIVATE);
        backgroundColor = backgroundPref.getInt("background", 0);
        activityLayout = (RelativeLayout) findViewById(R.id.activity_list);
        activityLayout.setBackgroundColor(backgroundColor);

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        foodList = loadFoodlist(realm);
        FoodAdapter = new FoodAdapter(this, R.layout.item, foodList);
        readFile();

        list = (ListView) findViewById(R.id.list);
        list.setAdapter(FoodAdapter);

        //itemのクリック・ロングクリックで実装されるメソッドを実行する。
        // 詳細はしたのmethodに書いてある。
        // この中には本来メソドをかけないからこういう風に省略している
        initOnClickFunction();
    }


    private void initOnClickFunction() {
        // set on click
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, View view, final int i, long l) {
                AlertDialog.Builder editAlertDialog = createEditAlertDialog(i);

                //TODO そのあとの処理は普通の登録と同じ？

                // TODO 始めは登録していた時の情報が入ったまま(auto)、クリックで今まで同様に変更できる。

                // TODO Save ボタン　→ 上書き そもそもどこに書くの。saveしたら上書きできるのか
                // TODO <Food>の上書き
                editAlertDialog.create().show();
            }

        });

        // set on long click
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(final AdapterView<?> adapterView, View view,
                                           final int position, long l) {
                AlertDialog.Builder deleteAlertDialog = createDeleteAlertDialog();
                deleteAlertDialog.create().show();
                return false;
            }
        });
    }

    private AlertDialog.Builder createDeleteAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ListActivity.this);
        alertDialog.setMessage("delete this item?")

                //1つのクラス (削除しますを押した時の)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position) {
                        Food delete = FoodAdapter.getItem(position);
                        FoodAdapter.remove(delete);
                        list.setAdapter(FoodAdapter);

                        FoodAdapter.notifyDataSetChanged();
                    }
                })

                //1つのクラス(削除キャンセルを押した時の)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(ListActivity.this, ListActivity.class);
                    }
                });
        return alertDialog;
    }

    //引数 int iを受け渡してる
    private AlertDialog.Builder createEditAlertDialog(final int i) {
        //final＝書き換え禁止
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ListActivity.this);

        alertDialog
                .setMessage("edit this item?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    // TODO 編集機能 一旦削除する
                    public void onClick(DialogInterface dialogInterface, int position) {
                        Food edit = (Food) FoodAdapter.getItem(i);
                        FoodAdapter.remove(edit);
                        foodList.remove(i);
                        list.setAdapter(FoodAdapter);

                        FoodAdapter.notifyDataSetChanged();
                    }

                    Intent intentEdit = new Intent(ListActivity.this, MemoActivity.class);
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(ListActivity.this, ListActivity.class);
                    }
                });
        return alertDialog;
    }

    public void mainmenu(View v) {
        Intent intent = new Intent(ListActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


//    public void delete(View v) {
//        //TODO クリックしたらチェックボックスが各foodに現れる　選んでOKか何かを押して消す。
//
//        //TODO 削除処理
//
//        //TODO 各listのitemごとにチェックボタンを生えさせる
//
//        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ListActivity.this);
//        alertDialog.setMessage("delete" + +"items?")
//
//                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    //TODO クリックしたら項目削除
//                    public void onClick(DialogInterface dialogInterface, int position) {
//                        Food delete = FoodAdapter.getItem(position);
//                        FoodAdapter.remove(delete);
//                        list.setAdapter(FoodAdapter);
//
//                        FoodAdapter.notifyDataSetChanged();
//                    }
//                })
//                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                    }
//                });
//        alertDialog.create().show();
//        return false;
//
//        //画面上でショートログ通知
//        Snackbar.make(v, "Deleted", Snackbar.LENGTH_SHORT)
//                .setAction("Action", null).show();
//    }


    public List<Food> loadFoodlist(Realm realm) {
        // これは Realm 全く関係ない。
        // ただ、Food クラスのオブジェクトをまとめるところ。
        List<Food> list = new ArrayList<>();

        // ここから、Realm を使う。
        // 引数として渡された、 realm から、データを引き出す。
        // Realmの読み込み(クエリ)
        RealmQuery<Food> query = realm.where(Food.class);
        // Execute the query:
        RealmResults<Food> result1 = query.findAll();
        //RealmResults <Food> result1 = realm.where(Food.class).findAll();
        //新しい(毎日変わるやつ)differenceはdifferenceっていうlong型変数  でソート
        result1 = result1.sort("deadline");
        //何個のfooodでも同じようにmfoodadapterに追加できる。
        for (int foood = 0; foood < result1.size(); foood++) {
            Food value = new Food();
            value.setTitle(result1.get(foood).getTitle());
            value.setDate(result1.get(foood).getDate());
            value.setContent(result1.get(foood).getContent());
            value.setDeadline(result1.get(foood).getDeadline());

            list.add(value);
        }
        return list;
    }

    //これはgoogleによると端末のホームボタンを押すとそうなるみたい　いらなくね
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

    //したもいらなくね
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

}

