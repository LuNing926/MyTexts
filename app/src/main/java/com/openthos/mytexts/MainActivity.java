package com.openthos.mytexts;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private StatusCheckTask mStatusTask;
    private Timer mTimer;
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilder;
    private NotificationCompat.BigTextStyle mStyle;
    private long time;
    private int tag = -1;

    private Notification.Builder builder;
    private Notification notification;
    private Notification.BigTextStyle style;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StringBuffer s;

        time = System.currentTimeMillis();
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(this);

        builder = new Notification.Builder(this);
        notification = builder.getNotification();
        style = new Notification.BigTextStyle();
        builder.setContentTitle("云服务状态：");
        builder.setSmallIcon(R.mipmap.ic_launcher);
//        builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setAutoCancel(false);
        builder.setWhen(time);
        builder.setOngoing(true);

        mStatusTask = new StatusCheckTask();
        mTimer = new Timer();
        mTimer.schedule(mStatusTask, 0, 3 * 1000);
    }

    class StatusCheckTask extends TimerTask {
        int i = 0;

        @Override
        public void run() {
            Log.d("nnnnnn", "-------" + tag + "-------" + i + "-------" + new Date(System.currentTimeMillis()));
            if (i > 3) {
                mStatusTask.cancel();
                mTimer.cancel();
                mTimer = new Timer();
                mStatusTask = new StatusCheckTask();
                tag = 9;
                mTimer.schedule(mStatusTask, 0, 1000);
            } else {
                i++;
            }
            getNoti(tag + "---" + i + "\n" + tag + "---" +i);
//            showNofication(MainActivity.this, tag + "---" + i + "\n" + tag + "---" +i );
            //Utils.status();
        }
    }

    public void showNofication(Context context, String s) {
        Log.d("mmmmmm", "-------------------------------------------nofication");

        mBuilder.setContentTitle("云服务状态：");
//        String s = "DATA downloading 2/10 4.5M/s" + "\n UserConfig downloading";
//        builder.setContentText(s);
//        builder.setSubText("UserConfig downloading");

        mStyle = new NotificationCompat.BigTextStyle();
        mStyle.bigText(s);
        mBuilder.setStyle(mStyle);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setDefaults(NotificationCompat.DEFAULT_ALL);
        mBuilder.setAutoCancel(false);
        mBuilder.setWhen(time);
        mBuilder.setOngoing(true);
//        mBuilder.setOngoing(true);
//        mBuilder.setWhen(System.currentTimeMillis());
//        mBuilder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
//        Notification notification = mBuilder.build();
//        notification.flags |= Notification.FLAG_FOREGROUND_SERVICE;
//        notification.flags |= Notification.FLAG_ONGOING_EVENT;

//        notification.when = 0;
//        notification.flags |= Notification.FLAG_NO_CLEAR;
//        notification.when = System.currentTimeMillis();
//        notification.when = time;
        mNotificationManager.notify(0, mBuilder.build());
        s.replace("", "");
        // id: notification的唯一标识，cancel时根据id清除
//        manager.cancel(0);
    }

    private void getNoti(String s) {

        style.bigText(s);
        builder.setStyle(style);

        mNotificationManager.notify(1, builder.getNotification());
    }

    private void remoteNoti(String s) {

    }
}
