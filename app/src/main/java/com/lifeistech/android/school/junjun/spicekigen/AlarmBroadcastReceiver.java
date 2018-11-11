package com.lifeistech.android.school.junjun.spicekigen;
        import android.app.Notification;
        import android.app.NotificationManager;
        import android.app.PendingIntent;
        import android.content.BroadcastReceiver;
        import android.content.Context;
        import android.content.Intent;
        import android.graphics.Color;
        import android.media.RingtoneManager;
        import android.net.Uri;
        import android.os.Build;
        import android.support.v4.app.NotificationCompat;
        import android.util.Log;
        import android.widget.Toast;

        import com.google.android.gms.games.internal.constants.NotificationChannel;

        import java.text.SimpleDateFormat;
        import java.util.Locale;


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
 * ・日本語サイトはだめ. 古い. 言語設定を English にしなければいけない.
 * ・ここのコードはだいたい 2018/11/2 時点の英語記事のコードのまま.
 *
 * https://qiita.com/kawmra/items/9d80f15ea906f703d0d3
 * ・System UI has stopped のエラー対応
 * 　・Adaptive Icon を使用すると Android 8.0 のバグでシステムUIがクラッシュする
 */

public class AlarmBroadcastReceiver extends BroadcastReceiver{
    public static String NOTIFICATION_ID = "notificationId";
    public static String NOTIFICATION_CONTENT = "content";
    private static final String CHANNEL_ID = "sample_notification_channel";
    Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        int id = intent.getIntExtra(NOTIFICATION_ID, 0);
        String content = intent.getStringExtra(NOTIFICATION_CONTENT);
        notificationManager.notify(id, buildNotification(context, content));
    }

    private Notification buildNotification(Context context, String content) {
        //どこからintentがきましたか？？
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        Notification.Builder builder = new Notification.Builder(context);
        //                .setSmallIcon(R.mipmap.ic_launcher)
// Adaptive icon を使うと 8.0 で System UI がクラッシュする
        //以下はnotificationの部分のdesign,like an xml!
        builder.setContentTitle("Notification!!")
                .setContentText(content)
                .setSmallIcon(R.drawable.notiicon)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        return builder.build();
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getString(R.string.channel_name);
            String description = context.getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            android.app.NotificationChannel channel = new android.app.NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    }

