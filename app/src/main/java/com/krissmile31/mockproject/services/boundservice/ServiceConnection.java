package com.krissmile31.mockproject.services.boundservice;

import android.content.ComponentName;
import android.os.IBinder;

import com.krissmile31.mockproject.services.PlaySongService;

public class ServiceConnection {
    public static PlaySongService playSongService;
    public static boolean isConnected;

    public static android.content.ServiceConnection serviceConnection = new android.content.ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            PlaySongService.MySongBinder mySongBinder = (PlaySongService.MySongBinder) iBinder;
            playSongService = mySongBinder.getPlaySongService();
            isConnected = true;

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isConnected = false;
        }
    };

}
