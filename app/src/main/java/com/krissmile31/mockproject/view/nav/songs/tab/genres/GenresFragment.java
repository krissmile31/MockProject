package com.krissmile31.mockproject.view.nav.songs.tab.genres;

import static com.krissmile31.mockproject.utils.Constants.ALBUM_ART;

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
import com.krissmile31.mockproject.models.Artist;
import com.krissmile31.mockproject.models.Genre;
import com.krissmile31.mockproject.view.nav.songs.tab.genres.adapter.GenresAdapter;

import java.util.ArrayList;
import java.util.List;

public class GenresFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private RecyclerView mRclGenres;
    private GenresAdapter mGenresAdapter;
    private List<Genre> mGenreList = new ArrayList<>();

    public GenresFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_genres, container, false);
        mRclGenres = view.findViewById(R.id.rcl_genres);

        displayGenre();

        return view;
    }

    private void displayGenre() {
        initLoader();
        mGenresAdapter = new GenresAdapter(mGenreList);
        mRclGenres.setAdapter(mGenresAdapter);
        mRclGenres.setLayoutManager(new GridLayoutManager(getContext(), 2));
    }

    private void initLoader() {
        getLoaderManager().initLoader(0, null, this);
    }

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
        return MediaStore.Audio.Genres.EXTERNAL_CONTENT_URI ;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        mGenreList.clear();
        if (cursor != null && cursor.moveToFirst()) {

            do {
                long genreId = cursor.getLong((int) cursor.getColumnIndex(MediaStore.Audio.Genres._ID));
                String genreName = cursor.getString((int) cursor.getColumnIndex(MediaStore.Audio.Genres.NAME));
                Uri thumbnailGenre = ContentUris.withAppendedId(Uri.parse(ALBUM_ART), genreId);
//                int numSongsGenre = cursor.getInt((int) cursor.getColumnIndex(MediaStore.Audio.Genres._COUNT));
//                Log.e("TAG", "onLoadFinished: " + (String)cursor.getString((int) cursor.getColumnIndex(MediaStore.Audio.Genres._COUNT)));
                mGenreList.add(new Genre(genreId, genreName, thumbnailGenre.toString()));
            } while (cursor.moveToNext());
        }
        mGenresAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}