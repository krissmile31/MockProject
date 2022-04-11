package com.krissmile31.mockproject.songs.tab.genres;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.krissmile31.mockproject.R;
import com.krissmile31.mockproject.models.Album;
import com.krissmile31.mockproject.songs.tab.genres.adapter.GenresAdapter;

import java.util.ArrayList;
import java.util.List;

public class GenresFragment extends Fragment {
    private RecyclerView rcl_genres;
    private GenresAdapter genresAdapter;
    private List<Album> albumList;

    public GenresFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_genres, container, false);
        rcl_genres = view.findViewById(R.id.rcl_genres);

        albumList = new ArrayList<>();
        albumList.add(new Album(R.drawable.classical_genres, 56, "Classical"));
        albumList.add(new Album(R.drawable.pop_genres, 56, "Pop"));
        albumList.add(new Album(R.drawable.hiphop_genres, 56, "Hip-Hop"));
        albumList.add(new Album(R.drawable.rock_genres, 56, "Rock"));
        albumList.add(new Album(R.drawable.rb_genres, 56, "Soul and R&B"));
        albumList.add(new Album(R.drawable.instrumental_genres, 56, "Instrumental"));
        albumList.add(new Album(R.drawable.jazz_genres, 56, "Jazz"));
        albumList.add(new Album(R.drawable.country_music_genres, 56, "Country Music"));

        genresAdapter = new GenresAdapter(albumList);
        rcl_genres.setAdapter(genresAdapter);
        rcl_genres.setLayoutManager(new GridLayoutManager(getContext(), 2));

        return view;
    }
}