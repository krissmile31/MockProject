package com.krissmile31.mockproject.services.broadcast;

import static com.krissmile31.mockproject.utils.Constants.BROADCAST_RECEIVER;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.krissmile31.mockproject.services.PlayService;

public class SongReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intentService = new Intent(context, PlayService.class);
        intentService.putExtra(BROADCAST_RECEIVER, intent.getIntExtra(BROADCAST_RECEIVER, 0));

//        Log.e("SongReceiver", "onReceive: " + intent.getIntExtra(BROADCAST_RECEIVER, 0) );
        context.startService(intentService);
    }
}
