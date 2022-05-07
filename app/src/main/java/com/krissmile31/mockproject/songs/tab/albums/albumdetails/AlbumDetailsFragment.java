package com.krissmile31.mockproject.songs.tab.albums.albumdetails;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.krissmile31.mockproject.MainActivity;
import com.krissmile31.mockproject.R;
import com.krissmile31.mockproject.interfaces.OnBackPressedListener;
import com.krissmile31.mockproject.models.Song;
import com.krissmile31.mockproject.songs.tab.albums.albumdetails.adapter.AlbumDetailsAdapter;

import java.util.ArrayList;
import java.util.List;

public class AlbumDetailsFragment extends Fragment {
    private RecyclerView mRclAlbumDetails;
    private List<Song> mSongList;
    private AlbumDetailsAdapter mAlbumDetailsAdapter;
    private OnBackPressedListener mOnBackPressedListener;
    private ImageView mBtnBackAlbum, mThumbnailAlbum, mThumbnailAlbumDetail;
    private TextView mTvAlbumDetail, mSingerAlbum, mYearAlbum;

    public AlbumDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_album_details, container, false);
        mRclAlbumDetails = view.findViewById(R.id.rcl_album_details);
        mBtnBackAlbum = view.findViewById(R.id.btn_back_album);
        mThumbnailAlbum = view.findViewById(R.id.thumbnail_album_detail);
        mThumbnailAlbumDetail = view.findViewById(R.id.thumbnail_album_details);
        mTvAlbumDetail = view.findViewById(R.id.tv_album_details);
        mSingerAlbum = view.findViewById(R.id.tv_singer_details);
        mYearAlbum = view.findViewById(R.id.tv_period_album_details);

        mSongList = new ArrayList<>();
        mSongList.add(new Song("Billie Jean"));
        mSongList.add(new Song("The way you make me feel"));
        mSongList.add(new Song("She is out of my life"));
        mSongList.add(new Song("Thriller"));
        mSongList.add(new Song("Beat It"));
        mSongList.add(new Song("Bad"));
        mSongList.add(new Song("Man in the mirror"));
        mSongList.add(new Song("Scream"));

        mAlbumDetailsAdapter = new AlbumDetailsAdapter(mSongList);
        mRclAlbumDetails.setAdapter(mAlbumDetailsAdapter);
        mRclAlbumDetails.setLayoutManager(new LinearLayoutManager(getContext()));

        Bundle bundle = this.getArguments();
        Song song = (Song) bundle.get("album_details");

//        String naem = bundle.getString("name");
        mThumbnailAlbum.setImageResource(song.getThumbnail());
        mThumbnailAlbumDetail.setImageResource(song.getThumbnail());
        mTvAlbumDetail.setText(song.getAlbumName());
        mSingerAlbum.setText(song.getSinger());
//        tv_period_album_details.setText("1996 . " + album.getYear() + " . 64 min");

        mBtnBackAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnBackPressedListener = (MainActivity) getActivity();
                mOnBackPressedListener.onBackStackPressed();
            }
        });

        return view;
    }
}