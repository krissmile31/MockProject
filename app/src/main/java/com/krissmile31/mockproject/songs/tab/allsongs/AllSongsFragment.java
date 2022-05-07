package com.krissmile31.mockproject.songs.tab.allsongs;

import static com.krissmile31.mockproject.MainActivity.sBtnPreSongBar;
import static com.krissmile31.mockproject.MainActivity.sPlaySongBackground;
import static com.krissmile31.mockproject.MainActivity.sBtnPlayBar;
import static com.krissmile31.mockproject.MainActivity.sThumbnailPlaySong;
import static com.krissmile31.mockproject.MainActivity.sSingerBackground;
import static com.krissmile31.mockproject.MainActivity.sSongBackground;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.krissmile31.mockproject.R;
import com.krissmile31.mockproject.interfaces.OnItemClickListener;
import com.krissmile31.mockproject.interfaces.OnShowMusic;
import com.krissmile31.mockproject.models.Song;
import com.krissmile31.mockproject.nowplaying.NowPlayingFragment;
import com.krissmile31.mockproject.services.PlaySongService;
import com.krissmile31.mockproject.songs.tab.allsongs.adapter.AllSongsAdapter;
import com.squareup.picasso.Picasso;

import static com.krissmile31.mockproject.MainActivity.sSongList;
import static com.krissmile31.mockproject.services.PlaySongService.TAG;
import static com.krissmile31.mockproject.services.PlaySongService.pauseMusic;
import static com.krissmile31.mockproject.services.PlaySongService.resumeMusic;
import static com.krissmile31.mockproject.services.PlaySongService.sSongPlaying;

public class AllSongsFragment extends Fragment {
    private RecyclerView mRclAllSongs;
    public static AllSongsAdapter sAllSongsAdapter;
    public static OnShowMusic sOnShowMusic;

    public AllSongsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_songs, container, false);
        mRclAllSongs = view.findViewById(R.id.rcl_all_songs);

//        albumList.add(new Album(R.drawable.billie_jean, "Billie Jean", "Michael Jackson", R.raw.heather));
//        albumList.add(new Album(R.drawable.be_the_girl, "Be the Girl", "Bebe Rexa", R.raw.ifiaintgotu));
//        albumList.add(new Album(R.drawable.countryman, "Countryman", "Daughtry", R.raw.iluvuthree));
//        albumList.add(new Album(R.drawable.feel_lonelyness, "Do you feel lonelyness", "Marc Anthony", R.raw.imissu));
//        albumList.add(new Album(R.drawable.earth_song, "Earth Song", "Michael Jackson", R.raw.likemyfather));
//        albumList.add(new Album(R.drawable.smooth_criminal, "Smooth criminal", "Michael Jackson", R.raw.onlychild));
//        albumList.add(new Album(R.drawable.way_me_feel, "The way you make me feel","Michael Jackson", R.raw.lovethewayulie));
//        albumList.add(new Album(R.drawable.somebody_usedto_know, "Somebody that I used to know", "Gotye", R.raw.pricetag));
//        albumList.add(new Album(R.drawable.wild_thoughts, "Wild Thoughts", "Michael Jackson", R.raw.theonethatgotaway));


//        Log.e(TAG, "onCreateView: " + albumList );

        sOnShowMusic = (OnShowMusic) getContext();
        if (sOnShowMusic != null) {
            sOnShowMusic.displaySongs();
        }
        sAllSongsAdapter = new AllSongsAdapter(sSongList, mListener);
        mRclAllSongs.setAdapter(sAllSongsAdapter);
        mRclAllSongs.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    OnItemClickListener mListener = new OnItemClickListener() {
        @Override
        public void onItemClick(Song song) {
            Log.e(TAG, "onItemClick: " + song.getData() );

            openNowPlaying(song);

            sBtnPlayBar.setImageResource(R.drawable.ic_pause_empty);

            sPlaySongBackground.setVisibility(View.VISIBLE);
            Picasso.get().load(song.getImage()).placeholder(R.drawable.ic_logo).error(R.drawable.ic_logo).into(sThumbnailPlaySong);
//            thumbnail_play_song.setImageResource(album.getThumbnail());
            sSongBackground.setText(song.getSong());
            sSingerBackground.setText(song.getSinger());

            sBtnPlayBar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (sSongPlaying) {
                        sBtnPlayBar.setImageResource(R.drawable.ic_play_empty);
                        pauseMusic();
                        sSongPlaying = false;
                    }

                    else  {
                        sBtnPlayBar.setImageResource(R.drawable.ic_pause_empty);
                        resumeMusic();
                        sSongPlaying = true;
                    }
                }
            });

            sBtnPreSongBar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            sPlaySongBackground.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openNowPlaying(song);
                }
            });
        }
    };

    private void openNowPlaying(Song song) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("play_song_details", song);
        NowPlayingFragment nowPlayingFragment = new NowPlayingFragment();
        nowPlayingFragment.setArguments(bundle);

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.drawLayout, nowPlayingFragment).addToBackStack("now_playing").commit();

    }
}