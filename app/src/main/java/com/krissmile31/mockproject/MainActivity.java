package com.krissmile31.mockproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.krissmile31.mockproject.home.HomeFragment;
import com.krissmile31.mockproject.interfaces.OnBackPressedListener;
import com.krissmile31.mockproject.songs.MusicFragment;
import com.krissmile31.mockproject.settings.SettingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.krissmile31.mockproject.songs.tab.albums.AlbumsFragment;
import com.krissmile31.mockproject.songs.tab.albums.albumdetails.AlbumDetailsFragment;

public class MainActivity extends AppCompatActivity implements OnBackPressedListener {

    private BottomNavigationView mBottomNavigationView;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private ImageView mMenuSideBar;
    public static ConstraintLayout playSongBackground;
    private ImageView thumbnail_play_song, play_background, exit_play_song_background;
    private TextView tv_song_background, tv_singer_background;

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
    }

    public void replaceFragment(Fragment fragment) {
//        FragmentManager fragmentManager = getSupportFragmentManager();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
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
}