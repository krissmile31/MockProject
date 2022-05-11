package com.krissmile31.mockproject.broadcast;

import static com.krissmile31.mockproject.utils.Constants.*;
import static com.krissmile31.mockproject.utils.ServiceUtils.EXIT;
import static com.krissmile31.mockproject.utils.ServiceUtils.NEXT;
import static com.krissmile31.mockproject.utils.ServiceUtils.PAUSE;
import static com.krissmile31.mockproject.utils.ServiceUtils.PREVIOUS;
import static com.krissmile31.mockproject.utils.ServiceUtils.RESUME;
import static com.krissmile31.mockproject.utils.ServiceUtils.initMediaPlayer;
import static com.krissmile31.mockproject.utils.ServiceUtils.isInForeground;
import static com.krissmile31.mockproject.utils.ServiceUtils.nextMusic;
import static com.krissmile31.mockproject.utils.ServiceUtils.pauseMusic;
import static com.krissmile31.mockproject.utils.ServiceUtils.preMusic;
import static com.krissmile31.mockproject.utils.ServiceUtils.releaseMusic;
import static com.krissmile31.mockproject.utils.ServiceUtils.resumeMusic;
import static com.krissmile31.mockproject.utils.ServiceUtils.sCurrentSong;
import static com.krissmile31.mockproject.utils.ServiceUtils.sSongPlaying;
import static com.krissmile31.mockproject.utils.ServiceUtils.sendActionMediaPlayer;
import static com.krissmile31.mockproject.utils.SongUtils.setIconStatusAll;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.krissmile31.mockproject.models.Song;
import com.krissmile31.mockproject.services.PlaySongService;

public class SongReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();

        if (bundle == null)
            return;

        sCurrentSong = (Song) intent.getSerializableExtra(SONG_DETAIL);
        sSongPlaying = intent.getBooleanExtra(IS_PLAYING, false);
        int action = intent.getIntExtra(SONG_STATUS, 0);
        handleActionControlSong(context, action);

    }

    private void handleActionControlSong(Context context, int action) {
        switch (action) {
            case PAUSE:
                pauseMusic(context);
//                sendNotification(sCurrentSong());
                break;

            case RESUME:
                resumeMusic(context);
//                sendNotification(sCurrentSong());
                break;

            case EXIT:
                isInForeground = false;
//                stopForeground(true);
                releaseMusic();
                initMediaPlayer(sCurrentSong(), context);
                setIconStatusAll();
//                sendActionMediaPlayer(getEXIT);

//                stopSelf();
                break;

            case PREVIOUS:
                preMusic(context);
//                sendNotification(sCurrentSong());
                break;

            case NEXT:
                nextMusic(context);
//                sendNotification(sCurrentSong());
                break;
        }
    }
}
