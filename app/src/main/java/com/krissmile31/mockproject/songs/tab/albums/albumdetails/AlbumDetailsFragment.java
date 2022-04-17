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
import com.krissmile31.mockproject.models.Album;
import com.krissmile31.mockproject.songs.tab.albums.albumdetails.adapter.AlbumDetailsAdapter;

import java.util.ArrayList;
import java.util.List;

public class AlbumDetailsFragment extends Fragment {
    private RecyclerView rcl_album_details;
    private List<Album> albumList;
    private AlbumDetailsAdapter albumDetailsAdapter;
    private OnBackPressedListener onBackPressedListener;
    private ImageView btn_back_album, thumbnail_album_detail, thumbnail_album_details;
    private TextView tv_album_details, tv_singer_album_details, tv_period_album_details;

    public AlbumDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_album_details, container, false);
        rcl_album_details = view.findViewById(R.id.rcl_album_details);
        btn_back_album = view.findViewById(R.id.btn_back_album);
        thumbnail_album_detail = view.findViewById(R.id.thumbnail_album_detail);
        thumbnail_album_details = view.findViewById(R.id.thumbnail_album_details);
        tv_album_details = view.findViewById(R.id.tv_album_details);
        tv_singer_album_details = view.findViewById(R.id.tv_singer_details);
        tv_period_album_details = view.findViewById(R.id.tv_period_album_details);

        albumList = new ArrayList<>();
        albumList.add(new Album("Billie Jean"));
        albumList.add(new Album("The way you make me feel"));
        albumList.add(new Album("She is out of my life"));
        albumList.add(new Album("Thriller"));
        albumList.add(new Album("Beat It"));
        albumList.add(new Album("Bad"));
        albumList.add(new Album("Man in the mirror"));
        albumList.add(new Album("Scream"));

        albumDetailsAdapter = new AlbumDetailsAdapter(albumList);
        rcl_album_details.setAdapter(albumDetailsAdapter);
        rcl_album_details.setLayoutManager(new LinearLayoutManager(getContext()));

        Bundle bundle = this.getArguments();
        Album album = (Album) bundle.get("album_details");

//        String naem = bundle.getString("name");
        thumbnail_album_detail.setImageResource(album.getThumbnail());
        thumbnail_album_details.setImageResource(album.getThumbnail());
        tv_album_details.setText(album.getAlbumName());
        tv_singer_album_details.setText(album.getSinger());
//        tv_period_album_details.setText("1996 . " + album.getYear() + " . 64 min");

        btn_back_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressedListener = (MainActivity) getActivity();
                onBackPressedListener.onBackStackPressed();
            }
        });

        return view;
    }
}