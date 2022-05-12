package com.krissmile31.mockproject.utils;

import static com.krissmile31.mockproject.services.BoundService.ServiceConnection.*;
import static com.krissmile31.mockproject.utils.Constants.*;
import static com.krissmile31.mockproject.utils.SongUtils.*;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.krissmile31.mockproject.models.Song;
import com.krissmile31.mockproject.services.PlaySongService;

import java.io.IOException;

public class ServiceUtils implements MediaPlayer.OnCompletionListener {
    public static boolean sIsPlaying;
    public static int sCurrentSongIndex;
    public static MediaPlayer sMediaPlayer;
    public boolean isInForeground = true;

    private final int PAUSE = 1;
    private final int RESUME = 2;
    private final int EXIT = 3;
    private final int PREVIOUS = 4;
    private final int NEXT = 5;

    public Song sCurrentSong() {
        return getCurrentSong(sCurrentSongIndex);
    }

    public void pauseMusic() {
        if (sMediaPlayer != null && sIsPlaying) {
            sMediaPlayer.pause();
            sIsPlaying = false;
            sendActionMediaPlayer(sPlaySongService.getApplicationContext(), PAUSE);
            sPlaySongService.sendNotification(sCurrentSong());

        }
    }

    public void resumeMusic() {
        if (sMediaPlayer != null && !sIsPlaying) {
            sMediaPlayer.start();
            sIsPlaying = true;
            sendActionMediaPlayer(sPlaySongService.getApplicationContext(), RESUME);
            sPlaySongService.sendNotification(sCurrentSong());

        }
    }

//    public void resumeMusic(Context context) {
//        if (sMediaPlayer != null && !sSongPlaying) {
//            sMediaPlayer.start();
//            sSongPlaying = true;
//        }
//        if (isInForeground) {
//            context.startService(new Intent(context, PlaySongService.class));
//        }
//    }

    public void releaseMusic() {
        if (sMediaPlayer != null) {
            sMediaPlayer.release();
            sMediaPlayer = null;
            sIsPlaying = false;
        }
    }

    public void initMediaPlayer(Song song, Context context) {
        if (sMediaPlayer == null) {
            sMediaPlayer = new MediaPlayer();
        }
        try {
            sMediaPlayer.setDataSource(context, Uri.parse(song.getData()));
        } catch (IllegalArgumentException | IllegalStateException | IOException e) {
            e.printStackTrace();
        }

    }

    public void playMusic(Song song, Context context) {
        try {
            sMediaPlayer.reset();
            sMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            sMediaPlayer.setDataSource(context, Uri.parse(song.getData()));
            sMediaPlayer.prepare();
            sMediaPlayer.start();

        } catch (IllegalArgumentException | IllegalStateException | IOException e) {
            e.printStackTrace();
        }

        Log.e("TAG", "playMusic: " + sMediaPlayer.getDuration());

//        sendActionMediaPlayer(context, PLAY);
    }

    public Song getCurrentSong(int position) {
        return sSongList.get(position);
    }
    public void playCurrentSong(int position, Context context) {
        Song song = getCurrentSong(position);
        playMusic(song, context);
        sIsPlaying = true;
        sPlaySongService.sendNotification(sCurrentSong());
//        getDataSongPlaying();
//        setData();

    }

    public void preMusic(Context context) {
        if (sCurrentSongIndex > 0)
            sCurrentSongIndex--;
        else
            sCurrentSongIndex = sSongList.size() - 1;

        playCurrentSong(sCurrentSongIndex, context);
        sendActionMediaPlayer(sPlaySongService.getApplicationContext(), PREVIOUS);

    }

    public void nextMusic(Context context) {
        if (sCurrentSongIndex < sSongList.size() - 1)
            sCurrentSongIndex++;
        else
            sCurrentSongIndex = 0;

        playCurrentSong(sCurrentSongIndex, context);
        sendActionMediaPlayer(sPlaySongService.getApplicationContext(), NEXT);

    }

    // running out a song -> run next song
    public void onSongCompletion(Context context) {
        sMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if (sCurrentSongIndex < sSongList.size() - 1) {
                    sCurrentSongIndex++;

                } else {
                    sCurrentSongIndex = 0;
                }
                playMusic(sSongList.get(sCurrentSongIndex), context);
            }
        });
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {

    }

    public String getSongDuration(long milliseconds) {
        long secondsConvert = milliseconds/1000;
        int hours = (int) secondsConvert/3600;
        int minutes = (int) (secondsConvert % 3600) / 60;
        int seconds = (int) (secondsConvert % 3600) % 60;

        String duration = "";

//        if (hours > 0) {
//            if (mi)
//        }

        if (seconds > 10)
            return duration + minutes + ":" + seconds;
        else
            return duration + minutes + ":" + "0" + seconds;

    }

    public String getCurrentDuration() {
        return getSongDuration(getCurrentPosition());
    }

    public String getTotalDuration() {
        return getSongDuration(getTotalTime());
    }

    public float getProgress() {
        return ((float)getCurrentPosition()/getTotalTime()) * 100;
    }

    public int getCurrentPosition() {
        return sMediaPlayer.getCurrentPosition();
    }

    public int getTotalTime() {
        return sMediaPlayer.getDuration();
    }

    public void startMusicPlayerService(Context context) {
//        Song song = sSongList.get(sCurrentSongIndex);
        Intent intent = new Intent(context, PlaySongService.class);
        intent.putExtra(SONG_DETAIL, sCurrentSong());

        // started
        context.startService(intent);

        // bound service
        context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        ContextCompat.startForegroundService(context, intent);
    }
    
    public void checkExistForeground(Context context) {
        if (!isInForeground) {
            startMusicPlayerService(context);
        }
    }

    public void sendActionMediaPlayer(Context context, int action) {
        Intent intent = new Intent(BROADCAST_RECEIVER);
        intent.putExtra(SONG_DETAIL, sCurrentSong());
        intent.putExtra(IS_PLAYING, sIsPlaying);
        intent.putExtra(SONG_STATUS, action);

        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
}
