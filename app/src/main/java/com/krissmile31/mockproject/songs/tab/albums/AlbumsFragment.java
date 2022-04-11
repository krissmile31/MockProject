package com.krissmile31.mockproject.songs.tab.albums;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.krissmile31.mockproject.R;
import com.krissmile31.mockproject.models.Album;
import com.krissmile31.mockproject.songs.tab.albums.adapter.AlbumsAdapter;

import java.util.ArrayList;
import java.util.List;

public class AlbumsFragment extends Fragment {
    private RecyclerView rcl_albums;
    private AlbumsAdapter albumsAdapter;
    private List<Album> albumList;

    public AlbumsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_albums, container, false);
        rcl_albums = view.findViewById(R.id.rcl_albums);

        albumList = new ArrayList<>();
        albumList.add(new Album(R.drawable.history_albums, "Michael Jackson", 10, "History"));
        albumList.add(new Album(R.drawable.thriller_albums, "Michael Jackson", 10, "Thriller"));
        albumList.add(new Album(R.drawable.wont_soon_albums, "Maroon 5", 10, "It Wom't Be Soon"));
        albumList.add(new Album(R.drawable.iam_yours_albums, "Beyonce", 10, "I Am... Yours"));
        albumList.add(new Album(R.drawable.unknown_albums, "Anonymous", 0, "Unknown"));
        albumList.add(new Album(R.drawable.unknown_albums_two, "Anonymous", 0, "Unknown"));

        albumsAdapter = new AlbumsAdapter(albumList);
        rcl_albums.setAdapter(albumsAdapter);
        rcl_albums.setLayoutManager(new GridLayoutManager(getContext(), 2));
//        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(context, R.dimen.item_offset);
//        mRecyclerView.addItemDecoration(itemDecoration);
//        rcl_albums.addItemDecoration(new Space);

        return view;
    }
}