package com.krissmile31.mockproject.songs.tab.genres;

import static com.krissmile31.mockproject.MainActivity.sGenreList;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.krissmile31.mockproject.R;
import com.krissmile31.mockproject.models.Song;
import com.krissmile31.mockproject.songs.tab.genres.adapter.GenresAdapter;

import java.util.ArrayList;
import java.util.List;

public class GenresFragment extends Fragment {
    private RecyclerView mRclGenres;
    private GenresAdapter mGenresAdapter;
    private List<Song> mSongList;

    public GenresFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_genres, container, false);
        mRclGenres = view.findViewById(R.id.rcl_genres);

//        mSongList = new ArrayList<>();
//        mSongList.add(new Song(R.drawable.classical_genres, 56, "Classical"));
//        mSongList.add(new Song(R.drawable.pop_genres, 56, "Pop"));
//        mSongList.add(new Song(R.drawable.hiphop_genres, 56, "Hip-Hop"));
//        mSongList.add(new Song(R.drawable.rock_genres, 56, "Rock"));
//        mSongList.add(new Song(R.drawable.rb_genres, 56, "Soul and R&B"));
//        mSongList.add(new Song(R.drawable.instrumental_genres, 56, "Instrumental"));
//        mSongList.add(new Song(R.drawable.jazz_genres, 56, "Jazz"));
//        mSongList.add(new Song(R.drawable.country_music_genres, 56, "Country Music"));

        mGenresAdapter = new GenresAdapter(sGenreList);
        mRclGenres.setAdapter(mGenresAdapter);
        mRclGenres.setLayoutManager(new GridLayoutManager(getContext(), 2));

        return view;
    }
}