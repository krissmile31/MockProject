package com.krissmile31.mockproject.songs.tab.artists;

import static com.krissmile31.mockproject.MainActivity.sArtistList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.krissmile31.mockproject.R;
import com.krissmile31.mockproject.interfaces.OnArtistClickListener;
import com.krissmile31.mockproject.interfaces.OnSongClickListener;
import com.krissmile31.mockproject.models.Artist;
import com.krissmile31.mockproject.models.Song;
import com.krissmile31.mockproject.songs.tab.artists.adapter.ArtistAdapter;
import com.krissmile31.mockproject.songs.tab.artists.artistdetails.ArtistDetailsFragment;

public class ArtistsFragment extends Fragment {
    private ArtistAdapter mArtistAdapter;
    private RecyclerView mRclArtists;

    public ArtistsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_artists, container, false);
        mRclArtists = view.findViewById(R.id.rcl_artist);

        mArtistAdapter = new ArtistAdapter(sArtistList, mListener);
        mRclArtists.setAdapter(mArtistAdapter);
        mRclArtists.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    private OnArtistClickListener mListener = new OnArtistClickListener() {
        @Override
        public void onItemClick(Artist artist) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("artist_details", artist);
            ArtistDetailsFragment artistDetailsFragment = new ArtistDetailsFragment();
            artistDetailsFragment.setArguments(bundle);

            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.constraint_fragment_songs, artistDetailsFragment)
                    .addToBackStack("artist_details")
                    .commit();
        }
    };
}