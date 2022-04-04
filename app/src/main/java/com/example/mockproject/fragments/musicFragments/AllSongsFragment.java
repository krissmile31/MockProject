package com.example.mockproject.fragments.musicFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mockproject.R;
import com.example.mockproject.adapters.tab.AllSongsAdapter;
import com.example.mockproject.models.Album;

import java.util.ArrayList;
import java.util.List;

public class AllSongsFragment extends Fragment {
    private RecyclerView rcl_all_songs;
    private AllSongsAdapter allSongsAdapter;
    private List<Album> albumList;

    public AllSongsFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_songs, container, false);
        rcl_all_songs = view.findViewById(R.id.rcl_all_songs);

        albumList = new ArrayList<>();
        albumList.add(new Album(R.drawable.billie_jean, "Billie Jean", "Michael Jackson"));
        albumList.add(new Album(R.drawable.be_the_girl, "Be the Girl", "Bebe Rexa"));
        albumList.add(new Album(R.drawable.countryman, "Countryman", "Daughtry"));
        albumList.add(new Album(R.drawable.feel_lonelyness, "Do you feel lonelyness", "Marc Anthony"));
        albumList.add(new Album(R.drawable.earth_song, "Earth Song", "Michael Jackson"));
        albumList.add(new Album(R.drawable.smooth_criminal, "Smooth criminal", "Michael Jackson"));
        albumList.add(new Album(R.drawable.way_me_feel, "The way you make me feel","Michael Jackson"));
        albumList.add(new Album(R.drawable.somebody_usedto_know, "Somebody that I used to know", "Gotye"));
        albumList.add(new Album(R.drawable.wild_thoughts, "Wild Thoughts", "Michael Jackson"));

        allSongsAdapter = new AllSongsAdapter(albumList);
        rcl_all_songs.setAdapter(allSongsAdapter);
        rcl_all_songs.setLayoutManager(new LinearLayoutManager(getContext()));


        return view;
    }
}