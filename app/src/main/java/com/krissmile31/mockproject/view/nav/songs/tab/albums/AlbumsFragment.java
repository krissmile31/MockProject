package com.krissmile31.mockproject.view.nav.songs.tab.albums;

import static com.krissmile31.mockproject.utils.Constants.*;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.krissmile31.mockproject.R;
import com.krissmile31.mockproject.interfaces.OnAlbumClickListener;
import com.krissmile31.mockproject.models.Album;
import com.krissmile31.mockproject.view.nav.songs.tab.albums.adapter.AlbumsAdapter;
import com.krissmile31.mockproject.view.nav.songs.tab.albums.albumdetails.AlbumDetailsFragment;

import java.util.ArrayList;
import java.util.List;

public class AlbumsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private RecyclerView mRclAlbums;
    private AlbumsAdapter mAlbumsAdapter;
    private List<Album> mAlbumList = new ArrayList<>();

    public AlbumsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_albums, container, false);
        mRclAlbums = view.findViewById(R.id.rcl_albums);

        displayAlbum();

        return view;
    }

    private void displayAlbum() {
        initLoader();
        mAlbumsAdapter = new AlbumsAdapter(mAlbumList, mListener);
        mRclAlbums.setAdapter(mAlbumsAdapter);
        mRclAlbums.setLayoutManager(new GridLayoutManager(getContext(), 2));
    }

    private void initLoader() {
        getLoaderManager().initLoader(0, null, this);
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

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        if (id == 0) {
            return new CursorLoader(getContext(), getUri(),
                    null,
                    null,
                    null,
                    null);
        }
        return null;
    }

    private Uri getUri() {
        return MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI ;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {

        if (cursor != null && cursor.moveToFirst()) {

            do {
                long albumId = cursor.getLong((int) cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ID));
                String albumName = cursor.getString((int) cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM));
                String albumSinger = cursor.getString((int) cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST));
                Uri thumbnail = ContentUris.withAppendedId(Uri.parse(ALBUM_ART), albumId);
                int numSongsAlbum = cursor.getInt((int) cursor.getColumnIndex(MediaStore.Audio.Albums.NUMBER_OF_SONGS));
//                Log.e("TAG", "onLoadFinished: " + numSongsAlbum);
                mAlbumList.add(new Album(albumId, thumbnail.toString(), albumName, albumSinger, numSongsAlbum));
            } while (cursor.moveToNext());
        }
        mAlbumsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}