package com.krissmile31.mockproject;

import static com.krissmile31.mockproject.utils.Constants.*;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.krissmile31.mockproject.interfaces.OnBackPressedListener;
import com.krissmile31.mockproject.interfaces.OnDataMiniPlayer;
import com.krissmile31.mockproject.interfaces.OnItemSongPlay;
import com.krissmile31.mockproject.interfaces.OnMiniPlayerClickListener;
import com.krissmile31.mockproject.interfaces.OnSeekBarListener;
import com.krissmile31.mockproject.models.Song;
import com.krissmile31.mockproject.services.PlayService;
import com.krissmile31.mockproject.view.nav.home.HomeFragment;
import com.krissmile31.mockproject.view.nav.settings.SettingFragment;
import com.krissmile31.mockproject.view.nav.songs.MusicFragment;
import com.krissmile31.mockproject.view.nowplaying.NowPlayingFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OnBackPressedListener,
        OnSeekBarListener,
        View.OnClickListener, NavigationBarView.OnItemSelectedListener, OnItemSongPlay {

    private BottomNavigationView mBottomNavigationView;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private ImageView mMenuSideBar, mMiniThumbnailPlayer, mMiniBtnPlay,
            mMiniBtnPre, mMiniBtnNext, mMiniExitPlayer;
    private ConstraintLayout mMiniPlayer;
    private TextView mMiniSongPlayer, mMiniSingerPlayer,
            mHeaderNumSongs, mHeaderNumAlbums, getmHeaderNumArtists;
    private Song song;
    private boolean isPlaying;
    private int action;
    public SeekBar seekBarMiniPlayer;
    private Handler mHandler = new Handler();
    private Runnable mRunnable;
    public PlayService service;
    public boolean isConnected;
    private OnMiniPlayerClickListener onMiniPlayerClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        // set null to put gradient color vector
        mBottomNavigationView.setItemIconTintList(null);
        mNavigationView.setItemIconTintList(null);

        mMiniPlayer.setVisibility(View.GONE);

        bindService();

        replaceFragment(new MusicFragment());
        mMenuSideBar.setOnClickListener(this); // open side bar
        mBottomNavigationView.setOnItemSelectedListener(this);

        onNewIntent(getIntent());
    }

    private void init() {
        mBottomNavigationView = findViewById(R.id.bottomNavigation);
        mNavigationView = findViewById(R.id.navigationView);
        mDrawerLayout = findViewById(R.id.drawLayout);
        mMenuSideBar = findViewById(R.id.menu_side_bar);

        mMiniPlayer = findViewById(R.id.mini_player);
        mMiniThumbnailPlayer = findViewById(R.id.thumbnail_play_song);
        mMiniSongPlayer = findViewById(R.id.tv_song_background);
        mMiniSingerPlayer = findViewById(R.id.tv_singer_background);
        mMiniBtnPlay = findViewById(R.id.btn_play);
        mMiniBtnPre = findViewById(R.id.btn_pre);
        mMiniBtnNext = findViewById(R.id.btn_next);
        mMiniExitPlayer = findViewById(R.id.btn_exit);
        seekBarMiniPlayer = findViewById(R.id.seek_bar_mini_player);

        mHeaderNumSongs = findViewById(R.id.header_num_songs);
        mHeaderNumAlbums = findViewById(R.id.header_num_albums);
        getmHeaderNumArtists = findViewById(R.id.header_num_artists);

    }

    private void bindService() {
        Intent intent = new Intent(this, PlayService.class);
//        intent.putExtra(SONG_DETAIL, song);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    @Override
    public void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver,
                new IntentFilter(BROADCAST_RECEIVER));


        // bound service

    }

    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getExtras() == null)
                return;
            song = (Song) intent.getSerializableExtra(SONG_DETAIL);
            isPlaying = intent.getBooleanExtra(IS_PLAYING, false);
            action = intent.getIntExtra(SONG_STATUS, 0);

            Log.e("TAG", "onReceive: " + song);

            setData(song);

        }
    };

    @Override
    protected void onNewIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey(NOTIFICATION)) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(SONG_DETAIL, song);
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
//        if (getFragmentManager().getBackStackEntryCount() > 0 ){
//            getFragmentManager().popBackStack();
//        } else {
//            super.onBackPressed();
//        }
        super.onBackPressed();
    }

//    private void setSideBar() {
//        mHeaderNumSongs.setText(String.valueOf(mSongList.size()));
//        mHeaderNumAlbums.setText(String.valueOf(sAlbumList.size()));
//        getmHeaderNumArtists.setText(String.valueOf(sArtistList.size()));
//    }

//    @Override
//    public void onDisplayData(Song song) {
//        setData(song);
//    }

    public void setData(Song song) {
        mMiniPlayer.setVisibility(View.VISIBLE);
//        mMiniBtnPlay.setImageResource(R.drawable.ic_pause_empty);

        if (isPlaying)
            mMiniBtnPlay.setImageResource(R.drawable.ic_pause_empty);
        else
            mMiniBtnPlay.setImageResource(R.drawable.ic_play_empty);

        Picasso.get().load(song.getThumbnail())
                .placeholder(R.drawable.ic_logo)
                .error(R.drawable.ic_logo)
                .fit()
                .into(mMiniThumbnailPlayer);
        mMiniSongPlayer.setText(song.getSongName());
        mMiniSingerPlayer.setText(song.getSinger());

        mRunnable = new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                if (service.mediaPlayer == null)
                    return;
                seekBarMiniPlayer.setProgress(service.getCurrentPosition());
                seekBarMiniPlayer.setMax(service.getTotalTime());

            }
        };
        mHandler.postDelayed(mRunnable, 0);

        mMiniBtnPlay.setOnClickListener(this);
        mMiniBtnPre.setOnClickListener(this);
        mMiniBtnNext.setOnClickListener(this);
        mMiniExitPlayer.setOnClickListener(this);
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
//                Bundle bundle = new Bundle();
//                bundle.putSerializable(SONG_DETAIL, song);
//                NowPlayingFragment nowPlayingFragment = new NowPlayingFragment();
//                nowPlayingFragment.setArguments(bundle);

                replaceFragment(new NowPlayingFragment());
//                getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.drawLayout, new NowPlayingFragment())
//                        .addToBackStack(NOW_PLAYING).commit();

//            case R.id.btn_play:
//                if (isPlaying)
//                    serviceUtils.pauseMusic();
//                else
//                    serviceUtils.resumeMusic();
//
//                break;
//
//            case R.id.btn_pre:
//                serviceUtils.preMusic(this);
//                break;
//
//            case R.id.btn_next:
//                serviceUtils.nextMusic(this);
//                break;
//
//            case R.id.btn_exit:
//                mMiniPlayer.setVisibility(View.GONE);
//                serviceUtils.releaseMusic();
//                break;

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

    public ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            PlayService.MySongBinder mySongBinder = (PlayService.MySongBinder) iBinder;
            service = mySongBinder.getPlayService();
            isConnected = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            service = null;
            isConnected = false;
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onSongPlay(int position) {
        service.setIndex(position);
    }

    @Override
    public void updateSongList(List<Song> songList) {
        service.setSongList(songList);
    }

    @Override
    public void checkMedia() {
        if (service.mediaPlayer == null) {
            return;
        }
    }

    @Override
    public int currentDuration() {
        return service.getCurrentPosition();
    }

    @Override
    public int totalDuration() {
        return service.getTotalTime();
    }

    @Override
    public String getCurrentDuration() {
        return service.getCurrentDuration();
    }

    @Override
    public String getTotalDuration() {
        return service.getTotalDuration();
    }
}