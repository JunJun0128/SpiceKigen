package com.lifeistech.android.schoolseventeen.junjun.spicekigen;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * Created by junekelectric on 2017/02/24.
 */

//使わない
public class Card extends RealmObject {
    public String titleitem;
    public String dateitem;
    public String contentitem;
//    public long exactdeadlineitem;

    public Card () {
    }

    public Card(String title, String date, String content, long exactdeadlineitem) {
        this.titleitem = title;
        this.dateitem = date;
        this.contentitem = content;
//        this.exactdeadlineitem = exactdeadlineitem;
    }

        public String getTitle() {
            return titleitem;
        }

        public void setTitle(String title) {
            this.titleitem = title;
        }

        public String getDate() {
            return dateitem;
        }

        public void setDate(String date){
            this.dateitem = date;
        }

        public String getContent() {
            return contentitem;
        }

        public void setContent(String content) {
            this.contentitem = content;
        }

//        public long getExactdeadline() {
//            return exactdeadlineitem;
//        }
//        public void setExactdeadline(long exactdeadline) {
//            this.exactdeadlineitem = exactdeadline;
//        }
}
