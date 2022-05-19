package com.krissmile31.mockproject.nav.songs.tab.playlists;

import static com.krissmile31.mockproject.utils.Constants.PLAYLIST_NAME;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.krissmile31.mockproject.R;
import com.krissmile31.mockproject.database.playlist.PlaylistManager;
import com.krissmile31.mockproject.nav.home.adapters.PlaylistAdapter;
import com.krissmile31.mockproject.interfaces.OnPlaylistClickListener;
import com.krissmile31.mockproject.models.Playlist;
import com.krissmile31.mockproject.playlists.PlaylistDetailFragment;

import java.util.ArrayList;
import java.util.List;

public class PlaylistsFragment extends Fragment {
    private RecyclerView mRclMyPlaylists;
    private PlaylistAdapter mPlaylistsAdapter;
    private FloatingActionButton mFloatingActionButton;
    private List<Playlist> mPlaylist;
    private PlaylistManager mPlaylistManager;

    public PlaylistsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_playlists, container, false);
        init(view);
        displayPlaylist();

        return view;
    }

    private void init(View view) {
        mRclMyPlaylists = view.findViewById(R.id.rcl_myPlaylists);
        mFloatingActionButton = view.findViewById(R.id.float_button);
        mPlaylist = new ArrayList<>();
        mPlaylistManager = PlaylistManager.getInstance(getContext());
        mFloatingActionButton.setImageTintList(null);
    }

    // display playlists below
    private void displayPlaylist() {
        mPlaylist = mPlaylistManager.all();
        mPlaylistsAdapter = new PlaylistAdapter(mPlaylist, mOnPlaylistClickListener);
        mRclMyPlaylists.setAdapter(mPlaylistsAdapter);
        mRclMyPlaylists.setLayoutManager(new LinearLayoutManager(getContext(),
                RecyclerView.HORIZONTAL, false));
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
}