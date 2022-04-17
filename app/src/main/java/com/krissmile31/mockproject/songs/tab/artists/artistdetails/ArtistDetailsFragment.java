package com.krissmile31.mockproject.songs.tab.artists.artistdetails;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.krissmile31.mockproject.MainActivity;
import com.krissmile31.mockproject.R;
import com.krissmile31.mockproject.interfaces.OnBackPressedListener;
import com.krissmile31.mockproject.models.Album;
import com.krissmile31.mockproject.songs.tab.albums.albumdetails.AlbumDetailsFragment;
import com.krissmile31.mockproject.songs.tab.artists.artistdetails.adapters.TopAlbumsAdapter;
import com.krissmile31.mockproject.songs.tab.artists.artistdetails.adapters.TopSongsAdapter;

import java.util.ArrayList;
import java.util.List;

public class ArtistDetailsFragment extends Fragment {
    private RecyclerView rcl_top_albums, rcl_top_songs;
    private TopSongsAdapter topSongsAdapter;
    private TopAlbumsAdapter topAlbumsAdapter;
    private List<Album> albumList;
    private ImageView btn_back_artists, thumbnail_artist_detail;
    private TextView tv_artist_detail;
    private OnBackPressedListener onBackPressedListener;

    public ArtistDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_artist_details, container, false);
        rcl_top_albums = view.findViewById(R.id.rcl_top_albums);
        rcl_top_songs = view.findViewById(R.id.rcl_top_songs);
        btn_back_artists = view.findViewById(R.id.btn_back_artists);
        thumbnail_artist_detail = view.findViewById(R.id.thumbnail_artist_detail);
        tv_artist_detail = view.findViewById(R.id.tv_artist_detail);

        // Top Albums
        albumList = new ArrayList<>();
        albumList.add(new Album(R.drawable.fire_dragon_artist_details, "Fire Dragon", 2019));
        albumList.add(new Album(R.drawable.sound_of_life_artist_details, "Sound of Life", 2018));
        albumList.add(new Album(R.drawable.giving_heart_artist_details, "Giving Heart", 2017));
        albumList.add(new Album(R.drawable.dream_of_top_albums, "Dream of", 2016));

        topAlbumsAdapter = new TopAlbumsAdapter(albumList);
        rcl_top_albums.setAdapter(topAlbumsAdapter);
        rcl_top_albums.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));

        // Top Songs
        albumList = new ArrayList<>();
        albumList.add(new Album("Second of You"));
        albumList.add(new Album("Whisper of Heart"));

        topSongsAdapter = new TopSongsAdapter(albumList);
        rcl_top_songs.setAdapter(topSongsAdapter);
        rcl_top_songs.setLayoutManager(new LinearLayoutManager(getContext()));

        Bundle bundle = this.getArguments();
        Album album = (Album) bundle.get("artist_details");
        thumbnail_artist_detail.setImageResource(album.getThumbnail());
        tv_artist_detail.setText(album.getSinger());


        btn_back_artists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressedListener = (MainActivity) getActivity();
                onBackPressedListener.onBackStackPressed();
            }
        });

        return view;
    }
}