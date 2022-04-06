package com.example.mockproject.services;

import static com.example.mockproject.services.MySong.CHANNEL_ID;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

import com.example.mockproject.MainActivity;
import com.example.mockproject.R;
import com.example.mockproject.fragments.musicFragments.AllSongsFragment;
import com.example.mockproject.models.Album;
import com.example.mockproject.services.broadcastReceiver.SongReceiver;

public class PlaySongService extends Service {
    private MediaPlayer mediaPlayer;
    private Album album;
    private boolean songPlaying;
    private static final int ACTION_REPLAY = 5;
    private static final int ACTION_PAUSE = 1;
    private static final int ACTION_EXIT = 2;
    private static final int ACTION_PREVIOUS = 3;
    private static final int ACTION_NEXT = 4;
    
    public PlaySongService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.song);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent != null) {
            Album getAlbum = (Album) intent.getSerializableExtra("song_details");
            album = getAlbum;
            mediaPlayer.start();

            notification(getAlbum);
        }

        handleActionControlSong(intent.getIntExtra("album_details", 0));

        return super.onStartCommand(intent, flags, startId);
    }

    private void notification(Album album) {
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, AllSongsFragment.class), PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.play_song_foreground);
        remoteViews.setImageViewResource(R.id.thumnail_foreground, album.getThumbnail());
        remoteViews.setTextViewText(R.id.tv_song_foreground, album.getSong());
        remoteViews.setTextViewText(R.id.tv_singer_foreground, album.getSinger());
        remoteViews.setImageViewResource(R.id.previous_foreground, R.drawable.ic_previous_song);
        remoteViews.setImageViewResource(R.id.next_foreground, R.drawable.ic_next_song);
        remoteViews.setImageViewResource(R.id.exit_foreground, R.drawable.ic_exit);

        // handle event click on foreground
        if (songPlaying) {
            remoteViews.setOnClickPendingIntent(R.id.play_foreground, getPendingIntent(this, ACTION_PAUSE));
            remoteViews.setImageViewResource(R.id.play_foreground, R.drawable.ic_play);
        }
        else {
            remoteViews.setOnClickPendingIntent(R.id.play_foreground, getPendingIntent(this, ACTION_REPLAY));
            remoteViews.setImageViewResource(R.id.play_foreground, R.drawable.ic_play);
        }

        //exit
        remoteViews.setOnClickPendingIntent(R.id.exit_foreground, getPendingIntent(this, ACTION_EXIT));


        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_logo)
                .setContentIntent(pendingIntent)
                .setCustomContentView(remoteViews)
                .setSound(null)
                .build();

        startForeground(1, notification);
    }

    private PendingIntent getPendingIntent(Context context, int action) {
        Intent intent = new Intent(context, SongReceiver.class);
        intent.putExtra("get_song_details", action);

        return PendingIntent.getBroadcast(context.getApplicationContext(), action, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void handleActionControlSong(int action) {
        switch (action) {
            case ACTION_PAUSE:
                if (mediaPlayer != null && songPlaying) {
                    mediaPlayer.pause();
//                    notification(album);
                }
                break;

            case ACTION_REPLAY:
                if (mediaPlayer != null && !songPlaying) {
                    mediaPlayer.start();
//                    notification(album);
                }
                break;

            case ACTION_EXIT:
                mediaPlayer.release();
                stopSelf();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
    }
}