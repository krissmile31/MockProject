package com.krissmile31.mockproject;

import static com.krissmile31.mockproject.songs.tab.allsongs.AllSongsFragment.sAllSongsAdapter;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
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
import com.krissmile31.mockproject.interfaces.OnBackPressedListener;
import com.krissmile31.mockproject.interfaces.OnShowMusic;
import com.krissmile31.mockproject.models.Album;
import com.krissmile31.mockproject.models.Artist;
import com.krissmile31.mockproject.models.Genre;
import com.krissmile31.mockproject.models.Song;
import com.krissmile31.mockproject.nowplaying.NowPlayingFragment;
import com.krissmile31.mockproject.settings.SettingFragment;
import com.krissmile31.mockproject.songs.MusicFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnBackPressedListener, OnShowMusic, LoaderManager.LoaderCallbacks<Cursor> {

    private BottomNavigationView mBottomNavigationView;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private ImageView mMenuSideBar;
    public static ConstraintLayout sPlaySongBackground;
    public static ImageView sThumbnailPlaySong, sBtnPlayBar, sBtnPreSongBar, sBtnNextSongBar;
    private ImageView mExitPlaySongBackground;
    public static TextView sSongBackground, sSingerBackground;
    private boolean mIsLoaded;
    public static List<Song> sSongList = new ArrayList<>();
    public static List<Album> sAlbumList = new ArrayList<>();
    public static List<Artist> sArtistList = new ArrayList<>();
    public static List<Genre> sGenreList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBottomNavigationView = findViewById(R.id.bottomNavigation);
        mNavigationView = findViewById(R.id.navigationView);
        mDrawerLayout = findViewById(R.id.drawLayout);
        mMenuSideBar = findViewById(R.id.menu_side_bar);

        sPlaySongBackground = findViewById(R.id.play_song_background);
        sThumbnailPlaySong = findViewById(R.id.thumbnail_play_song);
        sSongBackground = findViewById(R.id.tv_song_background);
        sSingerBackground = findViewById(R.id.tv_singer_background);
        sBtnPlayBar = findViewById(R.id.play_background);
        sBtnPreSongBar = findViewById(R.id.btn_pre);
        sBtnNextSongBar = findViewById(R.id.btn_next);
        mExitPlaySongBackground = findViewById(R.id.exit_play_song_background);

        mBottomNavigationView.setItemIconTintList(null);
        mNavigationView.setItemIconTintList(null);

        // open side bar
        mMenuSideBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });

        replaceFragment(new MusicFragment());

        mBottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
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
        });


        mExitPlaySongBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sPlaySongBackground.setVisibility(View.GONE);
            }
        });

        onNewIntent(getIntent());

//        Log.e("TAG", "onCreate: " + getIntent().getStringExtra("notification"));
//        openNowPlaying();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        Log.e("TAG", "onCreate: " + extras );

        if (extras != null) {
            if (extras.containsKey("onNotiClick")) {
                Log.e("TAG", "onCreate: " + extras);

                getSupportFragmentManager().beginTransaction().replace(R.id.drawLayout, new NowPlayingFragment()).addToBackStack("now_playing").commit();

            }
        }
        super.onNewIntent(intent);

    }

    private void openNowPlaying() {
        String extras = getIntent().getStringExtra("notification");
        if (extras != null && extras.equals("onNotiClick")) {
            Log.e("TAG", "onCreate: " + extras );
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new NowPlayingFragment()).addToBackStack("bottom_nav").commit();

        }
    }

    public void replaceFragment(Fragment fragment) {
//        FragmentManager fragmentManager = getSupportFragmentManager();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).addToBackStack("bottom_nav").commit();
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
        super.onBackPressed();
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
                String thumbnailArtist = cursor.getString((int) cursor.getColumnIndex(MediaStore.Audio.Artists.ARTIST_KEY));
//                String noAlbumsArtist = cursor.getString((int) cursor.getColumnIndex(MediaStore.Audio.ArtistColumns.NUMBER_OF_ALBUMS));
//                String noSongsArtist = cursor.getString((int) cursor.getColumnIndex(MediaStore.Audio.ArtistColumns.NUMBER_OF_TRACKS));
                sArtistList.add(new Artist(artistId, singer, thumbnail.toString()));

                // Genre
                long genreId = cursor.getLong((int) cursor.getColumnIndex(MediaStore.Audio.Genres._ID));
//                String genreName = cursor.getString((int) cursor.getColumnIndex(MediaStore.Audio.GenresColumns.NAME));
                Uri thumbnailGenre = ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"),
                        genreId);
                sGenreList.add(new Genre(genreId, thumbnailGenre.toString()));
            } while (cursor.moveToNext());
        }

        Collections.sort(sSongList, new Comparator<Song>() {
            @Override
            public int compare(Song song_first, Song song_second) {
                return song_first.getSong().compareTo(song_second.getSong());
            }
        });

        sAllSongsAdapter.notifyDataSetChanged();

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}