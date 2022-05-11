package com.krissmile31.mockproject;

import static com.krissmile31.mockproject.utils.ServiceUtils.*;
import static com.krissmile31.mockproject.utils.SongUtils.*;
import static com.krissmile31.mockproject.utils.Constants.*;
import static com.krissmile31.mockproject.musics.tab.allsongs.AllSongsFragment.sAllSongsAdapter;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.krissmile31.mockproject.home.HomeFragment;
import com.krissmile31.mockproject.interfaces.*;
import com.krissmile31.mockproject.models.*;
import com.krissmile31.mockproject.nowplaying.NowPlayingFragment;
import com.krissmile31.mockproject.settings.SettingFragment;
import com.krissmile31.mockproject.musics.MusicFragment;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity implements OnBackPressedListener, OnShowMusic,
        OnDataMiniPlayer, LoaderManager.LoaderCallbacks<Cursor>,
        View.OnClickListener, NavigationBarView.OnItemSelectedListener {

    private BottomNavigationView mBottomNavigationView;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    public static ImageView sMenuSideBar;
    public static ConstraintLayout sMiniPlayer;
    private static ImageView mMiniThumbnailPlayer;
    private static ImageView mMiniBtnPre;
    private static ImageView mMiniBtnNext;
    private static ImageView mMiniExitPlayer;
    private static TextView mMiniSongPlayer;
    private static TextView mMiniSingerPlayer;
    private boolean mIsLoaded;
    private OnMiniPlayerClickListener onMiniPlayerClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBottomNavigationView = findViewById(R.id.bottomNavigation);
        mNavigationView = findViewById(R.id.navigationView);
        mDrawerLayout = findViewById(R.id.drawLayout);
        sMenuSideBar = findViewById(R.id.menu_side_bar);

        sMiniPlayer = findViewById(R.id.mini_player);
        mMiniThumbnailPlayer = findViewById(R.id.thumbnail_play_song);
        mMiniSongPlayer = findViewById(R.id.tv_song_background);
        mMiniSingerPlayer = findViewById(R.id.tv_singer_background);
        sMiniBtnPlay = findViewById(R.id.btn_play);
        mMiniBtnPre = findViewById(R.id.btn_pre);
        mMiniBtnNext = findViewById(R.id.btn_next);
        mMiniExitPlayer = findViewById(R.id.btn_exit);

        // set null to put gradient color vector
        mBottomNavigationView.setItemIconTintList(null);
        mNavigationView.setItemIconTintList(null);

        replaceFragment(new MusicFragment());
        sMenuSideBar.setOnClickListener(this); // open side bar
        mBottomNavigationView.setOnItemSelectedListener(this);

        onNewIntent(getIntent());

        registerActionMusicPlayer(this);

//        Log.e("TAG", "onCreate: " + getIntent().getStringExtra("notification"));
//        openNowPlaying();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey(NOTIFICATION)) {
//                Song song = (Song) intent.getSerializableExtra(NOTIFICATION);
//                Song song = getCurrentSong(sCurrentSongIndex);
                Bundle bundle = new Bundle();
                bundle.putSerializable(SONG_DETAIL, sCurrentSong());
                NowPlayingFragment nowPlayingFragment = new NowPlayingFragment();
                nowPlayingFragment.setArguments(bundle);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.drawLayout, nowPlayingFragment)
                        .addToBackStack(NOW_PLAYING).commit();
            }
        }
        super.onNewIntent(intent);
    }

    public void replaceFragment(Fragment fragment) {
//        FragmentManager fragmentManager = getSupportFragmentManager();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment)
                .addToBackStack("bottom_nav").commit();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START))
            mDrawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    @Override
    public void onBackStackPressed() {
        setData();
        super.onBackPressed();
    }

    @Override
    public void onDisplayData(Song song) {
        sMiniPlayer.setVisibility(View.VISIBLE);
        sMiniBtnPlay.setImageResource(R.drawable.ic_pause_empty);
        Picasso.get().load(song.getImage()).placeholder(R.drawable.ic_logo)
                .error(R.drawable.ic_logo).into(mMiniThumbnailPlayer);
        mMiniSongPlayer.setText(song.getSong());
        mMiniSingerPlayer.setText(song.getSinger());

        sMiniBtnPlay.setOnClickListener(this);
        mMiniBtnPre.setOnClickListener(this);
        mMiniBtnNext.setOnClickListener(this);
        mMiniExitPlayer.setOnClickListener(this);

    }

    public static void setData() {
        Song song = getCurrentSong(sCurrentSongIndex);
        sMiniPlayer.setVisibility(View.VISIBLE);
        sMiniBtnPlay.setImageResource(R.drawable.ic_pause_empty);
        Picasso.get().load(song.getImage()).placeholder(R.drawable.ic_logo)
                .error(R.drawable.ic_logo).into(mMiniThumbnailPlayer);
        mMiniSongPlayer.setText(song.getSong());
        mMiniSingerPlayer.setText(song.getSinger());

//        sMiniBtnPlay.setOnClickListener(this);
//        mMiniBtnPre.setOnClickListener(this);
//        mMiniBtnNext.setOnClickListener(this);
//        mMiniExitPlayer.setOnClickListener(this);
    }

    private void openNowPlaying(Song song) {


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.menu_side_bar:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;

            case R.id.mini_player:

            case R.id.btn_play:
                setIconPlaying(sMiniBtnPlay, R.drawable.ic_play_empty, R.drawable.ic_pause_empty, this);
                setIconStatusAll();
                checkExistForeground(this);

                break;

            case R.id.btn_pre:
                preMusic(this);
                onDisplayData(getCurrentSong(sCurrentSongIndex));
                checkExistForeground(this);

                break;

            case R.id.btn_next:
                nextMusic(this);
                onDisplayData(getCurrentSong(sCurrentSongIndex));
                checkExistForeground(this);

                break;

            case R.id.btn_exit:
                sMiniPlayer.setVisibility(View.GONE);
                releaseMusic();
                break;

        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home_nav:
                replaceFragment(new HomeFragment());
                return true;

            case R.id.music_nav:
                replaceFragment(new MusicFragment());
                return true;

            case R.id.setting_nav:
                replaceFragment(new SettingFragment());
                return true;
        }
        return false;
    }

    @Override
    public void displaySongs() {
        if (!mIsLoaded) {
            mIsLoaded = true;
            getLoaderManager().initLoader(0, null, this);
        }
        else
            getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        if (i == 0) {
            return new CursorLoader(this, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    null,
                    null,
                    null,
                    null);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor != null && cursor.moveToFirst()) {

            do {

                // All Songs
                long id = cursor.getLong((int) cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                String song = cursor.getString((int) cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                String singer = cursor.getString((int) cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));

                Uri data = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);
//                Uri data = Uri.parse(cursor.getString((int) cursor.getColumnIndex(MediaStore.Audio.Media.DATA)));
                Uri thumbnail = ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"),
                        cursor.getLong((int) cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)));

                sSongList.add(new Song(id, song, singer, thumbnail.toString(), data.toString()));

                // Playlists
                long playlistId = cursor.getLong((int) cursor.getColumnIndex(MediaStore.Audio.Playlists._ID));
                String playlistName = cursor.getString((int) cursor.getColumnIndex(MediaStore.Audio.Playlists.DISPLAY_NAME));
                Uri thumbnailPlaylist = ContentUris.withAppendedId(Uri.parse("content://com.google.android.music.MusicContent/playlists"),
                        playlistId);
//                Uri thumbnailPlaylist = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, playlistId);
//                Log.e(TAG, "onLoadFinished: " + cursor.getString((int) cursor.getColumnIndex(MediaStore.Audio.Playlists.DISPLAY_NAME)) );
//                Log.e(TAG, "onLoadFinished: " + cursor.getInt((int) cursor.getColumnIndex(MediaStore.Audio.Playlists.NUM_TRACKS)));
//                int noSongsPlaylist = cursor.getInt((int) cursor.getColumnIndex(MediaStore.Audio.Playlists.NUM_TRACKS));
                sPlaylist.add(new Playlist(playlistId, playlistName, thumbnailPlaylist.toString()));
                // ... tobe continued

                // Albums
                long albumId = cursor.getLong((int) cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ID));
                String albumName = cursor.getString((int) cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM));
