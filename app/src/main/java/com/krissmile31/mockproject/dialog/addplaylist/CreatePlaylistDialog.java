package com.krissmile31.mockproject.dialog.addplaylist;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.krissmile31.mockproject.MainActivity;
import com.krissmile31.mockproject.R;
import com.krissmile31.mockproject.database.playlist.PlaylistManager;
import com.krissmile31.mockproject.database.playlist.songplaylist.SongPlaylistManager;
import com.krissmile31.mockproject.models.Playlist;
import com.krissmile31.mockproject.models.Song;
import com.krissmile31.mockproject.services.PlayService;

import java.util.ArrayList;
import java.util.List;

public class CreatePlaylistDialog extends DialogFragment implements View.OnClickListener {
    private EditText mPlaylistName;
    private TextView mBtnOkCreat, mBtnCancelCreate;
    private PlaylistManager mPlaylistManager;
    private SongPlaylistManager mSongManager;
    private PlayService mService;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog =  new Dialog(getContext(), R.style.Dialog_Playlist);
        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.create_playlist_dialog, null);

        init(view);
        dialog.setContentView(view);

        mBtnOkCreat.setOnClickListener(this);
        mBtnCancelCreate.setOnClickListener(this);

        return dialog;
    }

    private void init(View view) {
        mPlaylistName = view.findViewById(R.id.edt_create_playlist);
        mBtnOkCreat = view.findViewById(R.id.btn_oke_create_playlist);
        mBtnCancelCreate = view.findViewById(R.id.btn_cancle_create_playlist);

        mPlaylistManager = PlaylistManager.getInstance(getContext());
        mSongManager = SongPlaylistManager.getInstance(getContext());
        mService = ((MainActivity) requireActivity()).getService();

    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            InsetDrawable inset = new InsetDrawable(new ColorDrawable(Color.TRANSPARENT), 50);
            dialog.getWindow().setBackgroundDrawable(inset);
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_oke_create_playlist:
                String playlistName = mPlaylistName.getText().toString();
                Playlist playlist = new Playlist(playlistName);
                mPlaylistManager.add(playlist);

                if (mService.getCurrentSong() != null) {
                    Song song = mService.getCurrentSong();
                    mSongManager.add(song);
                }

                dismiss();
                break;

            case R.id.btn_cancle_create_playlist:
                dismiss();
                break;
        }
    }
}
