package com.lifeistech.android.schoolseventeen.junjun.spicekigen;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

import static com.lifeistech.android.schoolseventeen.junjun.spicekigen.R.drawable.add;

public class ListActivity extends AppCompatActivity {
    private final int EDIT_INTENT = 200; //適当な数字。合言葉的な
    ListView list;
    FoodAdapter foodAdapter;
    List<Food> foodList;
    SharedPreferences intentnumber;
    int intentcode;

    Realm realm;
    SharedPreferences backgroundPref;
    int backgroundColor;
    RelativeLayout activityLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        backgroundPref = getSharedPreferences("DataSave", Context.MODE_PRIVATE);
        backgroundColor = backgroundPref.getInt("background", 0);
        activityLayout = (RelativeLayout) findViewById(R.id.activity_list);
        activityLayout.setBackgroundColor(backgroundColor);

        intentnumber = getSharedPreferences("number", Context.MODE_PRIVATE);
        int intentcode = intentnumber.getInt("intentnumber", 0);

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
                AlertDialog.Builder deleteAlertDialog = createDeleteAlertDialog(position);
                deleteAlertDialog.create().show();
                return true;
            }
        });
    }

    private AlertDialog.Builder createDeleteAlertDialog(final int itemPosition) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ListActivity.this);
        alertDialog.setMessage("delete this item?")

                //以下、1つのクラスです (削除しますを押した時の)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position) {
                        Log.d("List", "position=" + position);
                        Log.d("List", "size=" + foodAdapter.getCount());

                        Food delete = foodAdapter.getItem(itemPosition);
                        foodAdapter.remove(delete);
                        list.setAdapter(foodAdapter);

                        foodAdapter.notifyDataSetChanged();
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
    private AlertDialog.Builder createEditAlertDialog(final int itemPosition) {
        //final＝書き換え禁止
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ListActivity.this);

        alertDialog
                .setMessage("edit this item?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    // TODO 編集機能 一旦削除する
                    public void onClick(DialogInterface dialogInterface, int position) {
                        Food edit = (Food) foodAdapter.getItem(itemPosition);

                        intentcode = 1;

                        Intent intentEdit = new Intent(ListActivity.this, MemoActivity.class);
                        intentEdit.putExtra("id_key", edit);
//                        intentEdit.putExtra("position_key", edit.itemPosition);

                        //intenteditには、editの内容を残しつつ、listからは削除
                        foodAdapter.remove(edit);
                        foodList.remove(edit);
                        list.setAdapter(foodAdapter);
                        foodAdapter.notifyDataSetChanged();

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

    public void add(View v) {
        //intentcode = 0;
        Intent intent = new Intent(ListActivity.this, MemoActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void scan (View v){

    }

    public void mainmenu(View v) {
        Intent intent = new Intent(ListActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public List<Food> loadFoodlist(Realm realm) {
        // これは Realm 全く関係ない。
        // ただ、Food クラスのオブジェクトをまとめるところ。
        List<Food> list = new ArrayList<>();

        // Realm
        // 引数として渡された、 realm から、データを引き出す。
        // Realmの読み込み(クエリ)
        RealmQuery<Food> query = realm.where(Food.class);
        // Execute the query:
        RealmResults<Food> result1 = query.findAll();
        //RealmResults <Food> result1 = realm.where(Food.class).findAll();
        //新しい(毎日変わるやつ)differenceはdifferenceっていうlong型変数  でソート
        result1 = result1.sort("deadline");
        //何個のfooodでも同じようにmfoodadapterに追加できる。


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

