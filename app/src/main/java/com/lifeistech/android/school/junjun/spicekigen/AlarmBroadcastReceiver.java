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
        import android.util.Log;
        import android.widget.Toast;

        import com.google.android.gms.games.internal.constants.NotificationChannel;

        import java.text.SimpleDateFormat;
        import java.util.Locale;

public class AlarmBroadcastReceiver extends BroadcastReceiver{
    public static String NOTIFICATION_ID = "notificationId";
    public static String NOTIFICATION_CONTENT = "content";

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        int id = intent.getIntExtra(NOTIFICATION_ID, 0);
        String content = intent.getStringExtra(NOTIFICATION_CONTENT);
        notificationManager.notify(id, buildNotification(context, content));
    }

    private Notification buildNotification(Context context, String content) {
        Notification.Builder builder = new Notification.Builder(context);
        builder.setContentTitle("Notification!!")
                .setContentText(content)
                .setSmallIcon(R.drawable.notiicon);

        return builder.build();
    }

//    @Override   // データを受信した
//    public void onReceive(Context context, Intent intent) {
//        Toast.makeText(context, "Received ", Toast.LENGTH_LONG).show();
//
//        Log.d("AlarmBroadcastReceiver","onReceive() pid=" + android.os.Process.myPid());
//
//        int requestCode = intent.getIntExtra("RequestCode",0);
//
//        PendingIntent pendingIntent =
//                PendingIntent.getActivity(context, requestCode, intent,
//                        PendingIntent.FLAG_UPDATE_CURRENT);
//
//        String channelId = "default";
//        // app name
//        String title = context.getString(R.string.app_name);
//
//        long currentTime = System.currentTimeMillis();
//        SimpleDateFormat dataFormat =
//                new SimpleDateFormat("HH:mm:ss", Locale.JAPAN);
//        //TODO 上のlocaleで、端末の位置情報を抜き出せるといい。
//        String cTime = dataFormat.format(currentTime);
//
//        // メッセージ　+ 時刻
//        String message = "Expired"+cTime ;
//
//        if (Build.VERSION.SDK_INT < 26) {
//            return;
//        }
//
//        NotificationManager notificationManager =
//                (NotificationManager)context.getSystemService(
//                        Context.NOTIFICATION_SERVICE);
//
//        Uri defaultSoundUri = RingtoneManager.getDefaultUri(
//                RingtoneManager.TYPE_NOTIFICATION);
//
//        // Notification　Channel 設定
//        NotificationChannel channel = new NotificationChannel(
//                // アプリでユニークな ID
//                "channel_notification",
//                // ユーザが「設定」アプリで見ることになるチャンネル名
//                "tt",
//                // チャンネルの重要度（0 ~ 4）
//                NotificationManager.IMPORTANCE_DEFAULT
//        );
//
//        channel.setDescription(message);
//        channel.enableVibration(true);
//        channel.canShowBadge();
//        channel.enableLights(true);
//        channel.setLightColor(Color.BLUE);
//        // the channel appears on the lockscreen
//        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
//        channel.setSound(defaultSoundUri, null);
//        channel.setShowBadge(true);
//
//        if(notificationManager != null){
//            notificationManager.createNotificationChannel(channel);
//
//            Notification notification = new Notification.Builder(context,channelId)
//                    .setContentTitle(title)
//                    // android標準アイコンから
//                    .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
//                    .setContentText(message)
//                    .setAutoCancel(true)
//                    .setContentIntent(pendingIntent)
//                    .setWhen(System.currentTimeMillis())
//                    .build();
//
//            // 通知
//            notificationManager.notify(R.string.app_name, notification);
//
//        }
    }

