package com.lifeistech.android.schoolseventeen.junjun.spicekigen;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;

/**
 * Created by junekelectric on 2017/09/09.
 */

public class Food extends RealmObject {
    public int foodid;
    public String mcontent;
    //public RealmList<Card> cardRealmList;
    public String mtitle;
    public String mdate;
    public long mdiffday;
    public long mexactdeadline;

    public Food () {

    }
    public Food (String title, String date, String content, long mexactdeadline) {
        this.mtitle = title;
        this.mdate = date;
        this.mcontent = content;
        //this.mdiffday = diffday;
        this.mexactdeadline = exactdeadline;
    }

    public int getFoodid() { return foodid;}
    public void setFoodid(int foodid) {this.foodid = foodid; }

    public String getMtitle() { return mtitle; }
    public void   setMtitle(String mtitle) { this.mtitle = mtitle; }
    public String getMdate() { return mdate; }
    public void   setMdate(String mdate) { this.mdate = mdate; }
    public String getMcontent() { return mcontent; }
    public void   setMcontent(String mcontent) { this.mcontent = mcontent; }
//    public long getMdiff() {
//        return mdiffday;
//    }
//    public void setMdiff(long mdiffday) {
//        this.mdiffday = mdiffday;
//    }
    public long getMexactdeadline() {
        return mexactdeadline;
    }
    public void setMexactdeadline() {
        this.mexactdeadline = mexactdeadline;
    }
}

