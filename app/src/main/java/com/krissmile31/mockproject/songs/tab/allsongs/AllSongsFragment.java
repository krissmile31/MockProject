package com.krissmile31.mockproject.songs.tab.allsongs;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.krissmile31.mockproject.R;
import com.krissmile31.mockproject.interfaces.OnItemClickListener;
import com.krissmile31.mockproject.services.PlaySongService;
import com.krissmile31.mockproject.songs.MusicFragment;
import com.krissmile31.mockproject.songs.tab.allsongs.adapter.AllSongsAdapter;
import com.krissmile31.mockproject.models.Album;

import java.util.ArrayList;
import java.util.List;

public class AllSongsFragment extends Fragment {
    private RecyclerView rcl_all_songs;
    private AllSongsAdapter allSongsAdapter;
    private List<Album> albumList;
    public static ConstraintLayout playSongBackground;
    private ImageView thumbnail_play_song, play_background, exit_play_song_background;
    private TextView tv_song_background, tv_singer_background;

    public AllSongsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_songs, container, false);
        rcl_all_songs = view.findViewById(R.id.rcl_all_songs);
        playSongBackground = view.findViewById(R.id.play_song_background);
        thumbnail_play_song = view.findViewById(R.id.thumbnail_play_song);
        tv_song_background = view.findViewById(R.id.tv_song_background);
        tv_singer_background = view.findViewById(R.id.tv_singer_background);
        play_background = view.findViewById(R.id.play_background);
        exit_play_song_background = view.findViewById(R.id.exit_play_song_background);

        albumList = new ArrayList<>();
        albumList.add(new Album(R.drawable.billie_jean, "Billie Jean", "Michael Jackson", R.raw.heather));
        albumList.add(new Album(R.drawable.be_the_girl, "Be the Girl", "Bebe Rexa", R.raw.ifiaintgotu));
        albumList.add(new Album(R.drawable.countryman, "Countryman", "Daughtry", R.raw.iluvuthree));
        albumList.add(new Album(R.drawable.feel_lonelyness, "Do you feel lonelyness", "Marc Anthony", R.raw.imissu));
        albumList.add(new Album(R.drawable.earth_song, "Earth Song", "Michael Jackson", R.raw.likemyfather));
        albumList.add(new Album(R.drawable.smooth_criminal, "Smooth criminal", "Michael Jackson", R.raw.onlychild));
        albumList.add(new Album(R.drawable.way_me_feel, "The way you make me feel","Michael Jackson", R.raw.lovethewayulie));
        albumList.add(new Album(R.drawable.somebody_usedto_know, "Somebody that I used to know", "Gotye", R.raw.pricetag));
        albumList.add(new Album(R.drawable.wild_thoughts, "Wild Thoughts", "Michael Jackson", R.raw.theonethatgotaway));

        allSongsAdapter = new AllSongsAdapter(albumList, listener);
        rcl_all_songs.setAdapter(allSongsAdapter);
        rcl_all_songs.setLayoutManager(new LinearLayoutManager(getContext()));

        exit_play_song_background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSongBackground.setVisibility(View.GONE);
            }
        });

        return view;
    }

    OnItemClickListener listener = new OnItemClickListener() {
        @Override
        public void onItemClick(Album album) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("play_song_details", album);
//            MusicFragment musicFragment = new MusicFragment();
//            musicFragment.setArguments(bundle);
//
//            MusicFragment.playSongBackground.setVisibility(View.VISIBLE);
            thumbnail_play_song.setImageResource(album.getThumbnail());
            tv_song_background.setText(album.getSong());
            tv_singer_background.setText(album.getSinger());

            play_background.setImageResource(R.drawable.ic_pause_empty);


        }
    };
}