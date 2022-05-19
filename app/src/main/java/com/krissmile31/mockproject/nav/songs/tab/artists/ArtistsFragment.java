package com.krissmile31.mockproject.nav.songs.tab.artists;

import static com.krissmile31.mockproject.utils.Constants.*;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.krissmile31.mockproject.MainActivity;
import com.krissmile31.mockproject.R;
import com.krissmile31.mockproject.interfaces.OnArtistClickListener;
import com.krissmile31.mockproject.interfaces.OnListArtistListener;
import com.krissmile31.mockproject.models.Artist;
import com.krissmile31.mockproject.nav.songs.tab.artists.adapter.ArtistAdapter;
import com.krissmile31.mockproject.nav.songs.tab.artists.artistdetails.ArtistDetailsFragment;

import java.util.ArrayList;
import java.util.List;

public class ArtistsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private ArtistAdapter mArtistAdapter;
    private RecyclerView mRclArtists;
    private List<Artist> mArtistList;
    private OnListArtistListener mOnListArtistListener;

    public ArtistsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_artists, container, false);
        init(view);
        displayArtist();

        return view;
    }

    private void init(View view) {
        mRclArtists = view.findViewById(R.id.rcl_artist);
        mArtistList = new ArrayList<>();
        mOnListArtistListener = (MainActivity) getActivity();
    }

    private void displayArtist() {
        initLoader();
        mArtistAdapter = new ArtistAdapter(mArtistList, mListener);
        mRclArtists.setAdapter(mArtistAdapter);
        mRclArtists.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void initLoader() {
        getLoaderManager().initLoader(0, null, this);
    }

    private OnArtistClickListener mListener = new OnArtistClickListener() {
        @Override
        public void onItemClick(Artist artist) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(ARTIST_DETAIL, artist);
            ArtistDetailsFragment artistDetailsFragment = new ArtistDetailsFragment();
            artistDetailsFragment.setArguments(bundle);

            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.constraint_fragment_songs, artistDetailsFragment)
                    .addToBackStack(ARTIST_DETAIL)
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
        return MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI ;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        mArtistList.clear();
        if (cursor != null && cursor.moveToFirst()) {

            do {
                long artistId = cursor.getLong((int) cursor.getColumnIndex(MediaStore.Audio.Artists._ID));
                String artistName = cursor.getString((int) cursor.getColumnIndex(MediaStore.Audio.Artists.ARTIST));
                Uri thumbnail = ContentUris.withAppendedId(Uri.parse(ALBUM_ART), artistId);
                int numSongsAlbum = cursor.getInt((int) cursor.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_ALBUMS));
                int numSongsArtist = cursor.getInt((int) cursor.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_TRACKS));
                mArtistList.add(new Artist(artistId, artistName, thumbnail.toString(), numSongsAlbum, numSongsArtist));
            } while (cursor.moveToNext());
        }
        mOnListArtistListener.getListArtist(mArtistList.size());
        mArtistAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}