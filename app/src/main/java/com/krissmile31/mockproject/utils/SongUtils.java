package com.krissmile31.mockproject.utils;

import static com.krissmile31.mockproject.services.BoundService.ServiceConnection.sPlaySongService;
import static com.krissmile31.mockproject.utils.Constants.*;
import static com.krissmile31.mockproject.utils.ServiceUtils.*;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.ImageView;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.krissmile31.mockproject.R;
import com.krissmile31.mockproject.broadcast.SongReceiver;
import com.krissmile31.mockproject.models.Album;
import com.krissmile31.mockproject.models.Artist;
import com.krissmile31.mockproject.models.Genre;
import com.krissmile31.mockproject.models.Playlist;
import com.krissmile31.mockproject.models.Song;
import com.krissmile31.mockproject.services.PlaySongService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SongUtils {
    public static List<Song> sSongList = new ArrayList<>();
    public static List<Playlist> sPlaylist = new ArrayList<>();
    public static List<Album> sAlbumList = new ArrayList<>();
    public static List<Artist> sArtistList = new ArrayList<>();
    public static List<Genre> sGenreList = new ArrayList<>();

    public static SongReceiver songReceiver = new SongReceiver();

    private static ServiceUtils serviceUtils = new ServiceUtils();

    public static void getThumbnail(String data, ImageView drawable) {
        Picasso.get().load(data)
                .placeholder(R.drawable.ic_logo)
                .error(R.drawable.ic_logo)
                .fit()
                .into(drawable);
    }

    public static void setIconPlaying(ImageView icon, int playIcon, int pauseIcon) {
        if (sIsPlaying) {
            icon.setImageResource(playIcon);
            serviceUtils.pauseMusic();
            sIsPlaying = false;
//            sendActionToService(PAUSE);
        }

        else  {
            icon.setImageResource(pauseIcon);
            serviceUtils.resumeMusic();
            sIsPlaying = true;
//            sendActionToService(RESUME);

        }
    }

//    public static void setIconStatusAll() {
//        if (sIsPlaying) {
//            sMiniBtnPlay.setImageResource(R.drawable.ic_pause_empty);
////            sPlayNowPlaying.setImageResource(R.drawable.ic_pause_song_action);
//            sBtnPlaySong.setImageResource(R.drawable.ic_pause_gradie);
//        }
//        else  {
//            sMiniBtnPlay.setImageResource(R.drawable.ic_play_empty);
////            sPlayNowPlaying.setImageResource(R.drawable.ic_play_song_action);
//            sBtnPlaySong.setImageResource(R.drawable.ic_played);
//
//        }
//    }

    public static void registerActionMusicPlayer(Context context, BroadcastReceiver receiver) {
        LocalBroadcastManager.getInstance(context).registerReceiver(receiver,
                new IntentFilter(BROADCAST_RECEIVER));
    }

    public static void unregisterActionMusicPlayer(Context context, BroadcastReceiver receiver) {
        LocalBroadcastManager.getInstance(context).unregisterReceiver(receiver);
    }

    public static void sendActionToService(int action) {
        Intent intent = new Intent(sPlaySongService.getApplicationContext(), PlaySongService.class);
        intent.putExtra(BROADCAST_RECEIVER, action);
        sPlaySongService.getApplicationContext().startService(intent);
    }
}
