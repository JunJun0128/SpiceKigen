package com.lifeistech.android.schoolseventeen.junjun.spicekigen;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

import static com.lifeistech.android.schoolseventeen.junjun.spicekigen.R.layout.item;

/**
 * Created by junekelectric on 2017/01/27.
 */

//realm継承中
public class foodAdapter extends ArrayAdapter<Food> {
    List<Food> FoodList;
    private LayoutInflater inflater;
    int position = 1;
    String countdownString;
    Realm realm;

    public foodAdapter (Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        FoodList = new ArrayList<Food>();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount () {
        return FoodList.size();
    }
    @Override
    public Food getItem (int position) {
        return FoodList.get(position);
    }
    @Override
    public void add (Food position) {
        FoodList.add(position);
    }
    public void remove (Food position) {
        FoodList.remove(position);
    }

    private class ViewHolder {
        //継承前のitem.xmlの中身を書きます
        //get instance
        TextView titleTv;
        TextView daysTv;
        TextView diffTv;
        TextView contentTv;
    }

    //各Foodのexactdeadlineも保存し、diffというtextviewに出す。diffitem,difftvもそう。
    @Override
    public View getView (final int position, View convertView, ViewGroup parent){
        final ViewHolder viewHolder;

        if (convertView == null) {
            inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = LayoutInflater.from(getContext()).inflate(item, null);

            TextView titleitem = (TextView) convertView.findViewById(R.id.titleitem);
            TextView dateitem = (TextView) convertView.findViewById(R.id.dateitem);
            TextView contentitem = (TextView) convertView.findViewById(R.id.contentitem);
            TextView diffitem = (TextView) convertView.findViewById(R.id.diff);

            viewHolder = new ViewHolder();
            viewHolder.titleTv = titleitem;
            viewHolder.daysTv = dateitem;
            viewHolder.contentTv = contentitem;
            viewHolder.diffTv = diffitem;

            convertView.setTag(viewHolder);
            //ここでtagを設定しないと落ちる
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //Realmの宣言
        realm = Realm.getDefaultInstance();
        // Realmの読み込み(クエリ)
        RealmQuery<Food> query = realm.where(Food.class);
        // Execute the query:
        RealmResults<Food> result1 = query.findAll();
        final Food fooditem = getItem(position);
        // Log.d("Adapter", "check: " + fooditem.toString());
        long currentTimeMillis = System.currentTimeMillis();

        long countDownLong = fooditem.getMdeadline() - currentTimeMillis;


        countDownLong = countDownLong / 1000;
        countDownLong = countDownLong / 60;
        countDownLong = countDownLong / 60;
        countDownLong = countDownLong / 24;

        String countdownString = (String.valueOf(countDownLong));

        if (fooditem != null){
            viewHolder.titleTv.setText(fooditem.getMtitle());
            viewHolder.daysTv.setText(fooditem.getMdate());
            viewHolder.contentTv.setText(fooditem.getMcontent());
            viewHolder.diffTv.setText(countdownString);
        }
        return convertView;
    }
}