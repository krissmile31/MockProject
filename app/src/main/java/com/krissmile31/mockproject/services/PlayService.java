package com.krissmile31.mockproject.services;

import static com.krissmile31.mockproject.utils.Constants.BROADCAST_RECEIVER;
import static com.krissmile31.mockproject.utils.Constants.*;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.krissmile31.mockproject.database.recentsong.SongManager;
import com.krissmile31.mockproject.utils.LogUtils;
import com.krissmile31.mockproject.MainActivity;
import com.krissmile31.mockproject.R;
import com.krissmile31.mockproject.models.Song;
import com.krissmile31.mockproject.services.broadcast.SongReceiver;

import java.io.IOException;

public class PlayService extends android.app.Service implements MediaPlayer.OnCompletionListener {
    private final int PLAY = 6;
    private final int PAUSE = 1;
    private final int RESUME = 2;
    private final int EXIT = 3;
    private final int PREVIOUS = 4;
    private final int NEXT = 5;

    private boolean mIsPlaying;
    private Song mCurrentSong;
    public MediaPlayer mediaPlayer;

    public int mCurrentSongIndex;

    private final String TAG = PlayService.class.getSimpleName();

    private MySongBinder mMySongBinder = new MySongBinder();

    public PlayService() {
    }

    public class MySongBinder extends Binder {
        public PlayService getPlaySongService() {
            return PlayService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Log.e(TAG, "onBind()");

        startMediaPlayer(intent);
        return mMySongBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate()");
        mediaPlayer = new MediaPlayer();

        // after timeout play a song
//        onSongCompletion(getApplicationContext());
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e(TAG, "onUnBind()");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.e(TAG, "onRebind()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        startMediaPlayer(intent);

        return START_NOT_STICKY;
    }

    private void startMediaPlayer(Intent intent) {

        if (intent != null) {
//            Song song = serviceUtils.sCurrentSong();
            Song song = (Song) intent.getSerializableExtra(SONG_DETAIL);
            mCurrentSong = song;

            if (song != null) {
                mCurrentSong = song;
                mIsPlaying = true;
                playMusic(song);
                sendNotification(song);
                Log.e(TAG, "startMediaPlayer: " + mediaPlayer.getDuration() );
            }
        }
        handleActionControlSong(intent.getIntExtra(BROADCAST_RECEIVER, 0));

//        sendActionMediaPlayer(PLAY);

        LogUtils.d(String.valueOf(mIsPlaying));
    }

    public void sendNotification(Song song) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(NOTIFICATION, song);

        PendingIntent pendingIntent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            pendingIntent = PendingIntent.getActivity(
                    this,
                    0,
                    intent,
                    PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
        } else {
            pendingIntent = PendingIntent.getActivity(
                    this,
                    0,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

        }

        Bitmap bitmap = null;
        try
        {
            bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(),
                    Uri.parse(song.getThumbnail()));
        }
        catch (Exception e)
        {
            //handle exception

        }

        if (bitmap == null) {
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        }

        NotificationCompat.Builder notificationCompat = new NotificationCompat.Builder(
                this,
                CHANNEL_ID)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setSmallIcon(R.drawable.ic_logo)
                .setContentTitle(song.getSongName())
                .setContentText(song.getSinger())
                .setContentIntent(pendingIntent)
//                .setLargeIcon(BitmapFactory.decodeResource(getResources(), album.getThumbnail()))
                .setLargeIcon(bitmap)
                .setProgress(100, 30, false)
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(0, 1, 2)
                        .setMediaSession(new MediaSessionCompat(this,
                                "Play Music").getSessionToken()))
                .addAction(R.drawable.ic_baseline_skip_previous_24,
                        "Previous",
                        getPendingIntent(this, PREVIOUS))

                .addAction(setDrawablePlaying(), setTitlePlaying(), setActionPlaying())

                .addAction(R.drawable.ic_baseline_skip_next_24,
                        "Next",
                        getPendingIntent(this, NEXT))

                .addAction(R.drawable.ic_baseline_clear_24,
                        "Exit",
                        getPendingIntent(this, EXIT));

        Log.d(TAG, "onPicassoStartLoading: " );

        Notification notification = notificationCompat.build();
        startForeground(1, notification);
    }
    private PendingIntent setActionPlaying() {
        if (mIsPlaying) {
            return getPendingIntent(this, PAUSE);
        }
        else {
            return getPendingIntent(this, RESUME);
        }
    }

    private int setDrawablePlaying() {
        if (mIsPlaying)
            return R.drawable.ic_baseline_pause_24;
        else
            return R.drawable.ic_baseline_play_arrow_24;
    }

    private String setTitlePlaying() {
        if (mIsPlaying)
            return "Pause";
        else
            return "Play";
    }

