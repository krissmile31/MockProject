package com.krissmile31.mockproject.nowplaying;

import static com.krissmile31.mockproject.MainActivity.sMenuSideBar;
import static com.krissmile31.mockproject.services.BoundService.ServiceConnection.serviceConnection;
import static com.krissmile31.mockproject.utils.Constants.*;
import static com.krissmile31.mockproject.utils.ServiceUtils.*;
import static com.krissmile31.mockproject.utils.SongUtils.*;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.krissmile31.mockproject.MainActivity;
import com.krissmile31.mockproject.R;
import com.krissmile31.mockproject.interfaces.OnBackPressedListener;
import com.krissmile31.mockproject.models.Song;
import com.krissmile31.mockproject.services.PlaySongService;
import com.squareup.picasso.Picasso;

public class NowPlayingFragment extends Fragment implements View.OnClickListener {
    private ImageView mBtnBackNowPlaying;
    private OnBackPressedListener mOnBackPressedListener;
    private static ImageView mThumbnailNowPlaying;
    private ImageView mPreNowPlaying;
    private ImageView mNextNowPlaying;
    private ImageView mEquiliser;
    private static TextView mSongNowPlaying;
    private static TextView mSingerNowPlaying;

    public NowPlayingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_now_playing, container, false);

        mBtnBackNowPlaying = view.findViewById(R.id.btn_back_now_playing);
        mThumbnailNowPlaying = view.findViewById(R.id.thumbnail_now_playing);
        mSongNowPlaying = view.findViewById(R.id.tv_song_now_playing);
        mSingerNowPlaying = view.findViewById(R.id.tv_singer_now_playing);
        mPreNowPlaying = view.findViewById(R.id.previous_now_playing);
        sPlayNowPlaying = view.findViewById(R.id.play_now_playing);
        mNextNowPlaying = view.findViewById(R.id.next_now_playing);
        mEquiliser = view.findViewById(R.id.equaliser_now_playing);

        getDataSongPlaying();

        Glide.with(getContext()).load(R.drawable.equiliser_now_playing).into(mEquiliser);

        registerActionMusicPlayer(getContext());
        sMenuSideBar.setVisibility(View.GONE);
        sPlayNowPlaying.setOnClickListener(this);
        mPreNowPlaying.setOnClickListener(this);
        mNextNowPlaying.setOnClickListener(this);
        mBtnBackNowPlaying.setOnClickListener(this);

        return view;
    }

    public static void getDataSongPlaying() {
//        Bundle bundle = this.getArguments();
//        Song song = (Song) bundle.get(SONG_DETAIL);
//        Log.e("TAG", "onDisplayData: " + bundle );

//        Song song = getCurrentSong(sCurrentSongIndex);
        displaySongNowPlaying(sCurrentSong());
    }

    public static void displaySongNowPlaying(Song song) {
        Picasso.get().load(song.getImage()).placeholder(R.drawable.ic_logo).into(mThumbnailNowPlaying);
        mSongNowPlaying.setText(song.getSong());
        mSingerNowPlaying.setText(song.getSinger());
        sPlayNowPlaying.setImageResource(R.drawable.ic_pause_song_action);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.play_now_playing:
                setIconPlaying(sPlayNowPlaying, R.drawable.ic_play_song_action, R.drawable.ic_pause_song_action, getContext());
                setIconStatusAll();
                checkExistForeground(getContext());

                if (sSongPlaying)
                    Glide.with(getContext()).load(R.drawable.equiliser_now_playing).into(mEquiliser);
                else
                    mEquiliser.setImageResource(R.drawable.equiliser_now_playing);

                break;

            case R.id.btn_back_now_playing:
                mOnBackPressedListener = (MainActivity) getActivity();
                mOnBackPressedListener.onBackStackPressed();
                break;

            case R.id.previous_now_playing:
                preMusic(getContext());
                updateSongNowPlaying();
                checkExistForeground(getContext());

                break;

            case R.id.next_now_playing:
                nextMusic(getContext());
                updateSongNowPlaying();
                checkExistForeground(getContext());

                break;
        }
    }

    private void updateSongNowPlaying() {
        Song song = getCurrentSong(sCurrentSongIndex);
        displaySongNowPlaying(song);

    }

}