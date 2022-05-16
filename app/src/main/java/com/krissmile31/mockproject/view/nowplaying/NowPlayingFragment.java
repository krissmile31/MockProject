package com.krissmile31.mockproject.view.nowplaying;

import static com.krissmile31.mockproject.utils.Constants.*;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.bumptech.glide.Glide;
import com.krissmile31.mockproject.MainActivity;
import com.krissmile31.mockproject.R;
import com.krissmile31.mockproject.customview.CircleSeekBar;
import com.krissmile31.mockproject.dialog.PlaylistDialogFragment;
import com.krissmile31.mockproject.interfaces.OnBackPressedListener;
import com.krissmile31.mockproject.interfaces.OnSeekBarListener;
import com.krissmile31.mockproject.models.Song;
import com.krissmile31.mockproject.services.PlayService;
import com.squareup.picasso.Picasso;

public class NowPlayingFragment extends Fragment implements View.OnClickListener {
    private ImageView mBtnBackNowPlaying, mPlayNowPlaying, mPreNowPlaying, mNextNowPlaying,
                 mThumbnailNowPlaying, mEquiliser, mBtnPlaylist;
    private OnBackPressedListener mOnBackPressedListener;
    private TextView mSongNowPlaying, mSingerNowPlaying, mSongDurationPlaying,
            mCurrentDuration, mTotalDuration;
    private SeekBar mSeekBar;
    private CircleSeekBar mCircleSeekBar;
    private Song mSong;
    private boolean mIsPlaying;
    private int action;
    private Handler mHandler = new Handler();
    private Runnable mRunnable;
    private OnSeekBarListener mOnSeekBarListener;

    public NowPlayingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_now_playing, container, false);

        init(view);

//        Bundle bundle = this.getArguments();
//        Song song = (Song) bundle.getSerializable(SONG_DETAIL);
//        Picasso.get().load(song.getThumbnail())
//                .placeholder(R.drawable.ic_logo)
//                .error(R.drawable.ic_logo)
//                .fit()
//                .into(mThumbnailNowPlaying);
//        mSongNowPlaying.setText(song.getSongName());
//        mSingerNowPlaying.setText(song.getSinger());
//        mPlayNowPlaying.setImageResource(R.drawable.ic_pause_song_action);
//        Glide.with(getContext()).load(R.drawable.equiliser_now_playing).into(mEquiliser);

        runSeekBar();

        mPlayNowPlaying.setOnClickListener(this);
        mPreNowPlaying.setOnClickListener(this);
        mNextNowPlaying.setOnClickListener(this);
        mBtnBackNowPlaying.setOnClickListener(this);
        mBtnPlaylist.setOnClickListener(this);

        return view;
    }

    private void init(View view) {
        mBtnBackNowPlaying = view.findViewById(R.id.btn_back_now_playing);
        mThumbnailNowPlaying = view.findViewById(R.id.thumbnail_now_playing);
        mSongNowPlaying = view.findViewById(R.id.tv_song_now_playing);
        mSingerNowPlaying = view.findViewById(R.id.tv_singer_now_playing);
        mPreNowPlaying = view.findViewById(R.id.previous_now_playing);
        mPlayNowPlaying = view.findViewById(R.id.play_now_playing);
        mNextNowPlaying = view.findViewById(R.id.next_now_playing);
        mEquiliser = view.findViewById(R.id.equaliser_now_playing);
        mCircleSeekBar = view.findViewById(R.id.circle_seek_bar);
        mSeekBar = view.findViewById(R.id.seek_bar_now_playing);
        mSongDurationPlaying = view.findViewById(R.id.time_now_playing);
        mCurrentDuration = view.findViewById(R.id.current_duration_now_playing);
        mTotalDuration = view.findViewById(R.id.total_duration_now_playing);
        mBtnPlaylist = view.findViewById(R.id.btn_add_playlist);

        mOnSeekBarListener = (MainActivity) getActivity();
    }

    @Override
    public void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mReceiver,
                new IntentFilter(BROADCAST_RECEIVER));

    }

    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mReceiver);
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            mSong = (Song) intent.getSerializableExtra(SONG_DETAIL);
            mIsPlaying = intent.getBooleanExtra(IS_PLAYING, false);
            action = intent.getIntExtra(SONG_STATUS, 0);

            displaySongNowPlaying(mSong);

            if (mIsPlaying) {
                mPlayNowPlaying.setImageResource(R.drawable.ic_pause_song_action);
                Glide.with(getContext()).load(R.drawable.equiliser_now_playing).into(mEquiliser);
            }

            else  {
                mPlayNowPlaying.setImageResource(R.drawable.ic_play_song_action);
                mEquiliser.setImageResource(R.drawable.equiliser_now_playing);
            }
        }};

    private void displaySongNowPlaying(Song song) {
        Picasso.get().load(song.getThumbnail())
                .placeholder(R.drawable.ic_logo)
                .error(R.drawable.ic_logo)
                .fit()
                .into(mThumbnailNowPlaying);
        mSongNowPlaying.setText(song.getSongName());
        mSingerNowPlaying.setText(song.getSinger());
        mPlayNowPlaying.setImageResource(R.drawable.ic_pause_song_action);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.play_now_playing:
//
//                if (mIsPlaying) {
//                    pauseMusic();
////            sendActionToService(PAUSE);
//                }
//
//                else  {
//                    resumeMusic();
////            sendActionToService(RESUME);
//
//                }
//                break;
//
//            case R.id.btn_back_now_playing:
//                mOnBackPressedListener = (MainActivity) getActivity();
//                mOnBackPressedListener.onBackStackPressed();
//                break;
//
//            case R.id.previous_now_playing:
//                preMusic();
//
//                break;
//
//            case R.id.next_now_playing:
//                nextMusic();
//
//                break;


            case R.id.btn_add_playlist:

                PlaylistDialogFragment playlistDialogFragment = new PlaylistDialogFragment();
                playlistDialogFragment.show(getActivity().getSupportFragmentManager(),
                        playlistDialogFragment.getTag());
//                openDialogPlaylist(view);
        }
    }

