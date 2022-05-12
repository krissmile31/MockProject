package com.krissmile31.mockproject.musics.tab.allsongs;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.krissmile31.mockproject.MainActivity;
import com.krissmile31.mockproject.R;
import com.krissmile31.mockproject.interfaces.OnDataMiniPlayer;
import com.krissmile31.mockproject.interfaces.OnSongClickListener;
import com.krissmile31.mockproject.interfaces.OnShowMusic;
import com.krissmile31.mockproject.models.Song;
import com.krissmile31.mockproject.nowplaying.NowPlayingFragment;
import com.krissmile31.mockproject.musics.tab.allsongs.adapter.AllSongsAdapter;
import com.krissmile31.mockproject.utils.ServiceUtils;

import static com.krissmile31.mockproject.utils.SongUtils.*;
import static com.krissmile31.mockproject.utils.Constants.*;

public class AllSongsFragment extends Fragment {
    private RecyclerView mRclAllSongs;
    public static AllSongsAdapter sAllSongsAdapter;
    private OnShowMusic onShowMusic;
    private OnDataMiniPlayer onDataMiniPlayer;
    public static RelativeLayout sEmptySearch;
    private ServiceUtils serviceUtils = new ServiceUtils();

    public AllSongsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_songs, container, false);
        mRclAllSongs = view.findViewById(R.id.rcl_all_songs);
        sEmptySearch = view.findViewById(R.id.empty_search);

        // get data, init loader
        onShowMusic = (OnShowMusic) getContext();
        if (onShowMusic != null) {
            onShowMusic.displaySongs();
        }

        sAllSongsAdapter = new AllSongsAdapter(sSongList, mListener);
        mRclAllSongs.setAdapter(sAllSongsAdapter);
        mRclAllSongs.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    private OnSongClickListener mListener = new OnSongClickListener() {
        @Override
        public void onItemClick(Song song) {
            Log.e(TAG, "onItemClick: " + song.getData());

            // get position
            serviceUtils.sCurrentSongIndex = sSongList.indexOf(song);
            song = serviceUtils.sCurrentSong();

            onDataMiniPlayer = (MainActivity) getActivity();
            if (onDataMiniPlayer != null)
                onDataMiniPlayer.onDisplayData(song);

            openNowPlaying(song);
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

}