package com.krissmile31.mockproject.nav.songs.tab.artists.artistdetails;

import static com.krissmile31.mockproject.utils.Constants.*;

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
import com.krissmile31.mockproject.nav.songs.tab.artists.artistdetails.adapters.TopAlbumsAdapter;
import com.krissmile31.mockproject.nav.songs.tab.artists.artistdetails.adapters.TopSongsAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ArtistDetailsFragment extends Fragment implements View.OnClickListener {
    private RecyclerView mRclTopAlbums, mRclTopSongs;
    private TopSongsAdapter mTopSongsAdapter;
    private TopAlbumsAdapter mTopAlbumsAdapter;
    private List<Song> mSongList;
    private ImageView mBtnBackArtists, mThumbnailArtistDetail;
    private TextView mTvArtist, mTvArtistDetail, mNumFollowers, mNumListeners;
    private OnBackPressedListener mOnBackPressedListener;

    public ArtistDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_artist_details, container, false);
        init(view);
        displayArtistDetails();
        displayTopAlbums();
        displayTopSongs();

        mBtnBackArtists.setOnClickListener(this);

        return view;
    }

    private void init(View view) {
        mRclTopAlbums = view.findViewById(R.id.rcl_top_albums);
        mRclTopSongs = view.findViewById(R.id.rcl_top_songs);
        mBtnBackArtists = view.findViewById(R.id.btn_back_artists);
        mThumbnailArtistDetail = view.findViewById(R.id.thumbnail_artist_detail);
        mTvArtist = view.findViewById(R.id.tv_artist);
        mTvArtistDetail = view.findViewById(R.id.tv_artist_details);
        mNumFollowers = view.findViewById(R.id.quantity_followers);
        mNumListeners = view.findViewById(R.id.quantity_listeners);

        mTvArtistDetail.setText("Pop rock, Funk pop, Heavy Mental");
        mNumFollowers.setText("4,367");
        mNumListeners.setText("128,980");

        mOnBackPressedListener = (MainActivity) getActivity();
    }

    private void displayArtistDetails() {
        Bundle bundle = this.getArguments();
        Artist artist = (Artist) bundle.get(ARTIST_DETAIL);

        Picasso.get().load(artist.getThumbnailArtist())
                .placeholder(R.drawable.ic_logo)
                .error(R.drawable.ic_logo)
                .fit()
                .into(mThumbnailArtistDetail);
        mTvArtist.setText(artist.getArtistName());
    }

    private void displayTopSongs() {
        // Top Songs
        mSongList = new ArrayList<>();
        mSongList.add(new Song("Second of You"));
        mSongList.add(new Song("Whisper of Heart"));

        mTopSongsAdapter = new TopSongsAdapter(mSongList);
        mRclTopSongs.setAdapter(mTopSongsAdapter);
        mRclTopSongs.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void displayTopAlbums() {
        // Top Albums
        mSongList = new ArrayList<>();
        mSongList.add(new Song(R.drawable.muzic, "Fire Dragon", 2019));
        mSongList.add(new Song(R.drawable.muzic, "Sound of Life", 2018));
        mSongList.add(new Song(R.drawable.muzic, "Giving Heart", 2017));
        mSongList.add(new Song(R.drawable.muzic, "Dream of", 2016));

        mTopAlbumsAdapter = new TopAlbumsAdapter(mSongList);
        mRclTopAlbums.setAdapter(mTopAlbumsAdapter);
        mRclTopAlbums.setLayoutManager(new LinearLayoutManager(getContext(),
                RecyclerView.HORIZONTAL, false));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back_artists:
                mOnBackPressedListener.onBackStackPressed();
        }
    }
}