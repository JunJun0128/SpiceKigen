package com.lifeistech.android.schoolseventeen.junjun.spicekigen;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by junekelectric on 2017/09/09.
 */

public class Food extends RealmObject {

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

    public int getMfoodid() { return foodid;}
    public void setMfoodid(int foodid) { this.foodid = foodid; }
    public String getMtitle() { return title; }
    public void   setMtitle(String title) { this.title = title; }
    public String getMdate() { return date; }
    public void   setMdate(String date) { this.date = date; }
    public String getMcontent() { return content; }
    public void   setMcontent(String content) { this.content = content; }
    public long getMdeadline() { return deadline; }
    public void setMdeadline(long mdeadline) { this.deadline = mdeadline; }


    @Override
    public String toString() {
        return super.toString() + "[deadline]" + String.valueOf(deadline);
    }
}

