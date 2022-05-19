package com.krissmile31.mockproject.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.krissmile31.mockproject.MainActivity;
import com.krissmile31.mockproject.R;
import com.krissmile31.mockproject.database.playlist.PlaylistManager;
import com.krissmile31.mockproject.database.playlist.songplaylist.SongPlaylistManager;
import com.krissmile31.mockproject.dialog.adapter.PlaylistItemAdapter;
import com.krissmile31.mockproject.dialog.addplaylist.CreatePlaylistDialog;
import com.krissmile31.mockproject.interfaces.OnPlaylistItemClickListener;
import com.krissmile31.mockproject.models.Playlist;
import com.krissmile31.mockproject.models.Song;
import com.krissmile31.mockproject.services.PlayService;

import java.util.ArrayList;
import java.util.List;

public class PlaylistDialogFragment extends BottomSheetDialogFragment implements View.OnClickListener {
    private ConstraintLayout mCreatePlaylist;
    private RecyclerView mRclPlaylistItems;
    private PlaylistItemAdapter mPlaylistItemAdapter;
    private List<Playlist> mPlaylists;
    private PlaylistManager mPlaylistManager;
    private PlayService mService;
    private SongPlaylistManager mSongManager;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(),
                R.style.Dialog_Playlist);

        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.fragment_playlist_dialog, null);

        init(view);
        displayPlaylistItem();
        bottomSheetDialog.setContentView(view);
        mCreatePlaylist.setOnClickListener(this);

        return bottomSheetDialog;
    }

    private void init(View view) {
        mCreatePlaylist = view.findViewById(R.id.create_playlist);
        mRclPlaylistItems = view.findViewById(R.id.rcl_playlist_items);

        mPlaylists = new ArrayList<>();

        mPlaylistManager = PlaylistManager.getInstance(getContext());
        mSongManager = SongPlaylistManager.getInstance(getContext());
        mService = ((MainActivity) requireActivity()).getService();

    }

    private void displayPlaylistItem() {
        mPlaylists = mPlaylistManager.all();
        mPlaylistItemAdapter = new PlaylistItemAdapter(mPlaylists, mListener);
        mRclPlaylistItems.setAdapter(mPlaylistItemAdapter);
        mRclPlaylistItems.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.create_playlist:
                CreatePlaylistDialog createPlaylistDialog = new CreatePlaylistDialog();
                createPlaylistDialog.show(getActivity().getSupportFragmentManager(),
                        createPlaylistDialog.getTag());
        }
    }

    // click on available playlist
    private OnPlaylistItemClickListener mListener = new OnPlaylistItemClickListener() {
        @Override
        public void OnItemClick(Playlist playlist) {
            if (mService.getCurrentSong() != null) {
                Song song = mService.getCurrentSong();
                mSongManager.add(song);
            }
            dismiss();
        }
    };
}
