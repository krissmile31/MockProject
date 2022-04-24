package com.krissmile31.mockproject.songs.tab.allsongs;

import static com.krissmile31.mockproject.MainActivity.playSongBackground;
import static com.krissmile31.mockproject.MainActivity.play_background;
import static com.krissmile31.mockproject.MainActivity.thumbnail_play_song;
import static com.krissmile31.mockproject.MainActivity.tv_singer_background;
import static com.krissmile31.mockproject.MainActivity.tv_song_background;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.krissmile31.mockproject.MainActivity;
import com.krissmile31.mockproject.R;
import com.krissmile31.mockproject.interfaces.OnShowMusic;
import com.krissmile31.mockproject.interfaces.OnItemClickListener;
import com.krissmile31.mockproject.model.Album;
import com.krissmile31.mockproject.nowplaying.NowPlayingFragment;
import com.krissmile31.mockproject.songs.tab.allsongs.adapter.AllSongsAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AllSongsFragment extends Fragment {
    private RecyclerView rcl_all_songs;
    private AllSongsAdapter allSongsAdapter;
    public static List<Album> albumList = new ArrayList<>();
    public static OnShowMusic onShowMusic;

    public AllSongsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_songs, container, false);
        rcl_all_songs = view.findViewById(R.id.rcl_all_songs);

//        albumList.add(new Album(R.drawable.billie_jean, "Billie Jean", "Michael Jackson", R.raw.heather));
//        albumList.add(new Album(R.drawable.be_the_girl, "Be the Girl", "Bebe Rexa", R.raw.ifiaintgotu));
//        albumList.add(new Album(R.drawable.countryman, "Countryman", "Daughtry", R.raw.iluvuthree));
//        albumList.add(new Album(R.drawable.feel_lonelyness, "Do you feel lonelyness", "Marc Anthony", R.raw.imissu));
//        albumList.add(new Album(R.drawable.earth_song, "Earth Song", "Michael Jackson", R.raw.likemyfather));
//        albumList.add(new Album(R.drawable.smooth_criminal, "Smooth criminal", "Michael Jackson", R.raw.onlychild));
//        albumList.add(new Album(R.drawable.way_me_feel, "The way you make me feel","Michael Jackson", R.raw.lovethewayulie));
//        albumList.add(new Album(R.drawable.somebody_usedto_know, "Somebody that I used to know", "Gotye", R.raw.pricetag));
//        albumList.add(new Album(R.drawable.wild_thoughts, "Wild Thoughts", "Michael Jackson", R.raw.theonethatgotaway));

        onShowMusic = (OnShowMusic) getContext();
        if (onShowMusic != null) {
            onShowMusic.displaySongs();
        }

//        Bundle bundle = this.getArguments();
//        if (bundle != null) {
////            Album album = (Album) bundle.get("show_music");
////
////            if (album != null)
////                albumList.add(album);
//            albumList = (List<Album>) bundle.get("show_music");
//        }

//        Log.e("TAG", "onCreateView: " + displaySongs);
        Log.e("TAG", "onCreateView: " + albumList);

        allSongsAdapter = new AllSongsAdapter(albumList, listener);
        rcl_all_songs.setAdapter(allSongsAdapter);
        rcl_all_songs.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    OnItemClickListener listener = new OnItemClickListener() {
        @Override
        public void onItemClick(Album album) {

            playSongBackground.setVisibility(View.VISIBLE);
            Picasso.get().load(album.getImage()).placeholder(R.drawable.ic_logo).error(R.drawable.ic_logo).into(thumbnail_play_song);
//            thumbnail_play_song.setImageResource(album.getThumbnail());
            tv_song_background.setText(album.getSong());
            tv_singer_background.setText(album.getSinger());

            play_background.setImageResource(R.drawable.ic_pause_empty);

            playSongBackground.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("play_song_details", album);
                    NowPlayingFragment nowPlayingFragment = new NowPlayingFragment();
                    nowPlayingFragment.setArguments(bundle);

                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.drawLayout, nowPlayingFragment).addToBackStack("now_playing").commit();

                }
            });
        }
    };
}