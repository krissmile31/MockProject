package com.krissmile31.mockproject.nav.songs.tab.albums.albumdetails.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.krissmile31.mockproject.R;
import com.krissmile31.mockproject.models.Song;

import java.util.List;

public class AlbumDetailsAdapter extends RecyclerView.Adapter<AlbumDetailsAdapter.MyViewHolder> {
    private List<Song> mSongList;

    public AlbumDetailsAdapter(List<Song> songList) {
        mSongList = songList;
    }

    @NonNull
    @Override
    public AlbumDetailsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                               int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.album_details_item,
                parent, false);
        return new MyViewHolder(itemView, context);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumDetailsAdapter.MyViewHolder holder, int position) {
        holder.bind(mSongList.get(position));
    }

    @Override
    public int getItemCount() {
        return mSongList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private Context mContext;
        private TextView mSongAlbumDetail, mTimeAlbumDetail;

        public MyViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            mContext = context;

            mSongAlbumDetail = itemView.findViewById(R.id.song_album_details);
            mTimeAlbumDetail = itemView.findViewById(R.id.time_album_details);
        }

        public void bind(Song song) {
            mSongAlbumDetail.setText(song.getSongName());
            mTimeAlbumDetail.setText("3:56");
        }
    }
}
