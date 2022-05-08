package com.krissmile31.mockproject.nowplaying;

import static com.krissmile31.mockproject.services.ServiceUtils.getCurrentSong;
import static com.krissmile31.mockproject.services.ServiceUtils.nextMusic;
import static com.krissmile31.mockproject.services.ServiceUtils.preMusic;
import static com.krissmile31.mockproject.services.ServiceUtils.sCurrentSongIndex;
import static com.krissmile31.mockproject.services.ServiceUtils.setIconPlaying;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.krissmile31.mockproject.MainActivity;
import com.krissmile31.mockproject.R;
import com.krissmile31.mockproject.interfaces.OnBackPressedListener;
import com.krissmile31.mockproject.models.Song;
import com.squareup.picasso.Picasso;

public class NowPlayingFragment extends Fragment implements View.OnClickListener {
    private ImageView mBtnBackNowPlaying;
    private OnBackPressedListener mOnBackPressedListener;
    private ImageView mThumbnailNowPlaying, mPreNowPlaying, mPlayNowPlaying, mNextNowPlaying;
    private TextView mSongNowPlaying, mSingerNowPlaying;

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

        getDataSongPlaying();

        mPlayNowPlaying.setOnClickListener(this);
        mPreNowPlaying.setOnClickListener(this);
        mNextNowPlaying.setOnClickListener(this);
        mBtnBackNowPlaying.setOnClickListener(this);

        return view;
    }

    private void getDataSongPlaying() {
        Bundle bundle = this.getArguments();
        Song song = (Song) bundle.get("play_song_details");
        Log.e("TAG", "onDisplayData: " + bundle );

        displaySongNowPlaying(song);
    }

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
                setIconPlaying(mPlayNowPlaying, R.drawable.ic_play_song_action, R.drawable.ic_pause_song_action);
                break;

            case R.id.btn_back_now_playing:
                mOnBackPressedListener = (MainActivity) getActivity();
                mOnBackPressedListener.onBackStackPressed();
                break;

            case R.id.previous_now_playing:
                preMusic(getContext());
                updateSongNowPlaying();

                break;

            case R.id.next_now_playing:
                nextMusic(getContext());
                updateSongNowPlaying();

                break;
        }
    }

    private void updateSongNowPlaying() {
        Song song = getCurrentSong(sCurrentSongIndex);
        displaySongNowPlaying(song);

    }

}