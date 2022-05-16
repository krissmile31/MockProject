package com.krissmile31.mockproject.services.boundservice;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.krissmile31.mockproject.services.PlayService;

public class BoundService {
    public PlayService service;
    public boolean isConnected;

    public ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            PlayService.MySongBinder mySongBinder = (PlayService.MySongBinder) iBinder;
            service = mySongBinder.getPlaySongService();
            isConnected = true;
            service.setSongList();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            service = null;
            isConnected = false;
        }
    };

}
