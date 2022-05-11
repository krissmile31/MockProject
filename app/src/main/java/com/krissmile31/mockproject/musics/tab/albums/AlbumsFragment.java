package com.krissmile31.mockproject.musics.tab.albums;

import static com.krissmile31.mockproject.utils.Constants.*;
import static com.krissmile31.mockproject.utils.SongUtils.*;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.krissmile31.mockproject.R;
import com.krissmile31.mockproject.interfaces.OnAlbumClickListener;
import com.krissmile31.mockproject.models.Album;
import com.krissmile31.mockproject.musics.tab.albums.adapter.AlbumsAdapter;
import com.krissmile31.mockproject.musics.tab.albums.albumdetails.AlbumDetailsFragment;

public class AlbumsFragment extends Fragment {
    private RecyclerView mRclAlbums;
    private AlbumsAdapter mAlbumsAdapter;

    public AlbumsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_albums, container, false);
        mRclAlbums = view.findViewById(R.id.rcl_albums);

        mAlbumsAdapter = new AlbumsAdapter(sAlbumList, mListener);
        mRclAlbums.setAdapter(mAlbumsAdapter);
        mRclAlbums.setLayoutManager(new GridLayoutManager(getContext(), 2));

        return view;
    }

    private OnAlbumClickListener mListener = new OnAlbumClickListener() {
        @Override
        public void onItemClick(Album album) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(ALBUM_DETAIL, album);

//            bundle.putString("name", "Krissmile31");
            AlbumDetailsFragment albumDetailsFragment = new AlbumDetailsFragment();
            albumDetailsFragment.setArguments(bundle);
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.constraint_fragment_songs, albumDetailsFragment)
                    .addToBackStack(ALBUM_DETAIL)
                    .commit();

        }
    };
}