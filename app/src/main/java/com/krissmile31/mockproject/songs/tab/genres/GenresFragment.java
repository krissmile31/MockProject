package com.krissmile31.mockproject.songs.tab.genres;

import static com.krissmile31.mockproject.MainActivity.sGenreList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.krissmile31.mockproject.R;
import com.krissmile31.mockproject.songs.tab.genres.adapter.GenresAdapter;

public class GenresFragment extends Fragment {
    private RecyclerView mRclGenres;
    private GenresAdapter mGenresAdapter;

    public GenresFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_genres, container, false);
        mRclGenres = view.findViewById(R.id.rcl_genres);

        mGenresAdapter = new GenresAdapter(sGenreList);
        mRclGenres.setAdapter(mGenresAdapter);
        mRclGenres.setLayoutManager(new GridLayoutManager(getContext(), 2));

        return view;
    }
}