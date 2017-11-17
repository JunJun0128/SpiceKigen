package com.lifeistech.android.schoolseventeen.junjun.spicekigen;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

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
    //ListView mListView;
    foodAdapter mFoodAdapter;
//    ArrayList<Card> foodList;
//    List<Card> readFoodList;
    ArrayList<Food> FoodList;
    List<Food> readFoodList;
    //List<Card> saveList;
//    Card addCard;
    Food addFood;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        ImageButton fab = (ImageButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                save();
            }
        });

        ImageButton DeleteButton = (ImageButton)findViewById(R.id.DeleteButton);
        DeleteButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                //削除処理
                //Snackbarで通知
                Snackbar.make(v, "削除しました", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Realm.init(this);
        realm = Realm.getDefaultInstance();
        // Build the query looking at all users:
        RealmQuery<Food> query = realm.where(Food.class);
        // Execute the query:
        RealmResults<Food>foodsss = query.findAll();

        readFoodList = new ArrayList<>();
        readFile();

        //foodList = new ArrayList<>();

        //prefについて
        //pref = getSharedPreferences("pref_memo", MODE_PRIVATE);
        //foodList.add(new Card(getString("key_title")), getString("key_date"), getString("key_content"))));
        //foodList.add(new Card(getString(R.id.titleTextView)), getString(dateTextView), getString(contentTextView))));
        //foodList.add(new Card(pref.getString("key_title", ""), pref.getString("key_date", ""), pref.getString("key_content","")));

        mFoodAdapter = new foodAdapter(this, R.layout.item, readFoodList);
        Food food = new Food("gao", "171225", "will die", 88);
        mFoodAdapter.add(food);
        list = (ListView)findViewById(R.id.list);
        list.setAdapter(mFoodAdapter);
        //AlertDialog
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> adapterView, View view, final int i, long l) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(listActivity.this);
                alertDialog.setMessage("Delete this item?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int position) {
                                Food delete = (Food) mFoodAdapter.getItem(i);
                                mFoodAdapter.remove(delete);
                                readFoodList.remove(delete);
                            }
                        })
                        .setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                alertDialog.create().show();
            }
        });
    }
    //ここのかっこは　protected void onCreate(Bundle savedInstanceState)

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
