package com.example.mockproject.services;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class MySong extends Application {
    public final static String CHANNEL_ID = "my_song";

    @Override
    public void onCreate() {
        super.onCreate();
        onNotify();
    }

    private void onNotify() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "all_song", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setSound(null, null);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null)
                notificationManager.createNotificationChannel(notificationChannel);
        }
    }
}
