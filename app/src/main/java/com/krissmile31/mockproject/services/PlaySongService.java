package com.krissmile31.mockproject.services;

import static com.krissmile31.mockproject.MainActivity.sSongList;
import static com.krissmile31.mockproject.services.MyChannel.CHANNEL_ID;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.krissmile31.mockproject.LogUtils;
import com.krissmile31.mockproject.MainActivity;
import com.krissmile31.mockproject.R;
import com.krissmile31.mockproject.models.Song;
import com.krissmile31.mockproject.services.BroadcastReceiver.SongReceiver;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;

public class PlaySongService extends Service {
    public static MediaPlayer sMediaPlayer;
    private Song mGetSong;
    public static boolean sSongPlaying;
    private int mCurrentSongIndex;

    public static final String BROADCAST_RECEIVER = "broadcast_receiver";
    public static final String TAG = PlaySongService.class.getSimpleName();
    private final int PAUSE = 1;
    private final int RESUME = 2;
    private final int EXIT = 3;
    private final int PREVIOUS = 4;
    private final int NEXT = 5;

    private MySongBinder mMySongBinder = new MySongBinder();

    public PlaySongService() {
    }

    public class MySongBinder extends Binder {
        public PlaySongService getPlaySongService() {
            return PlaySongService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Log.e(TAG, "onBind()");

        if (intent != null) {
            Song song = (Song) intent.getSerializableExtra("song_details");

            if (song != null) {
                mGetSong = song;
                playMusic(song);
                sSongPlaying = true;
            }
        }
        handleActionControlSong(intent.getIntExtra(BROADCAST_RECEIVER, 0));


        return mMySongBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate()");
        sMediaPlayer = new MediaPlayer();

        onCompletion();
//        if(mediaPlayer == null)
//            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.song);
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

        if (intent != null) {
            Song song = (Song) intent.getSerializableExtra("song_details");
//            Log.e(TAG, "playMusic: " + song.getData());


            if (song != null) {
                mGetSong = song;
                playMusic(song);
                sSongPlaying = true;

                sendNotification(song);
            }
        }

        handleActionControlSong(intent.getIntExtra(BROADCAST_RECEIVER, 0));

        LogUtils.d(String.valueOf(sSongPlaying));

        return START_NOT_STICKY;
    }

    private void playMusic(Song song) {
        try {
            sMediaPlayer.reset();
            sMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            sMediaPlayer.setDataSource(getApplicationContext(), Uri.parse(song.getData()));
//            Log.e(TAG, "playMusic: " + song.getData());
//            Log.e(TAG, "playMusic: " + song.getSinger());

//            mSongPlaying = true;
            sMediaPlayer.prepare();
            sMediaPlayer.start();

        } catch (IllegalArgumentException | IllegalStateException | IOException e) {
            e.printStackTrace();
        }

    }

    private void initMediaPlayer(Song song) {
        if (sMediaPlayer == null) {
            sMediaPlayer = new MediaPlayer();

        }

        sMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            sMediaPlayer.setDataSource(getApplicationContext(), Uri.parse(song.getData()));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sendNotification(Song song) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("notification", "onNotiClick");

        PendingIntent pendingIntent = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            pendingIntent = PendingIntent.getActivity(this, 0, intent,PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
        } else {
            pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        }

        NotificationCompat.Builder notificationCompat = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setSmallIcon(R.drawable.ic_logo)
                .setContentTitle(song.getSong())
                .setContentText(song.getSinger())
                .setContentIntent(pendingIntent)
//                .setLargeIcon(BitmapFactory.decodeResource(getResources(), album.getThumbnail()))
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.logo))
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(0, 1, 2)
                        .setMediaSession(new MediaSessionCompat(this, "Play Music").getSessionToken()));

        Picasso.get().load(song.getImage()).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//                Log.d(TAG, "onBitmapLoaded: " + bitmap);
                notificationCompat.setLargeIcon(bitmap);
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });

        if (sSongPlaying) {
            notificationCompat
                    .addAction(R.drawable.ic_baseline_skip_previous_24, "Previous", getPendingIntent(this, PREVIOUS))
                    .addAction(R.drawable.ic_baseline_pause_24, "Pause", getPendingIntent(this, PAUSE))
                    .addAction(R.drawable.ic_baseline_skip_next_24, "Next", getPendingIntent(this, NEXT))
                    .addAction(R.drawable.ic_baseline_clear_24, "Exit", getPendingIntent(this, EXIT));
        } else {
            notificationCompat
                    .addAction(R.drawable.ic_baseline_skip_previous_24, "Previous", getPendingIntent(this, PREVIOUS))
                    .addAction(R.drawable.ic_baseline_play_arrow_24, "Play", getPendingIntent(this, RESUME))
                    .addAction(R.drawable.ic_baseline_skip_next_24, "Next", getPendingIntent(this, NEXT))
                    .addAction(R.drawable.ic_baseline_clear_24, "Exit", getPendingIntent(this, EXIT));
        }

        Notification notification = notificationCompat.build();
        startForeground(1, notification);
    }

    private PendingIntent getPendingIntent(Context context, int action) {
        Intent intent = new Intent(this, SongReceiver.class);
        intent.putExtra(BROADCAST_RECEIVER, action);

        return PendingIntent.getBroadcast(context, action, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void handleActionControlSong(int action) {
        switch (action) {
            case PAUSE:
                pauseMusic();
                sendNotification(mGetSong);
                break;

            case RESUME:
                resumeMusic();
                sendNotification(mGetSong);
                break;

            case EXIT:
//                sMediaPlayer.release();
                stopForeground(true);
                releaseMusic();

                initMediaPlayer(mGetSong);
//                playMusic(mGetSong);
//                pauseMusic();
//                stopSelf();
                break;

            case PREVIOUS:
                preMusic();
                break;

            case NEXT:
                nextMusic();
                break;
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

    private void playCurrentSong(int position) {
        Song song = sSongList.get(position);
        playMusic(song);
        sendNotification(song);
        sSongPlaying = true;
    }

    private void preMusic() {
        if (mCurrentSongIndex > 0)
            mCurrentSongIndex--;
        else
            mCurrentSongIndex = sSongList.size() - 1;

        playCurrentSong(mCurrentSongIndex);
    }

    private void nextMusic() {
        if (mCurrentSongIndex >= sSongList.size() - 1)
            mCurrentSongIndex = 0;
        else
            mCurrentSongIndex++;

        playCurrentSong(mCurrentSongIndex);
    }

    private void onCompletion() {
        sMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {

                if (mCurrentSongIndex >= sSongList.size() - 1) {
                    mCurrentSongIndex = 0;
                } else {
//                    playMusic(sSongList.get(mCurrentSongIndex + 1));
                    mCurrentSongIndex++;
                }
                playMusic(sSongList.get(mCurrentSongIndex));
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy()");

        releaseMusic();
    }

    public static void releaseMusic() {
        if (sMediaPlayer != null) {
            sMediaPlayer.release();
            sMediaPlayer = null;
        }
    }
}