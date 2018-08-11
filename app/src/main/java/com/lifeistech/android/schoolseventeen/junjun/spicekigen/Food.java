package com.lifeistech.android.schoolseventeen.junjun.spicekigen;
import java.io.Serializable;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by junekelectric on 2017/09/09.
 */

public class Food extends RealmObject implements Serializable{

    //@PrimaryKey
    public int foodid;
    public String content;
    public String date;
    public String title;
    public long deadline;

    public Food () {
    }

    public Food (int foodid, String title, String date, String content, long deadline) {
        this.foodid = foodid;
        this.title = title;
        this.date = date;
        this.content = content;
        this.deadline = deadline;
    }

    public int getFoodid() { return foodid;}
    public void setFoodid(int foodid) { this.foodid = foodid; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public long getDeadline() { return deadline; }
    public void setDeadline(long mdeadline) { this.deadline = mdeadline; }


    @Override
    public String toString() {
        return super.toString() + "[deadline]" + String.valueOf(deadline);
    }
}

