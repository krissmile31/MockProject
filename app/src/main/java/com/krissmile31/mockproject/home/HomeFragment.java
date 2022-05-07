package com.krissmile31.mockproject.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.krissmile31.mockproject.R;
import com.krissmile31.mockproject.home.adapters.HotRecommendedAdapter;
import com.krissmile31.mockproject.home.adapters.PlaylistAdapter;
import com.krissmile31.mockproject.home.adapters.RecentlyPlayedAdapter;
import com.krissmile31.mockproject.models.Song;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView mRclHotRecommended, mRclPlaylist, mRclRecentlyPlayed;
    private List<Song> mSongList;
    private HotRecommendedAdapter mHotRecommendedAdapter;
    private PlaylistAdapter mPlaylistAdapter;
    private RecentlyPlayedAdapter mRecentlyPlayedAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mRclHotRecommended = view.findViewById(R.id.rcl_hotRecommended);
        mRclPlaylist = view.findViewById(R.id.rcl_playlist);
        mRclRecentlyPlayed = view.findViewById(R.id.rcl_recentlyPlayed);

        // Hot Recommended preview
        mSongList = new ArrayList<>();
        mSongList.add(new Song(R.drawable.sound_sky, "Sound of Sky", "Dilon Bruce"));
        mSongList.add(new Song(R.drawable.girl_on_fire, "Girl on Fire", "Alecia Keys"));

        mHotRecommendedAdapter = new HotRecommendedAdapter(mSongList);
        mRclHotRecommended.setAdapter(mHotRecommendedAdapter);
        mRclHotRecommended.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        // Playlist preview
        mSongList = new ArrayList<>();
        mSongList.add(new Song(R.drawable.classic_playlist, "Classic Playlist", "Piano Guys"));
        mSongList.add(new Song(R.drawable.summer_playlist, "Summer Playlist", "Dilon Bruce"));
        mSongList.add(new Song(R.drawable.pop_music, "Pop Music", "Michael Jackson"));
        mPlaylistAdapter = new PlaylistAdapter(mSongList);
        mRclPlaylist.setAdapter(mPlaylistAdapter);
        mRclPlaylist.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        // Recently Played preview
        mSongList = new ArrayList<>();
        mSongList.add(new Song("Billie Jean", "Michael jackson"));
        mSongList.add(new Song("Earth Song", "Michael Jackson"));
        mSongList.add(new Song("Mirror", "Justin Timerlake"));
        mSongList.add(new Song("Remember the Time", "Michael Jackson"));
        mRecentlyPlayedAdapter = new RecentlyPlayedAdapter(mSongList);
        mRclRecentlyPlayed.setAdapter(mRecentlyPlayedAdapter);
        mRclRecentlyPlayed.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }


}