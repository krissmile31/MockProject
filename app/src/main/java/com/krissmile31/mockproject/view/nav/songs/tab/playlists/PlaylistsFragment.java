package com.krissmile31.mockproject.view.nav.songs.tab.playlists;

import static com.krissmile31.mockproject.utils.Constants.ALBUM_ART;
import static com.krissmile31.mockproject.utils.Constants.PLAYLIST_NAME;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.krissmile31.mockproject.R;
import com.krissmile31.mockproject.view.nav.home.adapters.PlaylistAdapter;
import com.krissmile31.mockproject.interfaces.OnPlaylistClickListener;
import com.krissmile31.mockproject.models.Playlist;
import com.krissmile31.mockproject.view.playlists.PlaylistDetailFragment;

import java.util.ArrayList;
import java.util.List;

public class PlaylistsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private RecyclerView mRclMyPlaylists;
    private PlaylistAdapter mPlaylistsAdapter;
    private FloatingActionButton mFloatingActionButton;
    private List<Playlist> mPlaylist = new ArrayList<>();

    public PlaylistsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_playlists, container, false);
        mRclMyPlaylists = view.findViewById(R.id.rcl_myPlaylists);
        mFloatingActionButton = view.findViewById(R.id.float_button);

        mFloatingActionButton.setImageTintList(null);

        displayPlaylist();

        return view;
    }

    private void displayPlaylist() {
//        PlaylistManager playlistManager = PlaylistManager.getInstance(getContext());
//        mPlaylist = playlistManager.all();
        mPlaylist.add(new Playlist("Rain"));
        initLoader();
        Log.e("TAG", "displayPlaylist: " + mPlaylist );
        mPlaylistsAdapter = new PlaylistAdapter(mPlaylist, mOnPlaylistClickListener);
        mRclMyPlaylists.setAdapter(mPlaylistsAdapter);
        mRclMyPlaylists.setLayoutManager(new LinearLayoutManager(getContext(),
                RecyclerView.HORIZONTAL, false));
    }

    private void initLoader() {
        getLoaderManager().initLoader(0, null, this);
    }

    private OnPlaylistClickListener mOnPlaylistClickListener = new OnPlaylistClickListener() {
        @Override
        public void onItemClick(Playlist playlist) {
            Bundle bundle = new Bundle();
            bundle.putString(PLAYLIST_NAME, playlist.getPlaylistName());
            PlaylistDetailFragment playlistDetailFragment = new PlaylistDetailFragment();
            playlistDetailFragment.setArguments(bundle);

            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.drawLayout, playlistDetailFragment)
                    .addToBackStack(null)
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
        return MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI ;
    }
    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        Log.e("TAG", "onLoadFinished: " );

        if (cursor != null && cursor.moveToFirst()) {

            do {
                Log.e("TAG", "onLoadFinished: " );

                long playlistId = cursor.getLong((int) cursor.getColumnIndex(MediaStore.Audio.Playlists._ID));
                String playlistName = cursor.getString((int) cursor.getColumnIndex(MediaStore.Audio.Playlists.NAME));
                Uri thumbnail = ContentUris.withAppendedId(Uri.parse(ALBUM_ART), playlistId);
//                int numSongsGenre = cursor.getInt((int) cursor.getColumnIndex(MediaStore.Audio.Genres._COUNT));
//                Log.e("TAG", "onLoadFinished: " + (String)cursor.getString((int) cursor.getColumnIndex(MediaStore.Audio.Genres._COUNT)));
                mPlaylist.add(new Playlist(playlistId, playlistName, thumbnail.toString()));
                Log.e("TAG", "onLoadFinished: " + playlistName);
            } while (cursor.moveToNext());
        }
        mPlaylistsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}