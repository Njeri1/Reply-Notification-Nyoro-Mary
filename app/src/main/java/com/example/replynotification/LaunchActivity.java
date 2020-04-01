package com.example.replynotification;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.RemoteInput;

import android.app.NotificationManager;
import android.app.PendingIntent;


import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

@SuppressWarnings("ALL")
public class LaunchActivity extends AppCompatActivity {
    private static final String KEY_TEXT_REPLY = "key_text_reply";

    int mRequestCode = 1000;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);


        String replyLabel = getString(R.string.app_name);
        RemoteInput remoteInput = new RemoteInput.Builder(KEY_TEXT_REPLY).setLabel(replyLabel).build();

        Intent resultIntent = new Intent(this, NotificationActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        stackBuilder.addParentStack(NotificationActivity.class);

        stackBuilder.addNextIntent(resultIntent);

        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        NotificationCompat.Action action =
                new NotificationCompat.Action.Builder(R.drawable.ic_stat_social_notifications_on, replyLabel, resultPendingIntent)
                        .addRemoteInput(remoteInput)
                        .setAllowGeneratedReplies(true)
                        .build();




        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .addAction(action)
                        .setAutoCancel(true)
                        .setSmallIcon(R.drawable.ic_stat_social_notifications_on)
                        .setContentTitle("This is a reply Notification")
                        .setContentText("Are you able to reply ?");

        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //Show it
        mNotificationManager.notify(mRequestCode, mBuilder.build());
    }
}
