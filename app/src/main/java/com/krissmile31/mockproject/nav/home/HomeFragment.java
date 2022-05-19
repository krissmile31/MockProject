package com.krissmile31.mockproject.nav.home;

import static com.krissmile31.mockproject.utils.Constants.PLAYLIST_NAME;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.krissmile31.mockproject.R;
import com.krissmile31.mockproject.database.playlist.PlaylistManager;
import com.krissmile31.mockproject.database.recentsong.SongManager;
import com.krissmile31.mockproject.nav.home.adapters.HotRecommendedAdapter;
import com.krissmile31.mockproject.nav.home.adapters.PlaylistAdapter;
import com.krissmile31.mockproject.nav.home.adapters.RecentlyPlayedAdapter;
import com.krissmile31.mockproject.interfaces.OnPlaylistClickListener;
import com.krissmile31.mockproject.models.Playlist;
import com.krissmile31.mockproject.models.Song;
import com.krissmile31.mockproject.playlists.PlaylistDetailFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView mRclHotRecommended, mRclPlaylist, mRclRecentlyPlayed;
    private List<Song> mSongList, mRecentSongList;
    private List<Playlist> mPlaylist;
    private HotRecommendedAdapter mHotRecommendedAdapter;
    private PlaylistAdapter mPlaylistAdapter;
    private RecentlyPlayedAdapter mRecentlyPlayedAdapter;
    private SongManager mSongManager;
    private PlaylistManager mPlaylistManager;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        init(view);
        displayHotRecommended();
        displayPlaylists();
        displayRecentlyPlayed();
        return view;
    }

    private void init(View view) {
        mRclHotRecommended = view.findViewById(R.id.rcl_hotRecommended);
        mRclPlaylist = view.findViewById(R.id.rcl_playlist);
        mRclRecentlyPlayed = view.findViewById(R.id.rcl_recentlyPlayed);

        mPlaylistManager = PlaylistManager.getInstance(getContext());
        mSongManager = SongManager.getInstance(getContext());
        mSongList = new ArrayList<>();
        mRecentSongList = new ArrayList<>(1);
        mPlaylist = new ArrayList<>();

    }

    private void displayHotRecommended() {
        // Hot Recommended preview
        mSongList.add(new Song(R.drawable.sound_sky, "Sound of Sky", "Dilon Bruce"));
        mSongList.add(new Song(R.drawable.girl_on_fire, "Girl on Fire", "Alecia Keys"));

        mHotRecommendedAdapter = new HotRecommendedAdapter(mSongList);
        mRclHotRecommended.setAdapter(mHotRecommendedAdapter);
        mRclHotRecommended.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
    }

    // playlist
    private void displayPlaylists() {
        mPlaylist = mPlaylistManager.all();
        mPlaylistAdapter = new PlaylistAdapter(mPlaylist, mOnPlaylistClickListener);
        mRclPlaylist.setAdapter(mPlaylistAdapter);
        mRclPlaylist.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
    }

    // recently played
    private void displayRecentlyPlayed() {
        mRecentSongList = mSongManager.all();
        arrangeRecentSong();

        mRecentlyPlayedAdapter = new RecentlyPlayedAdapter(mRecentSongList);
        mRclRecentlyPlayed.setAdapter(mRecentlyPlayedAdapter);
        mRclRecentlyPlayed.setHasFixedSize(true);
        mRclRecentlyPlayed.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    private void arrangeRecentSong() {

        Collections.reverse(mRecentSongList);
        for (int i = 0; i < mRecentSongList.size(); i++) {
            if (mRecentSongList.size() > 6) {  // max size
                mRecentSongList.remove(mRecentSongList.size() - 1);
            }
            for (int j = i + 1; j < mRecentSongList.size(); j++) {
                if (mRecentSongList.get(j).equals(mRecentSongList.get(i))) { // duplication
                    mRecentSongList.remove(mRecentSongList.get(j));
                }
            }
        }

//        Collections.reverse(songList);
//        for (int i = 0; i < songList.size(); i++) {
//            if (songList.size() > 6) {  // max size
//                songList.remove(songList.size() - 1);
//            }
//            for (int j = i + 1; j < songList.size(); j++) {
//                if (songList.get(j).equals(songList.get(i))) { // duplication
//                    songList.remove(songList.get(j));
//                }
//            }
//        }
    }

    // display playlist name when open detail
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
}