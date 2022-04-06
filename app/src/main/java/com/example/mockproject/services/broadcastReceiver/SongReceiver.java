package com.example.mockproject.services.broadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.mockproject.services.PlaySongService;

public class SongReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intentService = new Intent(context, PlaySongService.class);
        intent.putExtra("album_details", intent.getIntExtra("get_song_details", 0));
        context.startService(intentService);
    }
}
