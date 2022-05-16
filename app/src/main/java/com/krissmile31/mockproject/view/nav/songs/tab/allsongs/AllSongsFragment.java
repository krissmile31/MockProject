package com.krissmile31.mockproject.view.nav.songs.tab.allsongs;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.krissmile31.mockproject.R;
import com.krissmile31.mockproject.database.recentsong.SongManager;
import com.krissmile31.mockproject.interfaces.OnDataMiniPlayer;
import com.krissmile31.mockproject.interfaces.OnItemSongPlay;
import com.krissmile31.mockproject.interfaces.OnSongClickListener;
import com.krissmile31.mockproject.models.Song;
import com.krissmile31.mockproject.services.PlayService;
import com.krissmile31.mockproject.view.nowplaying.NowPlayingFragment;
import com.krissmile31.mockproject.view.nav.songs.tab.allsongs.adapter.AllSongsAdapter;

import static com.krissmile31.mockproject.utils.Constants.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AllSongsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private RecyclerView mRclAllSongs;
    private AllSongsAdapter mAllSongsAdapter;
    private OnDataMiniPlayer onDataMiniPlayer;
    private RelativeLayout mEmptySearch;
    private List<Song> mSongList = new ArrayList<>();
    OnItemSongPlay mOnItemSongPlay;
    boolean isSongListUpdated;

    public AllSongsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_songs, container, false);
        init(view);

        displayAllSongs();

        return view;
    }

    private void init(View view) {
        mRclAllSongs = view.findViewById(R.id.rcl_all_songs);
        mEmptySearch = view.findViewById(R.id.empty_search);

        mOnItemSongPlay = (OnItemSongPlay) getActivity();

    }

    private void displayAllSongs() {
        initLoader();
        Log.e("TAG", "displayAllSongs: " + mSongList);

        mAllSongsAdapter = new AllSongsAdapter(mSongList, mListener);
        mRclAllSongs.setAdapter(mAllSongsAdapter);
        mRclAllSongs.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void initLoader() {
        getLoaderManager().initLoader(0, null, this);
    }


    private OnSongClickListener mListener = new OnSongClickListener() {
        @Override
        public void onItemClick(Song song) {

            // get position
//            serviceUtils.sCurrentSongIndex = sSongList.indexOf(song);

//            mService.mCurrentSongIndex = sSongList.indexOf(song);

//            onDataMiniPlayer = (MainActivity) getActivity();
//            if (onDataMiniPlayer != null)
//                onDataMiniPlayer.onDisplayData(song);

//            sRecentSongList.add(song);
//            SongManager songManager = SongManager.getInstance(getContext());
//            songManager.add(song);
//            Log.e("TAG", "onItemClick: " + song.getSongName());


            Intent intent = new Intent(getContext(), PlayService.class);
            intent.putExtra(SONG_DETAIL, song);
//            intent.putExtra("index", mSongList.indexOf(song));
            isSongListUpdated = true;

            openNowPlaying(song);

            mOnItemSongPlay.onSongPlay(mSongList.indexOf(song));
            mOnItemSongPlay.updateSongList(mSongList);
            getContext().startService(intent);

        }
    };

    private void openNowPlaying(Song song) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(SONG_DETAIL, song);
        NowPlayingFragment nowPlayingFragment = new NowPlayingFragment();
        nowPlayingFragment.setArguments(bundle);

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.drawLayout, nowPlayingFragment)
                .addToBackStack(NOW_PLAYING)
                .commit();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("TAG", "onStart: " + mSongList);

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("TAG", "onResume: " + mSongList);

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("TAG", "onPause: " + mSongList);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        if (id == 0) {
//            return new CursorLoader()
            return new CursorLoader(getContext(), getUri(),
                    null,
                    null,
                    null,
                    null);
        }
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {

        Log.e("TAG", "onLoadFinished: " + mSongList);

        if (cursor != null && cursor.moveToFirst()) {

            do {

                // All Songs
                long id = cursor.getLong((int) cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                String song = cursor.getString((int) cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                String singer = cursor.getString((int) cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                Uri data = ContentUris.withAppendedId(getUri(), id);
                Uri path = Uri.parse(cursor.getString((int) cursor.getColumnIndex(MediaStore.Audio.Media.DATA)));
                Uri thumbnail = ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"),
                        cursor.getLong((int) cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)));

                boolean checkTime = cursor.getInt((int) cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)) > 3000;

                if (path.toString().contains(".mp3") && checkTime) {
                    mSongList.add(new Song(id, song, singer, thumbnail.toString(), data.toString()));
                }
            } while (cursor.moveToNext());
        }

        Collections.sort(mSongList, new Comparator<Song>() {
            @Override
            public int compare(Song first, Song second) {
                return first.getSongName().compareTo(second.getSongName());
            }
        });

        mAllSongsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }

    private Uri getUri() {
        return MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    }
}