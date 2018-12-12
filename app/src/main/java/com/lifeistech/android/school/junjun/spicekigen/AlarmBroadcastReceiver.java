package com.lifeistech.android.school.junjun.spicekigen;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;


/***
 *
 * 1. Intent を受け取る(id)
 * 2. Intent を受け取ったら, (ローカルの)通知を送る
 *
 *
 * 参考になったサイト
 * https://tech.pjin.jp/blog/2017/05/31/android_schedule_notification/
 * ・MainActivity の方の処理はこのサイトのまま
 * 　・onRecieve が実行されるところまではこのサイトのままでできる
 * 　・ただ, Notification周りの仕様変更により, AlarmReceiver周りは変更が必要
 *
 *
 * https://developer.android.com/training/notify-user/build-notification#java
 * ・日本語サイトはだめ、古い. 言語設定を English にしなければいけない.
 * ・ここのコードはだいたい 2018/11/2 時点の英語記事のコードのまま.
 *
 * https://qiita.com/kawmra/items/9d80f15ea906f703d0d3
 * ・System UI has stopped のエラー対応
 * 　・Adaptive Icon を使用すると Android 8.0 のバグでシステムUIがクラッシュする
 */

public class AlarmBroadcastReceiver extends BroadcastReceiver {

    public static final String NOTIFICATION_ID = "notificationId";
    public static final String NOTIFICATION_CONTENT = "content";
    private static final String CHANNEL_ID = "sample_notification_channel";
    Context context;

    @Override
    public void onReceive(Context context, Intent intent) {

        this.context = context;
        int id = intent.getIntExtra(NOTIFICATION_ID, 1);
        String content = intent.getStringExtra(NOTIFICATION_CONTENT);
        Log.d("debug", "got notification" + id + " " + content);

        createNotificationChannel();
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(id, buildNotification(context, content));
    }


    public Notification buildNotification(Context context, String content) {
        // Create an explicit intent for an Activity in your app
        //(通知をクリックしたら、どこに飛ぶかということ)

        Intent _intent = new Intent(context, ListActivity.class);
        //Activityを起動させる為。
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0, _intent, PendingIntent.FLAG_UPDATE_CURRENT);
        _intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setContentIntent(pendingIntent); //where its from
        mBuilder.setSmallIcon(android.R.drawable.sym_def_app_icon); // notification icon
        mBuilder.setContentTitle("Notification ! "); // title for notification
        mBuilder.setContentText(content); // message for notification ... the details of the "content" is set in the memoactivity.
        mBuilder.setPriority(NotificationCompat.PRIORITY_DEFAULT); //idk the priority of the notifs
        //TODO したのやつをfalseにしたら、永遠に残る感じの通知になるのか？これをnotificationsettingsでいじりたい
        mBuilder.setAutoCancel(false); // clear notification after click

        return mBuilder.build();
    }


    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+(Android 8.0/Oreo or newer) because the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getString(R.string.channel_name);
            String description = context.getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}

