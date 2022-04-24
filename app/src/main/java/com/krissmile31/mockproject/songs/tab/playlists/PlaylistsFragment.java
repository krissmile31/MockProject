package com.krissmile31.mockproject.songs.tab.playlists;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.krissmile31.mockproject.R;
import com.krissmile31.mockproject.songs.tab.playlists.adapter.MyPlaylistsAdapter;
import com.krissmile31.mockproject.model.Album;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class PlaylistsFragment extends Fragment {
    private RecyclerView rcl_my_playlists;
    private MyPlaylistsAdapter myPlaylistsAdapter;
    private List<Album> albumList;
    private FloatingActionButton floatingActionButton;

    public PlaylistsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_playlists, container, false);
        rcl_my_playlists = view.findViewById(R.id.rcl_myPlaylists);
        floatingActionButton = view.findViewById(R.id.float_button);

        floatingActionButton.setImageTintList(null);

        albumList = new ArrayList<>();
        albumList.add(new Album(R.drawable.queen_colle, "Queens Collection"));
        albumList.add(new Album(R.drawable.rihanna_coll, "Rihanna Collection"));
        albumList.add(new Album(R.drawable.mj_coll, "MJ Collection"));
        albumList.add(new Album(R.drawable.classical_coll, "Classic Collection"));

        myPlaylistsAdapter = new MyPlaylistsAdapter(albumList);
        rcl_my_playlists.setAdapter(myPlaylistsAdapter);
        rcl_my_playlists.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));


        return view;
    }
}