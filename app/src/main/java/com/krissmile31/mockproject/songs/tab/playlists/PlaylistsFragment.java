package com.krissmile31.mockproject.songs.tab.playlists;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.krissmile31.mockproject.R;
import com.krissmile31.mockproject.models.Song;
import com.krissmile31.mockproject.songs.tab.playlists.adapter.MyPlaylistsAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class PlaylistsFragment extends Fragment {
    private RecyclerView mRclMyPlaylists;
    private MyPlaylistsAdapter mMyPlaylistsAdapter;
    private List<Song> mSongList;
    private FloatingActionButton mFloatingActionButton;

    public PlaylistsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_playlists, container, false);
        mRclMyPlaylists = view.findViewById(R.id.rcl_myPlaylists);
        mFloatingActionButton = view.findViewById(R.id.float_button);

        mFloatingActionButton.setImageTintList(null);

        mSongList = new ArrayList<>();
        mSongList.add(new Song(R.drawable.queen_colle, "Queens Collection"));
        mSongList.add(new Song(R.drawable.rihanna_coll, "Rihanna Collection"));
        mSongList.add(new Song(R.drawable.mj_coll, "MJ Collection"));
        mSongList.add(new Song(R.drawable.classical_coll, "Classic Collection"));

        mMyPlaylistsAdapter = new MyPlaylistsAdapter(mSongList);
        mRclMyPlaylists.setAdapter(mMyPlaylistsAdapter);
        mRclMyPlaylists.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));


        return view;
    }
}