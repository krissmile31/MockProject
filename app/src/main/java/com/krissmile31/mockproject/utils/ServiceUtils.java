package com.krissmile31.mockproject.utils;

import static com.krissmile31.mockproject.utils.SongUtils.*;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;

import com.krissmile31.mockproject.models.Song;

import java.io.IOException;

public class ServiceUtils implements MediaPlayer.OnCompletionListener {
    public static boolean sSongPlaying;
    public static int sCurrentSongIndex;
    public static MediaPlayer sMediaPlayer;

    public static void pauseMusic() {
        if (sMediaPlayer != null && sSongPlaying) {
            sMediaPlayer.pause();
            sSongPlaying = false;
        }
    }

    public static void resumeMusic() {
        if (sMediaPlayer != null && !sSongPlaying) {
            sMediaPlayer.start();
            sSongPlaying = true;
        }
    }

    public static void releaseMusic() {
        if (sMediaPlayer != null) {
            sMediaPlayer.release();
            sMediaPlayer = null;
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
    }

    public static Song getCurrentSong(int position) {
        return sSongList.get(position);
    }
    public static void playCurrentSong(int position, Context context) {
        Song song = getCurrentSong(position);
        playMusic(song, context);
//        sendNotification(song);
        sSongPlaying = true;
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
}
