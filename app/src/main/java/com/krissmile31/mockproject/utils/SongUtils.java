package com.krissmile31.mockproject.utils;

import static com.krissmile31.mockproject.utils.ServiceUtils.*;

import android.widget.ImageView;

import com.krissmile31.mockproject.R;
import com.krissmile31.mockproject.models.Album;
import com.krissmile31.mockproject.models.Artist;
import com.krissmile31.mockproject.models.Genre;
import com.krissmile31.mockproject.models.Playlist;
import com.krissmile31.mockproject.models.Song;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SongUtils {
    public static List<Song> sSongList = new ArrayList<>();
    public static List<Playlist> sPlaylist = new ArrayList<>();
    public static List<Album> sAlbumList = new ArrayList<>();
    public static List<Artist> sArtistList = new ArrayList<>();
    public static List<Genre> sGenreList = new ArrayList<>();

    public static void getThumbnail(String data, ImageView drawable) {
        Picasso.get().load(data)
                .placeholder(R.drawable.ic_logo)
                .error(R.drawable.ic_logo)
                .fit()
                .into(drawable);
    }

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
}
