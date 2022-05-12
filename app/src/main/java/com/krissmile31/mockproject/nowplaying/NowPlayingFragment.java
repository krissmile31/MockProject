package com.krissmile31.mockproject.nowplaying;

import static com.krissmile31.mockproject.utils.Constants.IS_PLAYING;
import static com.krissmile31.mockproject.utils.Constants.SONG_DETAIL;
import static com.krissmile31.mockproject.utils.Constants.SONG_STATUS;
import static com.krissmile31.mockproject.utils.ServiceUtils.*;
import static com.krissmile31.mockproject.utils.SongUtils.*;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.krissmile31.mockproject.MainActivity;
import com.krissmile31.mockproject.R;
import com.krissmile31.mockproject.customview.CircleSeekBar;
import com.krissmile31.mockproject.interfaces.OnBackPressedListener;
import com.krissmile31.mockproject.models.Song;
import com.krissmile31.mockproject.utils.ServiceUtils;
import com.squareup.picasso.Picasso;

public class NowPlayingFragment extends Fragment implements View.OnClickListener {
    private ImageView mBtnBackNowPlaying;
    private OnBackPressedListener mOnBackPressedListener;
    private ImageView mThumbnailNowPlaying;
    private ImageView mPlayNowPlaying;
    private ImageView mPreNowPlaying;
    private ImageView mNextNowPlaying;
    private ImageView mEquiliser;
    private TextView mSongNowPlaying;
    private TextView mSingerNowPlaying;
    private TextView mSongDurationPlaying, mCurrentDuration, mTotalDuration;
    private SeekBar seekBar;
    private CircleSeekBar mCircleSeekBar;
    private Song song;
    private boolean isPlaying;
    private int action;
    private ServiceUtils serviceUtils = new ServiceUtils();
    private Handler mHandler = new Handler();
    private Runnable mRunnable;

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
        mPlayNowPlaying = view.findViewById(R.id.play_now_playing);
        mNextNowPlaying = view.findViewById(R.id.next_now_playing);
        mEquiliser = view.findViewById(R.id.equaliser_now_playing);
        mCircleSeekBar = view.findViewById(R.id.circle_seek_bar);
        seekBar = view.findViewById(R.id.seek_bar_now_playing);
        mSongDurationPlaying = view.findViewById(R.id.time_now_playing);
        mCurrentDuration = view.findViewById(R.id.current_duration_now_playing);
        mTotalDuration = view.findViewById(R.id.total_duration_now_playing);

//        getDataSongPlaying();

        displaySongNowPlaying(serviceUtils.sCurrentSong());
        Glide.with(getContext()).load(R.drawable.equiliser_now_playing).into(mEquiliser);

        runSeekBar();

        mPlayNowPlaying.setOnClickListener(this);
        mPreNowPlaying.setOnClickListener(this);
        mNextNowPlaying.setOnClickListener(this);
        mBtnBackNowPlaying.setOnClickListener(this);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        registerActionMusicPlayer(getContext(), mReceiver);

    }

    @Override
    public void onStop() {
        super.onStop();
        unregisterActionMusicPlayer(getContext(), mReceiver);
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            song = (Song) intent.getSerializableExtra(SONG_DETAIL);
            isPlaying = intent.getBooleanExtra(IS_PLAYING, false);
            action = intent.getIntExtra(SONG_STATUS, 0);

            displaySongNowPlaying(song);

            if (isPlaying) {
                mPlayNowPlaying.setImageResource(R.drawable.ic_pause_song_action);
                Glide.with(getContext()).load(R.drawable.equiliser_now_playing).into(mEquiliser);
            }

            else  {
                mPlayNowPlaying.setImageResource(R.drawable.ic_play_song_action);
                mEquiliser.setImageResource(R.drawable.equiliser_now_playing);
        }
    }};

    private void displaySongNowPlaying(Song song) {

        Picasso.get().load(song.getImage()).placeholder(R.drawable.ic_logo).into(mThumbnailNowPlaying);
        mSongNowPlaying.setText(song.getSong());
        mSingerNowPlaying.setText(song.getSinger());
        mPlayNowPlaying.setImageResource(R.drawable.ic_pause_song_action);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.play_now_playing:

                if (isPlaying) {
                    serviceUtils.pauseMusic();
//            sendActionToService(PAUSE);
                }

                else  {
                    serviceUtils.resumeMusic();
//            sendActionToService(RESUME);

                }
                break;

            case R.id.btn_back_now_playing:
                mOnBackPressedListener = (MainActivity) getActivity();
                mOnBackPressedListener.onBackStackPressed();
                break;

            case R.id.previous_now_playing:
                serviceUtils.preMusic(getContext());

                break;

            case R.id.next_now_playing:
                serviceUtils.nextMusic(getContext());

                break;
        }
    }

    public void runSeekBar() {
        mRunnable = new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                if (sMediaPlayer == null)
                    return;

                mSongDurationPlaying.setText(serviceUtils.getCurrentDuration()
                        + " | " + serviceUtils.getTotalDuration());

                mCurrentDuration.setText(serviceUtils.getCurrentDuration());
                mTotalDuration.setText(serviceUtils.getTotalDuration());

                mCircleSeekBar.setProgress(serviceUtils.getProgress());
                seekBar.setProgress(serviceUtils.getCurrentPosition());
                seekBar.setMax(serviceUtils.getTotalTime());

                mHandler.postDelayed(this, 0);
            }
        };
        mRunnable.run();
    }

}