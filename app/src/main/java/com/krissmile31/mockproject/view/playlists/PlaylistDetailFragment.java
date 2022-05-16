package com.krissmile31.mockproject.view.playlists;

import static com.krissmile31.mockproject.utils.Constants.PLAYLIST_NAME;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.krissmile31.mockproject.MainActivity;
import com.krissmile31.mockproject.R;
import com.krissmile31.mockproject.database.playlist.songplaylist.SongPlaylistManager;
import com.krissmile31.mockproject.interfaces.OnBackPressedListener;
import com.krissmile31.mockproject.models.Song;
import com.krissmile31.mockproject.view.playlists.adapter.SongListItemAdapter;

import java.util.List;

public class PlaylistDetailFragment extends Fragment {
    private SongListItemAdapter mSongListItemAdapter;
    private RecyclerView mRclPlaylistDetail;
    private ImageView mThumbnailPlaylist, mBtnBackPlaylistDetail;
    private TextView mTvPlaylist;
    private OnBackPressedListener mOnBackPressedListener;
    private SongPlaylistManager mSongPlaylistManager;
    private List<Song> mSongList;

    public PlaylistDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_playlist_detail, container, false);
        mRclPlaylistDetail = view.findViewById(R.id.rcl_playlist_detail);
        mThumbnailPlaylist = view.findViewById(R.id.thumbnail_playlist_detail);
        mTvPlaylist = view.findViewById(R.id.playlist_name);
        mBtnBackPlaylistDetail = view.findViewById(R.id.btn_back_playlist_details);

        displaySongs();
        displayPlaylistName();

        mBtnBackPlaylistDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnBackPressedListener = (MainActivity) getActivity();
                mOnBackPressedListener.onBackStackPressed();
            }
        });

        return view;
    }

    private void displaySongs() {
        mSongPlaylistManager = SongPlaylistManager.getInstance(getContext());
        mSongList = mSongPlaylistManager.all();
        mSongListItemAdapter = new SongListItemAdapter(mSongList);
        mRclPlaylistDetail.setAdapter(mSongListItemAdapter);
        mRclPlaylistDetail.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    private void displayPlaylistName() {
        Bundle bundle = this.getArguments();
        String playlistName = (String) bundle.get(PLAYLIST_NAME);
        mTvPlaylist.setText(playlistName);
    }
}