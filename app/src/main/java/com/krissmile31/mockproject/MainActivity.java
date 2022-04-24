package com.krissmile31.mockproject;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.krissmile31.mockproject.home.HomeFragment;
import com.krissmile31.mockproject.interfaces.OnBackPressedListener;
import com.krissmile31.mockproject.interfaces.OnShowMusic;
import com.krissmile31.mockproject.model.Album;
import com.krissmile31.mockproject.settings.SettingFragment;
import com.krissmile31.mockproject.songs.MusicFragment;
import com.krissmile31.mockproject.songs.tab.allsongs.AllSongsFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnBackPressedListener, OnShowMusic, LoaderManager.LoaderCallbacks<Cursor> {

    private BottomNavigationView mBottomNavigationView;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private ImageView mMenuSideBar;
    public static ConstraintLayout playSongBackground;
    public static ImageView thumbnail_play_song, play_background, exit_play_song_background;
    public static TextView tv_song_background, tv_singer_background;
    private boolean isLoaded;
    public static List<Album> albumList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBottomNavigationView = findViewById(R.id.bottomNavigation);
        mNavigationView = findViewById(R.id.navigationView);
        mDrawerLayout = findViewById(R.id.drawLayout);
        mMenuSideBar = findViewById(R.id.menu_side_bar);

        playSongBackground = findViewById(R.id.play_song_background);
        thumbnail_play_song = findViewById(R.id.thumbnail_play_song);
        tv_song_background = findViewById(R.id.tv_song_background);
        tv_singer_background = findViewById(R.id.tv_singer_background);
        play_background = findViewById(R.id.play_background);
        exit_play_song_background = findViewById(R.id.exit_play_song_background);

        mBottomNavigationView.setItemIconTintList(null);
        mNavigationView.setItemIconTintList(null);

        // open side bar
        mMenuSideBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });

        displaySongs();

        replaceFragment(new HomeFragment());

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


        exit_play_song_background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSongBackground.setVisibility(View.GONE);
            }
        });
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[] {
                        Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, 0);
            }
        }

        if (!isLoaded) {
            isLoaded = true;
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
            while (cursor.moveToNext()) {
                long id = cursor.getLong((int) cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                String song = cursor.getString((int) cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                String singer = cursor.getString((int) cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));

                Uri data = Uri.parse(cursor.getString((int) cursor.getColumnIndex(MediaStore.Audio.Media.DATA)));
                Uri thumbnail = ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"),
                        cursor.getLong((int) cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)));

                albumList.add(new Album(id, song, singer, thumbnail.toString(), data.toString()));
//                Log.e(TAG, "onLoadFinished: " + albumList);

            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}