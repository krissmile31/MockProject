package com.krissmile31.mockproject.nowplaying;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.krissmile31.mockproject.MainActivity;
import com.krissmile31.mockproject.R;
import com.krissmile31.mockproject.interfaces.OnBackPressedListener;
import com.krissmile31.mockproject.models.Album;
import com.krissmile31.mockproject.services.PlaySongService;

import me.tankery.lib.circularseekbar.CircularSeekBar;

public class NowPlayingFragment extends Fragment {
    private CircularSeekBar circularSeekBar;
    private ImageView btn_back_now_playing;
    private OnBackPressedListener onBackPressedListener;
    private ImageView thumbnail_now_playing, previous_now_playing, play_now_playing, next_now_playing;
    private TextView tv_song_now_playing, tv_singer_now_playing;

    public NowPlayingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_now_playing, container, false);
//        circularSeekBar = view.findViewById(R.id.cicular_seek_bar);
//        circularSeekBar.setMax(100);
//        circularSeekBar.setProgress(0);

        btn_back_now_playing = view.findViewById(R.id.btn_back_now_playing);
        thumbnail_now_playing = view.findViewById(R.id.thumbnail_now_playing);
        tv_song_now_playing = view.findViewById(R.id.tv_song_now_playing);
        tv_singer_now_playing = view.findViewById(R.id.tv_singer_now_playing);
        previous_now_playing = view.findViewById(R.id.previous_now_playing);
        play_now_playing = view.findViewById(R.id.play_now_playing);
        next_now_playing = view.findViewById(R.id.next_now_playing);

        Bundle bundle = this.getArguments();
        Album album = (Album) bundle.get("play_song_details");
        thumbnail_now_playing.setImageResource(album.getThumbnail());
        tv_song_now_playing.setText(album.getSong());
        tv_singer_now_playing.setText(album.getSinger());

        btn_back_now_playing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressedListener = (MainActivity) getActivity();
                onBackPressedListener.onBackStackPressed();
            }
        });

        if (PlaySongService.songPlaying) {
            play_now_playing.setImageResource(R.drawable.ic_pause_song_action);
        }
        else
            play_now_playing.setImageResource(R.drawable.ic_play_song_action);

        return view;
    }
}