//                String albumSinger = cursor.getString((int) cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST));
//                int noSongsAlbum = cursor.getInt((int) cursor.getColumnIndex(MediaStore.Audio.Albums.NUMBER_OF_SONGS));
                sAlbumList.add(new Album(albumId, thumbnail.toString(), albumName, singer));

                // Artist
                long artistId = cursor.getLong((int) cursor.getColumnIndex(MediaStore.Audio.Artists._ID));
//                String artistName = cursor.getString((int) cursor.getColumnIndex(MediaStore.Audio.Artists.ARTIST));
//                String thumbnailArtist = cursor.getString((int) cursor.getColumnIndex(MediaStore.Audio.Artists.ARTIST_KEY));
                Uri thumbnailArtist = ContentUris.withAppendedId(Uri.parse("content://media/external/audio/artistart"),
                        cursor.getLong((int) cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST_ID)));
//                String noAlbumsArtist = cursor.getString((int) cursor.getColumnIndex(MediaStore.Audio.ArtistColumns.NUMBER_OF_ALBUMS));
//                String noSongsArtist = cursor.getString((int) cursor.getColumnIndex(MediaStore.Audio.ArtistColumns.NUMBER_OF_TRACKS));
                sArtistList.add(new Artist(artistId, singer, thumbnail.toString()));

                // Genre
                long genreId = cursor.getLong((int) cursor.getColumnIndex(MediaStore.Audio.Genres._ID));
//                String genreName = cursor.getString((int) cursor.getColumnIndex(MediaStore.Audio.GenresColumns.NAME));
                Uri thumbnailGenre = ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"),
                        genreId);
                sGenreList.add(new Genre(genreId, thumbnail.toString()));
            } while (cursor.moveToNext());
        }

        Collections.sort(sSongList, new Comparator<Song>() {
            @Override
            public int compare(Song first, Song second) {
                return first.getSong().compareTo(second.getSong());
            }
        });

        sAllSongsAdapter.notifyDataSetChanged();

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}