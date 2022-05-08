package com.krissmile31.mockproject.songs.tab.artists.artistdetails;

import static com.krissmile31.mockproject.services.ServiceUtils.getThumbnail;

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
import com.krissmile31.mockproject.models.Artist;
import com.krissmile31.mockproject.models.Song;
import com.krissmile31.mockproject.songs.tab.artists.artistdetails.adapters.TopAlbumsAdapter;
import com.krissmile31.mockproject.songs.tab.artists.artistdetails.adapters.TopSongsAdapter;

import java.util.ArrayList;
import java.util.List;

public class ArtistDetailsFragment extends Fragment {
    private RecyclerView mRclTopAlbums, mRclTopSongs;
    private TopSongsAdapter mTopSongsAdapter;
    private TopAlbumsAdapter mTopAlbumsAdapter;
    private List<Song> mSongList;
    private ImageView mBtnBackArtists, mThumbnailArtistDetail;
    private TextView mTvArtistDetail;
    private OnBackPressedListener mOnBackPressedListener;

    public ArtistDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_artist_details, container, false);
        mRclTopAlbums = view.findViewById(R.id.rcl_top_albums);
        mRclTopSongs = view.findViewById(R.id.rcl_top_songs);
        mBtnBackArtists = view.findViewById(R.id.btn_back_artists);
        mThumbnailArtistDetail = view.findViewById(R.id.thumbnail_artist_detail);
        mTvArtistDetail = view.findViewById(R.id.tv_artist_detail);

        // Top Albums
        mSongList = new ArrayList<>();
        mSongList.add(new Song(R.drawable.fire_dragon_artist_details, "Fire Dragon", 2019));
        mSongList.add(new Song(R.drawable.sound_of_life_artist_details, "Sound of Life", 2018));
        mSongList.add(new Song(R.drawable.giving_heart_artist_details, "Giving Heart", 2017));
        mSongList.add(new Song(R.drawable.dream_of_top_albums, "Dream of", 2016));

        mTopAlbumsAdapter = new TopAlbumsAdapter(mSongList);
        mRclTopAlbums.setAdapter(mTopAlbumsAdapter);
        mRclTopAlbums.setLayoutManager(new LinearLayoutManager(getContext(),
                RecyclerView.HORIZONTAL, false));

        // Top Songs
        mSongList = new ArrayList<>();
        mSongList.add(new Song("Second of You"));
        mSongList.add(new Song("Whisper of Heart"));

        mTopSongsAdapter = new TopSongsAdapter(mSongList);
        mRclTopSongs.setAdapter(mTopSongsAdapter);
        mRclTopSongs.setLayoutManager(new LinearLayoutManager(getContext()));

        Bundle bundle = this.getArguments();
        Artist artist = (Artist) bundle.get("artist_details");
        getThumbnail(artist.getThumbnailArtist(), mThumbnailArtistDetail);
        mTvArtistDetail.setText(artist.getArtistName());

        mBtnBackArtists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnBackPressedListener = (MainActivity) getActivity();
                mOnBackPressedListener.onBackStackPressed();
            }
        });

        return view;
    }
}