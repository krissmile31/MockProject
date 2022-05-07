package com.krissmile31.mockproject.songs.tab.playlists.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.krissmile31.mockproject.R;
import com.krissmile31.mockproject.models.Song;

import java.util.List;

public class MyPlaylistsAdapter extends RecyclerView.Adapter<MyPlaylistsAdapter.MyViewHolder> {
    private List<Song> mSongList;

    public MyPlaylistsAdapter(List<Song> songList) {
        mSongList = songList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.my_playlists_item, parent, false);
        return new MyViewHolder(itemView, context);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(mSongList.get(position));
    }

    @Override
    public int getItemCount() {
        return mSongList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private Context mContext;
        private ImageView mThumbnailPlaylists;
        private TextView mTvSingerPlaylists;

        public MyViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            mContext = context;

            mThumbnailPlaylists = itemView.findViewById(R.id.img_my_playlists);
            mTvSingerPlaylists = itemView.findViewById(R.id.tv_singer_playlists);

        }

        public void bind(Song song) {
            mThumbnailPlaylists.setImageResource(song.getThumbnail());
            mTvSingerPlaylists.setText(song.getSinger());
        }
    }
}
