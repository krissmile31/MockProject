package com.krissmile31.mockproject.broadcast;

import static com.krissmile31.mockproject.utils.Constants.*;
import static com.krissmile31.mockproject.utils.ServiceUtils.*;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.krissmile31.mockproject.MainActivity;
import com.krissmile31.mockproject.models.Song;
import com.krissmile31.mockproject.services.PlaySongService;
import com.krissmile31.mockproject.utils.ServiceUtils;

public class SongReceiver extends BroadcastReceiver {
    private ServiceUtils serviceUtils = new ServiceUtils();

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();

        if (bundle == null)
            return;

        Song song = (Song) intent.getSerializableExtra(SONG_DETAIL);
        boolean isPlaying = intent.getBooleanExtra(IS_PLAYING, false);
        int action = intent.getIntExtra(SONG_STATUS, 0);

        intent.putExtra(SONG_DETAIL, song);
        intent.putExtra(IS_PLAYING, isPlaying);
        intent.putExtra(SONG_STATUS, action);

        context.startActivity(new Intent(context, MainActivity.class));

    }
}
