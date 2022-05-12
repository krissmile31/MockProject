package com.krissmile31.mockproject.services;

import static com.krissmile31.mockproject.utils.Constants.BROADCAST_RECEIVER;
import static com.krissmile31.mockproject.utils.ServiceUtils.sIsPlaying;
import static com.krissmile31.mockproject.utils.SongUtils.*;
import static com.krissmile31.mockproject.utils.Constants.*;


import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.krissmile31.mockproject.utils.LogUtils;
import com.krissmile31.mockproject.MainActivity;
import com.krissmile31.mockproject.R;
import com.krissmile31.mockproject.models.Song;
import com.krissmile31.mockproject.broadcast.ForegroundReceiver;
import com.krissmile31.mockproject.utils.ServiceUtils;

public class PlaySongService extends Service {
    private final int PAUSE = 1;
    private final int RESUME = 2;
    private final int EXIT = 3;
    private final int PREVIOUS = 4;
    private final int NEXT = 5;
    private ServiceUtils serviceUtils = new ServiceUtils();


    private final String TAG = PlaySongService.class.getSimpleName();

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

        startMediaPlayer(intent);
        return mMySongBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate()");
        serviceUtils.sMediaPlayer = new MediaPlayer();

        // after timeout play a song
        serviceUtils.onSongCompletion(getApplicationContext());
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
            Song song = serviceUtils.sCurrentSong();

            if (song != null) {
                serviceUtils.playMusic(song, getApplicationContext());
                sIsPlaying = true;
                sendNotification(song);
            }
        }
        handleActionControlSong(intent.getIntExtra(BROADCAST_RECEIVER, 0));

//            Song song = (Song) intent.getSerializableExtra(SONG_DETAIL);

        LogUtils.d(String.valueOf(sIsPlaying));
    }

    public void sendNotification(Song song) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(NOTIFICATION, song);

        PendingIntent pendingIntent;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
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
                    Uri.parse(song.getImage()));
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
                .setContentTitle(song.getSong())
                .setContentText(song.getSinger())
                .setContentIntent(pendingIntent)
//                .setLargeIcon(BitmapFactory.decodeResource(getResources(), album.getThumbnail()))
                .setLargeIcon(bitmap)
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
        if (sIsPlaying) {
            return getPendingIntent(this, PAUSE);
        }
        else {
            return getPendingIntent(this, RESUME);
        }
    }

    private int setDrawablePlaying() {
        if (sIsPlaying)
            return R.drawable.ic_baseline_pause_24;
        else
            return R.drawable.ic_baseline_play_arrow_24;
    }

    private String setTitlePlaying() {
        if (sIsPlaying)
            return "Pause";
        else
            return "Play";
    }

    private PendingIntent getPendingIntent(Context context, int action) {
        Intent intent = new Intent(this, ForegroundReceiver.class);
        intent.putExtra(BROADCAST_RECEIVER, action);

        return PendingIntent.getBroadcast(context, action, intent,
                PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void handleActionControlSong(int action) {
        switch (action) {
            case PAUSE:
                serviceUtils.pauseMusic();
//                sendNotification(sCurrentSong());
                break;

            case RESUME:
                serviceUtils.resumeMusic();
//                sendNotification(sCurrentSong());
                break;

            case EXIT:
//                serviceUtils.isInForeground = false;
                stopForeground(true);
                serviceUtils.releaseMusic();
                serviceUtils.initMediaPlayer(serviceUtils.sCurrentSong(), getApplicationContext());
                serviceUtils.sendActionMediaPlayer(getApplicationContext(), EXIT);

//                stopSelf();
                break;

            case PREVIOUS:
                serviceUtils.preMusic(getApplicationContext());
//                sendNotification(sCurrentSong());
                break;

            case NEXT:
                serviceUtils.nextMusic(getApplicationContext());
//                sendNotification(sCurrentSong());
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy()");

        serviceUtils.releaseMusic();
    }
}