    private PendingIntent getPendingIntent(Context context, int action) {
        Intent intent = new Intent(this, SongReceiver.class);
        intent.putExtra(BROADCAST_RECEIVER, action);

        return PendingIntent.getBroadcast(context, action, intent,
                PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void handleActionControlSong(int action) {
        switch (action) {
            case PAUSE:
                pauseMusic();
                break;

            case RESUME:
                resumeMusic();
                break;

            case EXIT:
                stopForeground(true);
                releaseMusic();
                initMediaPlayer(mCurrentSong);
//                sendActionMediaPlayer(EXIT);

//                stopSelf();
                break;

            case PREVIOUS:
//                preMusic();
                break;

            case NEXT:
//                nextMusic();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy()");

        releaseMusic();
    }


//    public Song sCurrentSong() {
//        return getCurrentSong(sCurrentSongIndex);
//    }

    public void pauseMusic() {
        if (mediaPlayer != null && mIsPlaying) {
            mediaPlayer.pause();
            mIsPlaying = false;
//            sendActionMediaPlayer(PAUSE);
            sendNotification(mCurrentSong);

        }
    }

    public void resumeMusic() {
        if (mediaPlayer != null && !mIsPlaying) {
            mediaPlayer.start();
            mIsPlaying = true;
//            sendActionMediaPlayer(RESUME);
            sendNotification(mCurrentSong);

        }
    }

    public void releaseMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
            mIsPlaying = false;
        }
    }

    public void initMediaPlayer(Song song) {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
        try {
            mediaPlayer.setDataSource(this, Uri.parse(song.getData()));
        } catch (IllegalArgumentException | IllegalStateException | IOException e) {
            e.printStackTrace();
        }

    }

    public void playMusic(Song song) {
        try {
            mediaPlayer.reset();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(this, Uri.parse(song.getData()));
            mediaPlayer.prepare();
            mediaPlayer.start();

        } catch (IllegalArgumentException | IllegalStateException | IOException e) {
            e.printStackTrace();
        }

        Log.e("TAG", "playMusic: " + mediaPlayer.getDuration());

        sendActionMediaPlayer(song, mIsPlaying, PLAY);
    }

//    public Song getCurrentSong(int position) {
//        return sSongList.get(position);
//    }
//    public void playCurrentSong(int position) {
////        Song song = getCurrentSong(position);
//        mCurrentSong = sSongList.get(position);
//        playMusic(mCurrentSong);
//        mIsPlaying = true;
//        sendNotification(mCurrentSong);
//
//    }
//
    public void preMusic() {
        if (mCurrentSongIndex > 0) {
            mCurrentSongIndex--;
        } else {
//            mCurrentSongIndex = sSongList.size() - 1;
        }

//        playCurrentSong(mCurrentSongIndex);

        sendActionMediaPlayer(mCurrentSong, mIsPlaying, PREVIOUS);
//        sRecentSongList.add(mCurrentSong);
        SongManager songManager = SongManager.getInstance(this);
        songManager.add(mCurrentSong);

    }

    public void nextMusic() {
//        if (mCurrentSongIndex < sSongList.size() - 1)
//            mCurrentSongIndex++;
//        else
            mCurrentSongIndex = 0;

//        playCurrentSong(mCurrentSongIndex);
//        sendActionMediaPlayer(mCurrentSong, mIsPlaying, NEXT);
//        sRecentSongList.add(mCurrentSong);
        SongManager songManager = SongManager.getInstance(this);
        songManager.add(mCurrentSong);

    }

    // running out a song -> run next song
//    public void onSongCompletion(Context context) {
//        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mediaPlayer) {
//                if (mCurrentSongIndex < sSongList.size() - 1) {
//                    mCurrentSongIndex++;
//
//                } else {
//                    mCurrentSongIndex = 0;
//                }
//                playMusic(mCurrentSong);
//            }
//        });
//    }

    public void setSongList(){

    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
//        if (mCurrentSongIndex < sSongList.size() - 1) {
//            mCurrentSongIndex++;
//
//        } else {
//            mCurrentSongIndex = 0;
//        }
//        playMusic(mCurrentSong);
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
        return mediaPlayer.getCurrentPosition();
    }

    public int getTotalTime() {
        return mediaPlayer.getDuration();
    }

//    public void startMusicPlayerService() {
//        Intent intent = new Intent(this, PlaySongService.class);
//        intent.putExtra(SONG_DETAIL, mCurrentSong);
//
//        // started
//        startService(intent);
//
//        // bound service
//        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
//        ContextCompat.startForegroundService(this, intent);
//    }

    public void sendActionMediaPlayer(Song song, boolean isPlaying, int action) {
        Intent intent = new Intent(BROADCAST_RECEIVER);
        intent.putExtra(SONG_DETAIL, song);
        intent.putExtra(IS_PLAYING, isPlaying);
        intent.putExtra(SONG_STATUS, action);

        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
