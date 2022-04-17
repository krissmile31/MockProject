package com.krissmile31.mockproject.songs.tab.artists;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.krissmile31.mockproject.R;
import com.krissmile31.mockproject.interfaces.OnItemClickListener;
import com.krissmile31.mockproject.models.Album;
import com.krissmile31.mockproject.songs.tab.artists.adapter.ArtistAdapter;
import com.krissmile31.mockproject.songs.tab.artists.artistdetails.ArtistDetailsFragment;

import java.util.ArrayList;
import java.util.List;

public class ArtistsFragment extends Fragment {
    private ArtistAdapter artistAdapter;
    private List<Album> albumList;
    private RecyclerView rcl_artists;

    public ArtistsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_artists, container, false);
        rcl_artists = view.findViewById(R.id.rcl_artist);

        albumList = new ArrayList<>();
        albumList.add(new Album(R.drawable.beyonce_artists, "Beyonce", 4, 38));
        albumList.add(new Album(R.drawable.bebe_rexha_artists, "Bebe Rexha", 2, 17));
        albumList.add(new Album(R.drawable.maroon_artists, "Maroon 5", 5, 46));
        albumList.add(new Album(R.drawable.michael_jackson_artists, "Michael Jackson", 10, 112));
        albumList.add(new Album(R.drawable.queens_artists, "Queens", 3, 32));
        albumList.add(new Album(R.drawable.yani_artists, "Yani", 1, 13));

        artistAdapter = new ArtistAdapter(albumList, listener);
        rcl_artists.setAdapter(artistAdapter);
        rcl_artists.setLayoutManager(new LinearLayoutManager(getContext()));


        return view;
    }

    private OnItemClickListener listener = new OnItemClickListener() {
        @Override
        public void onItemClick(Album album) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("artist_details", album);
            ArtistDetailsFragment artistDetailsFragment = new ArtistDetailsFragment();
            artistDetailsFragment.setArguments(bundle);

            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.constraint_fragment_songs, artistDetailsFragment).addToBackStack("artist_details").commit();
        }
    };
}