//    private void openDialogPlaylist(View view) {
//        ConstraintLayout containPlaylist = view.findViewById(R.id.layout_playlist);
//        ConstraintLayout createPlaylist = view.findViewById(R.id.layout_create_playlist);
//
//        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(),
//                R.style.Dialog_Playlist);
//
//        View bottomSheetView = LayoutInflater.from(getContext())
//                .inflate(R.layout.fragment_playlist_dialog,
//                        containPlaylist);
//
//        bottomSheetView.findViewById(R.id.layout_create_playlist).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openDialogCreatePlaylist();
//            }
//        });
//
//
//        bottomSheetDialog.setContentView(bottomSheetView);
//        bottomSheetDialog.show();
//    }

//    private void openDialogCreatePlaylist() {
//        EditText editText = new EditText(getActivity());
//        editText.setPadding(20, 0, 20, 0);
//
//        new AlertDialog.Builder(getContext())
//                .setTitle("Create Playlist")
//                .setView(editText)
//                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        String playListName = editText.getText().toString();
//                        sSongPlaylist.add(serviceUtils.sCurrentSong());
//                        sPlaylist.add(new Playlist(playListName, serviceUtils.sCurrentSong().getThumbnail(), sSongPlaylist));
//                    }
//                })
//                .setNegativeButton("Cancel", null)
//                .show();
//    }

    public void runSeekBar() {
        mRunnable = new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
//                if (mService.mediaPlayer == null)
//                    return;
//
//                mSongDurationPlaying.setText(serviceUtils.getCurrentDuration()
//                        + " | " + serviceUtils.getTotalDuration());
//
//                mCurrentDuration.setText(serviceUtils.getCurrentDuration());
//                mTotalDuration.setText(serviceUtils.getTotalDuration());
//
//                mCircleSeekBar.setProgress(serviceUtils.getProgress());
//                mSeekBar.setProgress(serviceUtils.getCurrentPosition());
//                mSeekBar.setMax(serviceUtils.getTotalTime());

                mOnSeekBarListener.checkMedia();

                long currentDuration = mOnSeekBarListener.currentDuration();
                long totalDuration = mOnSeekBarListener.totalDuration();

                String setTvCurrentDuration = mOnSeekBarListener.getCurrentDuration();
                String setTvTotalDuration = mOnSeekBarListener.getTotalDuration();

                mSongDurationPlaying.setText(setTvCurrentDuration + " | " + setTvTotalDuration);

                mCurrentDuration.setText(setTvCurrentDuration);
                mTotalDuration.setText(setTvTotalDuration);

                mCircleSeekBar.setProgress((float) currentDuration/totalDuration *100);
                mSeekBar.setProgress((int) currentDuration);
                mSeekBar.setMax((int) totalDuration);

            }
        };
        mHandler.postDelayed(mRunnable, 0);
    }

}