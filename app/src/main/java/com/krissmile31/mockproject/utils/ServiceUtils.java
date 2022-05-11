package com.krissmile31.mockproject.utils;

import static com.krissmile31.mockproject.MainActivity.setData;
import static com.krissmile31.mockproject.nowplaying.NowPlayingFragment.displaySongNowPlaying;
import static com.krissmile31.mockproject.nowplaying.NowPlayingFragment.getDataSongPlaying;
import static com.krissmile31.mockproject.services.BoundService.ServiceConnection.sPlaySongService;
import static com.krissmile31.mockproject.services.BoundService.ServiceConnection.serviceConnection;
import static com.krissmile31.mockproject.utils.Constants.BROADCAST_RECEIVER;
import static com.krissmile31.mockproject.utils.Constants.IS_PLAYING;
import static com.krissmile31.mockproject.utils.Constants.SONG_DETAIL;
import static com.krissmile31.mockproject.utils.Constants.SONG_STATUS;
import static com.krissmile31.mockproject.utils.SongUtils.*;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;

import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.krissmile31.mockproject.models.Song;
import com.krissmile31.mockproject.services.PlaySongService;

import java.io.IOException;

public class ServiceUtils implements MediaPlayer.OnCompletionListener {
    public static boolean sSongPlaying;
    public static int sCurrentSongIndex;
    public static MediaPlayer sMediaPlayer;
    public static boolean isInForeground = true;
    public static Song sCurrentSong;

    public static final int PLAY = 1;
    public static final int PAUSE = 2;
    public static final int RESUME = 3;
    public static final int PREVIOUS = 4;
    public static final int NEXT = 5;
    public static final int EXIT = 6;

    public static Song sCurrentSong() {
        return getCurrentSong(sCurrentSongIndex);
    }

    public static void pauseMusic(Context context) {
        if (sMediaPlayer != null && sSongPlaying) {
            sMediaPlayer.pause();
            sSongPlaying = false;
            setIconStatusAll();
            sendActionMediaPlayer(context, PAUSE);
            PlaySongService playSongService = new PlaySongService();
            sPlaySongService.sendNotification(sCurrentSong());

        }
    }

    public static void resumeMusic(Context context) {
        if (sMediaPlayer != null && !sSongPlaying) {
            sMediaPlayer.start();
            sSongPlaying = true;
            setIconStatusAll();
            sendActionMediaPlayer(context, RESUME);
            sPlaySongService.sendNotification(sCurrentSong());

        }
    }

//    public static void resumeMusic(Context context) {
//        if (sMediaPlayer != null && !sSongPlaying) {
//            sMediaPlayer.start();
//            sSongPlaying = true;
//        }
//        if (isInForeground) {
//            context.startService(new Intent(context, PlaySongService.class));
//        }
//    }

    public static void releaseMusic() {
        if (sMediaPlayer != null) {
            sMediaPlayer.release();
            sMediaPlayer = null;
            sSongPlaying = false;
        }
    }

    public static void initMediaPlayer(Song song, Context context) {
        if (sMediaPlayer == null) {
            sMediaPlayer = new MediaPlayer();
        }
        try {
            sMediaPlayer.setDataSource(context, Uri.parse(song.getData()));
        } catch (IllegalArgumentException | IllegalStateException | IOException e) {
            e.printStackTrace();
        }

    }

    public static void playMusic(Song song, Context context) {
        try {
            sMediaPlayer.reset();
            sMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            sMediaPlayer.setDataSource(context, Uri.parse(song.getData()));
            sMediaPlayer.prepare();
            sMediaPlayer.start();

        } catch (IllegalArgumentException | IllegalStateException | IOException e) {
            e.printStackTrace();
        }

        sendActionMediaPlayer(context, PLAY);
    }

    public static Song getCurrentSong(int position) {
        return sSongList.get(position);
    }
    public static void playCurrentSong(int position, Context context) {
        Song song = getCurrentSong(position);
        playMusic(song, context);
        sSongPlaying = true;
        sPlaySongService.sendNotification(sCurrentSong());
        getDataSongPlaying();
        setData();

    }

    public static void preMusic(Context context) {
        if (sCurrentSongIndex > 0)
            sCurrentSongIndex--;
        else
            sCurrentSongIndex = sSongList.size() - 1;

        playCurrentSong(sCurrentSongIndex, context);
    }

    public static void nextMusic(Context context) {
        if (sCurrentSongIndex < sSongList.size() - 1)
            sCurrentSongIndex++;
        else
            sCurrentSongIndex = 0;

        playCurrentSong(sCurrentSongIndex, context);
    }

    // running out a song -> run next song
    public static void onSongCompletion(Context context) {
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

    public static void startMusicPlayerService(Context context) {
//        Song song = sSongList.get(sCurrentSongIndex);
        Intent intent = new Intent(context, PlaySongService.class);
        intent.putExtra(SONG_DETAIL, sCurrentSong());

        // started
        context.startService(intent);

        // bound service
        context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        ContextCompat.startForegroundService(context, intent);
    }
    
    public static void checkExistForeground(Context context) {
        if (!isInForeground) {
            startMusicPlayerService(context);
        }
    }

    public static void sendActionMediaPlayer(Context context, int action) {
        Intent intent = new Intent(BROADCAST_RECEIVER);
        intent.putExtra(SONG_DETAIL, sCurrentSong());
        intent.putExtra(IS_PLAYING, sSongPlaying);
        intent.putExtra(SONG_STATUS, action);

        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
}
