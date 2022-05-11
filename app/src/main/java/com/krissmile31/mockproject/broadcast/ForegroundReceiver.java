package com.krissmile31.mockproject.broadcast;

import static com.krissmile31.mockproject.utils.Constants.BROADCAST_RECEIVER;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.krissmile31.mockproject.services.PlaySongService;

public class ForegroundReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intentService = new Intent(context, PlaySongService.class);
        intentService.putExtra(BROADCAST_RECEIVER, intent.getIntExtra(BROADCAST_RECEIVER, 0));

//        Log.e("SongReceiver", "onReceive: " + intent.getIntExtra(BROADCAST_RECEIVER, 0) );
        context.startService(intentService);
    }
}
