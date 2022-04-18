package com.krissmile31.mockproject.services;

import static com.krissmile31.mockproject.services.MyApp.CHANNEL_ID;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

import com.krissmile31.mockproject.LogUtils;
import com.krissmile31.mockproject.MainActivity;
import com.krissmile31.mockproject.R;
import com.krissmile31.mockproject.models.Album;
import com.krissmile31.mockproject.services.BroadcastReceiver.SongReceiver;
import com.krissmile31.mockproject.songs.tab.allsongs.AllSongsFragment;
import com.krissmile31.mockproject.songs.tab.allsongs.adapter.AllSongsAdapter;

public class PlaySongService extends Service {
    public static MediaPlayer mediaPlayer;
    private static Album getSong;
    public static boolean songPlaying;

    public static final String BROADCAST_RECEIVER = "broadcast_receiver";
    public static final String TAG = PlaySongService.class.getSimpleName();
    private static final int PAUSE = 1;
    private static final int RESUME = 2;
    private static final int EXIT = 3;
    private static final int ACTION_PREVIOUS = 4;
    private static final int ACTION_NEXT = 5;

    private MySongBinder mySongBinder = new MySongBinder();
    
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
        Log.d(TAG, "onBind()");

        if (intent != null) {
            Album album = (Album) intent.getSerializableExtra("song_details");

            if (album != null) {
                getSong = album;
                playMusic(album);
                songPlaying = true;
            }
        }

        return mySongBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate()");

//        if(mediaPlayer == null)
//            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.song);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnBind()");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.d(TAG, "onRebind()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent != null) {
            Album album = (Album) intent.getSerializableExtra("song_details");

            if (album != null) {
                getSong = album;
                playMusic(album);
                songPlaying = true;
                sendNotification(album);
            }
        }

        handleActionControlSong(intent.getIntExtra(BROADCAST_RECEIVER, 0));

        LogUtils.d(String.valueOf(songPlaying));

        return super.onStartCommand(intent, flags, startId);
    }

    private void playMusic(Album album) {
        if (mediaPlayer == null)
            mediaPlayer = MediaPlayer.create(getApplicationContext(), album.getMusic());
        mediaPlayer.start();
    }

    private void sendNotification(Album album) {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationCompat = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setSmallIcon(R.drawable.ic_logo)
                .setContentTitle(album.getSong())
                .setContentText(album.getSinger())
                .setContentIntent(pendingIntent)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), album.getThumbnail()))
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                            .setShowActionsInCompactView(0, 1, 2)
                            .setMediaSession(new MediaSessionCompat(this, "Play Music").getSessionToken()))
                .addAction(R.drawable.ic_baseline_skip_previous_24, "Previous", null);

        if (songPlaying) {
            notificationCompat.addAction(R.drawable.ic_baseline_pause_24, "Pause", getPendingIntent(this, PAUSE))
                    .addAction(R.drawable.ic_baseline_skip_next_24, "Next", null)
                    .addAction(R.drawable.ic_baseline_clear_24, "Exit", getPendingIntent(this, EXIT));
        }

        else {
            notificationCompat.addAction(R.drawable.ic_baseline_play_arrow_24, "Play", getPendingIntent(this, RESUME))
                    .addAction(R.drawable.ic_baseline_skip_next_24, "Next", null)
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
                sendNotification(getSong);
                break;

            case RESUME:
                resumeMusic();
                sendNotification(getSong);
                break;

            case EXIT:
                mediaPlayer.release();
                stopForeground(true);
                stopSelf();
                break;
        }
    }

    public static void pauseMusic() {
        if (mediaPlayer != null && songPlaying) {
            mediaPlayer.pause();
            songPlaying = false;

        }
    }

    public static void resumeMusic() {
        if (mediaPlayer != null && !songPlaying) {
            mediaPlayer.start();
            songPlaying = true;
        }
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");

        releaseMusic();
    }

    public static void releaseMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}