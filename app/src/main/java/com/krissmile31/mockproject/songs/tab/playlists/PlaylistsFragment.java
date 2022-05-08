package com.krissmile31.mockproject.songs.tab.playlists;

import static com.krissmile31.mockproject.utils.SongUtils.sPlaylist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.krissmile31.mockproject.R;
import com.krissmile31.mockproject.songs.tab.playlists.adapter.MyPlaylistsAdapter;

public class PlaylistsFragment extends Fragment {
    private RecyclerView mRclMyPlaylists;
    private MyPlaylistsAdapter mMyPlaylistsAdapter;
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

        mMyPlaylistsAdapter = new MyPlaylistsAdapter(sPlaylist);
        mRclMyPlaylists.setAdapter(mMyPlaylistsAdapter);
        mRclMyPlaylists.setLayoutManager(new LinearLayoutManager(getContext(),
                RecyclerView.HORIZONTAL, false));

        return view;
    }
}