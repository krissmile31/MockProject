package com.krissmile31.mockproject.services;

import static com.krissmile31.mockproject.MainActivity.sSongList;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.widget.ImageView;

import com.krissmile31.mockproject.R;
import com.krissmile31.mockproject.models.Song;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class ServiceUtils implements MediaPlayer.OnCompletionListener {
    public static boolean sSongPlaying;
    public static int sCurrentSongIndex;
    public static MediaPlayer sMediaPlayer;

    public static final String BROADCAST_RECEIVER = "broadcast_receiver";
    public static final String TAG = PlaySongService.class.getSimpleName();

    public static void setIconPlaying(ImageView icon, int play, int pause) {
        if (sSongPlaying) {
            icon.setImageResource(play);
            pauseMusic();
            sSongPlaying = false;
        }

        else  {
            icon.setImageResource(pause);
            resumeMusic();
            sSongPlaying = true;
        }
    }

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

    public static void getThumbnail(String data, ImageView drawable) {
        Picasso.get().load(data)
                .placeholder(R.drawable.ic_logo)
                .error(R.drawable.ic_logo)
                .fit()
                .into(drawable);
    }
}
