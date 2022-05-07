package com.krissmile31.mockproject.songs.tab.albums;

import static com.krissmile31.mockproject.MainActivity.sAlbumList;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.krissmile31.mockproject.R;
import com.krissmile31.mockproject.interfaces.OnItemClickListener;
import com.krissmile31.mockproject.models.Album;
import com.krissmile31.mockproject.models.Song;
import com.krissmile31.mockproject.songs.tab.albums.adapter.AlbumsAdapter;
import com.krissmile31.mockproject.songs.tab.albums.albumdetails.AlbumDetailsFragment;

import java.util.ArrayList;
import java.util.List;

public class AlbumsFragment extends Fragment {
    private RecyclerView mRclAlbums;
    private AlbumsAdapter mAlbumsAdapter;
    private List<Song> mSongList;

    public AlbumsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_albums, container, false);
        mRclAlbums = view.findViewById(R.id.rcl_albums);

//        mSongList = new ArrayList<>();
//        mSongList.add(new Song(R.drawable.history_albums, "Michael Jackson", 10, "History"));
//        mSongList.add(new Song(R.drawable.thriller_albums, "Michael Jackson", 10, "Thriller"));
//        mSongList.add(new Song(R.drawable.wont_soon_albums, "Maroon 5", 10, "It Wom't Be Soon"));
//        mSongList.add(new Song(R.drawable.iam_yours_albums, "Beyonce", 10, "I Am... Yours"));
//        mSongList.add(new Song(R.drawable.unknown_albums, "Anonymous", 0, "Unknown"));
//        mSongList.add(new Song(R.drawable.unknown_albums_two, "Anonymous", 0, "Unknown"));

        mAlbumsAdapter = new AlbumsAdapter(sAlbumList);
        mRclAlbums.setAdapter(mAlbumsAdapter);
        mRclAlbums.setLayoutManager(new GridLayoutManager(getContext(), 2));
//        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(context, R.dimen.item_offset);
//        mRecyclerView.addItemDecoration(itemDecoration);
//        rcl_albums.addItemDecoration(new Space);

        return view;
    }

//    private OnItemClickListener mListener = new OnItemClickListener() {
//        @Override
//        public void onItemClick(Album album) {
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("album_details", album);
//
////            bundle.putString("name", "Krissmile31");
//            AlbumDetailsFragment albumDetailsFragment = new AlbumDetailsFragment();
//            albumDetailsFragment.setArguments(bundle);
//            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.constraint_fragment_songs, albumDetailsFragment).addToBackStack("album_details").commit();
//
//        }
//    };
}