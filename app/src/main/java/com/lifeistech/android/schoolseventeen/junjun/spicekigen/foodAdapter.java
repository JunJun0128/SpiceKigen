package com.lifeistech.android.schoolseventeen.junjun.spicekigen;

import android.content.Context;
import android.support.annotation.NonNull;
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

//import static android.R.id.content;
//import static android.R.id.title;
//import static android.R.id.days;
//import static android.R.id.diff;
//import static com.example.junekelectric.shoumikigenlist.R.id.content;
//import static com.example.junekelectric.shoumikigenlist.R.id.date;
//import static com.example.junekelectric.shoumikigenlist.R.id.title;
//import static com.example.junekelectric.shoumikigenlist.R.id.diff;

/**
 * Created by junekelectric on 2017/01/27.
 */

public class foodAdapter extends ArrayAdapter<Food> {
    //Context context;
    //List<Card> foodList;
    List<Food> FoodList;
    private LayoutInflater inflater;

//    public foodAdapter (Context context, int textViewResourceId, List<Card> cList) {
//        super(context, textViewResourceId, cList);
//        foodList = cList;
//        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        this.foodList = foodList;
//    }

    public foodAdapter (Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        FoodList = new ArrayList<Food>();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

//    public foodAdapter (Context context, int layoutResourceId, List<Card> objects) {
//        super(context, layoutResourceId, objects);
//        foodList = objects;
//        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        this.foodList = foodList;
//    }

//        @Override
//        public int getCount () {
//            return foodList.size();
//        }
//
//        @Override
//        public Card getItem (int position) {
//            return foodList.get(position);
//        }

    @Override
    public int getCount () {
        return FoodList.size();
    }

    @Override
    public Food getItem (int position) {
        return FoodList.get(position);
    }

//        @Override
//        public long getFoodId(int position){
//            return 0;}

    @Override
    public void add (Food position) {
        FoodList.add(position);
    }


    public void delete (Food position) {
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

    @Override
    public View getView (final int position, View convertView, ViewGroup parent){
        final ViewHolder viewHolder;

        if (convertView == null) {
            inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, null);

            TextView titleitem = (TextView) convertView.findViewById(R.id.titleitem);
            TextView dateitem = (TextView) convertView.findViewById(R.id.dateitem);
            TextView diffitem = (TextView) convertView.findViewById(R.id.diff);
            TextView contentitem = (TextView) convertView.findViewById(R.id.contentitem);

            viewHolder = new ViewHolder();
            viewHolder.titleTv = titleitem;
            viewHolder.daysTv = dateitem;
            viewHolder.diffTv = diffitem;
            viewHolder.contentTv = contentitem;

            convertView.setTag(viewHolder);
            //ここでtagを設定しないと落ちる　by単語帳教科書
        } else {
            viewHolder = (ViewHolder) convertView.getTag();

            //以下はいらない
//                TextView title = (TextView) convertView.findViewById(R.id.title);
//                title.setText(listActivity.get(position).getTitle);
//
//                TextView date = (TextView) convertView.findViewById(R.id.date);
//                date.setText(listActivity.get(position).getDate);
//
//                TextView content = (TextView) convertView.findViewById(R.id.content);
//                content.setText(listActivity.get(position).getContent);
        }

//        final Card item = getItem(position);
        final Food item = getItem(position);

        if (item != null){
            //set data
//            viewHolder.titleTv.setText(item.getTitle());
//            viewHolder.contentTv.setText(item.getContent());
//            viewHolder.daysTv.setText(item.getContent());
//            viewHolder.diffTv.setText(String.valueOf(item.getDiffday()));

            viewHolder.titleTv.setText(item.getMtitle());
            viewHolder.daysTv.setText(item.getMdate());
            viewHolder.diffTv.setText(String.valueOf(item.getMdiff()));
            viewHolder.contentTv.setText(item.getMcontent());
        }
        return convertView;
    }
}