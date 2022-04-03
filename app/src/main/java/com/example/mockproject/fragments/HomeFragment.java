package com.example.mockproject.fragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.mockproject.R;
import com.example.mockproject.adapters.HotRecommendedAdapter;
import com.example.mockproject.adapters.PlaylistAdapter;
import com.example.mockproject.adapters.RecentlyPlayedAdapter;
import com.example.mockproject.models.Album;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView rcl_hotRecommended, rcl_playlist, rcl_recentlyPlayed;
    private List<Album> albumList;
    private HotRecommendedAdapter hotRecommendedAdapter;
    private PlaylistAdapter playlistAdapter;
    private RecentlyPlayedAdapter recentlyPlayedAdapter;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private ImageView mMenuSideBar;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        rcl_hotRecommended = view.findViewById(R.id.rcl_hotRecommended);
        rcl_playlist = view.findViewById(R.id.rcl_playlist);
        rcl_recentlyPlayed = view.findViewById(R.id.rcl_recentlyPlayed);
        mNavigationView = view.findViewById(R.id.navigationView);
        mDrawLayout = view.findViewById(R.id.drawLayout);
        mMenuSideBar = view.findViewById(R.id.menu_side_bar);

        // Hot Recommended preview
        albumList = new ArrayList<>();
        albumList.add(new Album(R.drawable.sound_sky, "Sound of Sky", "Dilon Bruce"));
        albumList.add(new Album(R.drawable.girl_on_fire, "Girl on Fire", "Alecia Keys"));

        hotRecommendedAdapter = new HotRecommendedAdapter(albumList);
        rcl_hotRecommended.setAdapter(hotRecommendedAdapter);
        rcl_hotRecommended.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        // Playlist preview
        albumList = new ArrayList<>();
        albumList.add(new Album(R.drawable.classic_playlist, "Classic Playlist", "Piano Guys"));
        albumList.add(new Album(R.drawable.summer_playlist, "Summer Playlist", "Dilon Bruce"));
        albumList.add(new Album(R.drawable.pop_music, "Pop Music", "Michael Jackson"));
        playlistAdapter = new PlaylistAdapter(albumList);
        rcl_playlist.setAdapter(playlistAdapter);
        rcl_playlist.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        // Recently Played preview
        albumList = new ArrayList<>();
        albumList.add(new Album("Billie Jean", "Michael jackson"));
        albumList.add(new Album("Earth Song", "Michael Jackson"));
        albumList.add(new Album("Mirror", "Justin Timerlake"));
        albumList.add(new Album("Remember the Time", "Michael Jackson"));
        recentlyPlayedAdapter = new RecentlyPlayedAdapter(albumList);
        rcl_recentlyPlayed.setAdapter(recentlyPlayedAdapter);
        rcl_recentlyPlayed.setLayoutManager(new LinearLayoutManager(getContext()));

        mActionBarDrawerToggle = new ActionBarDrawerToggle((Activity) getContext(), mDrawLayout, R.string.advanced, R.string.advanced);
        mDrawLayout.addDrawerListener(mActionBarDrawerToggle);
        mActionBarDrawerToggle.syncState();
        mNavigationView.setItemIconTintList(null);
        return view;
    }
}