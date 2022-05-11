package com.krissmile31.mockproject.services.BoundService;

import android.content.ComponentName;
import android.os.IBinder;

import com.krissmile31.mockproject.services.PlaySongService;

public class ServiceConnection {
    public static PlaySongService sPlaySongService;
    public static boolean sIsConnected;

    public static android.content.ServiceConnection serviceConnection = new android.content.ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            PlaySongService.MySongBinder mySongBinder = (PlaySongService.MySongBinder) iBinder;
            sPlaySongService = mySongBinder.getPlaySongService();
            sIsConnected = true;

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            sPlaySongService = null;
            sIsConnected = false;
        }
    };

}
